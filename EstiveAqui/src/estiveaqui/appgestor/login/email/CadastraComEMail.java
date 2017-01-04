package estiveaqui.appgestor.login.email;

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
import haroldo.util.sql.ConexaoDB;
import estiveaqui.sql.mo.AppGestorMO;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

public class CadastraComEMail extends RegraNegocioGestor
{
  private static final Logger log = LogManager.getLogger();

  @Override
  public DadosOutVO acao(DadosInVO dadosInVo) throws EstiveAquiException
  {
    log.info("Entrando em CadastraComEMail.acao()");

    LoginEMailInVO loginEMailInVO = (LoginEMailInVO) dadosInVo;
    LoginComEMailOutVO loginEMailOutVO = new LoginComEMailOutVO();

    ConexaoDB connDB = null;
    try
    {
      //  Valida a versão do app.
      Versao.validaVersao(loginEMailInVO, new Versao(1, 0, 0), new Versao(1, 1, 0));

      //  Verifica se a senha é válida.
      UtilEMail.validaRegrasParaSenha(loginEMailInVO.getSenha());
          
      //
      //  Validações com acesso ao BD.
      ///////////////////////////////////////////////////////////////////////////
      connDB = new ConexaoDB("jdbc/EstiveAqui");

      AppGestorDB appGestorDb = new AppGestorDB(connDB);
      AppGestorMO appGestorMO = appGestorDb.buscaRegistroEmail(loginEMailInVO.getEmail().trim());

      //  E-Mail já existe?
      if (appGestorMO != null)
        throw new RegraDeNegocioException(CodigoErro.EMAIL_JA_CADASTRADO);

      //  Preprara um novo gestor para ser incluído no BD.
      appGestorMO = new AppGestorMO();
      appGestorMO.setIdentificadorAppGestor(GeraChaves.identificadorAppGestor());
      appGestorMO.setStatus(AppGestorMO.STATUS_HABILITADO);
      appGestorMO.setIdUltimoLancamentoRelatorio(0);
      appGestorMO.setEmail(loginEMailInVO.getEmail());
      appGestorMO.setSenhaVencida(0);
      appGestorMO.setCodValidacaoEMail(GeraChaves.codigoValidacaoEMail());
      
      appGestorDb.insereRegistro(appGestorMO);

      //  Atualiza e-mail e senha.
      UtilEMail.setSenhaCriptografada(appGestorMO, loginEMailInVO.getSenha());
      appGestorDb.atualizaEMail(appGestorMO);

      connDB.getConn().commit();

      //  Envia e-mail de confirmação.
      try
      {
        EMail email = new EMail("Confirmação de E-Mail EstiveAqui");
        email.enviaConfirmacaoEMail(loginEMailInVO.getEmail(), loginEMailInVO.getServidor(), appGestorMO.getCodValidacaoEMail());
      }
      catch (EMailException e)
      {
        log.warn("Problema no envio do e-mail para " + loginEMailInVO.getEmail() + " - " + e.getMessage());
        //  Ignora o erro.
      }

      //  Retorna os valores.
      loginEMailOutVO.setIdentificadorAppGestor(appGestorMO.getIdentificadorAppGestor());
      loginEMailOutVO.setExisteHistorico(false);
      loginEMailOutVO.setEmailValidado(false);
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

    log.info("Saindo de CadastraComEMail.acao()");
    return loginEMailOutVO;
  }
}
