package estiveaqui.appgestor.ledados;

import java.sql.SQLException;
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
import estiveaqui.sql.AppUsuarioDB;
import haroldo.util.sql.ConexaoDB;
import estiveaqui.sql.LancamentoDB;
import estiveaqui.sql.PassClockDB;
import estiveaqui.sql.mo.AppGestorMO;
import estiveaqui.vo.DadosInVO;

public class LeDados extends RegraNegocioGestor
{
  private static final Logger log = LogManager.getLogger();

  /**
   * Lê os dados de um gestor. <BR>
   * Os dados são:<BR>
   * <LI>Lançamentos do dia</LI>
   * <LI>PassClocks do gestor</LI>
   * <LI>AppUsuarios do gestors</LI>
   * 
   * @param atualizaDadosInVO
   * @return LeDadosOutVO
   * @throws EstiveAquiException 
   */
  @Override
  public LeDadosOutVO acao(DadosInVO dadosInVo) throws EstiveAquiException
  {
    String idLog = "";
    if (log.isInfoEnabled())
      idLog = ((DadosAppGestorInVO)dadosInVo).getIdentificadorAppGestor().substring(0, 8);
    log.info("Entrando em LeDados.acao({}...)", idLog);

    LeDadosInVO leDadosInVO = (LeDadosInVO)dadosInVo;
    LeDadosOutVO leDadosOutVO = new LeDadosOutVO();

    ConexaoDB connDB = null;
    try
    {
      //  Valida a versão do app.
      Versao.validaVersao(leDadosInVO, new Versao(1, 0, 0), new Versao(1, 0, 0));

      //
      //  Validações com acesso ao BD.
      ///////////////////////////////////////////////////////////////////////////
      connDB = new ConexaoDB("jdbc/EstiveAqui");

      //  Gestor existe e está habilitado?
      AppGestorMO appGestorMO  = validaGestor(connDB, leDadosInVO);
      leDadosOutVO.setEmailValidado(appGestorMO.getCodValidacaoEMail() == null);
      
      //  Busca todos os appUsuarios deste gestor.
      AppUsuarioDB appUsuarioDB = new AppUsuarioDB(connDB);
      leDadosOutVO.setAppUsuariosMO(appUsuarioDB.leRegistrosGestor(appGestorMO.getIdAppGestor()));

      //  Busca todos os passClocks deste gestor.
      PassClockDB passClockDB = new PassClockDB(connDB);
      leDadosOutVO.setPassClocksMO(passClockDB.leRegistrosGestor(appGestorMO.getIdAppGestor()));

      //  Busca todos os lançamentos do deste gestor a partir do IdUltimoLancamento.
      LancamentoDB lancamentoDB = new LancamentoDB(connDB);
      leDadosOutVO.setLancamentosMO(lancamentoDB.leLancamentosGestorDesdeAte(appGestorMO.getIdAppGestor(), leDadosInVO.getIdUltimoLancamento(), DateTime.now()));
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
      log.info("Saindo de LeDados.acao({}...)", idLog);
    }

    return leDadosOutVO;
  }
}
