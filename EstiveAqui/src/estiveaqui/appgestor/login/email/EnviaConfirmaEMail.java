package estiveaqui.appgestor.login.email;

import java.sql.SQLException;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;
import estiveaqui.RegraDeNegocioException;
import estiveaqui.Versao;
import estiveaqui.appgestor.DadosAppGestorInVO;
import estiveaqui.appgestor.EMail;
import estiveaqui.appgestor.EMailException;
import estiveaqui.appgestor.RegraNegocioGestor;
import estiveaqui.sql.AppGestorDB;
import haroldo.util.sql.ConexaoDB;
import estiveaqui.sql.mo.AppGestorMO;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

public class EnviaConfirmaEMail extends RegraNegocioGestor
{
  private static final Logger log = LogManager.getLogger();

  @Override
  public DadosOutVO acao(DadosInVO dadosInVo) throws EstiveAquiException
  {
    String idLog = "";
    if (log.isInfoEnabled())
      idLog = ((DadosAppGestorInVO)dadosInVo).getIdentificadorAppGestor().substring(0, 8);
    log.info("Entrando em EnviaConfirmaEMail.acao({}...)", idLog);

    DadosAppGestorInVO enviaConfirmaEMailInVO = (DadosAppGestorInVO) dadosInVo;

    ConexaoDB connDB = null;
    try
    {
      //  Valida a versão do app.
      Versao.validaVersao(enviaConfirmaEMailInVO, new Versao(1, 0, 0), new Versao(1, 0, 0));

      //
      //  Validações com acesso ao BD.
      ///////////////////////////////////////////////////////////////////////////
      connDB = new ConexaoDB("jdbc/EstiveAqui");

      AppGestorDB appGestorDb = new AppGestorDB(connDB);
      AppGestorMO appGestorMO = appGestorDb.buscaRegistroIdentificador(enviaConfirmaEMailInVO.getIdentificadorAppGestor());

      //  AppGestor encontrado?
      if (appGestorMO == null)
        throw new RegraDeNegocioException(CodigoErro.APPGESTOR_NAO_HABILITADO);

      //  E-Mail já confirmado?
      if (appGestorMO.getCodValidacaoEMail() == null)
        throw new RegraDeNegocioException(CodigoErro.EMAIL_JA_VALIDADO);
      
      //  Envia e-mail de confirmação.
      try
      {
        EMail email = new EMail("Reenvio de Confirmação de E-Mail EstiveAqui");
        email.enviaConfirmacaoEMail(appGestorMO.getEmail(), enviaConfirmaEMailInVO.getServidor(), appGestorMO.getCodValidacaoEMail());
      }
      catch (EMailException e)
      {
        log.warn("Problema no envio do e-mail para " + appGestorMO.getEmail() + " - " + e.getMessage());
        //  Ignora o erro.
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
      log.info("Saindo de EnviaConfirmaEMail.acao({}...)", idLog);
    }

    return null;
  }
}
