package estiveaqui.apppassclockvirtual;

public class AtivaPassClockVirtualOutVO
{
  private String numeroPassClock;
  private String apelidoPassClock;
  private String sementeToken;
  private int    codAlgoritmo;

  public String getNumeroPassClock()
  {
    return numeroPassClock;
  }

  public void setNumeroPassClock(String numeroPassClock)
  {
    this.numeroPassClock = numeroPassClock;
  }

  public String getApelidoPassClock()
  {
    return apelidoPassClock;
  }

  public void setApelidoPassClock(String apelidoPassClock)
  {
    this.apelidoPassClock = apelidoPassClock;
  }

  public String getSementeToken()
  {
    return sementeToken;
  }

  public void setSementeToken(String sementeToken)
  {
    this.sementeToken = sementeToken;
  }

  public int getCodAlgoritmo()
  {
    return codAlgoritmo;
  }

  public void setCodAlgoritmo(int codAlgoritmo)
  {
    this.codAlgoritmo = codAlgoritmo;
  }
}
