package estiveaqui.appusuario.leappusuario;

import java.util.ArrayList;
import estiveaqui.appusuario.DadosAppUsuarioOutVO;
import estiveaqui.sql.mo.LancamentoMO;
import estiveaqui.sql.mo.PassClockMO;

public class LeAppUsuarioOutVO extends DadosAppUsuarioOutVO
{
  private ArrayList<PassClockMO> passClocksMO = new ArrayList<PassClockMO>();
  private ArrayList<LancamentoMO> lancamentosMO = new ArrayList<LancamentoMO>();

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
