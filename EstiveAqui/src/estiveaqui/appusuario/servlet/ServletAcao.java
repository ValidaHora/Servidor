package estiveaqui.appusuario.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;
import estiveaqui.RegraDeNegocioException;
import estiveaqui.appusuario.RegraNegocioAppUsuario;
import estiveaqui.dados.JsonMsgRetorno;
import estiveaqui.dados.JsonResposta;
import estiveaqui.http.HTTPValidaHora;
import estiveaqui.servlet.ServletEstiveAqui;
import estiveaqui.servlet.ServletHelperFactoryException;
import estiveaqui.servlet.ServletParametros;
import estiveaqui.servlet.ServletParametrosException;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

/**
 * Implementa de forma padronizada as açÃµes do AppUsuario.
 */
@WebServlet(urlPatterns = { "/AppUsuario/Cadastra1", "/AppUsuario/LancaHora1", "/AppUsuario/LeInfos1", "/AppUsuario/LancaHoras1", "/AppUsuario/GetSementes1"})
public class ServletAcao extends ServletEstiveAqui
{
  private static final long   serialVersionUID = -5513094264945496209L;
  private static final Logger log              = LogManager.getLogger();

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    //  Para montagem da mensagem de resposta.
    JsonMsgRetorno jsonMsg = new JsonMsgRetorno("ServletAcao");
    ServletHelperFactory hf = null;

    try
    {
      //  Instancia a construtora das calsses ServletParametros, RegraNegocioGestor e JsonResposta.
      hf = new ServletHelperFactory(request);
      JsonResposta jsonResposta = hf.getInstanceJsonResposta();
      
      //  Interpreta parâmetros.
      ServletParametros servletParametros = hf.getInstanceServletParametros();
      DadosInVO dadosInVO =  servletParametros.getParametros();
      
      //  Executa as regras de negócio.
      RegraNegocioAppUsuario negocio = hf.getInstanceRegraNegocioUsuario();
      DadosOutVO dadosOutVo = negocio.acao(dadosInVO);
      
      //  Monta JSON de resposta.
      jsonMsg = jsonResposta.getJson(dadosInVO, dadosOutVo);
    }
    catch (ServletHelperFactoryException e)
    {
      jsonMsg.addMsgErro(e);
      log.error(e.getMessage());
    }
    catch (ServletParametrosException e)
    {
      jsonMsg.addMsgErro(e);
      log.error(e.getMessage());
    }
    catch (RegraDeNegocioException e)
    {
      jsonMsg.addMsgErro(e);
      log.error(e.getMessage());
    }
    catch (EstiveAquiException e)
    {
      jsonMsg.addMsgErro(e);
      log.error(e.getMessage());
    }
    catch (Throwable t)
    {
      jsonMsg.addMsgErro(CodigoErro.ERRO_INTERNO, t);
      log.fatal(t.getMessage());
      t.printStackTrace();
    }

    //  Retorna a mensagem de resposta.
    log.debug("Retornando mensagem de resposta...");
    response.getWriter().println(jsonMsg.getJsonMsg());
  }
  
  @Override
  public void init()
  {
    HTTPValidaHora.getContext(getServletContext());
  }

}
