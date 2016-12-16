package estiveaqui.appgestor.gerencia.passclock;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import estiveaqui.appgestor.servlet.ServletParametrosGerencia;
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
    gerenciaPassClockInVO.setIdentificadorAppGestor(getIdentificacaoApp(true));
    gerenciaPassClockInVO.setTz(getTimeZone(false));
//    gerenciaPassClockInVO.setNumPassClock(getNumeroPassClock(getAcao().equals("DIS") || getAcao().equals("UPD")));
    gerenciaPassClockInVO.setNumPassClock(getNumeroPassClock( !getAcao().equals("CAV")));
    gerenciaPassClockInVO.setApelido(getApelido(getAcao().equals("UPD") || getAcao().equals("CAV")));
    gerenciaPassClockInVO.setCodPassClock(getCodigoPassClock(getAcao().equals("CAD")));
    gerenciaPassClockInVO.setHashCode(getHashCode(getAcao().equals("CAD")));
//    gerenciaPassClockInVO.setHoraCalculada(getHoraCalculada(getAcao().equals("CAD"), gerenciaPassClockInVO.getTz()));
    gerenciaPassClockInVO.setHoraEnviada(getHoraEnviada(getAcao().equals("CAD"), gerenciaPassClockInVO.getTz()));
    
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
    return getParametro("APELIDO", obrigatorio, true);
  }

  private DateTime getHoraEnviada(boolean obrigatorio, DateTimeZone tz) throws ServletParametrosException
  {
    return getParametroHoraSegundos("HORAENVIADA", obrigatorio, tz);
  }
}
