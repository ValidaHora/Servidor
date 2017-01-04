package estiveaqui.appgestor;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.servlet.ServletParametros0;
import estiveaqui.servlet.ServletParametrosException;

@Deprecated
public class AppGestorParametrosSrvlt0 extends ServletParametros0
{
  private DadosAppGestorInVO dadosAppGestorInVo;
  
  @Deprecated
  public AppGestorParametrosSrvlt0(HttpServletRequest request, String acao, DadosAppGestorInVO dadosAppGestorInVo) throws IOException, ServletParametrosException
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
  @Deprecated
  public DadosAppGestorInVO getParametros() throws ServletParametrosException  
  {
    dadosAppGestorInVo.setIdentificadorAppGestor(getIdentificacaoApp(true));

    return dadosAppGestorInVo;
  }
}
