package estiveaqui.appgestor.login.email;

import java.sql.SQLException;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;
import estiveaqui.sql.AppGestorDB;
import haroldo.util.sql.ConexaoDB;

public class ConfirmaEMail
{
  private static final Logger log = LogManager.getLogger();

  public boolean confirma(String codAtivacaoEMail) throws EstiveAquiException
  {
    log.info("Entrando em ConfirmaEMail.confirma()");

    ConexaoDB connDB = null;
    try
    {
      //
      //  ValidaçÃµes com acesso ao BD.
      ///////////////////////////////////////////////////////////////////////////
      connDB = new ConexaoDB("jdbc/EstiveAqui");

      AppGestorDB appGestorDb = new AppGestorDB(connDB);

      //  Código de ativação encontrado?
      boolean emailValidado = appGestorDb.validaEMail(codAtivacaoEMail);

      //  Remove o código de validação.
      connDB.getConn().commit();
      
      return emailValidado;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw new EstiveAquiException(CodigoErro.ERRO_INTERNO, e.getMessage());
    }
    catch (NamingException e)
    {
      e.printStackTrace();
      throw new EstiveAquiException(CodigoErro.ERRO_INTERNO, e.getMessage());
    }
    finally
    {
      if (connDB != null)
        connDB.fechaConexao();
      log.info("Saindo de ConfirmaEMail.confirma()");
    }
  }

}
