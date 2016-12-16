package estiveaqui.appusuario.lancahoras;

import org.joda.time.DateTime;

/**
 * Classe para o ArrayList que agrupa todas as horas lançadas.
 * 
 * @author Haroldo
 *
 */
public class HoraEnviadaVO
{
  private int      idLancamento;
  private String   numeroPassClock;
  private String   codigoPassClock;
  private String   nota;
  private DateTime hrLancada;
  private DateTime hrDigitacao;
  private String   hashCode;
  private float    latitude;
  private float    longitude;

  public int getIdLancamento()
  {
    return idLancamento;
  }

  public void setIdLancamento(int idLancamento)
  {
    this.idLancamento = idLancamento;
  }

  public String getNumeroPassClock()
  {
    return numeroPassClock;
  }

  public void setNumeroPassClock(String numeroPassClock)
  {
    this.numeroPassClock = numeroPassClock;
  }

  public String getCodigoPassClock()
  {
    return codigoPassClock;
  }

  public void setCodigoPassClock(String codigoPassClock)
  {
    this.codigoPassClock = codigoPassClock;
  }

  public String getNota()
  {
    return nota;
  }

  public void setNota(String nota)
  {
    this.nota = nota;
  }

  public DateTime getHrLancada()
  {
    return hrLancada;
  }

  public void setHrLancada(DateTime hrLancada)
  {
    this.hrLancada = hrLancada;
  }

  public DateTime getHrDigitacao()
  {
    return hrDigitacao;
  }

  public void setHrDigitacao(DateTime hrDigitacao)
  {
    this.hrDigitacao = hrDigitacao;
  }

  public String getHashCode()
  {
    return hashCode;
  }

  public void setHashCode(String hashCode)
  {
    this.hashCode = hashCode;
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
