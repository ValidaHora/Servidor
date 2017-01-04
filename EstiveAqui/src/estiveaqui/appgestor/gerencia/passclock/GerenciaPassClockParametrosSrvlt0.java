package estiveaqui.appgestor.gerencia.passclock;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import estiveaqui.appgestor.servlet.ServletParametrosGerencia0;
import estiveaqui.servlet.ServletParametrosException;

@Deprecated
public class GerenciaPassClockParametrosSrvlt0 extends ServletParametrosGerencia0
{
  private GerenciaPassClockInVO gerenciaPassClockInVO = (GerenciaPassClockInVO)dadosInVo;

  @Deprecated
  public GerenciaPassClockParametrosSrvlt0(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "Gerencia PassClock", new GerenciaPassClockInVO());
  }

  @Override
  @Deprecated
  public GerenciaPassClockInVO getParametros() throws ServletParametrosException
  {
    gerenciaPassClockInVO.setIdentificadorAppGestor(getIdentificacaoApp(true));
    gerenciaPassClockInVO.setTz(getTimeZone(false));
//    gerenciaPassClockInVO.setNumPassClock(getNumeroPassClock(getAcao().equals("DIS") || getAcao().equals("UPD")));
    gerenciaPassClockInVO.setNumPassClock(getNumeroPassClock0( !getAcao().equals("CAV")));
    gerenciaPassClockInVO.setApelido(getApelido(getAcao().equals("UPD") || getAcao().equals("CAV")));
    gerenciaPassClockInVO.setCodPassClock(getCodigoPassClock(getAcao().equals("CAD")));
//    gerenciaPassClockInVO.setHoraCalculada(getHoraCalculada(getAcao().equals("CAD"), gerenciaPassClockInVO.getTz()));
    gerenciaPassClockInVO.setHoraEnviada(getHoraEnviada(getAcao().equals("CAD"), gerenciaPassClockInVO.getTz()));
    gerenciaPassClockInVO.setHashCode(getHashCode(getAcao().equals("CAD")));
    
    return gerenciaPassClockInVO;
  }

  /**
   * Lê e retorna apelido do PassClock.
   * 
   * @param obrigatorio
   * @return
   * @throws ServletParametrosException
   */
  @Deprecated
  private String getApelido(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametro("APELIDO", obrigatorio, true);
  }

  @Deprecated
  private DateTime getHoraEnviada(boolean obrigatorio, DateTimeZone tz) throws ServletParametrosException
  {
    return getParametroHoraSegundos("HORAENVIADA", obrigatorio, tz);
  }
}
