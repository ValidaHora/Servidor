package estiveaqui.appusuario.cadastra;

import org.joda.time.DateTimeZone;
import estiveaqui.vo.DadosInVO;

public class CadastraAppUsuarioInVO extends DadosInVO
{
  private String codAtivacao = "";
  private DateTimeZone tz = null;

  public String getCodAtivacao()
  {
    return codAtivacao;
  }

  public void setCodAtivacao(String codAtivacao)
  {
    this.codAtivacao = codAtivacao;
  }

  public DateTimeZone getTz()
  {
    return tz;
  }

  public void setTz(DateTimeZone tz)
  {
    this.tz = tz;
  }

}
