package estiveaqui.appgestor.relatorio;

import haroldo.util.sql.ConexaoDB;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;
import estiveaqui.RegraDeNegocioException;
import estiveaqui.Versao;
import estiveaqui.appgestor.DadosAppGestorInVO;
import estiveaqui.appgestor.RegraNegocioGestor;
import estiveaqui.relatorios.LancamentosCSV;
import estiveaqui.relatorios.LancamentosExcel;
import estiveaqui.relatorios.LancamentosJson;
import estiveaqui.relatorios.Persiste;
import estiveaqui.relatorios.RelatorioException;
import estiveaqui.sql.LancamentoDB;
import estiveaqui.sql.RelatorioDB;
import estiveaqui.sql.mo.AppGestorMO;
import estiveaqui.sql.mo.LancamentoMO;
import estiveaqui.sql.mo.RelatorioMO;
import estiveaqui.vo.DadosInVO;

public class GeraRelatorio extends RegraNegocioGestor
{
  private static final Logger log = LogManager.getLogger();

  /**
   * Gera os relat�rios dos lan�amentos de horas dos usu�rios de um gestor.
   * 
   * @param geraRelatorioInVO
   * @return GeraRelatorioOutVO
   * @throws EstiveAquiException
   */
  public GeraRelatorioOutVO acao(DadosInVO dadosInVo) throws EstiveAquiException
  {
    String idLog = "";
    if (log.isInfoEnabled())
      idLog = ((DadosAppGestorInVO)dadosInVo).getIdentificadorAppGestor().substring(0, 8);
    log.info("Entrando em GeraRelatorio.acao({}...)", idLog);
    
    GeraRelatorioInVO geraRelatorioInVO = (GeraRelatorioInVO) dadosInVo;
    GeraRelatorioOutVO geraRelatorioOutVO = new GeraRelatorioOutVO();

    ConexaoDB connDB = null;
    try
    {
      //  Valida a vers�o do app.
      Versao.validaVersao(geraRelatorioInVO, new Versao(1, 0, 0), new Versao(1, 1, 0));

      //
      //  Valida��es com acesso ao BD.
      ///////////////////////////////////////////////////////////////////////////
      connDB = new ConexaoDB("jdbc/EstiveAqui");

      //  Gestor existe e est� habilitado?
      AppGestorMO appGestorMO = validaGestor(connDB, geraRelatorioInVO);

      //  Obt�m todos os relat�rios do AppGestor.
      RelatorioDB relatorioDb = new RelatorioDB(connDB);
      List<RelatorioMO> todosRelatoriosMO = relatorioDb.leRelatoriosGestor(appGestorMO.getIdAppGestor());

      //  Filtra apenas os relat�rios que tem que ser recriados.
      List<RelatorioMO> relatoriosMO = new ArrayList<RelatorioMO>();
      for (RelatorioMO relatorioMO : todosRelatoriosMO)
      {
        if (relatorioMO.getRecriarRelatorio() == RelatorioMO.RECRIAR_RELATORIO_SIM)
          relatoriosMO.add(relatorioMO);
      }

      //  Para cada m�s que deve gerar o relat�rio.
      for (RelatorioMO relatorioMO : relatoriosMO)
      {
        int mesAno = relatorioMO.getMes();
        DateTime dtMes = new DateTime(mesAno / 100, mesAno % 100, 1, 0, 0);

        //  Obt�m todos os lan�amentos do m�s de um gestor.
        LancamentoDB lancamentoDb = new LancamentoDB(connDB);
        List<LancamentoMO> lancamentosMesMO = lancamentoDb.leLancamentosGestorMes(appGestorMO.getIdAppGestor(), dtMes);

        //  Monta os lan�amentos do m�s.
        LancamentosMesVO lancamentosMesVo = new LancamentosMesVO(dtMes);
        for (LancamentoMO lancamentoMO : lancamentosMesMO)
        {
          //  Inclui o lan�amento nos lan�amentos do m�s.
          lancamentosMesVo.getLancamentos().add(lancamentoMO);
        }

        //  Ordena os lan�amentos por usu�rio (IdAppUsuario) e data de lan�amento.
        Collections.sort(lancamentosMesVo.getLancamentos(), new Comparator<LancamentoMO>() {
          @Override
          public int compare(LancamentoMO lanc1, LancamentoMO lanc2)
          {
            if (lanc1.getAppUsuarioMO().getIdAppUsuario() != lanc2.getAppUsuarioMO().getIdAppUsuario())
              return lanc1.getAppUsuarioMO().getIdAppUsuario() - lanc2.getAppUsuarioMO().getIdAppUsuario();

            return (int)(lanc1.getHrLancamento().getMillis() - lanc2.getHrLancamento().getMillis());
          }
        });
        
        //  Gera relat�rio em JSON
        LancamentosJson relJson = new LancamentosJson(appGestorMO.getIdAppGestor(), lancamentosMesVo.getMes());
        relJson.montaLancamentosMes(lancamentosMesVo.getLancamentos());

        //  Gera relat�rio em Excel.
        LancamentosExcel relExcel = new LancamentosExcel();
        relExcel.montaLancamentosMes(lancamentosMesVo.getLancamentos());

        //  Gera relat�rio em CSV.
        LancamentosCSV relCSV = new LancamentosCSV(lancamentosMesVo.getLancamentos());

        try
        {
          //  Persiste relat�rio.
          String nomeUnico = relatorioMO.getNomeArquivo();
          Persiste relatPersiste = new Persiste(nomeUnico);
          relatPersiste.persiste(relJson);
          relatPersiste.persiste(relCSV);
          relatPersiste.persiste(relExcel);
        }
        catch (RelatorioException e)
        {
          todosRelatoriosMO.remove(relatorioMO);
          log.error(e.getMessage());
          continue;
        }

        //  Marca relat�rio como gerado.
        relatorioDb.marcaComoGerado(appGestorMO.getIdAppGestor(), mesAno);
      }

      connDB.getConn().commit();

      geraRelatorioOutVO.setRelatoriosMO(todosRelatoriosMO);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw new RegraDeNegocioException(CodigoErro.ERRO_INTERNO, e.getMessage());
    }
    catch (NamingException e)
    {
      e.printStackTrace();
      throw new RegraDeNegocioException(CodigoErro.ERRO_INTERNO, e.getMessage());
    }
    finally
    {
      if (connDB != null)
        connDB.fechaConexao();
      log.info("Saindo de GeraRelatorio.acao({}...)", idLog);
    }

    return geraRelatorioOutVO;
  }
}
