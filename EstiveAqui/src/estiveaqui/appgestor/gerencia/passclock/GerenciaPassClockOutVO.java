package estiveaqui.appgestor.gerencia.passclock;

import java.util.List;
import estiveaqui.sql.mo.PassClockMO;
import estiveaqui.vo.DadosOutVO;

public class GerenciaPassClockOutVO implements DadosOutVO
{
  private PassClockMO passClockMO = null;
  private List<PassClockMO> passClocksMO = null;

  public PassClockMO getPassClockMO()
  {
    return passClockMO;
  }

  public void setPassClockMO(PassClockMO passClockMO)
  {
    this.passClockMO = passClockMO;
  }

  public List <PassClockMO> getPassClocksMO()
  {
    return passClocksMO;
  }

  public void setPassClocksMO(List<PassClockMO> passClocksMO)
  {
    this.passClocksMO = passClocksMO;
  }
}
