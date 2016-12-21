package estiveaqui.apppassclockvirtual.servlet;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.apppassclockvirtual.AtivaPassClockVirtualInVO;
import estiveaqui.servlet.ServletParametros0;
import estiveaqui.servlet.ServletParametrosException;

public class SrvltAtivaPassClockVirtualParametros extends ServletParametros0
{
  private AtivaPassClockVirtualInVO ativaPassClockVirtualInVO = (AtivaPassClockVirtualInVO)dadosInVo;
  
  public SrvltAtivaPassClockVirtualParametros(HttpServletRequest request) throws IOException, ServletParametrosException 
  {
    super(request, "LeUsuario", new AtivaPassClockVirtualInVO());
  }
  
  /**
   * Lê e retorna os parâmetros para o AtivaPassClockVirtual.
   * 
   * @return
   * @throws ServletParametrosException
   */
  public AtivaPassClockVirtualInVO getParametros() throws ServletParametrosException
  {
    ativaPassClockVirtualInVO.setCodigoAtivacao(getCodigoAtivacao(true));

    return ativaPassClockVirtualInVO;
  }


}
