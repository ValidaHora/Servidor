package estiveaqui.appgestor.login.email.alterasenha;

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
import estiveaqui.appgestor.login.email.UtilEMail;
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
      if (((DadosAppGestorInVO)dadosInVo).getIdentificadorAppGestor() != null)
        idLog = ((DadosAppGestorInVO)dadosInVo).getIdentificadorAppGestor().substring(0, 8);
      else
        idLog = "Codigo Senha";
    log.info("Entrando em AlteraSenhaEMail.acao({}...)", idLog);

    AlteraSenhaEMailInVO alteraSenhaEMailInVO = (AlteraSenhaEMailInVO) dadosInVo;

    ConexaoDB connDB = null;
    try
    {
      //  Valida a vers�o do app.
      Versao.validaVersao(alteraSenhaEMailInVO, new Versao(1, 0, 0), new Versao(1, 1, 0));

      //  Verifica se a nova senha � v�lida.
      UtilEMail.validaRegrasParaSenha(alteraSenhaEMailInVO.getSenhaNova());

      //
      //  Valida��es com acesso ao BD.
      ///////////////////////////////////////////////////////////////////////////
      connDB = new ConexaoDB("jdbc/EstiveAqui");

      //  Gestor existe e est� habilitado?
      AppGestorDB appGestorDb = new AppGestorDB(connDB);
      AppGestorMO appGestorMO;
      
      //  Valida senha ou c�digo.
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

        //  C�digo encontrado?
        if (appGestorMO == null)
          throw new RegraDeNegocioException(CodigoErro.CODIGO_INVALIDO, "C�digo de recupera��o de senha n�o existe");
        
        //  Se n�o h� c�digo definido.
        if (appGestorMO.getCodRecuperaSenha() == null)
          throw new RegraDeNegocioException(CodigoErro.CODIGO_VENCIDO);
        //  C�digo correto?
        if (!appGestorMO.getCodRecuperaSenha().equalsIgnoreCase(alteraSenhaEMailInVO.getCodRecuperaSenha()))
          throw new RegraDeNegocioException(CodigoErro.CODIGO_INVALIDO);
        //  C�digo v�lido?
        if (appGestorMO.getHrCodRecuperaSenha().plusDays(1).isBefore(DateTime.now()))
          throw new RegraDeNegocioException(CodigoErro.CODIGO_VENCIDO);
        
        //  Limpa o c�digo de recupera��o de senha.
        appGestorMO.setCodRecuperaSenha(null);
      }
      else
        throw new RegraDeNegocioException(CodigoErro.SENHA_INCORRETA);

      //  Atualiza a senha e o c�digo de recupera��o.
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
