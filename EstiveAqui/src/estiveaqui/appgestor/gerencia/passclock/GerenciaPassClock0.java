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
import estiveaqui.servidor.util.GeraChaves;
import estiveaqui.sql.PassClockDB;
import estiveaqui.sql.mo.AppGestorMO;
import estiveaqui.sql.mo.PassClockMO;
import estiveaqui.vo.DadosInVO;

@Deprecated
public class GerenciaPassClock0 extends GerenciaPassClock
{
  private static final Logger log = LogManager.getLogger();

  @Deprecated
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

  /**
   * Cadastra um PassClock físico.
   * 
   * @param connDB
   * @param gerenciaPassClockInVo
   * @return
   * @throws RegraDeNegocioException
   * @throws SQLException
   */
  @Deprecated
  private GerenciaPassClockOutVO cadastraFisico(ConexaoDB connDB, GerenciaPassClockInVO gerenciaPassClockInVo)
      throws RegraDeNegocioException, SQLException
  {
    GerenciaPassClockOutVO gerenciaPassClockOutVo = new GerenciaPassClockOutVO();

    //  Valida se o hashcode é válido.
    validaCheckSum(gerenciaPassClockInVo.getHashCode());

    //  Encontra o gestor associado a este PassClock.
    AppGestorMO appGestorMO = validaGestor(connDB, gerenciaPassClockInVo);
    
    //  PassClock já cadastrado?
    PassClockDB passClockDb = new PassClockDB(connDB);
    PassClockMO passClockMo = passClockDb.buscaPorNumeroPassClock(gerenciaPassClockInVo.getNumPassClock());
    if (passClockMo != null)
      throw new RegraDeNegocioException(CodigoErro.PASSCLOCK_JA_EXISTE);
    
    //  Prepara o registro do PassClock.
    passClockMo = new PassClockMO();
    passClockMo = new PassClockMO();
    passClockMo.setNumPassClock(gerenciaPassClockInVo.getNumPassClock());
    passClockMo.setApelido(gerenciaPassClockInVo.getApelido());
    passClockMo.setSenhaCadastro(GeraChaves.senhaPassClock());
    passClockMo.setIdAppGestor(appGestorMO.getIdAppGestor());
    
    //  Inclui o PassClock físico no BD.
    passClockDb.insereRegistro(passClockMo);
    
    gerenciaPassClockOutVo.setPassClockMO(passClockMo);
    return gerenciaPassClockOutVo;
  }
}
