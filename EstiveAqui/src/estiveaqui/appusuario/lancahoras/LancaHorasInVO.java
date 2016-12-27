package estiveaqui.appusuario.lancahoras;

import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import estiveaqui.appusuario.DadosAppUsuarioInVO;

public class LancaHorasInVO extends DadosAppUsuarioInVO
{
  private DateTimeZone        tz;
  private DateTime            horaEnviada;
  private String              idDispositivo;
  private List<HoraEnviadaVO> horasEnviadas;

  public DateTimeZone getTz()
  {
    return tz;
  }

  public void setTz(DateTimeZone tz)
  {
    this.tz = tz;
  }

  public DateTime getHoraEnviada()
  {
    return horaEnviada;
  }

  public void setHoraEnviada(DateTime horaEnviada)
  {
    this.horaEnviada = horaEnviada;
  }

  public String getIdDispositivo()
  {
    return idDispositivo;
  }

  public void setIdDispositivo(String idDispositivo)
  {
    this.idDispositivo = idDispositivo;
  }

  public List<HoraEnviadaVO> getHorasEnviadas()
  {
    return horasEnviadas;
  }

  public void setHorasEnviadas(List<HoraEnviadaVO> horasEnviadas)
  {
    this.horasEnviadas = horasEnviadas;
  }

}
