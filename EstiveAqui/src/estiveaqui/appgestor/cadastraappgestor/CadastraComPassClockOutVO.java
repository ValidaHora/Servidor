package estiveaqui.appgestor.cadastraappgestor;

import java.util.ArrayList;
import estiveaqui.sql.mo.AppGestorMO;
import estiveaqui.sql.mo.AppUsuarioMO;
import estiveaqui.sql.mo.LancamentoMO;
import estiveaqui.sql.mo.PassClockMO;
import estiveaqui.vo.DadosOutVO;

public class CadastraComPassClockOutVO implements DadosOutVO
{
  private AppGestorMO             appGestorMO   = null;
  private ArrayList<PassClockMO>  passClocksMO  = new ArrayList<PassClockMO>();
  private ArrayList<AppUsuarioMO> appUsuariosMO = new ArrayList<AppUsuarioMO>();
  private ArrayList<LancamentoMO> lancamentosMO = new ArrayList<LancamentoMO>();

  public AppGestorMO getAppGestorMO()
  {
    return appGestorMO;
  }

  public void setAppGestorMO(AppGestorMO appGestorMO)
  {
    this.appGestorMO = appGestorMO;
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
