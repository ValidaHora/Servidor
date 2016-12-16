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
      //  Valida a versão do app.
      Versao.validaVersao(gerenciaLancamentoInVo, new Versao(1, 0, 0), new Versao(1, 0, 0));

      //
      //  Validações com acesso ao BD.
      ///////////////////////////////////////////////////////////////////////////
      connDB = new ConexaoDB("jdbc/EstiveAqui");

      //  Gestor existe e está habilitado?
      AppGestorMO appGestorMO = validaGestor(connDB, gerenciaLancamentoInVo);

      //  Qual a ação?
      log.debug("Gerenciando Lancamento com a ação \"{}\"", gerenciaLancamentoInVo.getAcao());
      switch (gerenciaLancamentoInVo.getAcao())
      {
      //  Cadastra um Lancamento físico.
        case "CAD":
          gerenciaLancamentoOutVo = cadastraManual(connDB, gerenciaLancamentoInVo, appGestorMO);
          break;
        //  Habilita Lancamento.
        case "ENA": //  Habilitação apenas para o AppLancamento físico.
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
   * Cadastra um lançamento manual.
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
    //  Busca o usuário no BD.
    AppUsuarioDB appUsuarioDb = new AppUsuarioDB(connDB);
    AppUsuarioMO appUsuarioMO = appUsuarioDb.leRegistroPK(gerenciaLancamentoInVo.getIdAppUsuario());

    //  Usuário existe?
    if (appUsuarioMO == null)
      throw new RegraDeNegocioException(CodigoErro.ERRO_INTERNO, "Usuário não encontrado");

    //  Usuário é gerenciado por este gestor?
    if (appGestorMO.getIdAppGestor() != appUsuarioMO.getIdAppGestor())
      throw new RegraDeNegocioException(CodigoErro.ERRO_INTERNO, "Usuário de outro gestor");

    //  Hora já lançada?
    LancamentoDB lancamentoDb = new LancamentoDB(connDB);
    if (lancamentoDb.horaLancada(appUsuarioMO.getIdAppUsuario(), gerenciaLancamentoInVo.getHoraManual()))
      throw new RegraDeNegocioException(CodigoErro.CODIGO_JA_LANCADO, "Hora já lançada");
    
    //  Prepara o lançamento manual.
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

    //  Insere o lançamento manual no BD.
    lancamentoMO = lancamentoDb.insereRegistro(lancamentoMO);

    // Prepara a resposta.
    GerenciaLancamentoOutVO gerenciaLancamentoOutVo = new GerenciaLancamentoOutVO();
    gerenciaLancamentoOutVo.setLancamentoMO(lancamentoMO);

    //  Atualiza a tabela de relatório para esta hora lançada.
    RelatorioDB relatorioDb = new RelatorioDB(connDB);
    relatorioDb.marcaComoNaoGerado(appUsuarioMO.getIdAppGestor(), Util.paraMesAno(lancamentoMO.getHrLancamento()));

    //  Retorna o lançamento inserido.
    return gerenciaLancamentoOutVo;
  }

  /**
   * Habilita um lançamento.
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
   * Desabilita um lançamento.
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
   * Altera o status de um lançamento para o status passado como parâmetro.
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

    //  Busca o lançamento.
    LancamentoMO lancamentoMO = lancamentoDb.leRegistroPK(gerenciaLancamentoInVo.getIdLancamento());
    
    //  Lancamento existe?
    if (lancamentoMO == null)
      throw new RegraDeNegocioException(CodigoErro.PASSCLOCK_NAO_EXISTE, "Lancamento não encontrado");

    //  Altera o status do lançamento.
    lancamentoMO = lancamentoDb.defineStatus(lancamentoMO.getIdLancamento(), status);

    // Prepara a resposta.
    GerenciaLancamentoOutVO gerenciaLancamentoOutVo = new GerenciaLancamentoOutVO();
    gerenciaLancamentoOutVo.setLancamentoMO(lancamentoMO);
    
    return gerenciaLancamentoOutVo;
  }
}
