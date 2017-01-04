package estiveaqui.appgestor.cadastraappgestor;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.appgestor.servlet.ServletParametrosAppGestor;
import estiveaqui.servlet.NomeParametroServlet;
import estiveaqui.servlet.ServletParametrosException;

public class CadastraComPassClockParametrosSrvlt extends ServletParametrosAppGestor
{
  private CadastraComPassClockInVO cadastraComPassClockInVO = (CadastraComPassClockInVO)dadosInVo;

  public CadastraComPassClockParametrosSrvlt(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "CadastraComPassClock", new CadastraComPassClockInVO());
  }

  public CadastraComPassClockInVO getParametros() throws ServletParametrosException
  {
    cadastraComPassClockInVO.setNumeroPassClock(getNumeroPassClock(true));
    cadastraComPassClockInVO.setCodPassCLock(getCodigoPassClock(true));
    cadastraComPassClockInVO.setSenhaCadastro(getSenhaCadastro(true));
//    getTimeZonePassClock(obrigatorio)

    return cadastraComPassClockInVO;
  }
  
  /**
   * Lê e retorna a senha de cadastro de um passclock.
   * 
   * @param obrigatorio
   * @return
   * @throws ServletParametrosException
   */
  private String getSenhaCadastro(boolean obrigatorio) throws ServletParametrosException
  {
    String val = getParametro(NomeParametroServlet.Senha, obrigatorio, false);

    return val;
  }
}
