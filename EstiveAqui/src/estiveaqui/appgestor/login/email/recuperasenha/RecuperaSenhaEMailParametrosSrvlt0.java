package estiveaqui.appgestor.login.email.recuperasenha;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.appgestor.login.email.LoginEMailInVO;
import estiveaqui.servlet.ServletParametros0;
import estiveaqui.servlet.ServletParametrosException;
import estiveaqui.vo.DadosInVO;

@Deprecated
public class RecuperaSenhaEMailParametrosSrvlt0 extends ServletParametros0
{
  private LoginEMailInVO srvltInVo = (LoginEMailInVO)dadosInVo;

  @Deprecated
  public RecuperaSenhaEMailParametrosSrvlt0(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "Esqueci Senha do e-mail", new LoginEMailInVO());
  }

  @Override
  @Deprecated
  public DadosInVO getParametros() throws ServletParametrosException
  {
    srvltInVo.setEmail(getEMail());

    return srvltInVo;
  }

  @Deprecated
  String getEMail() throws ServletParametrosException
  {
    String param = "EMAIL";
    String val = getParametro(param, true, true);

    return val;
  }

}
