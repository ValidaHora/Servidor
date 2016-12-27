package estiveaqui.appusuario.servlet;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.appusuario.RegraNegocioAppUsuario;
import estiveaqui.appusuario.cadastra.CadastraAppUsuario;
import estiveaqui.appusuario.cadastra.CadastraAppUsuarioParametrosSrvlt;
import estiveaqui.appusuario.cadastra.CadastraAppusuarioJson;
import estiveaqui.appusuario.lancahora.LancaHora;
import estiveaqui.appusuario.lancahora.LancaHoraJson;
import estiveaqui.appusuario.lancahora.LancaHoraParametrosSrvlt;
import estiveaqui.appusuario.lancahoras.LancaHoras;
import estiveaqui.appusuario.lancahoras.LancaHorasJson;
import estiveaqui.appusuario.lancahoras.LancaHorasParametrosSrvlt;
import estiveaqui.appusuario.leappusuario.LeAppUsuario;
import estiveaqui.appusuario.leappusuario.LeAppUsuarioJson;
import estiveaqui.appusuario.leappusuario.LeAppUsuarioParametrosSrvlt;
import estiveaqui.appusuario.lesementes.LeSementes;
import estiveaqui.appusuario.lesementes.LeSementesJson;
import estiveaqui.appusuario.lesementes.LeSementesParametrosSrvlt;
import estiveaqui.dados.JsonResposta;
import estiveaqui.servlet.ServletHelperFactoryException;
import estiveaqui.servlet.ServletParametros;
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
public class ServletHelperFactory
{
  private String                 operacao;
  private ServletParametros      servletParametros;
  private RegraNegocioAppUsuario regraNegocioAppUsuario;
  private JsonResposta           jsonResposta;

  /**
   * Retorna a inst�ncia da classe que cuida dos par�metros da servlet para esta opera��o.
   * 
   * @return
   * @throws ServletHelperFactoryException
   */
  public ServletParametros getInstanceServletParametros() throws ServletHelperFactoryException
  {
    return servletParametros;
  }

  /**
   * Retorna a inst�ncia da classe que implementa e executa as regras de neg�cio para esta opera��o.
   * 
   * @return
   * @throws ServletHelperFactoryException
   */
  public RegraNegocioAppUsuario getInstanceRegraNegocioUsuario() throws ServletHelperFactoryException
  {
    return regraNegocioAppUsuario;
  }

  /**
   * Retorna a inst�ncia da classe que monta o JSon de resposta para o chamador desta opera��o.
   * 
   * @return
   * @throws ServletHelperFactoryException
   */
  public JsonResposta getInstanceJsonResposta() throws ServletHelperFactoryException
  {
    return jsonResposta;
  }

  /**
   * Retorna o nome da opera��o que esta classe Helper est� tratando.
   * 
   * @return
   */
  public String getOperacao()
  {
    return operacao;
  }

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
   * @throws ServletParametrosException 
   */
  public ServletHelperFactory(HttpServletRequest request) throws ServletHelperFactoryException, IOException, ServletParametrosException
  {
    operacao = request.getRequestURI();
    int pos = operacao.indexOf('/', 1);
    if (pos <= 0)
      throw new ServletHelperFactoryException(operacao);
    operacao = operacao.substring(pos);

    if (operacao == null)
      throw new ServletHelperFactoryException(operacao);

    switch (operacao)
    {
      case "/AppUsuario/Cadastra1":
        servletParametros = new CadastraAppUsuarioParametrosSrvlt(request);
        regraNegocioAppUsuario = new CadastraAppUsuario();
        jsonResposta = new CadastraAppusuarioJson();
        break;

      case "/AppUsuario/LancaHora1":
        servletParametros = new LancaHoraParametrosSrvlt(request);
        regraNegocioAppUsuario = new LancaHora();
        jsonResposta = new LancaHoraJson();
        break;
        
      case "/AppUsuario/LancaHoras1":
        servletParametros = new LancaHorasParametrosSrvlt(request);
        regraNegocioAppUsuario = new LancaHoras();
        jsonResposta = new LancaHorasJson();
        break;

      case "/AppUsuario/LeInfos1":
        servletParametros = new LeAppUsuarioParametrosSrvlt(request);
        regraNegocioAppUsuario = new LeAppUsuario();
        jsonResposta = new LeAppUsuarioJson();
        break;
        
      case "/AppUsuario/GetSementes1":
        servletParametros = new LeSementesParametrosSrvlt(request);
        regraNegocioAppUsuario = new LeSementes();
        jsonResposta = new LeSementesJson();
        break;
        
      default:
        throw new ServletHelperFactoryException(operacao);
    }
  }
}
