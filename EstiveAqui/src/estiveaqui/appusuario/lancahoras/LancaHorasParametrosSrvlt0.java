package estiveaqui.appusuario.lancahoras;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import estiveaqui.Util;
import estiveaqui.servlet.ServletParametros0;
import estiveaqui.servlet.ServletParametrosException;
import estiveaqui.vo.DadosInVO;

public class LancaHorasParametrosSrvlt0 extends ServletParametros0
{
  private LancaHorasInVO lancaHoraInVO = (LancaHorasInVO) dadosInVo;

  public LancaHorasParametrosSrvlt0(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "Lança Horas", new LancaHorasInVO());
  }

  @Override
  public DadosInVO getParametros() throws ServletParametrosException
  {
    lancaHoraInVO.setTz(getTimeZone(true));
    lancaHoraInVO.setIdentificacaoApp(getIdentificacaoApp0(true));
    lancaHoraInVO.setHoraEnviada(getHoraEnviada(true, lancaHoraInVO.getTz()));
    lancaHoraInVO.setIdDispositivo(getDispositivo(false));
    lancaHoraInVO.setHorasEnviadas(getHorasEnviadas(true));

    return lancaHoraInVO;
  }

  /**
   * Lê o parâmetro da hora enviada.
   * 
   * @param obrigatorio
   * @param tz
   * @return
   * @throws ServletParametrosException
   */
  protected DateTime getHoraEnviada(boolean obrigatorio, DateTimeZone tz) throws ServletParametrosException
  {
    return getParametroHoraSegundos("HREN", obrigatorio, tz);
  }

  /**
   * Transforma as horas enviadas do parâmetro JSON para ArrayList<HoraEnvioInVO>
   * 
   * @param obrigatorio
   * @return
   * @throws ServletParametrosException
   */
  protected ArrayList<HoraEnviadaVO> getHorasEnviadas(boolean obrigatorio) throws ServletParametrosException
  {
    String sHorasEnviadas = getParametro("LANCS", obrigatorio, true);
    JSONTokener tokener = new JSONTokener(sHorasEnviadas);
    JSONArray jsonHorasEnviadas = new JSONArray(tokener);

    ArrayList<HoraEnviadaVO> horasEnviadas = new ArrayList<HoraEnviadaVO>();
    for (int i = 0; i < jsonHorasEnviadas.length(); i++)
    {
      JSONObject jsonHoraEnviada = jsonHorasEnviadas.getJSONObject(i);

      HoraEnviadaVO horaEnviadaVo = new HoraEnviadaVO();
      horaEnviadaVo.setIdLancamento(jsonHoraEnviada.getInt("IL"));
      horaEnviadaVo.setNumeroPassClock(jsonHoraEnviada.getString("PC"));
      horaEnviadaVo.setCodigoPassClock(jsonHoraEnviada.getString("CD"));
      horaEnviadaVo.setNota(jsonHoraEnviada.getString("NT"));
      horaEnviadaVo.setHashCode(jsonHoraEnviada.getString("HC"));
      horaEnviadaVo.setHrLancada(Util.parseHora(jsonHoraEnviada.getString("HL")));
      horaEnviadaVo.setHrDigitacao(Util.parseHoraSegundos(jsonHoraEnviada.getString("HD")));
      horaEnviadaVo.setLatitude((float) jsonHoraEnviada.getDouble("LA"));
      horaEnviadaVo.setLongitude((float) jsonHoraEnviada.getDouble("LO"));

      horasEnviadas.add(horaEnviadaVo);
    }
    
//    private String   numeroPassClock;
//    private String   codigoPassClock;
//    private String   nota;
//    private DateTime hrLancada;
//    private DateTime hrDigitacao;
//    private String   hashCode;
//    private float    latitude;
//    private float    longitude;
    
    return horasEnviadas;
  }
}
