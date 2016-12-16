package estiveaqui.appusuario.leappusuario;

import org.joda.time.DateTimeZone;
import estiveaqui.appusuario.DadosAppUsuarioInVO;

public class LeAppUsuarioInVO extends DadosAppUsuarioInVO
{
  private String identificacaoApp = "";
  private DateTimeZone tz = null;

  public String getIdentificacaoApp()
  {
    return identificacaoApp;
  }

  public void setIdentificacaoApp(String identificacaoApp)
  {
    this.identificacaoApp = identificacaoApp;
  }

  public DateTimeZone getTz()
  {
    return tz;
  }

  public void setTz(DateTimeZone tz)
  {
    this.tz = tz;
  }
}
