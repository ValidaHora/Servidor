package estiveaqui.appgestor.cadastraappgestor;

import java.util.ArrayList;
import java.util.List;
import estiveaqui.sql.mo.AppGestorMO;
import estiveaqui.sql.mo.AppUsuarioMO;
import estiveaqui.sql.mo.LancamentoMO;
import estiveaqui.sql.mo.PassClockMO;
import estiveaqui.vo.DadosOutVO;

public class CadastraComPassClockOutVO implements DadosOutVO
{
  private AppGestorMO        appGestorMO   = null;
  private List<PassClockMO>  passClocksMO  = new ArrayList<PassClockMO>();
  private List<AppUsuarioMO> appUsuariosMO = new ArrayList<AppUsuarioMO>();
  private List<LancamentoMO> lancamentosMO = new ArrayList<LancamentoMO>();

  public AppGestorMO getAppGestorMO()
  {
    return appGestorMO;
  }

  public void setAppGestorMO(AppGestorMO appGestorMO)
  {
    this.appGestorMO = appGestorMO;
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
