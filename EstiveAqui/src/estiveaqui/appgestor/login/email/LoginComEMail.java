package estiveaqui.appgestor.login.email;

import java.sql.SQLException;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.CodigoErro;
import estiveaqui.Criptografa;
import estiveaqui.EstiveAquiException;
import estiveaqui.RegraDeNegocioException;
import estiveaqui.Versao;
import estiveaqui.appgestor.RegraNegocioGestor;
import estiveaqui.servidor.util.GeraChaves;
import estiveaqui.sql.AppGestorDB;
import haroldo.util.sql.ConexaoDB;
import estiveaqui.sql.mo.AppGestorMO;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

public class LoginComEMail extends RegraNegocioGestor
{
  private static final Logger log = LogManager.getLogger();

  @Override
  public DadosOutVO acao(DadosInVO dadosInVo) throws EstiveAquiException
  {
    log.info("Entrando em LoginComEMail.acao()");

    LoginEMailInVO loginEMailInVO = (LoginEMailInVO) dadosInVo;
    LoginComEMailOutVO loginComEMailOutVO = new LoginComEMailOutVO();

    ConexaoDB connDB = null;
    try
    {
      //  Valida a versão do app.
      Versao.validaVersao(loginEMailInVO, new Versao(1, 0, 0), new Versao(1, 0, 0));

      //
      //  Validações com acesso ao BD.
      ///////////////////////////////////////////////////////////////////////////
      connDB = new ConexaoDB("jdbc/EstiveAqui");

      AppGestorDB appGestorDb = new AppGestorDB(connDB);
      AppGestorMO appGestorMO = appGestorDb.buscaRegistroEmail(loginEMailInVO.getEmail().trim());
      
      //  E-Mail não existe?
      if (appGestorMO == null)
        throw new RegraDeNegocioException(CodigoErro.EMAIL_NAO_CADASTRADO);

      //  Senha confere?
      if (! appGestorMO.getSenha().equals(Criptografa.criptografa(loginEMailInVO.getSenha(), appGestorMO.getIdAppGestor())))
        throw new RegraDeNegocioException(CodigoErro.SENHA_INCORRETA);

      //  Senha esta vencida?
      if (appGestorMO.getSenhaVencida() == 1)
        throw new RegraDeNegocioException(CodigoErro.SENHA_VENCIDA);
        
      //  Existem lançamentos ou tokens cadastrados?
      loginComEMailOutVO.setExisteHistorico(appGestorDb.existeHistorico(appGestorMO.getIdAppGestor()));
      
      //  Gera nova chave para o AppGestor para este login.
      String identificadorAppGestor = GeraChaves.identificadorAppGestor();
      appGestorDb.atualizaIdenficiador(appGestorMO.getIdAppGestor(), identificadorAppGestor);

      connDB.getConn().commit();

      loginComEMailOutVO.setIdentificadorAppGestor(identificadorAppGestor);
      loginComEMailOutVO.setEmailValidado(appGestorMO.getCodValidacaoEMail() == null);
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

    log.info("Saindo de LoginComEMail.acao()");
    return loginComEMailOutVO;
  }
}
