package estiveaqui.appgestor.login.email;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.servlet.ServletParametros0;
import estiveaqui.servlet.ServletParametrosException;

public class EMailParametrosSrvlt extends ServletParametros0
{
  private LoginEMailInVO srvltInVo = (LoginEMailInVO)dadosInVo;

  public EMailParametrosSrvlt(HttpServletRequest request, String acao) throws IOException, ServletParametrosException
  {
    super(request, acao, new LoginEMailInVO());
  }

  @Override
  public LoginEMailInVO getParametros() throws ServletParametrosException
  {
    srvltInVo.setEmail(getEMail());
    srvltInVo.setSenha(getSenha());

    return srvltInVo;
  }

  String getEMail() throws ServletParametrosException
  {
    String param = "EMAIL";
    String val = getParametro(param, true, true);

    return val;
  }

  String getSenha() throws ServletParametrosException
  {
    String param = "SENHA";
    String val = getParametro(param, true, false);

    return val;
  }
}
