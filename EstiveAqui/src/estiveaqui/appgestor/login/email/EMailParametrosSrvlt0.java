package estiveaqui.appgestor.login.email;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.servlet.ServletParametros0;
import estiveaqui.servlet.ServletParametrosException;

@Deprecated
public class EMailParametrosSrvlt0 extends ServletParametros0
{
  private LoginEMailInVO srvltInVo = (LoginEMailInVO)dadosInVo;

  @Deprecated
  public EMailParametrosSrvlt0(HttpServletRequest request, String acao) throws IOException, ServletParametrosException
  {
    super(request, acao, new LoginEMailInVO());
  }

  @Override
  @Deprecated
  public LoginEMailInVO getParametros() throws ServletParametrosException
  {
    srvltInVo.setEmail(getEMail());
    srvltInVo.setSenha(getSenha());

    return srvltInVo;
  }

  @Deprecated
  String getEMail() throws ServletParametrosException
  {
    String param = "EMAIL";
    String val = getParametro(param, true, true);

    return val;
  }

  @Deprecated
  String getSenha() throws ServletParametrosException
  {
    String param = "SENHA";
    String val = getParametro(param, true, false);

    return val;
  }
}
