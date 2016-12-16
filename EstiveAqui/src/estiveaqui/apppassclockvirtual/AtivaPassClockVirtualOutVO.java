package estiveaqui.apppassclockvirtual;

import estiveaqui.sql.mo.PassClockMO;

public class AtivaPassClockVirtualOutVO
{
  private PassClockMO  passClockMO  = new PassClockMO();

  public PassClockMO getPassClockMO()
  {
    return passClockMO;
  }

  public void setPassClockMO(PassClockMO passClockMO)
  {
    this.passClockMO = passClockMO;
  }
}
