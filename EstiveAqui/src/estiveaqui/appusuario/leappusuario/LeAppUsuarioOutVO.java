package estiveaqui.appusuario.leappusuario;

import java.util.ArrayList;
import java.util.List;
import estiveaqui.appusuario.DadosAppUsuarioOutVO;
import estiveaqui.sql.mo.LancamentoMO;
import estiveaqui.sql.mo.PassClockMO;

public class LeAppUsuarioOutVO extends DadosAppUsuarioOutVO
{
  private List<PassClockMO> passClocksMO = new ArrayList<PassClockMO>();
  private List<LancamentoMO> lancamentosMO = new ArrayList<LancamentoMO>();

  public List<PassClockMO> getPassClocksMO()
  {
    return passClocksMO;
  }

  public void setPassClocksMO(List<PassClockMO> passClocksMO)
  {
    this.passClocksMO = passClocksMO;
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
