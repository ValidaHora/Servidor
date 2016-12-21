package estiveaqui.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import estiveaqui.CodigoErro;
import estiveaqui.Versao;
import estiveaqui.sql.mo.LancamentoMO;
import estiveaqui.vo.DadosInVO;

/**
 * Interpreta e transforma de forma padronizada os par�metros recebidos pelo ValidaHora
 * na requisi��o HTTP.
 * 
 * @author Haroldo
 *
 */
@Deprecated
public abstract class ServletParametros0 
{
  private static final Logger           log                      = LogManager.getLogger();

  protected static final String         HTTPPARM_FORMATOINVALIDO = "Par�metro ''{0}'' com formato inv�lido em ''{1}''. Formato v�lido ''{2}'', valor passado = ''{3}''";
  protected static final String         HTTPPARM_VALORINVALIDO   = "Par�metro ''{0}'' com valor inv�lido em ''{1}''. Valor passado = ''{2}''";

  private Map<String, String[]>         params                   = null;
  protected String                      acao                     = "";
  protected DadosInVO dadosInVo;

  private static final String           FORMATOHORA              = "yyyyMMddHHmm";
  public static final DateTimeFormatter FMTHORA                  = DateTimeFormat.forPattern(FORMATOHORA);
  private static final String           FORMATOHORASEGUNDOS      = "yyyyMMddHHmmSS";
  public static final DateTimeFormatter FMTHORATRANSMISSAO       = DateTimeFormat.forPattern(FORMATOHORASEGUNDOS);
  private static final String           FORMATODATA              = "yyyyMMdd";
  public static final DateTimeFormatter FMTDATATRANSMISSAO       = DateTimeFormat.forPattern(FORMATODATA);

  /**
   * Busca os par�metros de um request de HTTP.
   * 
   * @param request
   *          - Par�metro request de uma chamada HTTP de onde os par�metros ser�o lidos.
   * @param acao
   *          - Opera��o que est� sendo executada. Descritivo da requisi��o.
   * @throws IOException
   * @throws ServletParametrosException 
   */
  @Deprecated
  public ServletParametros0(HttpServletRequest request, String acao, DadosInVO dadosInVo) throws IOException, ServletParametrosException
  {
    request.setCharacterEncoding("UTF-8");

    log.debug("Lendo par�metros HTTP para '{}'", acao);
    this.acao = acao;

    params = request.getParameterMap();

    String servidor = request.getServerName();  // + ":" + request.getLocalPort();
    
    String ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    ip = (ip != null ? ip : request.getRemoteAddr());
    
    //  Inicializa os valores padrões ou obrigat�rios de todas as chamadas.
    dadosInVo.setIp(ip);
    dadosInVo.setServidor(servidor);
    dadosInVo.setVersao(getVersao());
    
    this.dadosInVo = dadosInVo;
    
    log.debug("IP chamador = {}", ip);
  }

//  /**
//   * Busca os par�metros de um mapa Map<String, String[]>.
//   * 
//   * @param params
//   *          - Mapa de Strings de onde os par�metros ser�o lidos.
//   * @param acao
//   *          - A��o que est� sendo tomada.
//   */
//  public ServletParametros(Map<String, String[]> params, String acao, DadosInVO dadosInVo)
//  {
//    this.params = params;
//    this.acao = acao;
//  }
//
  /**
   * Retorna o valor de um par�metro HTTP.
   * Se n�o houver par�metro ou se houver mais de um par�metro, retorna null.
   * 
   * @param nomeParametro
   * @param obrigatorio
   * @param podeLogar 
   * @return
   * @throws ServletParametrosException
   */
  @Deprecated
  protected String getParametro(String nomeParametro, boolean obrigatorio, boolean podeLogar) throws ServletParametrosException
  {
    String[] valores = params.get(nomeParametro);
    if (valores == null || valores.length != 1)
    {
      if (obrigatorio)
        throw new ServletParametrosException(CodigoErro.ERRO_INTERNO, "Par�metro ''{0}'' � obrigat�rio em ''{1}''", nomeParametro, acao);
      return null;
    }

    log.debug("Par�metro: {} = {}", nomeParametro, (podeLogar ? valores[0] : "***"));
    return valores[0];
  }

  /**
   * Retorna os valores de um par�metro HTTP.
   * Se n�o houver par�metro, retorna null.
   * 
   * @param nomeParametro
   * @return
   * @throws ServletParametrosException
   */
  @Deprecated
  protected List<String> getParametros(String nomeParametro, boolean obrigatorio) throws ServletParametrosException
  {
    String[] valores = params.get(nomeParametro);
    if (valores == null)
    {
      if (obrigatorio)
        throw new ServletParametrosException(CodigoErro.ERRO_INTERNO, "Par�metro ''{0}'' � obrigat�rio em ''{1}''", nomeParametro, acao);
      return null;
    }


    return Arrays.asList(valores);
  }

  /**
   * Retorna um par�metro de n�mero inteiro.
   * 
   * @param nomeParametro
   * @param obrigatorio
   * @return
   * @throws ServletParametrosException
   */
  protected int getParametroInt(String nomeParametro, boolean obrigatorio, boolean podeLogar) throws ServletParametrosException
  {
    return (int)getParametroLong(nomeParametro, obrigatorio, podeLogar);
  }
  
  /**
   * Retorna um par�metro de n�mero longo.
   * 
   * @param nomeParametro
   * @param obrigatorio
   * @return
   * @throws ServletParametrosException
   */
  protected long getParametroLong(String nomeParametro, boolean obrigatorio, boolean podeLogar) throws ServletParametrosException
  {
    String val = getParametro(nomeParametro, obrigatorio, podeLogar);
    if (val == null)
      return 0;

    long valLong = 0;
    try
    {
      valLong = Long.parseLong(val);
    }
    catch (NumberFormatException e)
    {
      throw new ServletParametrosException(CodigoErro.ERRO_INTERNO, HTTPPARM_FORMATOINVALIDO, nomeParametro, acao, "99", val);
    }

    return valLong;
  }

  /**
   * 
   * @param nomeParametro
   * @param obrigatorio
   * @param podeLogar
   * @return
   * @throws ServletParametrosException
   */
  protected float getParametroFloat(String nomeParametro, boolean obrigatorio, boolean podeLogar) throws ServletParametrosException
  {
    String val = getParametro(nomeParametro, obrigatorio, podeLogar);
    if (val == null)
      return 0;

    float valFloat = 0;
    try
    {
      valFloat = Float.parseFloat(val);
    }
    catch (NumberFormatException e)
    {
      throw new ServletParametrosException(CodigoErro.ERRO_INTERNO, HTTPPARM_FORMATOINVALIDO, nomeParametro, acao, "99", val);
    }

    return valFloat;
  }

  /**
   * Busca e retorna o par�metro da vers�o do servidor que o cliente deseja se comunicar.
   * Par�metro sempre obrigat�rio.
   * 
   * V=<Vers�o> - Vers�o esperada do servidor. Ex. 1.0.0
   * 
   * @return - String contendo a vers�o no formato correto.
   * @throws ServletParametrosException
   */
  private final Versao getVersao() throws ServletParametrosException
  {
    String param = "V";
    String versao = getParametro(param, true, true);

    Versao v = new Versao(versao);
    if (!v.isOk())
      throw new ServletParametrosException(CodigoErro.ERRO_INTERNO, HTTPPARM_FORMATOINVALIDO, param, acao, "9.9.9", versao);

    return v;
  }

  /**
   * Busca e retorna o par�metro do n�mero de identifica��o do app.
   * 
   * IDAPP=<IdentificacaoApp> - N�mero de identifica��o do app.
   * 
   * @param obrigatorio
   * @return
   * @throws ServletParametrosException
   */
  @Deprecated
  protected String getIdentificacaoApp0(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametro("IDAPP", obrigatorio, false);
  }

  /**
   * Busca e retorna o par�metro do n�mero de identifica��o do app.
   * 
   * ID=<IdentificacaoApp> - N�mero de identifica��o do app.
   * 
   * @param obrigatorio
   * @return
   * @throws ServletParametrosException
   */
  @Deprecated
  protected String getIdentificacaoApp(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametro("ID", obrigatorio, false);
  }

  /**
   * Busca e retorna o par�metro do n�mero de identifica��o do cliente.
   * 
   * CLI=<N�meroCliente> - N�mero de identifica��o do cliente.
   * 
   * @param obrigatorio
   *          - Informa se o par�metro � ou n�o obrigat�rio.
   * @return
   * @throws ServletParametrosException
   */
  protected String getIdCliente(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametro("CLI", obrigatorio, true);
  }

  /**
   * Busca e retorna o par�metro do n�mero de identifica��o do cliente.
   * 
   * SEN=<SenhaCliente> - Senha do cliente.
   * 
   * @param obrigatorio
   *          - Informa se o par�metro � ou n�o obrigat�rio.
   * @return
   * @throws ServletParametrosException
   */
  protected String getSenhaCliente(boolean obrigatorio) throws ServletParametrosException
  {
    String param = "SEN";
    String val = getParametro(param, obrigatorio, false);

    return val;
  }

  /**
   * Busca e retorna o par�metro do fuso hor�rio.
   * 
   * TZ=<TimeZone> - Fuso hor�rio da hora lan�ada.
   * Formato sHHMM, onde:
   * s - Sinal + ou -.
   * HH - Hora no formato 24h. De 00 a 24.
   * MM - Minuto. De 00 a 59.
   * 
   * @param obrigatorio
   *          - Informa se o par�metro � ou n�o obrigat�rio.
   * @return - O fuso hor�rio no formato DateTimeZone
   * @throws ServletParametrosException
   */
  protected DateTimeZone getTimeZone(boolean obrigatorio) throws ServletParametrosException
  {
    String param = "TZ";
    String val = getParametro(param, obrigatorio, true);
    if (val == null)
      return null;

    if (val.length() != 5)
    {
      log.error("Erro formato TimeZone");
      throw new ServletParametrosException(CodigoErro.ERRO_INTERNO, HTTPPARM_FORMATOINVALIDO, param, acao, "sHHMM", val);
    }

    int hrTz = 0;
    int minTz = 0;
    try
    {
      hrTz = Integer.parseInt(val.substring(0, 3));
      minTz = Integer.parseInt(val.substring(3, 5));
    }
    catch (NumberFormatException e)
    {
      log.error("Erro formato TimeZone", e);
      throw new ServletParametrosException(CodigoErro.ERRO_INTERNO, HTTPPARM_FORMATOINVALIDO, param, acao, "sHHMM", val);
    }

    return DateTimeZone.forOffsetHoursMinutes(hrTz, minTz);
  }

  /**
   * Busca e retorna o par�metro n�mero de identifica��o do PassClock.
   * 
   * NUMPASSCLOCK=<N�meroPassClock> - N�mero de identifica��o do PassClock.
   * 
   * @param obrigatorio
   *          - Informa se o par�metro � ou n�o obrigat�rio.
   * @return
   * @throws ServletParametrosException
   */
  @Deprecated
  protected String getNumeroPassClock0(boolean obrigatorio) throws ServletParametrosException
  {
    String param = "NUMPASSCLOCK";
    String val = getParametro(param, obrigatorio, true);

    return val;
  }

  /**
   * Busca e retorna o par�metro n�mero de identifica��o do PassClock.
   * 
   * NUMPASSCLOCK=<N�meroPassClock> - N�mero de identifica��o do PassClock.
   * 
   * @param obrigatorio
   *          - Informa se o par�metro � ou n�o obrigat�rio.
   * @return
   * @throws ServletParametrosException
   */
  @Deprecated
  protected String getNumeroPassClock(boolean obrigatorio) throws ServletParametrosException
  {
    String param = "PC";
    String val = getParametro(param, obrigatorio, true);

    return val;
  }

  /**
   * Busca e retorna o par�metro do c�digo do PassClock.
   * 
   * COD=<C�digoPassClock> - C�digo apresentado pelo PassClock no momento da digita��o.
   * 
   * @param obrigatorio
   *          - Informa se o par�metro � ou n�o obrigat�rio.
   * @return
   * @throws ServletParametrosException
   */
  protected String getCodigoPassClock(boolean obrigatorio) throws ServletParametrosException
  {
    String param = "CODIGO";
    String val = getParametro(param, obrigatorio, false);
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

  /**
   * Busca e retorna o par�metro do c�digo de ativa��o de AppUsuario.
   * 
   * HC=<HashCode> - C�digo que identifica o lan�amento em ValidaHora.
   * 
   * @param obrigatorio
   *          - Informa se o par�metro � ou n�o obrigat�rio.
   * @return
   * @throws ServletParametrosException
   */
  protected String getHashCode(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametro("HASHCODE", obrigatorio, true);
  }

  /**
   * Busca e retorna o par�metro do c�digo de ativa��o de AppUsuario.
   * 
   * NOTA=<Observa��o do usu�rio> - Uma nota ou observa��o do usu�rio que lan�ou a hora, relativo ao lan�amento da hora.
   * 
   * @param obrigatorio
   *          - Informa se o par�metro � ou n�o obrigat�rio.
   * @return
   * @throws ServletParametrosException
   */
  protected String getNota(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametro("NOTA", obrigatorio, true);
  }

  /**
   * Busca e retorna o par�metro do c�digo de ativa��o de AppUsuario.
   * 
   * CODATIVACAO=<CodAtivacao> - C�digo para ativa��o de AppUsuario.
   * 
   * @param obrigatorio
   *          - Informa se o par�metro � ou n�o obrigat�rio.
   * @return
   * @throws ServletParametrosException
   */
  protected String getCodigoAtivacao(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametro("CODATIVACAO", obrigatorio, true);
  }

  /**
   * Busca e retorna par�metro hora no formato padr�o.
   * 
   * Formato AAAAMMDDHHmmSS
   * AAAA - Ano.
   * MM - M�s. De 01 a 12.
   * DD - Dia do m�s. De 01 a 31.
   * HH - Hora do dia. De 00 a 23.
   * mm - Minuto da hora. De 00 a 59.
   * SS - Segundo do minuto. De 00 a 59.
   * 
   * @param param
   *          - Nome do par�metro hora que deve ser retornado.
   * @param obrigatorio
   *          - Informa se o par�metro � ou n�o obrigat�rio.
   * @return
   * @throws ServletParametrosException
   */
  @Deprecated
  protected DateTime getParametroHoraSegundos(String param, boolean obrigatorio, DateTimeZone tz) throws ServletParametrosException
  {
    String val = getParametro(param, obrigatorio, true);
    if (val == null)
      return null;

    try
    {
      return FMTHORATRANSMISSAO.parseDateTime(val).withZone(tz);
    }
    catch (UnsupportedOperationException | IllegalArgumentException e)
    {
      throw new ServletParametrosException(CodigoErro.ERRO_INTERNO, HTTPPARM_FORMATOINVALIDO, param, acao, FORMATOHORASEGUNDOS, val);
    }
  }

  /**
   * Busca e retorna par�metro hora no formato padr�o.
   * 
   * Formato AAAAMMDDHHmm
   * AAAA - Ano.
   * MM - M�s. De 01 a 12.
   * DD - Dia do m�s. De 01 a 31.
   * HH - Hora do dia. De 00 a 23.
   * mm - Minuto da hora. De 00 a 59.
   * 
   * @param param
   *          - Nome do par�metro hora que deve ser retornado.
   * @param obrigatorio
   *          - Informa se o par�metro � ou n�o obrigat�rio.
   * @return
   * @throws ServletParametrosException
   */
  protected DateTime getParametroHora(String param, boolean obrigatorio, DateTimeZone tz) throws ServletParametrosException
  {
    String val = getParametro(param, obrigatorio, true);
    if (val == null)
      return null;

    try
    {
      return FMTHORA.parseDateTime(val).withZone(tz);
    }
    catch (UnsupportedOperationException | IllegalArgumentException e)
    {
      throw new ServletParametrosException(CodigoErro.ERRO_INTERNO, HTTPPARM_FORMATOINVALIDO, param, acao, FORMATOHORA, val);
    }
  }

  /**
   * Busca e retorna par�metro data no formato padr�o.
   * 
   * Formato AAAAMMDD
   * AAAA - Ano.
   * MM - M�s. De 01 a 12.
   * DD - Dia do m�s. De 01 a 31.
   * 
   * @param param
   *          Nome do par�metro data que deve ser retornado.
   * @param obrigatorio
   *          Informa se o par�metro � ou n�o obrigat�rio.
   * @return
   * @throws ServletParametrosException
   */
  protected DateTime getData(String param, boolean obrigatorio, DateTimeZone tz) throws ServletParametrosException
  {
    String val = getParametro(param, obrigatorio, true);
    if (val == null)
      return null;

    try
    {
      return FMTDATATRANSMISSAO.parseDateTime(val).withZone(tz);
    }
    catch (UnsupportedOperationException | IllegalArgumentException e)
    {
      throw new ServletParametrosException(CodigoErro.ERRO_INTERNO, HTTPPARM_FORMATOINVALIDO, param, acao, FORMATODATA, val);
    }
  }

  /**
   * Busca e retorna o par�metro que cont�m a toler�ncia em minutos para atraso ou adiantamento da hora do PassClock.
   * 
   * @param obrigatorio
   * @return
   * @throws ServletParametrosException
   */
  protected int getToleranciaHr(boolean obrigatorio) throws ServletParametrosException
  {
    String param = "TOL";
    String val = getParametro(param, obrigatorio, true);
    if (val == null)
      return 0;

    int toleranciaHr = 0;
    try
    {
      toleranciaHr = Integer.parseInt(val);
    }
    catch (NumberFormatException e)
    {
      throw new ServletParametrosException(CodigoErro.ERRO_INTERNO, HTTPPARM_FORMATOINVALIDO, param, acao, "99", val);
    }

    if (toleranciaHr < 0)
      throw new ServletParametrosException(CodigoErro.ERRO_INTERNO, HTTPPARM_VALORINVALIDO, param, acao, val);

    return toleranciaHr;
  }

  /**
   * Busca e retorna o identificador do dispositivo.
   *
   * DSP=<IdDispositivo> - Identifica��o do dispositivo. Um IMEI ou outra identifica��o qualquer.
   * 
   * @param obrigatorio
   *          - Informa se o par�metro � ou n�o obrigat�rio.
   * @return
   * @throws ServletParametrosException
   */
  protected String getDispositivo(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametro("IDDISPOSITIVO", obrigatorio, true);
  }

  /**
   * Busca e retorna latitude / longitude.
   * 
   * Erro � passado no seguinte formato:
   * 
   * @param param
   * @param obrigatorio
   *          - Informa se o par�metro � ou n�o obrigat�rio.
   * @return
   * @throws ServletParametrosException
   */
  protected float getLatLongitude(String param, int max, boolean obrigatorio) throws ServletParametrosException
  {
    float val = getParametroFloat(param, obrigatorio, true);
    if (val == 0)
      return LancamentoMO.INFO_NAO_RECEBIDA;


    if (val > max || val < -max)
    {
      if (val != LancamentoMO.SEM_SUPORTE && val != LancamentoMO.NAO_HABILITADO 
          && val != LancamentoMO.SEM_POSICIONAMENTO && val != LancamentoMO.ERRO_POSICIONAMENTO)
        throw new ServletParametrosException(CodigoErro.ERRO_INTERNO, HTTPPARM_VALORINVALIDO, param, acao, val);
    }

    return val;
  }

  /**
   * Busca e retorna latitude.
   * 
   * LAT=<Latitude> - Latitude no momento da digita��o do c�digo.
   * Formato s99.999999999999999. Com valores entre -90.000000000000000 e +90.000000000000000
   * 
   * @param obrigatorio
   *          - Informa se o par�metro � ou n�o obrigat�rio.
   * @return
   * @throws ServletParametrosException
   */
  protected float getLatitude(boolean obrigatorio) throws ServletParametrosException
  {
    return getLatLongitude("LATITUDE", 90, obrigatorio);
  }

  /**
   * Busca e retorna longitude.
   * 
   * LON=<Longitude> - Longitude no momento da digita��o do c�digo.
   * Formato s999.999999999999999. Com valores entre -180.000000000000000 e +180.000000000000000
   * 
   * @param obrigatorio
   *          - Informa se o par�metro � ou n�o obrigat�rio.
   * @return
   * @throws ServletParametrosException
   */
  protected float getLongitude(boolean obrigatorio) throws ServletParametrosException
  {
    return getLatLongitude("LONGITUDE", 180, obrigatorio);
  }

  /**
   * L� e interpreta os par�metros passados pela requisi��o HTTP.
   * 
   * @return DadosAppGestorInVO
   * @throws ServletParametrosException
   */
  public abstract DadosInVO getParametros() throws ServletParametrosException;

//  public static void main(String[] args)
//  {
//    Map<String, String[]> params = new java.util.HashMap<String, String[]>();
//
//    params.put("V", new String[] { "1.0.0" });
//    params.put("TZ", new String[] { "-0300" });
//    params.put("DSP", new String[] { "TESTETESTETESTE"});
//    params.put("NPC", new String[] { "79718823456" });
//    params.put("COD", new String[] { "123456" });
//    params.put("HDG", new String[] { "20160720194810"});
//    params.put("HEN", new String[] { "20160720194850"});
//    params.put("LAT", new String[] { "a20"});
//    params.put("LON", new String[] { "10"});
//    ParametrosServlet srvl = new ParametrosServlet(params, "Acao");
//
//    try
//    {
//      CalculaHoraVO calcHoraVO = srvl.getCalculaHora();
//      System.out.println("abc");
//    }
//    catch (ServletParametrosException e)
//    {
//      e.printStackTrace();
//      System.out.println("Erro: " + e.getMessage());
//    }
//  }
}
