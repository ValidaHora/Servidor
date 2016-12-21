package estiveaqui.appusuario.lancahora;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import estiveaqui.CodigoErro;
import estiveaqui.servlet.ServletParametros0;
import estiveaqui.servlet.ServletParametrosException;
import estiveaqui.vo.DadosInVO;

@Deprecated
public class LancaHoraParametrosSrvlt0 extends ServletParametros0
{
  private LancaHoraInVO lancaHoraInVO = (LancaHoraInVO)dadosInVo;

  @Deprecated
  public LancaHoraParametrosSrvlt0(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "Lan�a Hora", new LancaHoraInVO());
  }

  @Override
  @Deprecated
  public DadosInVO getParametros() throws ServletParametrosException
  {
    lancaHoraInVO.setTzPassClock(getTimeZone(true));
    lancaHoraInVO.setIdentificacaoApp(getIdentificacaoApp0(true));
    lancaHoraInVO.setNumPassClock(getNumeroPassClock0(true));
    lancaHoraInVO.setCodigo(getCodigoPassClock(true));
    lancaHoraInVO.setHashCode(getHashCode(true));
    lancaHoraInVO.setNota(getNota(false));
    lancaHoraInVO.setHoraCalculada(getHoraLancada(true, lancaHoraInVO.getTzPassClock()));
    lancaHoraInVO.setHoraDigitada(getHoraDigitada(true, lancaHoraInVO.getTzPassClock()));
    lancaHoraInVO.setHoraEnviada(getHoraEnviada(true, lancaHoraInVO.getTzPassClock()));
    lancaHoraInVO.setIdDispositivo(getDispositivo(false));
    lancaHoraInVO.setLatitude(getLatitude(true));
    lancaHoraInVO.setLongitude(getLongitude(true));
    
    return lancaHoraInVO;
  }

  /**
   * Busca e retorna o par�metro hora lan�ada.
   * 
   * HDG=<HoraDigita��o> - Hora da digita��o do c�digo apresentado pelo PassClock.
   * Formato AAAAMMDDHHmmSS
   * AAAA - Ano.
   * MM - M�s. De 01 a 12.
   * DD - Dia do m�s. De 01 a 31.
   * HH - Hora do dia. De 00 a 23.
   * mm - Minuto da hora. De 00 a 59.
   * SS - Segundo do minuto. De 00 a 59.
   * 
   * @param obrigatorio
   *          Informa se o par�metro � ou n�o obrigat�rio.
   * @return
   * @throws ServletParametrosException
   */
  @Deprecated
  protected DateTime getHoraLancada(boolean obrigatorio, DateTimeZone tz) throws ServletParametrosException
  {
    return getParametroHora("HRLN", obrigatorio, tz);
  }

  /**
   * Busca e retorna o par�metro hora digitada.
   * 
   * HDG=<HoraDigita��o> - Hora da digita��o do c�digo apresentado pelo PassClock.
   * Formato AAAAMMDDHHmmSS
   * AAAA - Ano.
   * MM - M�s. De 01 a 12.
   * DD - Dia do m�s. De 01 a 31.
   * HH - Hora do dia. De 00 a 23.
   * mm - Minuto da hora. De 00 a 59.
   * SS - Segundo do minuto. De 00 a 59.
   * 
   * @param obrigatorio
   *          - Informa se o par�metro � ou n�o obrigat�rio.
   * @return
   * @throws ServletParametrosException
   */
  @Deprecated
  protected DateTime getHoraDigitada(boolean obrigatorio, DateTimeZone tz) throws ServletParametrosException
  {
    return getParametroHoraSegundos("HRDG", obrigatorio, tz);
  }

  /**
   * Busca e retorna o par�metro hora enviada.
   * 
   * HEN=<HoraEnvio> - Hora em que esta requisi��o foi feita e o c�digo enviado.
   * Formato AAAAMMDDHHmmSS
   * AAAA - Ano.
   * MM - M�s. De 01 a 12.
   * DD - Dia do m�s. De 01 a 31.
   * HH - Hora do dia. De 00 a 23.
   * mm - Minuto da hora. De 00 a 59.
   * SS - Segundo do minuto. De 00 a 59.
   * 
   * @param obrigatorio
   *          - Informa se o par�metro � ou n�o obrigat�rio.
   * @return
   * @throws ServletParametrosException
   */
  @Deprecated
  protected DateTime getHoraEnviada(boolean obrigatorio, DateTimeZone tz) throws ServletParametrosException
  {
    return getParametroHoraSegundos("HREN", obrigatorio, tz);
  }

  //  Deve usar o getHashCode herdado. 
  @Deprecated
  protected String getHashCode(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametro("HC", obrigatorio, true);
  }

  //  Deve usar o getCodigoPassClock herdado. 
  @Deprecated
  protected String getCodigoPassClock(boolean obrigatorio) throws ServletParametrosException
  {
    String param = "CD";
    String val = getParametro(param, obrigatorio, true);
    if (val == null)
      return null;

    try
    {
      Integer.parseInt(val);
    }
    catch (NumberFormatException e)
    {
      throw new ServletParametrosException(CodigoErro.ERRO_INTERNO, HTTPPARM_FORMATOINVALIDO, param, acao, "999999", val);
    }

    if (val.length() != 6)
      throw new ServletParametrosException(CodigoErro.ERRO_INTERNO, HTTPPARM_FORMATOINVALIDO, param, acao, "999999", val);

    return val;
  }

}
