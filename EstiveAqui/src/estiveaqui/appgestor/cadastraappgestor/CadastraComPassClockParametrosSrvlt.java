package estiveaqui.appgestor.cadastraappgestor;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.servlet.ServletParametros;
import estiveaqui.servlet.ServletParametrosException;

public class CadastraComPassClockParametrosSrvlt extends ServletParametros
{
  private CadastraComPassClockInVO cadastraComPassClockInVO = (CadastraComPassClockInVO)dadosInVo;

  public CadastraComPassClockParametrosSrvlt(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "CadastraComPassClock", new CadastraComPassClockInVO());
  }

  public CadastraComPassClockInVO getParametros() throws ServletParametrosException
  {
    cadastraComPassClockInVO.setTz(getTimeZone(true));
    cadastraComPassClockInVO.setNumeroPassClock(getNumeroPassClock(true));
    cadastraComPassClockInVO.setCodPassCLock(getCodigoPassClock(true));
    cadastraComPassClockInVO.setSenhaCadastro(getSenhaCadastro(true));
    cadastraComPassClockInVO.setHashCode(getHashCode(true));

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
    String param = "SENHACADASTRO";
    String val = getParametro(param, obrigatorio, false);

    return val;
  }
}
