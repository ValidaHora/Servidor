package estiveaqui.appusuario.lancahoras;

import java.util.ArrayList;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import estiveaqui.appusuario.DadosAppUsuarioInVO;

public class LancaHorasInVO extends DadosAppUsuarioInVO
{
  private DateTimeZone               tz;
  private DateTime                   horaEnviada;
  private String                     idDispositivo;
  private ArrayList<HoraEnviadaVO> horasEnviadas;

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

  public ArrayList<HoraEnviadaVO> getHorasEnviadas()
  {
    return horasEnviadas;
  }

  public void setHorasEnviadas(ArrayList<HoraEnviadaVO> horasEnviadas)
  {
    this.horasEnviadas = horasEnviadas;
  }

}
