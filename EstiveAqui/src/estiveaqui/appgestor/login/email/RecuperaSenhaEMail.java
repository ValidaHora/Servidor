package estiveaqui.appgestor.login.email;

import haroldo.util.sql.ConexaoDB;
import java.sql.SQLException;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;
import estiveaqui.RegraDeNegocioException;
import estiveaqui.Versao;
import estiveaqui.appgestor.EMail;
import estiveaqui.appgestor.EMailException;
import estiveaqui.appgestor.RegraNegocioGestor;
import estiveaqui.servidor.util.GeraChaves;
import estiveaqui.sql.AppGestorDB;
import estiveaqui.sql.mo.AppGestorMO;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

public class RecuperaSenhaEMail extends RegraNegocioGestor
{
  private static final Logger log = LogManager.getLogger();

  @Override
  public DadosOutVO acao(DadosInVO dadosInVo) throws EstiveAquiException
  {
    log.info("Entrando em RecuperaSenhaEMail.acao()");

    LoginEMailInVO esquecisSenhaEMailInVO = (LoginEMailInVO)dadosInVo;

    ConexaoDB connDB = null;
    try
    {
      //  Valida a vers�o do app.
      Versao.validaVersao(esquecisSenhaEMailInVO, new Versao(1, 0, 0), new Versao(1, 0, 0));

      //
      //  Valida�ões com acesso ao BD.
      ///////////////////////////////////////////////////////////////////////////
      connDB = new ConexaoDB("jdbc/EstiveAqui");

      AppGestorDB appGestorDb = new AppGestorDB(connDB);
      AppGestorMO appGestorMO = appGestorDb.buscaRegistroEmail(esquecisSenhaEMailInVO.getEmail().trim());

      //  AppGestor encontrado?
      if (appGestorMO == null)
        throw new RegraDeNegocioException(CodigoErro.APPGESTOR_NAO_HABILITADO);

      //  Inclui um novo c�digo para recupera��o de senha.
      appGestorMO.setCodRecuperaSenha(GeraChaves.codigoRecuperacaoSenhaEMail());
      appGestorDb.atualizaCodigoEsqueciSenha(appGestorMO);
      
      connDB.getConn().commit();
      
      //  Envia e-mail de confirma��o.
      try
      {
        EMail email = new EMail("Recupera��o de Senha EstiveAqui");
        email.enviaEsqueciSenha(appGestorMO.getEmail(), appGestorMO.getCodRecuperaSenha());
      }
      catch (EMailException e)
      {
        log.warn("Problema no envio do e-mail para " + appGestorMO.getEmail() + " - " + e.getMessage());
        //  Ignora o erro.
        //  TODO: N�o ignorar este erro.
      }
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
      log.info("Saindo de RecuperaSenhaEMail.acao()");
    }

    return null;
  }
}
