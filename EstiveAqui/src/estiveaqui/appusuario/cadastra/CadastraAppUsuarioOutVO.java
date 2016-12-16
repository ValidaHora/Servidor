package estiveaqui.appusuario.cadastra;

import estiveaqui.appusuario.DadosAppUsuarioOutVO;
import estiveaqui.sql.mo.AppUsuarioMO;

public class CadastraAppUsuarioOutVO extends DadosAppUsuarioOutVO
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
