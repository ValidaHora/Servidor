package estiveaqui.appusuario.lancahora;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import estiveaqui.appusuario.DadosAppUsuarioInVO;

public class LancaHoraInVO extends DadosAppUsuarioInVO
{
  private DateTimeZone tzPassClock = null;
  private String numPassClock = "";
  private String codigo = "";
  @Deprecated
  private String hashCode = "";
  private String nota = "";
  private DateTime horaCalculada = null;
  private DateTime horaDigitada = null;
  private DateTime horaEnviada = null;
  private String idDispositivo = "";
  private float latitude;
  private float longitude;

  public DateTimeZone getTzPassClock()
  {
    return tzPassClock;
  }

  public void setTzPassClock(DateTimeZone tzPassClock)
  {
    this.tzPassClock = tzPassClock;
  }

  public String getCodigo()
  {
    return codigo;
  }

  public void setCodigo(String codigo)
  {
    this.codigo = codigo;
  }

  @Deprecated
  public String getHashCode()
  {
    return hashCode;
  }

  @Deprecated
  public void setHashCode(String hashCode)
  {
    this.hashCode = hashCode;
  }

  public String getNota()
  {
    return nota;
  }

  public void setNota(String nota)
  {
    this.nota = nota;
  }

  public String getNumPassClock()
  {
    return numPassClock;
  }

  public void setNumPassClock(String numPassClock)
  {
    this.numPassClock = numPassClock;
  }

  public DateTime getHoraCalculada()
  {
    return horaCalculada;
  }

  public void setHoraCalculada(DateTime horaCalculada)
  {
    this.horaCalculada = horaCalculada;
  }

  public DateTime getHoraDigitada()
  {
    return horaDigitada;
  }

  public void setHoraDigitada(DateTime horaDigitada)
  {
    this.horaDigitada = horaDigitada;
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

  public float getLatitude()
  {
    return latitude;
  }

  public void setLatitude(float latitude)
  {
    this.latitude = latitude;
  }

  public float getLongitude()
  {
    return longitude;
  }

  public void setLongitude(float longitude)
  {
    this.longitude = longitude;
  }

}
