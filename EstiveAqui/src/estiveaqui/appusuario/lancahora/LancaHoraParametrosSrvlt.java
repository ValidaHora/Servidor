package estiveaqui.appusuario.lancahora;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import estiveaqui.servlet.NomeParametroServlet;
import estiveaqui.servlet.ServletParametros;
import estiveaqui.servlet.ServletParametrosException;
import estiveaqui.vo.DadosInVO;

public class LancaHoraParametrosSrvlt extends ServletParametros
{
  private LancaHoraInVO lancaHoraInVO = (LancaHoraInVO)dadosInVo;

  public LancaHoraParametrosSrvlt(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "Lan�a Hora", new LancaHoraInVO());
  }

  @Override
  public DadosInVO getParametros() throws ServletParametrosException
  {
    lancaHoraInVO.setIdentificacaoAppUsuario(getIdentificacaoAppUsuario(true));
    lancaHoraInVO.setNumPassClock(getNumeroPassClock(true));
    lancaHoraInVO.setCodigo(getCodigoPassClock(true));
    lancaHoraInVO.setHoraDigitada(getHoraDigitada(true));
    lancaHoraInVO.setHoraEnviada(getHoraEnviada(true));
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
  protected DateTime getHoraLancada(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametroHora(NomeParametroServlet.HoraLancada, obrigatorio);
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
  protected DateTime getHoraDigitada(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametroHoraSegundos(NomeParametroServlet.HoraDigitada, obrigatorio);
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
  protected DateTime getHoraEnviada(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametroHoraSegundos(NomeParametroServlet.HoraEnviada, obrigatorio);
  }
}
