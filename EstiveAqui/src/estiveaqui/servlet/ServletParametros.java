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

public abstract class ServletParametros
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
  public ServletParametros(HttpServletRequest request, String acao, DadosInVO dadosInVo) throws IOException, ServletParametrosException
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
  protected String getParametro(NomeParametroServlet nomeParametro, boolean obrigatorio, boolean podeLogar) throws ServletParametrosException
  {
    System.out.println(nomeParametro);
    String[] valores = params.get(nomeParametro.toString());
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
  protected List<String> getParametros(NomeParametroServlet nomeParametro, boolean obrigatorio) throws ServletParametrosException
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
  protected int getParametroInt(NomeParametroServlet nomeParametro, boolean obrigatorio, boolean podeLogar) throws ServletParametrosException
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
  protected long getParametroLong(NomeParametroServlet nomeParametro, boolean obrigatorio, boolean podeLogar) throws ServletParametrosException
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
  protected float getParametroFloat(NomeParametroServlet nomeParametro, boolean obrigatorio, boolean podeLogar) throws ServletParametrosException
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
    NomeParametroServlet param = NomeParametroServlet.Versao;
    
    String versao = getParametro(param, true, true);

    Versao v = new Versao(versao);
    if (!v.isOk())
      throw new ServletParametrosException(CodigoErro.ERRO_INTERNO, HTTPPARM_FORMATOINVALIDO, param, acao, "9.9.9", versao);

    return v;
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
  protected String getIdentificacaoApp(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametro(NomeParametroServlet.IdentificacaoApp, obrigatorio, false);
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
    return getParametro(NomeParametroServlet.Cliente, obrigatorio, true);
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
    NomeParametroServlet param = NomeParametroServlet.Senha;
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
  protected DateTimeZone getTimeZonePassClock(boolean obrigatorio) throws ServletParametrosException
  {
    NomeParametroServlet param = NomeParametroServlet.TimeZone;
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
   * PC=<N�meroPassClock> - N�mero de identifica��o do PassClock.
   * 
   * @param obrigatorio
   *          - Informa se o par�metro � ou n�o obrigat�rio.
   * @return
   * @throws ServletParametrosException
   */
  protected String getNumeroPassClock(boolean obrigatorio) throws ServletParametrosException
  {
    NomeParametroServlet param = NomeParametroServlet.NumeroPassClock;
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
    NomeParametroServlet param = NomeParametroServlet.Codigo;
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
   * NOTA=<Observa��o do usu�rio> - Uma nota ou observa��o do usu�rio que lan�ou a hora, relativo ao lan�amento da hora.
   * 
   * @param obrigatorio
   *          - Informa se o par�metro � ou n�o obrigat�rio.
   * @return
   * @throws ServletParametrosException
   */
  protected String getNota(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametro(NomeParametroServlet.Nota, obrigatorio, true);
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
    return getParametro(NomeParametroServlet.CodigoAtivacao, obrigatorio, true);
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
  protected DateTime getParametroHoraSegundos(NomeParametroServlet param, boolean obrigatorio, DateTimeZone tz) throws ServletParametrosException
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
  protected DateTime getParametroHora(NomeParametroServlet param, boolean obrigatorio, DateTimeZone tz) throws ServletParametrosException
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
  protected DateTime getData(NomeParametroServlet param, boolean obrigatorio, DateTimeZone tz) throws ServletParametrosException
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
    return getParametro(NomeParametroServlet.IdDispositivo, obrigatorio, true);
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
  protected float getLatLongitude(NomeParametroServlet param, int max, boolean obrigatorio) throws ServletParametrosException
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
    return getLatLongitude(NomeParametroServlet.Latitude, 90, obrigatorio);
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
    return getLatLongitude(NomeParametroServlet.Longitude, 180, obrigatorio);
  }

  /**
   * L� e interpreta os par�metros passados pela requisi��o HTTP.
   * 
   * @return DadosAppGestorInVO
   * @throws ServletParametrosException
   */
  public abstract DadosInVO getParametros() throws ServletParametrosException;

}
