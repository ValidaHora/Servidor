package estiveaqui.appgestor.ledados;

import java.util.ArrayList;
import estiveaqui.sql.mo.AppUsuarioMO;
import estiveaqui.sql.mo.LancamentoMO;
import estiveaqui.sql.mo.PassClockMO;
import estiveaqui.vo.DadosOutVO;

public class LeDadosOutVO implements DadosOutVO
{
  private boolean emailValidado   = false;

  private ArrayList<PassClockMO>  passClocksMO   = new ArrayList<PassClockMO>();
  private ArrayList<AppUsuarioMO> appUsuariosMO  = new ArrayList<AppUsuarioMO>();
  private ArrayList<LancamentoMO> lancamentosMO  = new ArrayList<LancamentoMO>();

  public boolean isEmailValidado()
  {
    return emailValidado;
  }

  public void setEmailValidado(boolean emailValidado)
  {
    this.emailValidado = emailValidado;
  }

  public ArrayList<PassClockMO> getPassClocksMO()
  {
    return passClocksMO;
  }

  public void setPassClocksMO(ArrayList<PassClockMO> passClocksMO)
  {
    this.passClocksMO = passClocksMO;
  }

  public ArrayList<AppUsuarioMO> getAppUsuariosMO()
  {
    return appUsuariosMO;
  }

  public void setAppUsuariosMO(ArrayList<AppUsuarioMO> appUsuariosMO)
  {
    this.appUsuariosMO = appUsuariosMO;
  }

  public ArrayList<LancamentoMO> getLancamentosMO()
  {
    return lancamentosMO;
  }

  public void setLancamentosMO(ArrayList<LancamentoMO> lancamentosMO)
  {
    this.lancamentosMO = lancamentosMO;
  }

}
