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
  private int      contadorLancamento;
  private String   numeroPassClock;
  private String   codigoPassClock;
  private String   nota;
  @Deprecated
  private DateTime hrLancada;
  private DateTime hrDigitacao;
  @Deprecated
  private String   hashCode;
  private float    latitude;
  private float    longitude;
  private boolean  validaHora = true;

  public int getContadorLancamento()
  {
    return contadorLancamento;
  }

  public void setContadorLancamento(int contadorLancamento)
  {
    this.contadorLancamento = contadorLancamento;
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

  @Deprecated
  public DateTime getHrLancada()
  {
    return hrLancada;
  }

  @Deprecated
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

  /**
   * Define se chama ou não o servidor ValidaHora.
   * 
   * @return
   */
  public boolean isValidaHora()
  {
    return validaHora;
  }

  public void setValidaHora(boolean validaHora)
  {
    this.validaHora = validaHora;
  }
}
