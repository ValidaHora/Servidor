package estiveaqui.appgestor.servlet;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.appgestor.AppGestorParametrosSrvlt0;
import estiveaqui.appgestor.DadosAppGestorInVO;
import estiveaqui.appgestor.RegraNegocioGestor;
import estiveaqui.appgestor.gerencia.lancamento.GerenciaLancamento;
import estiveaqui.appgestor.gerencia.lancamento.GerenciaLancamentoJson0;
import estiveaqui.appgestor.gerencia.lancamento.GerenciaLancamentoParametrosSrvlt0;
import estiveaqui.appgestor.gerencia.passclock.GerenciaPassClock0;
import estiveaqui.appgestor.gerencia.passclock.GerenciaPassClockJson0;
import estiveaqui.appgestor.gerencia.passclock.GerenciaPassClockParametrosSrvlt0;
import estiveaqui.appgestor.gerencia.usuario.GerenciaAppUsuario;
import estiveaqui.appgestor.gerencia.usuario.GerenciaAppUsuarioJson0;
import estiveaqui.appgestor.gerencia.usuario.GerenciaAppUsuarioParametrosSrvlt0;
import estiveaqui.appgestor.ledados.LeDados;
import estiveaqui.appgestor.ledados.LeDadosJson0;
import estiveaqui.appgestor.ledados.LeDadosParametrosSrvlt0;
import estiveaqui.appgestor.login.email.CadastraComEMail;
import estiveaqui.appgestor.login.email.EMailParametrosSrvlt0;
import estiveaqui.appgestor.login.email.EnviaConfirmaEMail;
import estiveaqui.appgestor.login.email.LoginComEMail;
import estiveaqui.appgestor.login.email.LoginComEMailJson0;
import estiveaqui.appgestor.login.email.alterasenha.AlteraSenhaEMail;
import estiveaqui.appgestor.login.email.alterasenha.AlteraSenhaEMailParametrosSrvlt0;
import estiveaqui.appgestor.login.email.recuperasenha.RecuperaSenhaEMail;
import estiveaqui.appgestor.login.email.recuperasenha.RecuperaSenhaEMailParametrosSrvlt0;
import estiveaqui.appgestor.relatorio.GeraRelatorio;
import estiveaqui.appgestor.relatorio.GeraRelatorioJson0;
import estiveaqui.appgestor.relatorio.GeraRelatorioParametrosSrvlt0;
import estiveaqui.dados.JsonResposta0;
import estiveaqui.dados.JsonRespostaNula0;
import estiveaqui.servlet.ServletHelperFactoryException;
import estiveaqui.servlet.ServletParametros0;
import estiveaqui.servlet.ServletParametrosException;

/**
 * Classe criada para automatizar a cria��o e facilitar a manuten��o das chamadas das Servlets e as a��es
 * a serem tomadas.<BR>
 * Esta classe amarra os par�metros recebidos pela servlet com a a��o a ser tomada. Em seguida, amarra
 * o retorno da a��o tomada e monta uma string Json para retorno ao chamador da servlet.
 * 
 * @author Haroldo
 *
 */
@Deprecated
public class ServletHelperFactory0
{
  private static final Logger log = LogManager.getLogger();

  private String             operacao;
  private ServletParametros0  servletParametros;
  private RegraNegocioGestor regraNegocioGestor;
  private JsonResposta0       jsonResposta;

  /**
   * M�todo onde as novas funcionalidades s�o inclu�das para amarrar a entrada, com a a��o e com a sa�da.<BR>
   * Para usar este m�todo, � necess�rio:<BR>
   * <LI>Incluir um op��o <B>case</B> no <B>switch()</B></LI> <LI>Ler os par�metros instanciando uma classe que extende {@link estiveaqui.servlet.ServletParametros0}</LI> <LI>Tratar
   * as regras de neg�cio instanciando uma classe que extende {@link estiveaqui.appgestor.RegraNegocioGestor}</LI> <LI>E montar a resposta da servlet em JSon instanciando uma
   * classe que extende {@link estiveaqui.dados.JsonResposta}</LI>
   * 
   * @param request
   * @throws ServletHelperFactoryException
   * @throws IOException
   */
  @Deprecated
  public ServletHelperFactory0(HttpServletRequest request) throws ServletHelperFactoryException, IOException, ServletParametrosException
  {
    operacao = request.getRequestURI();
    int pos = operacao.indexOf('/', 1);
    if (pos <= 0)
      throw new ServletHelperFactoryException(operacao);
    operacao = operacao.substring(pos);

    log.info("Operacao solicitada - In�cio: {}", operacao);
    
    switch (operacao)
    {
      case "/AppGestor/LeDados":
      case "/LeDados":
        servletParametros = new LeDadosParametrosSrvlt0(request);
        regraNegocioGestor = new LeDados();
        jsonResposta = new LeDadosJson0();
        break;

      case "/AppGestor/CadastraComEMail":
        servletParametros = new EMailParametrosSrvlt0(request, "Cadastra com e-mail");
        regraNegocioGestor = new CadastraComEMail();
        jsonResposta = new LoginComEMailJson0("CadastraComEMail");
        break;

      case "/AppGestor/LoginComEMail":
        servletParametros = new EMailParametrosSrvlt0(request, "Login com e-mail");
        regraNegocioGestor = new LoginComEMail();
        jsonResposta = new LoginComEMailJson0("LoginComEMail");
        break;
     
      case "/AppGestor/AlteraSenhaEMail":
        servletParametros = new AlteraSenhaEMailParametrosSrvlt0(request);
        regraNegocioGestor = new AlteraSenhaEMail();
        jsonResposta = new JsonRespostaNula0("AlteraSenhaEMail");
        break;
        
      case "/AppGestor/EnviaConfirmaEMail":
        servletParametros = new AppGestorParametrosSrvlt0(request, "EnviaConfirmaEMail", new DadosAppGestorInVO());
        regraNegocioGestor = new EnviaConfirmaEMail();
        jsonResposta = new JsonRespostaNula0("EnviaConfirmaEMail");
        break;
        
      case "/AppGestor/RecuperaSenha":
        servletParametros = new RecuperaSenhaEMailParametrosSrvlt0(request);
        regraNegocioGestor = new RecuperaSenhaEMail();
        jsonResposta = new JsonRespostaNula0("RecuperaSenhaEMail");
        break;

////      case "/AppGestor/LeLancamentosPeriodo":
////      case "/LeLancamentosPeriodo":
////        servletParametros = new LeLancamentosPeriodoParametrosSrvlt(request);
////        regraNegocioGestor = new LeLancamentosPeriodo();
////        jsonResposta = new LeLancamentosPeriodoJson();
////        break;
////
//      case "/AppGestor/CadastraComPassClock":
//      case "/CadastraComPassClock":
//        servletParametros = new CadastraComPassClockParametrosSrvlt(request);
//        regraNegocioGestor = new CadastraComPassClock();
//        jsonResposta = new CadastraComPassClockJson();
//        break;
//
      case "/AppGestor/GeraRelatorio":
        servletParametros = new GeraRelatorioParametrosSrvlt0(request);
        regraNegocioGestor = new GeraRelatorio();
        jsonResposta = new GeraRelatorioJson0();
        break;

      case "/AppGestor/Gerencia/PassClock":
        servletParametros = new GerenciaPassClockParametrosSrvlt0(request);
        regraNegocioGestor = new GerenciaPassClock0();
        jsonResposta = new GerenciaPassClockJson0();
        break;

      case "/AppGestor/Gerencia/Lancamento":
        servletParametros = new GerenciaLancamentoParametrosSrvlt0(request);
        regraNegocioGestor = new GerenciaLancamento();
        jsonResposta = new GerenciaLancamentoJson0();
        break;

      case "/AppGestor/Gerencia/AppUsuario":
        servletParametros = new GerenciaAppUsuarioParametrosSrvlt0(request);
        regraNegocioGestor = new GerenciaAppUsuario();
        jsonResposta = new GerenciaAppUsuarioJson0();
        break;

      default:
        log.warn("Operacao n�o reconhecida: {}", operacao);
        throw new ServletHelperFactoryException(operacao);
    }
    log.info("Operacao solicitada - Fim: {}", operacao);
  }

  /**
   * Retorna a inst�ncia da classe que cuida dos par�metros da servlet para esta opera��o.
   * 
   * @return
   * @throws ServletHelperFactoryException
   */
  @Deprecated
  public ServletParametros0 getInstanceServletParametros() throws ServletHelperFactoryException
  {
    return servletParametros;
  }

  /**
   * Retorna a inst�ncia da classe que implementa e executa as regras de neg�cio para esta opera��o.
   * 
   * @return
   * @throws ServletHelperFactoryException
   */
  @Deprecated
  public RegraNegocioGestor getInstanceRegraNegocioGestor() throws ServletHelperFactoryException
  {
    return regraNegocioGestor;
  }

  /**
   * Retorna a inst�ncia da classe que monta o JSon de resposta para o chamador desta opera��o.
   * 
   * @return
   * @throws ServletHelperFactoryException
   */
  @Deprecated
  public JsonResposta0 getInstanceJsonResposta() throws ServletHelperFactoryException
  {
    return jsonResposta;
  }

  /**
   * Retorna o nome da opera��o que esta classe Helper est� tratando.
   * 
   * @return
   */
  @Deprecated
  public String getOperacao()
  {
    return operacao;
  }
}
