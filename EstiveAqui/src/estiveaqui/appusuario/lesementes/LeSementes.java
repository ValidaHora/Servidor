package estiveaqui.appusuario.lesementes;

import haroldo.util.sql.ConexaoDB;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;
import estiveaqui.RegraDeNegocioException;
import estiveaqui.Versao;
import estiveaqui.appusuario.RegraNegocioAppUsuario;
import estiveaqui.http.HTTPValidaHora;
import estiveaqui.sql.AppUsuarioDB;
import estiveaqui.sql.PassClockDB;
import estiveaqui.sql.mo.AppUsuarioMO;
import estiveaqui.sql.mo.PassClockMO;
import estiveaqui.sql.mo.externo.TokenMO;
import estiveaqui.vo.DadosInVO;

public class LeSementes extends RegraNegocioAppUsuario
{
  private static final Logger log = LogManager.getLogger();

  /**
   * Valida e lê o AppUsuario.
   * 
   * @param leSementesInVO
   * @return
   * @throws EstiveAquiException 
   */
  public LeSementesOutVO acao(DadosInVO dadosInVo) throws EstiveAquiException
  {
    log.info("Entrando em LeSemente.leSementes()");
    
    LeSementesInVO leSementesInVO = (LeSementesInVO)dadosInVo;
    LeSementesOutVO leSementesOutVO = new LeSementesOutVO();

    ConexaoDB connDB = null;
    try
    {
      //  Valida a versão do app.
      Versao.validaVersao(leSementesInVO, new Versao(1, 0, 0), new Versao(1, 1, 0));

      //
      //  Validações com acesso ao BD.
      ///////////////////////////////////////////////////////////////////////////
      connDB = new ConexaoDB("jdbc/EstiveAqui");

      //  Busca o usuário no BD.
      AppUsuarioDB appUsuarioDb = new AppUsuarioDB(connDB);
      
      //  Valida o AppUsuario
      AppUsuarioMO appUsuarioMO = validaAppUsuario(appUsuarioDb, leSementesInVO);
      leSementesOutVO.setAppUsuarioMO(appUsuarioMO);
      
      //  Encontra os PassClocks utilizados por este usuário. Cadastrados pelo gestor!
      PassClockDB passClockDb = new PassClockDB(connDB);
      List<PassClockMO> passClocksMO = passClockDb.leRegistrosGestor(appUsuarioMO.getIdAppGestor());

      //  Busca as sementes no ValidaHora
      List<TokenMO> tokensMO = HTTPValidaHora.buscaSementes(passClocksMO);
      leSementesOutVO.setTokensMO(tokensMO);
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

    log.info("Saindo de LeSemente.leSementes()");
    return leSementesOutVO;
  }
}
