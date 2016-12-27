package estiveaqui.appgestor.servlet;

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
import estiveaqui.appgestor.RegraNegocioGestor;
import estiveaqui.dados.JsonMsgRetorno;
import estiveaqui.dados.JsonResposta;
import estiveaqui.http.HTTPValidaHora;
import estiveaqui.relatorios.LancamentosExcel;
import estiveaqui.relatorios.Persiste;
import estiveaqui.servlet.ServletEstiveAqui;
import estiveaqui.servlet.ServletHelperFactoryException;
import estiveaqui.servlet.ServletParametros0;
import estiveaqui.servlet.ServletParametrosException;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

/**
 * Implementa de forma padronizada as ações do AppGestor.
 */
@WebServlet(urlPatterns = { "/AppGestor/LeDados", "/AppGestor/LeLancamentosPeriodo",
                            "/AppGestor/CadastraComPassClock", "/AppGestor/GeraRelatorio", "/AppGestor/LeAppUsuario",
                            "/AppGestor/CadastraComEMail", "/AppGestor/LoginComEMail", "/AppGestor/AlteraSenhaEMail",
                            "/AppGestor/EnviaConfirmaEMail", "/AppGestor/RecuperaSenha",
                            "/AppGestor/Gerencia/PassClock", "/AppGestor/Gerencia/Lancamento", "/AppGestor/Gerencia/AppUsuario"})
public class ServletAcao extends ServletEstiveAqui
{
  private static final long   serialVersionUID = -7607404647184462037L;
  private static final Logger log              = LogManager.getLogger();
  private static int contadorLog = 0;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    int contador = ++contadorLog;
    log.debug("ServletAcao({}): Iniciando", contador);
    
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    //  Para montagem da mensagem de resposta.
    JsonMsgRetorno jsonMsg = new JsonMsgRetorno("ServletAcao");
    ServletHelperFactory hf = null;

    try
    {
      //  Instancia a construtora das calsses ServletParametros, RegraNegocioGestor e JsonResposta.
      hf = new ServletHelperFactory(request);
      
      //  Interpreta parâmetros.
      log.debug("ServletAcao({}): Lendo parâmetros.", contador);
      ServletParametros0 servletParametros = hf.getInstanceServletParametros();
      DadosInVO dadosInVO =  servletParametros.getParametros();
      
      //  Executa as regras de negócio.
      log.debug("ServletAcao({}): Executando regras de negócio.", contador);
      RegraNegocioGestor negocio = hf.getInstanceRegraNegocioGestor();
      DadosOutVO dadosOutVo = negocio.acao(dadosInVO);
      
      //  Monta JSON de resposta.
      log.debug("ServletAcao({}): Montando a resposta em JSON.", contador);
      JsonResposta jsonResposta = hf.getInstanceJsonResposta();
      jsonMsg = jsonResposta.getJson(dadosInVO, dadosOutVo);
    }
    catch (ServletHelperFactoryException e)
    {
      jsonMsg.putMsgErro(e);
      log.error(e.getMessage());
    }
    catch (ServletParametrosException e)
    {
      jsonMsg.putMsgErro(e);
      log.error(e.getMessage());
    }
    catch (RegraDeNegocioException e)
    {
      jsonMsg.putMsgErro(e);
      log.error(e.getMessage());
    }
    catch (EstiveAquiException e)
    {
      jsonMsg.putMsgErro(e);
      log.error(e.getMessage());
    }
    catch (Throwable t)
    {
      jsonMsg.putMsgErro(CodigoErro.ERRO_INTERNO, t);
      log.fatal(t.getMessage());
      t.printStackTrace();
    }
    finally
    {
      log.debug("ServletAcao({}): Fim", contador);
    }

    //  Retorna a mensagem de resposta.
    response.getWriter().println(jsonMsg.getJsonMsg());
  }
  
  @Override
  public void init()
  {
    LancamentosExcel.getContext(getServletContext());
    Persiste.getContext(getServletContext());
    HTTPValidaHora.getContext(getServletContext());
  }
}
