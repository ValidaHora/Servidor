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

@Deprecated
public class LancaHorasParametrosSrvlt0 extends ServletParametros0
{
  private LancaHorasInVO lancaHoraInVO = (LancaHorasInVO) dadosInVo;

  @Deprecated
  public LancaHorasParametrosSrvlt0(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "Lança Horas", new LancaHorasInVO());
  }

  @Override
  @Deprecated
  public DadosInVO getParametros() throws ServletParametrosException
  {
    lancaHoraInVO.setTz(getTimeZone(true));
    lancaHoraInVO.setIdentificacaoAppUsuario(getIdentificacaoApp(true));
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
  @Deprecated
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
  @Deprecated
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
      horaEnviadaVo.setContadorLancamento(jsonHoraEnviada.getInt("IL"));
      horaEnviadaVo.setNumeroPassClock(jsonHoraEnviada.getString("PC"));
      horaEnviadaVo.setCodigoPassClock(jsonHoraEnviada.getString("CD"));
      horaEnviadaVo.setNota(jsonHoraEnviada.getString("NT"));
      horaEnviadaVo.setHashCode(jsonHoraEnviada.getString("HC"));
      horaEnviadaVo.setHrLancada(Util.parseDataTransmissaoSemSegundos(jsonHoraEnviada.getString("HL")));
      horaEnviadaVo.setHrDigitacao(Util.parseDataTransmissaoComSegundos(jsonHoraEnviada.getString("HD")));
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
