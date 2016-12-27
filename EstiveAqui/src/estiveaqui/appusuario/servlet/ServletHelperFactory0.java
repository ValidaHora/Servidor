package estiveaqui.appusuario.servlet;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.appusuario.RegraNegocioAppUsuario;
import estiveaqui.appusuario.cadastra.CadastraAppUsuario;
import estiveaqui.appusuario.cadastra.CadastraAppUsuarioParametrosSrvlt0;
import estiveaqui.appusuario.cadastra.CadastraAppusuarioJson0;
import estiveaqui.appusuario.lancahora.LancaHora0;
import estiveaqui.appusuario.lancahora.LancaHoraJson0;
import estiveaqui.appusuario.lancahora.LancaHoraParametrosSrvlt0;
import estiveaqui.appusuario.lancahoras.LancaHoras;
import estiveaqui.appusuario.lancahoras.LancaHoras0;
import estiveaqui.appusuario.lancahoras.LancaHorasJson0;
import estiveaqui.appusuario.lancahoras.LancaHorasParametrosSrvlt0;
import estiveaqui.appusuario.leappusuario.LeAppUsuario;
import estiveaqui.appusuario.leappusuario.LeAppUsuarioJson0;
import estiveaqui.appusuario.leappusuario.LeAppUsuarioParametrosSrvlt0;
import estiveaqui.dados.JsonResposta0;
import estiveaqui.servlet.ServletHelperFactoryException;
import estiveaqui.servlet.ServletParametros0;
import estiveaqui.servlet.ServletParametrosException;

@Deprecated
public class ServletHelperFactory0
{
  private String                 operacao;
  private ServletParametros0     servletParametros;
  private RegraNegocioAppUsuario regraNegocioAppUsuario;
  private JsonResposta0          jsonResposta;

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

  /**
   * M�todo onde as novas funcionalidades s�o inclu�das para amarrar a entrada, com a a��o e com a sa�da.<BR>
   * Para usar este m�todo, � necess�rio:<BR>
   * <LI>Incluir um op��o <B>case</B> no <B>switch()</B></LI> <LI>Ler os par�metros instanciando uma classe que extende
   * {@link estiveaqui.servlet.ServletParametros0}</LI> <LI>Tratar as regras de neg�cio instanciando uma classe que
   * extende {@link estiveaqui.appgestor.RegraNegocioGestor}</LI> <LI>E montar a resposta da servlet em JSon
   * instanciando uma classe que extende {@link estiveaqui.dados.JsonResposta}</LI>
   * 
   * @param request
   * @throws ServletHelperFactoryException
   * @throws IOException
   * @throws ServletParametrosException
   */
  @Deprecated
  public ServletHelperFactory0(HttpServletRequest request) throws ServletHelperFactoryException, IOException, ServletParametrosException
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
      case "/AppUsuario/Cadastra":
      case "/CadastraAppUsuario":
        servletParametros = new CadastraAppUsuarioParametrosSrvlt0(request);
        regraNegocioAppUsuario = new CadastraAppUsuario();
        jsonResposta = new CadastraAppusuarioJson0();
        break;

      case "/AppUsuario/LancaHora":
      case "/LancaHora":
        servletParametros = new LancaHoraParametrosSrvlt0(request);
        regraNegocioAppUsuario = new LancaHora0();
        jsonResposta = new LancaHoraJson0();
        break;

      case "/AppUsuario/LancaHoras":
        servletParametros = new LancaHorasParametrosSrvlt0(request);
        regraNegocioAppUsuario = new LancaHoras0();
        jsonResposta = new LancaHorasJson0();
        break;

      case "/AppUsuario/LeInfos":
      case "/LeAppUsuario":
        servletParametros = new LeAppUsuarioParametrosSrvlt0(request);
        regraNegocioAppUsuario = new LeAppUsuario();
        jsonResposta = new LeAppUsuarioJson0();
        break;

      default:
        throw new ServletHelperFactoryException(operacao);
    }
  }
}
