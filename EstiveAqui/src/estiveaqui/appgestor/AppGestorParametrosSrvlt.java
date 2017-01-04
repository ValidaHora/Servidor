package estiveaqui.appgestor;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.appgestor.servlet.ServletParametrosAppGestor;
import estiveaqui.servlet.ServletParametrosException;

public class AppGestorParametrosSrvlt extends ServletParametrosAppGestor
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
    dadosAppGestorInVo.setIdentificadorAppGestor(getIdentificacaoAppGestor(true));

    return dadosAppGestorInVo;
  }
}
