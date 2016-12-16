package estiveaqui.appgestor.gerencia.passclock;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import estiveaqui.appgestor.DadosGerenciaisInVO;

public class GerenciaPassClockInVO extends DadosGerenciaisInVO
{
  private String       numPassClock = "";
  private String       codPassClock = "";
  private String       hashCode     = "";
  private String       apelido      = "";
  private DateTimeZone tz           = null;
  private DateTime     horaEnviada  = null;

  public String getNumPassClock()
  {
    return numPassClock;
  }

  public void setNumPassClock(String numPassClock)
  {
    this.numPassClock = numPassClock;
  }

  public String getCodPassClock()
  {
    return codPassClock;
  }

  public void setCodPassClock(String codPassClock)
  {
    this.codPassClock = codPassClock;
  }

  public String getApelido()
  {
    return apelido;
  }

  public void setApelido(String apelido)
  {
    this.apelido = apelido;
  }

  public String getHashCode()
  {
    return hashCode;
  }

  public void setHashCode(String hashCode)
  {
    this.hashCode = hashCode;
  }

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

//if (params.codPassClock == null)
//jsonMsg.addMsgErro(2, "Falta o código do PassClock.");
//if (params.senhaCadastroPassClock == null)
//jsonMsg.addMsgErro(2, "Falta a senha de cadastro.");

}
