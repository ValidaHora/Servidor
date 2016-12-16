package estiveaqui.appusuario.leappusuario;

import org.joda.time.DateTimeZone;
import estiveaqui.appusuario.DadosAppUsuarioInVO;

public class LeAppUsuarioInVO extends DadosAppUsuarioInVO
{
  private DateTimeZone tz = null;

  public DateTimeZone getTz()
  {
    return tz;
  }

  public void setTz(DateTimeZone tz)
  {
    this.tz = tz;
  }
}
