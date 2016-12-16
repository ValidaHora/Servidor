package estiveaqui.sql.mo;

import java.sql.Date;
import java.sql.Timestamp;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class LancamentoMO implements MO
{
  private long         idLancamento     = 0;
  private int          status           = STATUS_HABILITADO;
  private AppUsuarioMO appUsuarioMO     = new AppUsuarioMO();
  private String       numPassClock     = "";
  private String       apelidoPassClock = "";
  private String       codPassClock     = null;
  private String       hashCode         = null;
  private String       nota             = "";
  private DateTimeZone tzPassClock      = DateTimeZone.UTC;
  private DateTime     hrLancamento     = null;
  private DateTime     hrPassClock      = null;
  private DateTime     hrServidor       = null;
  private DateTime     hrDigitacao      = null;
  private DateTime     hrEnvio          = null;
  private String       idDispositivo    = null;
  private String       ipDispositivo    = null;
  private float        latitude;
  private float        longitude;

//  public void vazio() {} // Para formatação automática com CTRL-SHIFT-F

  public static final int STATUS_HABILITADO   = 0;
  public static final int STATUS_DESABILITADO = 1;
  
  public static final int SEM_SUPORTE         = 1000;
  public static final int NAO_HABILITADO      = 2000;
  public static final int SEM_POSICIONAMENTO  = 3000;
  public static final int ERRO_POSICIONAMENTO = 4000;
  public static final int INFO_NAO_RECEBIDA   = 5000;
  public static final int VALOR_INVALIDO      = 6000;
  public static final int LANCAMENTO_MANUAL   = 7000;

  public long getIdLancamento()
  {
    return idLancamento;
  }

  public void setIdLancamento(long idLancamento)
  {
    this.idLancamento = idLancamento;
  }

  public AppUsuarioMO getAppUsuarioMO()
  {
    return appUsuarioMO;
  }

  public void setAppUsuarioMO(AppUsuarioMO appUsuarioMO)
  {
    this.appUsuarioMO = appUsuarioMO;
  }

  public int getStatus()
  {
    return status;
  }

  public void setStatus(int status)
  {
    this.status = status;
  }

  public String getNumPassClock()
  {
    return numPassClock;
  }

  public void setNumPassClock(String numPassClock)
  {
    this.numPassClock = numPassClock;
  }

  public String getApelidoPassClock()
  {
    return apelidoPassClock;
  }

  public void setApelidoPassClock(String apelidoPassClock)
  {
    this.apelidoPassClock = apelidoPassClock;
  }

  public String getCodPassClock()
  {
    return codPassClock;
  }

  public void setCodPassClock(String codPassClock)
  {
    this.codPassClock = codPassClock;
  }

  public String getHashCode()
  {
    return hashCode;
  }

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

  public DateTimeZone getTzPassClock()
  {
    return tzPassClock;
  }

  public void setTzPassClock(DateTimeZone tzPassClock)
  {
    this.tzPassClock = tzPassClock;
  }

  public DateTime getHrLancamento()
  {
    return hrLancamento;
  }

  public void setHrLancamento(DateTime hrLancamento)
  {
    this.hrLancamento = hrLancamento.withZone(DateTimeZone.UTC);
  }

  public void setHrLancamento(Timestamp hrLancamento)
  {
    this.hrLancamento = new DateTime(hrLancamento);
  }

  public void setHrLancamento(Date hrLancamento)
  {
    this.hrLancamento = new DateTime(hrLancamento);
  }

  public DateTime getHrPassClock()
  {
    return hrPassClock;
  }

  public void setHrPassClock(DateTime hrPassClock)
  {
    this.hrPassClock = hrPassClock.withZone(DateTimeZone.UTC);
  }

  public void setHrPassClock(Timestamp hrPassClock)
  {
    this.hrPassClock = new DateTime(hrPassClock);
  }

  public void setHrPassClockx(Date hrPassClock)
  {
    this.hrPassClock = new DateTime(hrPassClock);
  }

  public DateTime getHrServidor()
  {
    return hrServidor;
  }

  public void setHrServidor(DateTime hrServidor)
  {
    this.hrServidor = hrServidor.withZone(DateTimeZone.UTC);
  }

  public void setHrServidor(Timestamp hrServidor)
  {
    this.hrServidor = new DateTime(hrServidor);
  }

  public void setHrServidor(Date hrServidor)
  {
    this.hrServidor = new DateTime(hrServidor);
  }

  public DateTime getHrDigitacao()
  {
    return hrDigitacao;
  }

  public void setHrDigitacao(DateTime hrDigitacao)
  {
    this.hrDigitacao = hrDigitacao;
  }

  public void setHrDigitacao(Timestamp hrDigitacao)
  {
    this.hrDigitacao = new DateTime(hrDigitacao);
  }

  public void setHrDigitacao(Date hrDigitacao)
  {
    this.hrDigitacao = new DateTime(hrDigitacao);
  }

  public DateTime getHrEnvio()
  {
    return hrEnvio;
  }

  public void setHrEnvio(DateTime hrEnvio)
  {
    this.hrEnvio = hrEnvio;
  }

  public void setHrEnvio(Timestamp hrEnvio)
  {
    this.hrEnvio = new DateTime(hrEnvio);
  }

  public void setHrEnvio(Date hrEnvio)
  {
    this.hrEnvio = new DateTime(hrEnvio);
  }

  public String getIdDispositivo()
  {
    return idDispositivo;
  }

  public void setIdDispositivo(String idDispositivo)
  {
    this.idDispositivo = idDispositivo;
  }

  public String getIpDispositivo()
  {
    return ipDispositivo;
  }

  public void setIpDispositivo(String ipDispositivo)
  {
    this.ipDispositivo = (ipDispositivo == null ? "" : ipDispositivo);
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
