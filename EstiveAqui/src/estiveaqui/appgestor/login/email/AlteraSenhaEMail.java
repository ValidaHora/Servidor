package estiveaqui.appgestor.login.email;

import java.sql.SQLException;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import estiveaqui.CodigoErro;
import estiveaqui.Criptografa;
import estiveaqui.EstiveAquiException;
import estiveaqui.RegraDeNegocioException;
import estiveaqui.Versao;
import estiveaqui.appgestor.DadosAppGestorInVO;
import estiveaqui.appgestor.RegraNegocioGestor;
import estiveaqui.sql.AppGestorDB;
import haroldo.util.sql.ConexaoDB;
import estiveaqui.sql.mo.AppGestorMO;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

public class AlteraSenhaEMail extends RegraNegocioGestor
{
  private static final Logger log = LogManager.getLogger();

  @Override
  public DadosOutVO acao(DadosInVO dadosInVo) throws EstiveAquiException
  {
    String idLog = "";
    if (log.isInfoEnabled())
      idLog = ((DadosAppGestorInVO)dadosInVo).getIdentificadorAppGestor().substring(0, 8);
    log.info("Entrando em AlteraSenhaEMail.acao({}...)", idLog);

    AlteraSenhaEMailInVO alteraSenhaEMailInVO = (AlteraSenhaEMailInVO) dadosInVo;

    ConexaoDB connDB = null;
    try
    {
      //  Valida a versão do app.
      Versao.validaVersao(alteraSenhaEMailInVO, new Versao(1, 0, 0), new Versao(1, 0, 0));

      //  Verifica se a nova senha é válida.
      UtilEMail.validaRegrasParaSenha(alteraSenhaEMailInVO.getSenhaNova());

      //
      //  Validações com acesso ao BD.
      ///////////////////////////////////////////////////////////////////////////
      connDB = new ConexaoDB("jdbc/EstiveAqui");

      //  Gestor existe e está habilitado?
      AppGestorDB appGestorDb = new AppGestorDB(connDB);
      AppGestorMO appGestorMO;
      
      //  Valida senha ou código.
      if (alteraSenhaEMailInVO.getSenhaAntiga() != null)
      {
        //  Valida o gestor.
        appGestorMO = validaGestor(connDB, alteraSenhaEMailInVO);
        
        //  Senha confere?
        if (appGestorMO.getSenha() != null)
          if (!appGestorMO.getSenha().equals(Criptografa.criptografa(alteraSenhaEMailInVO.getSenhaAntiga(), appGestorMO.getIdAppGestor())))
            throw new RegraDeNegocioException(CodigoErro.SENHA_INCORRETA);
      }
      else if (alteraSenhaEMailInVO.getCodRecuperaSenha() != null)
      {
        //  Encontra o gestor.
        appGestorMO = appGestorDb.buscaPorCodigoRecuperacaoSenha(alteraSenhaEMailInVO.getCodRecuperaSenha());

        //  Código encontrado?
        if (appGestorMO == null)
          throw new RegraDeNegocioException(CodigoErro.CODIGO_INVALIDO, "Código de recuperação de senha não existe");
        
        //  Se não há código definido.
        if (appGestorMO.getCodRecuperaSenha() == null)
          throw new RegraDeNegocioException(CodigoErro.CODIGO_VENCIDO);
        //  Código correto?
        if (!appGestorMO.getCodRecuperaSenha().equalsIgnoreCase(alteraSenhaEMailInVO.getCodRecuperaSenha()))
          throw new RegraDeNegocioException(CodigoErro.CODIGO_INVALIDO);
        //  Código válido?
        if (appGestorMO.getHrCodRecuperaSenha().plusDays(1).isBefore(DateTime.now()))
          throw new RegraDeNegocioException(CodigoErro.CODIGO_VENCIDO);
        
        //  Limpa o código de recuperação de senha.
        appGestorMO.setCodRecuperaSenha(null);
      }
      else
        throw new RegraDeNegocioException(CodigoErro.SENHA_INCORRETA);

      //  Atualiza a senha e o código de recuperação.
      UtilEMail.setSenhaCriptografada(appGestorMO, alteraSenhaEMailInVO.getSenhaNova());
      appGestorDb.atualizaEMail(appGestorMO);

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
      log.info("Saindo em AlteraSenhaEMail.acao({}...)", idLog);
    }

    return null;
  }
}
