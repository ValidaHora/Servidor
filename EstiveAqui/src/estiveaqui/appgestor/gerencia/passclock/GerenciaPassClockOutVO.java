package estiveaqui.appgestor.gerencia.passclock;

import java.util.ArrayList;
import estiveaqui.sql.mo.PassClockMO;
import estiveaqui.vo.DadosOutVO;

public class GerenciaPassClockOutVO implements DadosOutVO
{
  private PassClockMO passClockMO = null;
  private ArrayList<PassClockMO> passClocksMO = null;

  public PassClockMO getPassClockMO()
  {
    return passClockMO;
  }

  public void setPassClockMO(PassClockMO passClockMO)
  {
    this.passClockMO = passClockMO;
  }

  public ArrayList<PassClockMO> getPassClocksMO()
  {
    return passClocksMO;
  }

  public void setPassClocksMO(ArrayList<PassClockMO> passClocksMO)
  {
    this.passClocksMO = passClocksMO;
  }
}
