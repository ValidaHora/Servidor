package estiveaqui.appgestor.gerencia.usuario;

import estiveaqui.sql.mo.AppUsuarioMO;
import estiveaqui.vo.DadosOutVO;

public class GerenciaAppUsuarioOutVO implements DadosOutVO
{

  private AppUsuarioMO appUsuarioMO;

  public AppUsuarioMO getAppUsuarioMO()
  {
    return appUsuarioMO;
  }

  public void setAppUsuarioMO(AppUsuarioMO appUsuarioMO)
  {
    this.appUsuarioMO = appUsuarioMO;
  }

}
