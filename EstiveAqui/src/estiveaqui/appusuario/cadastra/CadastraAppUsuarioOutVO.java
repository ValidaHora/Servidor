package estiveaqui.appusuario.cadastra;

import estiveaqui.sql.mo.AppUsuarioMO;
import estiveaqui.vo.DadosOutVO;

public class CadastraAppUsuarioOutVO implements DadosOutVO
{
  private AppUsuarioMO appUsuarioMO = new AppUsuarioMO();

  public AppUsuarioMO getAppUsuarioMO()
  {
    return appUsuarioMO;
  }

  public void setAppUsuarioMO(AppUsuarioMO appUsuarioMO)
  {
    this.appUsuarioMO = appUsuarioMO;
  }


}
