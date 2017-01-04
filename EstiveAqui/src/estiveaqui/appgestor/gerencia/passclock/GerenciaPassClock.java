package estiveaqui.appgestor.gerencia.passclock;

import haroldo.util.sql.ConexaoDB;
import java.sql.SQLException;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;
import estiveaqui.RegraDeNegocioException;
import estiveaqui.Versao;
import estiveaqui.appgestor.DadosAppGestorInVO;
import estiveaqui.appgestor.DadosGerenciaisInVO;
import estiveaqui.appgestor.RegraNegocioGestor;
import estiveaqui.http.HTTPValidaHora;
import estiveaqui.http.HTTPValidaHoraException;
import estiveaqui.servidor.util.GeraChaves;
import estiveaqui.sql.PassClockDB;
import estiveaqui.sql.mo.AppGestorMO;
import estiveaqui.sql.mo.PassClockMO;
import estiveaqui.vo.DadosInVO;

public class GerenciaPassClock extends RegraNegocioGestor
{
  private static final Logger log = LogManager.getLogger();

  public GerenciaPassClockOutVO acao(DadosInVO dadosInVo) throws EstiveAquiException
  {
    String idLog = "";
    if (log.isInfoEnabled())
      idLog = ((DadosAppGestorInVO)dadosInVo).getIdentificadorAppGestor().substring(0, 8);
    log.info("Entrando em GerenciaPassClock.acao({}..., {})", idLog, ((DadosGerenciaisInVO)dadosInVo).getAcao());

    GerenciaPassClockInVO gerenciaPassClockInVo = (GerenciaPassClockInVO) dadosInVo;
    GerenciaPassClockOutVO gerenciaPassClockOutVo = new GerenciaPassClockOutVO();

    ConexaoDB connDB = null;
    try
    {
      //  Valida a versão do app.
      Versao.validaVersao(gerenciaPassClockInVo, new Versao(1, 0, 0), new Versao(1, 1, 0));

      //
      //  Validações com acesso ao BD.
      ///////////////////////////////////////////////////////////////////////////
      connDB = new ConexaoDB("jdbc/EstiveAqui");

      //  Gestor existe e está habilitado?
      AppGestorMO appGestorMO = validaGestor(connDB, gerenciaPassClockInVo);

      //  Gestor pode gerenciar este PassClock?
      PassClockMO passClockMO = null;
      if (! gerenciaPassClockInVo.getAcao().startsWith("CAD"))  //  Se não é um cadastro de PassClock.
        passClockMO = gerenciaPassClock(connDB, appGestorMO.getIdAppGestor(), gerenciaPassClockInVo.getNumPassClock());
        

      //  Qual a ação?
      log.debug("Gerenciando PassClock com a ação \"{}\"", gerenciaPassClockInVo.getAcao());
      switch (gerenciaPassClockInVo.getAcao())
      {
        //  Cadastra um PassClock físico.
        case "CAD":
          gerenciaPassClockOutVo = cadastraFisico(connDB, gerenciaPassClockInVo);
          break;
        //  Cadastra um PassClock virtual.
        case "CAV":
          gerenciaPassClockOutVo = cadastraVirtual(connDB, appGestorMO.getIdAppGestor());
          break;
        //  Atualiza apelido do PassClock.
        case "UPD":
          gerenciaPassClockOutVo = atualizaApelido(connDB, passClockMO, gerenciaPassClockInVo.getApelido());
          break;
        //  Habilita PassClock.
        case "ENA": //  Habilitação apenas para o AppPassClock físico.
          gerenciaPassClockOutVo = habilita(connDB, passClockMO);
          break;
        //  Desabilita PassClock.
        case "DIS":
          gerenciaPassClockOutVo = desabilita(connDB, passClockMO);
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
      log.info("Saindo de GerenciaPassClock.acao({}...)", idLog);
    }

    return gerenciaPassClockOutVo;
  }

//  /**
//   * Retorna todos os PassClocks de um gestor.
//   * 
//   * @param connDB
//   * @param idAppGestor
//   * @return
//   * @throws SQLException
//   * @throws RegraDeNegocioException
//   */
//  private GerenciaPassClockOutVO lePassClocks(ConexaoDB connDB, int idAppGestor) throws SQLException, RegraDeNegocioException
//  {
//    log.info("Entrando em GerenciaPassClock.lePassClocks()");
//
//    //  Busca todos os appUsuarios deste gestor.
//    PassClockDB passClockDb = new PassClockDB(connDB);
//    GerenciaPassClockOutVO gerenciaPassClockOutVO = new GerenciaPassClockOutVO();
//    gerenciaPassClockOutVO.setPassClocksMO(passClockDb.leRegistrosGestor(idAppGestor));
//
//    log.info("Saindo de GerenciaPassClock.lePassClocks()");
//    return gerenciaPassClockOutVO;
//  }

  /**
   * Valida se o gestor pode gerenciar este PassClock.
   * 
   * @param connDB
   * @param idAppGestor
   * @param numPassClock
   * @return
   * @throws SQLException
   * @throws RegraDeNegocioException
   */
  public static PassClockMO gerenciaPassClock(ConexaoDB connDB, int idAppGestor, String numPassClock) throws SQLException, RegraDeNegocioException
  {
    //  Se o numPassClock não foi passado. Não há validação.
    if (numPassClock == null || numPassClock.isEmpty())
      return null;

    PassClockDB passClockDb = new PassClockDB(connDB);
    PassClockMO passClockMO = passClockDb.buscaPorNumeroPassClock(numPassClock);

    //  PassClock existe.
    if (passClockMO == null)
      throw new RegraDeNegocioException(CodigoErro.PASSCLOCK_NAO_EXISTE, "PassClock não existe");

    //  PassClock gerenciado?
    if (passClockMO.getIdAppGestor() != 0)
    {
      //  O PassClock é gerenciado pelo gestor?  
      if (passClockMO.getIdAppGestor() != idAppGestor)
        throw new RegraDeNegocioException(CodigoErro.PASSCLOCK_DE_OUTRO_GESTOR);
    }

    return passClockMO;
  }

  /**
   * Cadastra um PassClock virtual.
   * 
   * @param connDB
   * @param gerenciaPassClockInVO
   * @return
   * @throws SQLException
   * @throws RegraDeNegocioException
   */
  GerenciaPassClockOutVO cadastraVirtual(ConexaoDB connDB, int idAppGestor) throws SQLException, RegraDeNegocioException
  {
    GerenciaPassClockOutVO gerenciaPassClockOutVo = new GerenciaPassClockOutVO();
    PassClockDB passClockDb = new PassClockDB(connDB);

    //  PassClock existe?
    PassClockMO passClockMO = new PassClockMO();
    passClockMO.setNumPassClock("VIRTUAL-" + GeraChaves.sufixoPassClockVirtual());
    passClockMO.setApelido("Virtual");
    passClockMO.setCodAtivacaoVirtual(GeraChaves.codigoAtivacaoPassClockVirtual());
    passClockMO.setIdAppGestor(idAppGestor);
    passClockMO.setSenhaCadastro("VIRTUAL");
    passClockMO.setStatus(PassClockMO.STATUS_DESABILITADO);

    passClockDb.incluiPassClockVirtual(passClockMO);

    gerenciaPassClockOutVo.setPassClockMO(passClockMO);
    return gerenciaPassClockOutVo;
  }

  /**
   * Desabilita um PassClock.
   * 
   * @param connDB
   * @param passClockMO
   * @return
   * @throws SQLException
   * @throws RegraDeNegocioException
   */
  GerenciaPassClockOutVO desabilita(ConexaoDB connDB, PassClockMO passClockMO) throws SQLException, RegraDeNegocioException
  {
    return defineStatus(connDB, passClockMO, PassClockMO.STATUS_DESABILITADO);
  }
  
  /**
   * Habilita um PassClock físico.
   * 
   * @param connDB
   * @param passClockMO
   * @return
   * @throws SQLException
   * @throws RegraDeNegocioException
   */
  GerenciaPassClockOutVO habilita(ConexaoDB connDB, PassClockMO passClockMO) throws SQLException, RegraDeNegocioException
  {
    if (passClockMO.getCodAtivacaoVirtual() != null)
      throw new RegraDeNegocioException(CodigoErro.PASSCLOCK_DESABILITADO, "PassClocks virtuais são habilitados pelos usuários");
    
    return defineStatus(connDB, passClockMO, PassClockMO.STATUS_HABILITADO);
  }

  /**
   * Define o status de habilitado ou desabilitado de um PassClock.
   * 
   * @param connDB
   * @param passClockMO
   * @param status
   * @return
   * @throws SQLException
   * @throws RegraDeNegocioException
   */
  private GerenciaPassClockOutVO defineStatus(ConexaoDB connDB, PassClockMO passClockMO, int status) throws SQLException, RegraDeNegocioException
  {
    GerenciaPassClockOutVO gerenciaPassClockOutVo = new GerenciaPassClockOutVO();
    PassClockDB passClockDb = new PassClockDB(connDB);

    //  PassClock existe?
    if (passClockMO == null)
      throw new RegraDeNegocioException(CodigoErro.PASSCLOCK_NAO_EXISTE, "PassClock não encontrado");

    //  PassClock virtual?
    if (passClockMO.getCodAtivacaoVirtual() != null)
      passClockMO.setCodAtivacaoVirtual(GeraChaves.codigoAtivacaoPassClockVirtual());

    //  Define o status do PassClock.
    passClockDb.defineStatus(passClockMO.getIdPassClock(), status, passClockMO.getCodAtivacaoVirtual());

    passClockMO.setStatus(status);
    gerenciaPassClockOutVo.setPassClockMO(passClockMO);

    return gerenciaPassClockOutVo;
  }

  /**
   * Atualiza o apelido do PassClock.
   * 
   * @param connDB
   * @param passClockMO
   * @param apelido
   * @return
   * @throws SQLException
   * @throws RegraDeNegocioException
   */
  GerenciaPassClockOutVO atualizaApelido(ConexaoDB connDB, PassClockMO passClockMO, String apelido) throws SQLException, RegraDeNegocioException
  {
    GerenciaPassClockOutVO gerenciaPassClockOutVo = new GerenciaPassClockOutVO();
    PassClockDB passClockDb = new PassClockDB(connDB);

    //  PassClock existe?
    if (passClockMO == null)
      throw new RegraDeNegocioException(CodigoErro.PASSCLOCK_NAO_EXISTE, "PassClock não encontrado");

    passClockDb.atualizaApelido(passClockMO.getIdPassClock(), apelido);

    passClockMO.setApelido(apelido);
    gerenciaPassClockOutVo.setPassClockMO(passClockMO);

    return gerenciaPassClockOutVo;
  }

  /**
   * Cadastra um PassClock físico.
   * 
   * @param connDB
   * @param gerenciaPassClockInVo
   * @return
   * @throws RegraDeNegocioException
   * @throws SQLException
   * @throws HTTPValidaHoraException 
   */
  private GerenciaPassClockOutVO cadastraFisico(ConexaoDB connDB, GerenciaPassClockInVO gerenciaPassClockInVo)
      throws RegraDeNegocioException, SQLException
  {
    GerenciaPassClockOutVO gerenciaPassClockOutVo = new GerenciaPassClockOutVO();

    //  Encontra o gestor associado a este PassClock.
    AppGestorMO appGestorMO = validaGestor(connDB, gerenciaPassClockInVo);
    
    //  PassClock já cadastrado?
    PassClockDB passClockDb = new PassClockDB(connDB);
    PassClockMO passClockMo = passClockDb.buscaPorNumeroPassClock(gerenciaPassClockInVo.getNumPassClock());
    if (passClockMo != null)
      throw new RegraDeNegocioException(CodigoErro.PASSCLOCK_JA_EXISTE);

    try
    {
      HTTPValidaHora.validaCodigo(gerenciaPassClockInVo);
    }
    catch (HTTPValidaHoraException e)
    {
      throw new RegraDeNegocioException(CodigoErro.CODIGO_INVALIDO, e.getMessage());
    }
    
    //  Prepara o registro do PassClock.
    passClockMo = new PassClockMO();
    passClockMo.setNumPassClock(gerenciaPassClockInVo.getNumPassClock());
    passClockMo.setApelido(gerenciaPassClockInVo.getApelido());
    passClockMo.setSenhaCadastro(GeraChaves.senhaPassClock());
    passClockMo.setIdAppGestor(appGestorMO.getIdAppGestor());
    passClockMo.setTz(gerenciaPassClockInVo.getTz());
    
    //  Inclui o PassClock físico no BD.
    passClockDb.insereRegistro(passClockMo);
    
    gerenciaPassClockOutVo.setPassClockMO(passClockMo);
    return gerenciaPassClockOutVo;
  }
}
