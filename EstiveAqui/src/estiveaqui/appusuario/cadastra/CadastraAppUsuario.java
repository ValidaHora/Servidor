package estiveaqui.appusuario.cadastra;

import java.sql.SQLException;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;
import estiveaqui.RegraDeNegocioException;
import estiveaqui.Versao;
import estiveaqui.appusuario.RegraNegocioAppUsuario;
import estiveaqui.servidor.util.GeraChaves;
import estiveaqui.sql.AppUsuarioDB;
import haroldo.util.sql.ConexaoDB;
import estiveaqui.sql.mo.AppUsuarioMO;
import estiveaqui.vo.DadosInVO;

public class CadastraAppUsuario extends RegraNegocioAppUsuario
{
  private static final Logger log = LogManager.getLogger();

  /**
   * Valida e cadastra o AppUsuario.
   * 
   * @param cadAppUsuarioInVO
   * @return
   * @throws EstiveAquiException 
   */
  @Override
  public CadastraAppUsuarioOutVO acao(DadosInVO dadosInVo) throws EstiveAquiException
  {
    log.info("Entrando em CadastraAppUsuarioOutVO.cadastraAppUsuario()");

    CadastraAppUsuarioInVO cadAppUsuarioInVO = (CadastraAppUsuarioInVO) dadosInVo;
    CadastraAppUsuarioOutVO cadAppUsuarioOutVO = new CadastraAppUsuarioOutVO();

    ConexaoDB connDB = null;
    try
    {
      //  Valida a versão do app.
      Versao.validaVersao(cadAppUsuarioInVO, new Versao(1, 0, 0), new Versao(1, 1, 0));

      //
      //  Validações com acesso ao BD.
      ///////////////////////////////////////////////////////////////////////////
      connDB = new ConexaoDB("jdbc/EstiveAqui");

      AppUsuarioDB appUsuarioDb = new AppUsuarioDB(connDB);
      AppUsuarioMO appUsuarioMO = appUsuarioDb.buscaCodigoAtivacao(cadAppUsuarioInVO.getCodAtivacao());

      //  Código de ativação encontrado?
      if (appUsuarioMO == null)
        throw new RegraDeNegocioException(CodigoErro.APPUSUARIO_NAO_HABILITADO, "Código de ativação não encontrado");

      //  Zera o código de ativação
      appUsuarioMO = appUsuarioDb.habilitaAppUsuario(appUsuarioMO.getIdAppUsuario(), GeraChaves.identificadorAppUsuario());
      cadAppUsuarioOutVO.setAppUsuarioMO(appUsuarioMO);

      //  Encerra a transação.
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
    }

    log.info("Saindo de CadastraAppUsuarioOutVO.cadastraAppUsuario()");
    return cadAppUsuarioOutVO;
  }

}
