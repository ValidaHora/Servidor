package estiveaqui.sql.mo.externo;

public class TokenMO
{
  private String numeroToken;
  private int    codigoAlgoritmo;
  private String semente;

  public String getNumeroToken()
  {
    return numeroToken;
  }

  public void setNumeroToken(String numeroToken)
  {
    this.numeroToken = numeroToken;
  }

  public int getCodigoAlgoritmo()
  {
    return codigoAlgoritmo;
  }

  public void setCodigoAlgoritmo(int codigoAlgoritmo)
  {
    this.codigoAlgoritmo = codigoAlgoritmo;
  }

  public String getSemente()
  {
    return semente;
  }

  public void setSemente(String semente)
  {
    this.semente = semente;
  }
}
