package estiveaqui.appgestor.ledados;

import java.util.ArrayList;
import java.util.List;
import estiveaqui.sql.mo.AppUsuarioMO;
import estiveaqui.sql.mo.LancamentoMO;
import estiveaqui.sql.mo.PassClockMO;
import estiveaqui.vo.DadosOutVO;

public class LeDadosOutVO implements DadosOutVO
{
  private boolean emailValidado   = false;

  private List<PassClockMO>  passClocksMO   = new ArrayList<PassClockMO>();
  private List<AppUsuarioMO> appUsuariosMO  = new ArrayList<AppUsuarioMO>();
  private List<LancamentoMO> lancamentosMO  = new ArrayList<LancamentoMO>();

  public boolean isEmailValidado()
  {
    return emailValidado;
  }

  public void setEmailValidado(boolean emailValidado)
  {
    this.emailValidado = emailValidado;
  }

  public List<PassClockMO> getPassClocksMO()
  {
    return passClocksMO;
  }

  public void setPassClocksMO(List<PassClockMO> passClocksMO)
  {
    this.passClocksMO = passClocksMO;
  }

  public List<AppUsuarioMO> getAppUsuariosMO()
  {
    return appUsuariosMO;
  }

  public void setAppUsuariosMO(List<AppUsuarioMO> appUsuariosMO)
  {
    this.appUsuariosMO = appUsuariosMO;
  }

  public List<LancamentoMO> getLancamentosMO()
  {
    return lancamentosMO;
  }

  public void setLancamentosMO(List<LancamentoMO> lancamentosMO)
  {
    this.lancamentosMO = lancamentosMO;
  }

}
