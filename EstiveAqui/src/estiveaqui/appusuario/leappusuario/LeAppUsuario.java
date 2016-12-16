package estiveaqui.appusuario.leappusuario;

import java.sql.SQLException;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;
import estiveaqui.RegraDeNegocioException;
import estiveaqui.Versao;
import estiveaqui.appusuario.RegraNegocioAppUsuario;
import estiveaqui.sql.AppUsuarioDB;
import haroldo.util.sql.ConexaoDB;
import estiveaqui.sql.LancamentoDB;
import estiveaqui.sql.PassClockDB;
import estiveaqui.sql.mo.AppUsuarioMO;
import estiveaqui.vo.DadosInVO;

public class LeAppUsuario extends RegraNegocioAppUsuario
{
  private static final Logger log = LogManager.getLogger();

  /**
   * Valida e lê o AppUsuario.
   * 
   * @param leAppUsuarioInVO
   * @return
   * @throws EstiveAquiException 
   */
  public LeAppUsuarioOutVO acao(DadosInVO dadosInVo) throws EstiveAquiException
  {
    log.info("Entrando em LeAppUsuario.leAppUsuario()");
    
    LeAppUsuarioInVO leAppUsuarioInVO = (LeAppUsuarioInVO) dadosInVo;
    LeAppUsuarioOutVO leAppUsuarioOutVO = new LeAppUsuarioOutVO();

    ConexaoDB connDB = null;
    try
    {
      //  Valida a versão do app.
      Versao.validaVersao(leAppUsuarioInVO, new Versao(1, 0, 0), new Versao(1, 1, 0));

      //
      //  ValidaçÃµes com acesso ao BD.
      ///////////////////////////////////////////////////////////////////////////
      connDB = new ConexaoDB("jdbc/EstiveAqui");

      //  Busca o usuário no BD.
      AppUsuarioDB appUsuarioDb = new AppUsuarioDB(connDB);
      
      //  Valida o AppUsuario
      AppUsuarioMO appUsuarioMO = validaAppUsuario(appUsuarioDb, leAppUsuarioInVO);
      leAppUsuarioOutVO.setAppUsuarioMO(appUsuarioMO);
      
      //  Busca os PassClocks utilizados por este usuário. Cadastrados pelo gestor!
      PassClockDB passClockDb = new PassClockDB(connDB);
      leAppUsuarioOutVO.setPassClocksMO(passClockDb.leRegistrosGestor(appUsuarioMO.getIdAppGestor()));
      
      //  Lê os lançamentos do dia para este usuário.
      LancamentoDB lancamentoDb = new LancamentoDB(connDB);
      leAppUsuarioOutVO.setLancamentosMO(lancamentoDb.leLancamentosUsuarioHoje(appUsuarioMO.getIdAppUsuario(), leAppUsuarioInVO.getTz()));
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
    }

    log.info("Saindo de LeAppUsuario.leAppUsuario()");
    return leAppUsuarioOutVO;
  }
}
