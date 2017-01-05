package estiveaqui.apppassclockvirtual.servlet;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.CodigoErro;
import estiveaqui.RegraDeNegocioException;
import estiveaqui.apppassclockvirtual.AtivaPassClockVirtual;
import estiveaqui.apppassclockvirtual.AtivaPassClockVirtualInVO;
import estiveaqui.apppassclockvirtual.AtivaPassClockVirtualOutVO;
import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno;
import estiveaqui.servlet.ServletEstiveAqui;
import estiveaqui.servlet.ServletParametrosException;

/**
 * Servlet implementation class SrvltAtivaPassClockVirtual
 */
@WebServlet(name = "AtivaPassClockVirtual", urlPatterns = { "/AtivaPassClockVirtual" })
public class SrvltAtivaPassClockVirtual extends ServletEstiveAqui implements Servlet
{
  private static final long serialVersionUID = 3750289301290360086L;
  private static final Logger log = LogManager.getLogger();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    //  Prepara a codificação dos caracteres.
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    //  Para montagem da mensagem de resposta.
    JsonMsgRetorno jsonMsg = new JsonMsgRetorno("AtivaPassClockVirtual");

    try
    {
      //
      //  Interpreta os parâmetros.
      SrvltAtivaPassClockVirtualParametros srvParam = new SrvltAtivaPassClockVirtualParametros(request); 
      AtivaPassClockVirtualInVO ativaPassClockVirtualInVO =  srvParam.getParametros();

      //
      //  Lê e valida as informações do usuário
      AtivaPassClockVirtual ativaPassClockVirtual = new AtivaPassClockVirtual();
      AtivaPassClockVirtualOutVO ativaPassClockVirtualOutVO = ativaPassClockVirtual.cadastra(ativaPassClockVirtualInVO);
     
      //
      //  Monta a mensagem de resposta
      jsonMsg.put(ChaveJSON.AP, ativaPassClockVirtualOutVO.getApelidoPassClock());
      jsonMsg.put(ChaveJSON.PC, "" + ativaPassClockVirtualOutVO.getNumeroPassClock());
      jsonMsg.put(ChaveJSON.SM, "" + ativaPassClockVirtualOutVO.getSementeToken());
    }
    catch (ServletParametrosException e)
    {
      jsonMsg.putMsgErro(CodigoErro.ERRO_INTERNO, e);
      log.error(e.getMessage());
      e.printStackTrace();
    }
    catch (RegraDeNegocioException e)
    {
      jsonMsg.putMsgErro(CodigoErro.ERRO_INTERNO, e);
      log.error(e.getMessage());
    }
    catch (Throwable t)
    {
      jsonMsg.putMsgErro(CodigoErro.ERRO_INTERNO, t);
      log.fatal(t.getMessage());
      t.printStackTrace();
    }

    //  Retorna a mensagem de resposta.
    log.debug("Retornando mensagem de resposta...");
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.getWriter().println(jsonMsg.getJsonMsg());
  }
}
