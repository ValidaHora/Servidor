package estiveaqui.appgestor;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.servlet.ServletParametros0;
import estiveaqui.servlet.ServletParametrosException;

public class AppGestorParametrosSrvlt extends ServletParametros0
{
  private DadosAppGestorInVO dadosAppGestorInVo;
  
  public AppGestorParametrosSrvlt(HttpServletRequest request, String acao, DadosAppGestorInVO dadosAppGestorInVo) throws IOException, ServletParametrosException
  {
    super(request, acao, dadosAppGestorInVo);
    this.dadosAppGestorInVo = dadosAppGestorInVo;
  }

  /**
   * Lê e retorna os parâmetros para o LeDados.
   * 
   * @return
   * @throws ServletParametrosException
   */
  @Override
  public DadosAppGestorInVO getParametros() throws ServletParametrosException  
  {
    dadosAppGestorInVo.setIdentificadorAppGestor(getIdentificacaoApp0(true));

    return dadosAppGestorInVo;
  }
}
