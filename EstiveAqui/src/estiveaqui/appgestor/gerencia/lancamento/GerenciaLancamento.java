package estiveaqui.appgestor.gerencia.lancamento;

import java.sql.SQLException;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;
import estiveaqui.RegraDeNegocioException;
import estiveaqui.Util;
import estiveaqui.Versao;
import estiveaqui.appgestor.DadosAppGestorInVO;
import estiveaqui.appgestor.DadosGerenciaisInVO;
import estiveaqui.appgestor.RegraNegocioGestor;
import estiveaqui.sql.AppUsuarioDB;
import estiveaqui.sql.RelatorioDB;
import haroldo.util.sql.ConexaoDB;
import estiveaqui.sql.LancamentoDB;
import estiveaqui.sql.mo.AppGestorMO;
import estiveaqui.sql.mo.AppUsuarioMO;
import estiveaqui.sql.mo.LancamentoMO;
import estiveaqui.vo.DadosInVO;

public class GerenciaLancamento extends RegraNegocioGestor
{
  private static final Logger log = LogManager.getLogger();

  @Override
  public GerenciaLancamentoOutVO acao(DadosInVO dadosInVo) throws EstiveAquiException
  {
    String idLog = "";
    if (log.isInfoEnabled())
      idLog = ((DadosAppGestorInVO)dadosInVo).getIdentificadorAppGestor().substring(0, 8);
    log.info("Entrando em GerenciaLancamento.acao({}..., {})", idLog, ((DadosGerenciaisInVO)dadosInVo).getAcao());

    GerenciaLancamentoInVO gerenciaLancamentoInVo = (GerenciaLancamentoInVO) dadosInVo;
    GerenciaLancamentoOutVO gerenciaLancamentoOutVo = new GerenciaLancamentoOutVO();

    ConexaoDB connDB = null;
    try
    {
      //  Valida a vers�o do app.
      Versao.validaVersao(gerenciaLancamentoInVo, new Versao(1, 0, 0), new Versao(1, 0, 0));

      //
      //  Valida�ões com acesso ao BD.
      ///////////////////////////////////////////////////////////////////////////
      connDB = new ConexaoDB("jdbc/EstiveAqui");

      //  Gestor existe e est� habilitado?
      AppGestorMO appGestorMO = validaGestor(connDB, gerenciaLancamentoInVo);

      //  Qual a a��o?
      log.debug("Gerenciando Lancamento com a a��o \"{}\"", gerenciaLancamentoInVo.getAcao());
      switch (gerenciaLancamentoInVo.getAcao())
      {
      //  Cadastra um Lancamento f�sico.
        case "CAD":
          gerenciaLancamentoOutVo = cadastraManual(connDB, gerenciaLancamentoInVo, appGestorMO);
          break;
        //  Habilita Lancamento.
        case "ENA": //  Habilita��o apenas para o AppLancamento f�sico.
          gerenciaLancamentoOutVo = habilita(connDB, gerenciaLancamentoInVo);
          break;
        //  Desabilita Lancamento.
        case "DIS":
          gerenciaLancamentoOutVo = desabilita(connDB, gerenciaLancamentoInVo);
          break;
      }

      connDB.getConn().commit();
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
      log.info("Saindo de GerenciaLancamento.acao({}...)", idLog);
    }

    return gerenciaLancamentoOutVo;
  }

  /**
   * Cadastra um lan�amento manual.
   * 
   * @param connDB
   * @param gerenciaLancamentoInVo
   * @return
   * @throws SQLException
   * @throws RegraDeNegocioException
   */
  private GerenciaLancamentoOutVO cadastraManual(ConexaoDB connDB, GerenciaLancamentoInVO gerenciaLancamentoInVo, AppGestorMO appGestorMO)
                                throws SQLException, RegraDeNegocioException
  {
    //  Busca o usu�rio no BD.
    AppUsuarioDB appUsuarioDb = new AppUsuarioDB(connDB);
    AppUsuarioMO appUsuarioMO = appUsuarioDb.leRegistroPK(gerenciaLancamentoInVo.getIdAppUsuario());

    //  Usu�rio existe?
    if (appUsuarioMO == null)
      throw new RegraDeNegocioException(CodigoErro.ERRO_INTERNO, "Usu�rio n�o encontrado");

    //  Usu�rio � gerenciado por este gestor?
    if (appGestorMO.getIdAppGestor() != appUsuarioMO.getIdAppGestor())
      throw new RegraDeNegocioException(CodigoErro.ERRO_INTERNO, "Usu�rio de outro gestor");

    //  Hora j� lan�ada?
    LancamentoDB lancamentoDb = new LancamentoDB(connDB);
    if (lancamentoDb.horaLancada(appUsuarioMO.getIdAppUsuario(), gerenciaLancamentoInVo.getHoraManual()))
      throw new RegraDeNegocioException(CodigoErro.CODIGO_JA_LANCADO, "Hora j� lan�ada");
    
    //  Prepara o lan�amento manual.
    LancamentoMO lancamentoMO = new LancamentoMO();
    lancamentoMO.setStatus(LancamentoMO.STATUS_HABILITADO);
    lancamentoMO.setHrLancamento(gerenciaLancamentoInVo.getHoraManual());
    DateTime agora = DateTime.now();
    lancamentoMO.setHrDigitacao(agora);
    lancamentoMO.setHrEnvio(agora);
    lancamentoMO.getAppUsuarioMO().setIdAppUsuario(gerenciaLancamentoInVo.getIdAppUsuario());
    lancamentoMO.setNumPassClock("MANUAL");
    lancamentoMO.setApelidoPassClock("Manual");
    lancamentoMO.getAppUsuarioMO().setApelido(appUsuarioMO.getApelido());
    lancamentoMO.setNota(gerenciaLancamentoInVo.getNota());
    lancamentoMO.setIdDispositivo("MANUAL");
    lancamentoMO.setIpDispositivo("0.0.0.0");
    lancamentoMO.setLatitude(LancamentoMO.LANCAMENTO_MANUAL);
    lancamentoMO.setLongitude(LancamentoMO.LANCAMENTO_MANUAL);

    //  Insere o lan�amento manual no BD.
    lancamentoMO = lancamentoDb.insereRegistro(lancamentoMO);

    // Prepara a resposta.
    GerenciaLancamentoOutVO gerenciaLancamentoOutVo = new GerenciaLancamentoOutVO();
    gerenciaLancamentoOutVo.setLancamentoMO(lancamentoMO);

    //  Atualiza a tabela de relat�rio para esta hora lan�ada.
    RelatorioDB relatorioDb = new RelatorioDB(connDB);
    relatorioDb.marcaComoNaoGerado(appUsuarioMO.getIdAppGestor(), Util.paraMesAno(lancamentoMO.getHrLancamento()));

    //  Retorna o lan�amento inserido.
    return gerenciaLancamentoOutVo;
  }

  /**
   * Habilita um lan�amento.
   * 
   * @param connDB
   * @param gerenciaLancamentoInVo
   * @return
   * @throws SQLException
   * @throws RegraDeNegocioException
   */
  private GerenciaLancamentoOutVO habilita(ConexaoDB connDB, GerenciaLancamentoInVO gerenciaLancamentoInVo) throws SQLException, RegraDeNegocioException
  {
    return alteraStatus(connDB, gerenciaLancamentoInVo, LancamentoMO.STATUS_HABILITADO);
  }

  /**
   * Desabilita um lan�amento.
   * 
   * @param connDB
   * @param gerenciaLancamentoInVo
   * @return
   * @throws SQLException
   * @throws RegraDeNegocioException
   */
  private GerenciaLancamentoOutVO desabilita(ConexaoDB connDB, GerenciaLancamentoInVO gerenciaLancamentoInVo) throws SQLException, RegraDeNegocioException
  {
    return alteraStatus(connDB, gerenciaLancamentoInVo, LancamentoMO.STATUS_DESABILITADO);
  }

  /**
   * Altera o status de um lan�amento para o status passado como par�metro.
   * 
   * @param connDB
   * @param gerenciaLancamentoInVo
   * @param status
   * @return
   * @throws SQLException
   * @throws RegraDeNegocioException
   */
  private GerenciaLancamentoOutVO alteraStatus(ConexaoDB connDB, GerenciaLancamentoInVO gerenciaLancamentoInVo, int status) 
                                        throws SQLException, RegraDeNegocioException
  {
    LancamentoDB lancamentoDb = new LancamentoDB(connDB);

    //  Busca o lan�amento.
    LancamentoMO lancamentoMO = lancamentoDb.leRegistroPK(gerenciaLancamentoInVo.getIdLancamento());
    
    //  Lancamento existe?
    if (lancamentoMO == null)
      throw new RegraDeNegocioException(CodigoErro.PASSCLOCK_NAO_EXISTE, "Lancamento n�o encontrado");

    //  Altera o status do lan�amento.
    lancamentoMO = lancamentoDb.defineStatus(lancamentoMO.getIdLancamento(), status);

    // Prepara a resposta.
    GerenciaLancamentoOutVO gerenciaLancamentoOutVo = new GerenciaLancamentoOutVO();
    gerenciaLancamentoOutVo.setLancamentoMO(lancamentoMO);
    
    return gerenciaLancamentoOutVo;
  }
}
