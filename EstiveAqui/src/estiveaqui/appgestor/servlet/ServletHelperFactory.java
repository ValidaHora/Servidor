package estiveaqui.appgestor.servlet;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.appgestor.AppGestorParametrosSrvlt;
import estiveaqui.appgestor.DadosAppGestorInVO;
import estiveaqui.appgestor.RegraNegocioGestor;
import estiveaqui.appgestor.cadastraappgestor.CadastraComPassClock;
import estiveaqui.appgestor.cadastraappgestor.CadastraComPassClockJson;
import estiveaqui.appgestor.cadastraappgestor.CadastraComPassClockParametrosSrvlt;
import estiveaqui.appgestor.gerencia.lancamento.GerenciaLancamento;
import estiveaqui.appgestor.gerencia.lancamento.GerenciaLancamentoJson;
import estiveaqui.appgestor.gerencia.lancamento.GerenciaLancamentoParametrosSrvlt;
import estiveaqui.appgestor.gerencia.passclock.GerenciaPassClock;
import estiveaqui.appgestor.gerencia.passclock.GerenciaPassClockJson;
import estiveaqui.appgestor.gerencia.passclock.GerenciaPassClockParametrosSrvlt;
import estiveaqui.appgestor.gerencia.usuario.GerenciaAppUsuario;
import estiveaqui.appgestor.gerencia.usuario.GerenciaAppUsuarioJson;
import estiveaqui.appgestor.gerencia.usuario.GerenciaAppUsuarioParametrosSrvlt;
import estiveaqui.appgestor.ledados.LeDados;
import estiveaqui.appgestor.ledados.LeDadosJson;
import estiveaqui.appgestor.ledados.LeDadosParametrosSrvlt;
import estiveaqui.appgestor.login.email.CadastraComEMail;
import estiveaqui.appgestor.login.email.EMailParametrosSrvlt;
import estiveaqui.appgestor.login.email.EnviaConfirmaEMail;
import estiveaqui.appgestor.login.email.LoginComEMail;
import estiveaqui.appgestor.login.email.LoginComEMailJson;
import estiveaqui.appgestor.login.email.alterasenha.AlteraSenhaEMail;
import estiveaqui.appgestor.login.email.alterasenha.AlteraSenhaEMailParametrosSrvlt;
import estiveaqui.appgestor.login.email.recuperasenha.RecuperaSenhaEMail;
import estiveaqui.appgestor.login.email.recuperasenha.RecuperaSenhaEMailParametrosSrvlt;
import estiveaqui.appgestor.relatorio.GeraRelatorio;
import estiveaqui.appgestor.relatorio.GeraRelatorioJson;
import estiveaqui.appgestor.relatorio.GeraRelatorioParametrosSrvlt;
import estiveaqui.dados.JsonResposta;
import estiveaqui.dados.JsonRespostaNula;
import estiveaqui.servlet.ServletHelperFactoryException;
import estiveaqui.servlet.ServletParametros;
import estiveaqui.servlet.ServletParametrosException;

/**
 * Classe criada para automatizar a criação e facilitar a manutenção das chamadas das Servlets e as ações
 * a serem tomadas.<BR>
 * Esta classe amarra os parâmetros recebidos pela servlet com a ação a ser tomada. Em seguida, amarra
 * o retorno da ação tomada e monta uma string Json para retorno ao chamador da servlet.
 * 
 * @author Haroldo
 *
 */
public class ServletHelperFactory
{
  private static final Logger log = LogManager.getLogger();

  private String             operacao;
  private ServletParametros  servletParametros;
  private RegraNegocioGestor regraNegocioGestor;
  private JsonResposta       jsonResposta;

  /**
   * Método onde as novas funcionalidades são incluídas para amarrar a entrada, com a ação e com a saída.<BR>
   * Para usar este método, é necessário:<BR>
   * <LI>Incluir um opção <B>case</B> no <B>switch()</B></LI> <LI>Ler os parâmetros instanciando uma classe que extende {@link estiveaqui.servlet.ServletParametros0}</LI> <LI>Tratar
   * as regras de negócio instanciando uma classe que extende {@link estiveaqui.appgestor.RegraNegocioGestor}</LI> <LI>E montar a resposta da servlet em JSon instanciando uma
   * classe que extende {@link estiveaqui.dados.JsonResposta}</LI>
   * 
   * @param request
   * @throws ServletHelperFactoryException
   * @throws IOException
   */
  public ServletHelperFactory(HttpServletRequest request) throws ServletHelperFactoryException, IOException, ServletParametrosException
  {
    operacao = request.getRequestURI();
    int pos = operacao.indexOf('/', 1);
    if (pos <= 0)
      throw new ServletHelperFactoryException(operacao);
    operacao = operacao.substring(pos);

    log.info("Operacao solicitada - Início: {}", operacao);
    
    switch (operacao)
    {
      case "/AppGestor/LeDados1":
        servletParametros = new LeDadosParametrosSrvlt(request);
        regraNegocioGestor = new LeDados();
        jsonResposta = new LeDadosJson();
        break;

      case "/AppGestor/CadastraComEMail1":
        servletParametros = new EMailParametrosSrvlt(request, "Cadastra com e-mail");
        regraNegocioGestor = new CadastraComEMail();
        jsonResposta = new LoginComEMailJson("CadastraComEMail");
        break;

      case "/AppGestor/LoginComEMail1":
        servletParametros = new EMailParametrosSrvlt(request, "Login com e-mail");
        regraNegocioGestor = new LoginComEMail();
        jsonResposta = new LoginComEMailJson("LoginComEMail");
        break;
     
      case "/AppGestor/AlteraSenhaEMail1":
        servletParametros = new AlteraSenhaEMailParametrosSrvlt(request);
        regraNegocioGestor = new AlteraSenhaEMail();
        jsonResposta = new JsonRespostaNula("AlteraSenhaEMail");
        break;
        
      case "/AppGestor/EnviaConfirmaEMail1":
        servletParametros = new AppGestorParametrosSrvlt(request, "EnviaConfirmaEMail", new DadosAppGestorInVO());
        regraNegocioGestor = new EnviaConfirmaEMail();
        jsonResposta = new JsonRespostaNula("EnviaConfirmaEMail");
        break;
        
      case "/AppGestor/RecuperaSenha1":
        servletParametros = new RecuperaSenhaEMailParametrosSrvlt(request);
        regraNegocioGestor = new RecuperaSenhaEMail();
        jsonResposta = new JsonRespostaNula("RecuperaSenhaEMail");
        break;

//      case "/AppGestor/LeLancamentosPeriodo1":
//      case "/LeLancamentosPeriodo1":
//        servletParametros = new LeLancamentosPeriodoParametrosSrvlt(request);
//        regraNegocioGestor = new LeLancamentosPeriodo();
//        jsonResposta = new LeLancamentosPeriodoJson();
//        break;
//
      case "/AppGestor/CadastraComPassClock1":
        servletParametros = new CadastraComPassClockParametrosSrvlt(request);
        regraNegocioGestor = new CadastraComPassClock();
        jsonResposta = new CadastraComPassClockJson();
        break;

      case "/AppGestor/GeraRelatorio1":
        servletParametros = new GeraRelatorioParametrosSrvlt(request);
        regraNegocioGestor = new GeraRelatorio();
        jsonResposta = new GeraRelatorioJson();
        break;

      case "/AppGestor/Gerencia/PassClock1":
        servletParametros = new GerenciaPassClockParametrosSrvlt(request);
        regraNegocioGestor = new GerenciaPassClock();
        jsonResposta = new GerenciaPassClockJson();
        break;

      case "/AppGestor/Gerencia/Lancamento1":
        servletParametros = new GerenciaLancamentoParametrosSrvlt(request);
        regraNegocioGestor = new GerenciaLancamento();
        jsonResposta = new GerenciaLancamentoJson();
        break;

      case "/AppGestor/Gerencia/AppUsuario1":
        servletParametros = new GerenciaAppUsuarioParametrosSrvlt(request);
        regraNegocioGestor = new GerenciaAppUsuario();
        jsonResposta = new GerenciaAppUsuarioJson();
        break;

      default:
        log.warn("Operacao não reconhecida: {}", operacao);
        throw new ServletHelperFactoryException(operacao);
    }
    log.info("Operacao solicitada - Fim: {}", operacao);
  }

  /**
   * Retorna a instância da classe que cuida dos parâmetros da servlet para esta operação.
   * 
   * @return
   * @throws ServletHelperFactoryException
   */
  public ServletParametros getInstanceServletParametros() throws ServletHelperFactoryException
  {
    return servletParametros;
  }

  /**
   * Retorna a instância da classe que implementa e executa as regras de negócio para esta operação.
   * 
   * @return
   * @throws ServletHelperFactoryException
   */
  public RegraNegocioGestor getInstanceRegraNegocioGestor() throws ServletHelperFactoryException
  {
    return regraNegocioGestor;
  }

  /**
   * Retorna a instância da classe que monta o JSon de resposta para o chamador desta operação.
   * 
   * @return
   * @throws ServletHelperFactoryException
   */
  public JsonResposta getInstanceJsonResposta() throws ServletHelperFactoryException
  {
    return jsonResposta;
  }

  /**
   * Retorna o nome da operação que esta classe Helper está tratando.
   * 
   * @return
   */
  public String getOperacao()
  {
    return operacao;
  }
}
