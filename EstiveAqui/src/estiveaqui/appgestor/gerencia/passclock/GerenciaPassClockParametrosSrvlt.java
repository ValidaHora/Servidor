package estiveaqui.appgestor.gerencia.passclock;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import estiveaqui.CodigoErro;
import estiveaqui.appgestor.servlet.ServletParametrosGerencia;
import estiveaqui.servlet.NomeParametroServlet;
import estiveaqui.servlet.ServletParametrosException;

public class GerenciaPassClockParametrosSrvlt extends ServletParametrosGerencia
{
  private GerenciaPassClockInVO gerenciaPassClockInVO = (GerenciaPassClockInVO)dadosInVo;

  public GerenciaPassClockParametrosSrvlt(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "Gerencia PassClock", new GerenciaPassClockInVO());
  }

  @Override
  public GerenciaPassClockInVO getParametros() throws ServletParametrosException
  {
    gerenciaPassClockInVO.setIdentificadorAppGestor(getIdentificacaoAppGestor(true));
    gerenciaPassClockInVO.setNumPassClock(getNumeroPassClock( !getAcao().equals("CAV")));
    gerenciaPassClockInVO.setApelido(getApelido(getAcao().equals("UPD") || getAcao().equals("CAV")));
    gerenciaPassClockInVO.setCodPassClock(getCodigoPassClock(getAcao().equals("CAD")));
    gerenciaPassClockInVO.setHoraEnviada(getHoraEnviada(getAcao().equals("CAD")));
    gerenciaPassClockInVO.setTz(getTimeZoneName(true));
    
    return gerenciaPassClockInVO;
  }

  /**
   * Lê e retorna apelido do PassClock.
   * 
   * @param obrigatorio
   * @return
   * @throws ServletParametrosException
   */
  private String getApelido(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametro(NomeParametroServlet.ApelidoPassClock, obrigatorio, true);
  }

  private DateTime getHoraEnviada(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametroHoraSegundos(NomeParametroServlet.HoraEnviada, obrigatorio);
  }
  
  private DateTimeZone getTimeZoneName(boolean obrigatorio) throws ServletParametrosException
  {
    NomeParametroServlet param = NomeParametroServlet.TimeZoneName;
    String val = getParametro(param, obrigatorio, true);
    if (val == null)
      return null;

    try
    {
      return DateTimeZone.forID(val);
    }
    catch (UnsupportedOperationException | IllegalArgumentException e)
    {
      throw new ServletParametrosException(CodigoErro.ERRO_INTERNO, "Parâmetro ''{0}'' com formato inválido em ''{1}''. Valor passado ''{2}''", param, acao, val);
    }
  }
}
