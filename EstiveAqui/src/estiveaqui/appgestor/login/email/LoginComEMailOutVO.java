package estiveaqui.appgestor.login.email;

import estiveaqui.vo.DadosOutVO;

public class LoginComEMailOutVO implements DadosOutVO
{
  private String  identificadorAppGestor;
  private boolean emailValidado   = false;
  private boolean existeHistorico = false;

  public String getIdentificadorAppGestor()
  {
    return identificadorAppGestor;
  }

  public void setIdentificadorAppGestor(String identificadorAppGestor)
  {
    this.identificadorAppGestor = identificadorAppGestor;
  }

  public boolean isEmailValidado()
  {
    return emailValidado;
  }

  public void setEmailValidado(boolean emailValidado)
  {
    this.emailValidado = emailValidado;
  }

  public boolean isExisteHistorico()
  {
    return existeHistorico;
  }

  public void setExisteHistorico(boolean existeHistorico)
  {
    this.existeHistorico = existeHistorico;
  }
}
