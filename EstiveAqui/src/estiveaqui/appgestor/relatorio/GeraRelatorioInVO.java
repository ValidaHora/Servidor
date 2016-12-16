package estiveaqui.appgestor.relatorio;

import org.joda.time.DateTimeZone;
import estiveaqui.appgestor.DadosAppGestorInVO;

public class GeraRelatorioInVO extends DadosAppGestorInVO
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
