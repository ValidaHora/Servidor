package estiveaqui.appgestor.login.email;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.servlet.ServletParametros;
import estiveaqui.servlet.ServletParametrosException;
import estiveaqui.vo.DadosInVO;

public class RecuperaSenhaEMailParametrosSrvlt extends ServletParametros
{
  private LoginEMailInVO srvltInVo = (LoginEMailInVO)dadosInVo;

  public RecuperaSenhaEMailParametrosSrvlt(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "Esqueci Senha do e-mail", new LoginEMailInVO());
  }

  @Override
  public DadosInVO getParametros() throws ServletParametrosException
  {
    srvltInVo.setEmail(getEMail());

    return srvltInVo;
  }

  String getEMail() throws ServletParametrosException
  {
    String param = "EMAIL";
    String val = getParametro(param, true, true);

    return val;
  }

}
