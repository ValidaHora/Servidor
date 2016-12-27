package estiveaqui.appusuario.lancahoras;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import estiveaqui.Util;
import estiveaqui.dados.ChaveJSON;
import estiveaqui.servlet.NomeParametroServlet;
import estiveaqui.servlet.ServletParametros;
import estiveaqui.servlet.ServletParametrosException;
import estiveaqui.vo.DadosInVO;

public class LancaHorasParametrosSrvlt extends ServletParametros
{
  private LancaHorasInVO lancaHoraInVO = (LancaHorasInVO) dadosInVo;

  public LancaHorasParametrosSrvlt(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "Lança Horas", new LancaHorasInVO());
  }

  @Override
  public DadosInVO getParametros() throws ServletParametrosException
  {
    lancaHoraInVO.setIdentificacaoAppUsuario(getIdentificacaoAppUsuario(true));
    lancaHoraInVO.setHoraEnviada(getHoraEnviada(true));
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
  protected DateTime getHoraEnviada(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametroHoraSegundos(NomeParametroServlet.HoraEnviada, obrigatorio);
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
    String sHorasEnviadas = getParametro(NomeParametroServlet.Lancamentos, obrigatorio, true);

    JSONTokener tokener = new JSONTokener(sHorasEnviadas);
    JSONArray jsonHorasEnviadas = new JSONArray(tokener);

    ArrayList<HoraEnviadaVO> horasEnviadas = new ArrayList<HoraEnviadaVO>();
    for (int i = 0; i < jsonHorasEnviadas.length(); i++)
    {
      JSONObject jsonHoraEnviada = jsonHorasEnviadas.getJSONObject(i);

      HoraEnviadaVO horaEnviadaVo = new HoraEnviadaVO();
      horaEnviadaVo.setContadorLancamento(jsonHoraEnviada.getInt(ChaveJSON.CL.toString()));
      horaEnviadaVo.setNumeroPassClock(jsonHoraEnviada.getString(ChaveJSON.PC.toString()));
      horaEnviadaVo.setCodigoPassClock(jsonHoraEnviada.getString(ChaveJSON.CD.toString()));
      if (!jsonHoraEnviada.isNull(ChaveJSON.NT.toString()))
        horaEnviadaVo.setNota(jsonHoraEnviada.getString(ChaveJSON.NT.toString()));
      horaEnviadaVo.setHrDigitacao(Util.parseDataTransmissaoComSegundos(jsonHoraEnviada.getString(ChaveJSON.HD.toString())));
      horaEnviadaVo.setLatitude((float) jsonHoraEnviada.getDouble(ChaveJSON.LA.toString()));
      horaEnviadaVo.setLongitude((float) jsonHoraEnviada.getDouble(ChaveJSON.LO.toString()));

      horasEnviadas.add(horaEnviadaVo);
    }
    
    return horasEnviadas;
  }
}
