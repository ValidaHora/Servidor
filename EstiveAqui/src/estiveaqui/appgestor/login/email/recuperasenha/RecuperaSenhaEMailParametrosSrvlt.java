package estiveaqui.appgestor.login.email.recuperasenha;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.appgestor.login.email.LoginEMailInVO;
import estiveaqui.servlet.NomeParametroServlet;
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
    String val = getParametro(NomeParametroServlet.Email, true, true);

    return val;
  }

}
