package estiveaqui.appusuario.leappusuario;

import java.util.ArrayList;
import estiveaqui.sql.mo.AppUsuarioMO;
import estiveaqui.sql.mo.LancamentoMO;
import estiveaqui.sql.mo.PassClockMO;
import estiveaqui.vo.DadosOutVO;

public class LeAppUsuarioOutVO implements DadosOutVO
{
  private AppUsuarioMO appUsuarioMO = new AppUsuarioMO();
  private ArrayList<PassClockMO> passClocksMO = new ArrayList<PassClockMO>();
  private ArrayList<LancamentoMO> lancamentosMO = new ArrayList<LancamentoMO>();

  public AppUsuarioMO getAppUsuarioMO()
  {
    return appUsuarioMO;
  }

  public void setAppUsuarioMO(AppUsuarioMO appUsuarioMO)
  {
    this.appUsuarioMO = appUsuarioMO;
  }

  public ArrayList<PassClockMO> getPassClocksMO()
  {
    return passClocksMO;
  }

  public void setPassClocksMO(ArrayList<PassClockMO> passClocksMO)
  {
    this.passClocksMO = passClocksMO;
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
