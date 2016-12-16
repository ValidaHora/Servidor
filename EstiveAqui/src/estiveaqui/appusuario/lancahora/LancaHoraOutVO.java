package estiveaqui.appusuario.lancahora;

import estiveaqui.sql.mo.AppUsuarioMO;
import estiveaqui.sql.mo.LancamentoMO;
import estiveaqui.vo.DadosOutVO;

public class LancaHoraOutVO implements DadosOutVO
{
  private AppUsuarioMO appUsuarioMO = new AppUsuarioMO();
  private LancamentoMO lancamentoMO = new LancamentoMO();

  public AppUsuarioMO getAppUsuarioMO()
  {
    return appUsuarioMO;
  }

  public void setAppUsuarioMO(AppUsuarioMO appUsuarioMO)
  {
    this.appUsuarioMO = appUsuarioMO;
  }

  public LancamentoMO getLancamentoMO()
  {
    return lancamentoMO;
  }

  public void setLancamentoMO(LancamentoMO lancamentoMO)
  {
    this.lancamentoMO = lancamentoMO;
  }


}
