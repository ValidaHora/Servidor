package estiveaqui.appusuario.leappusuario;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.servlet.ServletParametros0;
import estiveaqui.servlet.ServletParametrosException;

public class LeAppUsuarioParametrosSrvlt extends ServletParametros0
{
  private LeAppUsuarioInVO leAppUsuarioInVO = (LeAppUsuarioInVO)dadosInVo;
  
  public LeAppUsuarioParametrosSrvlt(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "L� infos", new LeAppUsuarioInVO());
  }

  /**
   * L� e retorna os par�metros do LeAppUsuario
   * 
   * @return
   * @throws ServletParametrosException
   */
  public LeAppUsuarioInVO getParametros() throws ServletParametrosException
  {
    leAppUsuarioInVO.setTz(getTimeZone(false));
    leAppUsuarioInVO.setIdentificacaoApp(getIdentificacaoApp0(true));
    
    return leAppUsuarioInVO;
  }

}
