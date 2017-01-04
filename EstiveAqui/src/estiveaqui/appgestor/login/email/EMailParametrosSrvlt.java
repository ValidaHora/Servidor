package estiveaqui.appgestor.login.email;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.servlet.NomeParametroServlet;
import estiveaqui.servlet.ServletParametros;
import estiveaqui.servlet.ServletParametrosException;

public class EMailParametrosSrvlt extends ServletParametros
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
    String val = getParametro(NomeParametroServlet.Email, true, true);

    return val;
  }

  String getSenha() throws ServletParametrosException
  {
    String val = getParametro(NomeParametroServlet.Senha, true, false);

    return val;
  }
}
