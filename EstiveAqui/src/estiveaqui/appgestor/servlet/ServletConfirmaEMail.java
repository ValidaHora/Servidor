package estiveaqui.appgestor.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.EstiveAquiException;
import estiveaqui.appgestor.login.email.ConfirmaEMail;

@WebServlet("/AppGestor/ConfirmaEMail")
public class ServletConfirmaEMail extends HttpServlet
{
  private static final long   serialVersionUID          = -7923637479659529810L;
  private static final Logger log                       = LogManager.getLogger();

  private static final String paginaOk                  = "Email confirmado!";
  private static final String paginaCodigoNaoEncontrado = "Confirmação inválida. Peça novo e-mail de confirmação do seu endereço de e-mail.";
  private static final String paginaErro                = "Erro na confirmação de e-mail. Tente mais tarde ou solicite outro e-mail de confirmação";

  /**
   * Trata a requisição de confirmação de e-mail.
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    try
    {
      //  Interpreta parâmetros.
      String codAtivacaoEMail = request.getParameter("COD");
      if (codAtivacaoEMail == null)
      {
        response.getWriter().println(paginaErro);
        return;
      }

      //  Executa as regras de negócio.
      ConfirmaEMail confirmaEMail = new ConfirmaEMail();
      boolean confirmado = confirmaEMail.confirma(codAtivacaoEMail);

      //  Retorna a mensagem de resposta.
      log.debug("Retornando mensagem de resposta...");
      if (confirmado)
        response.getWriter().println(paginaOk);
      else
        response.getWriter().println(paginaCodigoNaoEncontrado);

      return;
    }
    catch (EstiveAquiException e)
    {
      log.error(e.getMessage());
    }
    catch (Throwable t)
    {
      log.fatal(t.getMessage());
      t.printStackTrace();
    }

    response.getWriter().println(paginaErro);
  }
}
