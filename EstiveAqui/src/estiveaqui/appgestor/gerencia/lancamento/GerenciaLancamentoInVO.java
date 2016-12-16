package estiveaqui.appgestor.gerencia.lancamento;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import estiveaqui.appgestor.DadosGerenciaisInVO;

public class GerenciaLancamentoInVO extends DadosGerenciaisInVO
{
  private DateTimeZone tz = null;
  private long         idLancamento;
  private int          idAppUsuario;
  private DateTime     horaManual;
  private String       nota;

  public DateTimeZone getTz()
  {
    return tz;
  }

  public void setTz(DateTimeZone tz)
  {
    this.tz = tz;
  }

  public long getIdLancamento()
  {
    return idLancamento;
  }

  public void setIdLancamento(long idLancamento)
  {
    this.idLancamento = idLancamento;
  }

  public int getIdAppUsuario()
  {
    return idAppUsuario;
  }

  public void setIdAppUsuario(int idAppUsuario)
  {
    this.idAppUsuario = idAppUsuario;
  }

  public DateTime getHoraManual()
  {
    return horaManual;
  }

  public void setHoraManual(DateTime horaManual)
  {
    this.horaManual = horaManual;
  }

  public String getNota()
  {
    return nota;
  }

  public void setNota(String nota)
  {
    this.nota = nota;
  }

}
