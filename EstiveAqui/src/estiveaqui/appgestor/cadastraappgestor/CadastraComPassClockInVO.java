package estiveaqui.appgestor.cadastraappgestor;

import org.joda.time.DateTimeZone;
import estiveaqui.vo.DadosInVO;

// public boolean cadastra(String sNumeroPassClock, String codPassClock, String senhaCad)

public class CadastraComPassClockInVO extends DadosInVO
{
  private String       numeroPassClock = "";
  private String       codPassCLock    = "";
  private String       senhaCadastro   = "";
  private DateTimeZone tz              = null;
  private String       hashCode        = "";

  public String getNumeroPassClock()
  {
    return numeroPassClock;
  }

  public void setNumeroPassClock(String numeroPassClock)
  {
    this.numeroPassClock = numeroPassClock;
  }

  public String getCodPassCLock()
  {
    return codPassCLock;
  }

  public void setCodPassCLock(String codPassCLock)
  {
    this.codPassCLock = codPassCLock;
  }

  public String getSenhaCadastro()
  {
    return senhaCadastro;
  }

  public void setSenhaCadastro(String senhaCadastro)
  {
    this.senhaCadastro = senhaCadastro;
  }

  public DateTimeZone getTz()
  {
    return tz;
  }

  public void setTz(DateTimeZone tz)
  {
    this.tz = tz;
  }

  public String getHashCode()
  {
    return hashCode;
  }

  public void setHashCode(String hashCode)
  {
    this.hashCode = hashCode;
  }

}
