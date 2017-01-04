package estiveaqui.appgestor.servlet;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.servlet.NomeParametroServlet;
import estiveaqui.servlet.ServletParametros;
import estiveaqui.servlet.ServletParametrosException;
import estiveaqui.vo.DadosInVO;

public class ServletParametrosAppGestor extends ServletParametros
{

  @Override
  public DadosInVO getParametros() throws ServletParametrosException
  {
    // TODO Auto-generated method stub
    return null;
  }

  public ServletParametrosAppGestor(HttpServletRequest request, String acao, DadosInVO dadosInVo) throws IOException, ServletParametrosException
  {
    super(request, acao, dadosInVo);
  }
  
  /**
   * Busca e retorna o par�metro do n�mero de identifica��o do appGestor.
   * 
   * ID=<IdentificacaoApp> - N�mero de identifica��o do app.
   * 
   * @param obrigatorio
   * @return
   * @throws ServletParametrosException
   */
  protected String getIdentificacaoAppGestor(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametro(NomeParametroServlet.IdentificacaoAppGestor, obrigatorio, false);
  }
}
