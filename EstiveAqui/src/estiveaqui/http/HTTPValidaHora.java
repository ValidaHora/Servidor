package estiveaqui.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import estiveaqui.CodigoErro;
import estiveaqui.RegraDeNegocioException;
import estiveaqui.Util;
import estiveaqui.appgestor.gerencia.passclock.GerenciaPassClockInVO;
import estiveaqui.appusuario.lancahora.LancaHoraInVO;
import estiveaqui.appusuario.lancahoras.HoraEnviadaVO;
import estiveaqui.appusuario.lancahoras.LancaHorasInVO;
import estiveaqui.appusuario.lancahoras.LancaValidaHoraServidorOutVO;
import estiveaqui.sql.mo.LancamentoMO;
import estiveaqui.sql.mo.PassClockMO;
import estiveaqui.sql.mo.externo.TokenMO;

public class HTTPValidaHora
{
  private static final Logger log = LogManager.getLogger();

  private static String SERVIDOR = "localhost";
  private static String PORTA    = "8080";
  private static String SENHA    = "Teste";

  /**
   * Busca as sementes no site do ValidaHora.
   * 
   * @param passClocksMO
   * @return
   */
  public static List<TokenMO> buscaSementes(List<PassClockMO> passClocksMO) throws HTTPValidaHoraException
  {
    List<TokenMO> tokensMO = new ArrayList<TokenMO>();

    //  Monta a string dos parâmetros.
    String params = "?V=1.0.0&CLI=EstiveAqui&SEN=" + SENHA;
    for (PassClockMO passClockMO : passClocksMO)
      params += "&TOKS=" + passClockMO.getNumPassClock();
    
    //  Busca as sementes no site ValidaHora.
    JSONObject jsonGetSementes = sendGet("GetSementes" + params);
    
    //  Se erro.
    if ( !jsonGetSementes.getBoolean("ValidadoOk"))
      throw new HTTPValidaHoraException(CodigoErro.ERRO_INTERNO, jsonGetSementes.getJSONArray("Mensagens").getJSONObject(0).getString("Log"));
    
    //  Copia as sementes para retorno.
    JSONArray jsonToken = jsonGetSementes.getJSONArray("Tokens");
    for (int i = 0; i < jsonToken.length(); i++)
    {
      JSONObject jToken = jsonToken.getJSONObject(i);
      TokenMO tokenMO = new TokenMO();
      tokenMO.setNumeroToken(jToken.getString("TOK"));
      tokenMO.setCodigoAlgoritmo(jToken.getInt("ALGRTM"));
      tokenMO.setSemente(jToken.getString("SMNT"));

      tokensMO.add(tokenMO);
    }

    return tokensMO;
  }

  /**
   * Chama via HTTP o servidor ValidaHora para calcular hora com base no código.
   * 
   * @param lancaHoraInVO
   * @throws HTTPValidaHoraException
   */
  public static LancamentoMO calculaHora(LancaHoraInVO lancaHoraInVO) throws HTTPValidaHoraException
  {
    //  Monta a string dos parâmetros.
    String params = "?V=1.0.0&CLI=EstiveAqui&SEN=" + SENHA + 
        "&TOK=" + lancaHoraInVO.getNumPassClock() + "&COD=" + lancaHoraInVO.getCodigo() +
        "&HDG=" + Util.formataDataTransmissaoSemSegundos(lancaHoraInVO.getHoraDigitada()) + "&HEN=" + Util.formataDataTransmissaoComSegundos(DateTime.now());
    
    //  Busca as sementes no site ValidaHora.
    JSONObject jsonGetHoraCalculada = sendGet("CalculaHora" + params);
    
    //  Houve erro?
    if ( !jsonGetHoraCalculada.getBoolean("ValidadoOk"))
    {
      int codigoErro = jsonGetHoraCalculada.getJSONArray("Mensagens").getJSONObject(0).getInt("Codigo");

      if (codigoErro == 102)  //  Código já lançado.
        throw new HTTPValidaHoraException(CodigoErro.CODIGO_INVALIDO);
      if (codigoErro == 101)  //  Código inválido.
        throw new HTTPValidaHoraException(CodigoErro.CODIGO_INVALIDO);
      if (codigoErro == 106)  //  Maximo de lançamentos alcançado
        throw new HTTPValidaHoraException(CodigoErro.ERRO_INTERNO, "Máximo de lançamentos alcançados pelo EstiveAqui.");
      if (codigoErro == 104)  //  Token não existe.
        throw new HTTPValidaHoraException(CodigoErro.ERRO_INTERNO, jsonGetHoraCalculada.getJSONArray("Mensagens").getJSONObject(0).getString("Log"));
      if (codigoErro == 103)  //  Token inválido, não habilitado.
        throw new HTTPValidaHoraException(CodigoErro.ERRO_INTERNO, jsonGetHoraCalculada.getJSONArray("Mensagens").getJSONObject(0).getString("Log"));

      throw new HTTPValidaHoraException(CodigoErro.ERRO_INTERNO, jsonGetHoraCalculada.getJSONArray("Mensagens").getJSONObject(0).getString("Log"));
    }

    //  TODO: Criar outro objeto para retorno dos dados que não sea o LancamentoMO.
    //  Retorna a hora calculada e o hashcode.
    LancamentoMO lancamentoMO = new LancamentoMO();
    lancamentoMO.setHrLancamento(Util.parseDataTransmissaoSemSegundos(jsonGetHoraCalculada.getString("HoraLancada")));
    lancamentoMO.setHrPassClock(lancamentoMO.getHrLancamento());
    lancamentoMO.setHashCode(jsonGetHoraCalculada.getString("HashCode"));
    
    return lancamentoMO;
  }

  /**
   * Calcula um conjunto de horas a partir de um conjunto de lançamento de códigos.
   * 
   * @param lancaHorasInVO
   * @return
   * @throws HTTPValidaHoraException
   */
  public static List<LancaValidaHoraServidorOutVO> calculaHoras(LancaHorasInVO lancaHorasInVO) throws HTTPValidaHoraException
  {
    //  Monta a string dos parâmetros.
    String params = "?V=1.0.0&CLI=EstiveAqui&SEN=" + SENHA +
        "&HEN=" + Util.formataDataTransmissaoComSegundos(DateTime.now()) +
        "&LANCS=[";
    
    String horas = "";
    for (HoraEnviadaVO horaEnviada : lancaHorasInVO.getHorasEnviadas())
    {
      if (! horaEnviada.isValidaHora())
        continue;

      horas += "{CL:" + horaEnviada.getContadorLancamento() + ",TK:\"" + horaEnviada.getNumeroPassClock() 
             + "\",CD:\"" + horaEnviada.getCodigoPassClock() 
             + "\",HD:\"" + Util.formataDataTransmissaoComSegundos(horaEnviada.getHrDigitacao()) 
             + "\",LA:" + horaEnviada.getLatitude() + ",LO:" + horaEnviada.getLongitude() + "},";
    }

    //  Busca as sementes no site ValidaHora.
    JSONObject jsonGetHoraCalculada = sendGet("CalculaHoras" + params + horas + "]");
    
    //  Houve erro?
    if ( !jsonGetHoraCalculada.getBoolean("ValidadoOk"))
    {
      int codigoErro = jsonGetHoraCalculada.getJSONArray("Mensagens").getJSONObject(0).getInt("Codigo");

      if (codigoErro == 102)  //  Código já lançado.
        throw new HTTPValidaHoraException(CodigoErro.CODIGO_INVALIDO);
      if (codigoErro == 101)  //  Código inválido.
        throw new HTTPValidaHoraException(CodigoErro.CODIGO_INVALIDO);
      if (codigoErro == 106)  //  Maximo de lançamentos alcançado
        throw new HTTPValidaHoraException(CodigoErro.ERRO_INTERNO, "Máximo de lançamentos alcançados pelo EstiveAqui.");
      if (codigoErro == 104)  //  Token não existe.
        throw new HTTPValidaHoraException(CodigoErro.ERRO_INTERNO, jsonGetHoraCalculada.getJSONArray("Mensagens").getJSONObject(0).getString("Log"));
      if (codigoErro == 103)  //  Token inválido, não habilitado.
        throw new HTTPValidaHoraException(CodigoErro.ERRO_INTERNO, jsonGetHoraCalculada.getJSONArray("Mensagens").getJSONObject(0).getString("Log"));

      throw new HTTPValidaHoraException(CodigoErro.ERRO_INTERNO, jsonGetHoraCalculada.getJSONArray("Mensagens").getJSONObject(0).getString("Log"));
    }

    //  Interpreta o retorno do lançamento.
    List<LancaValidaHoraServidorOutVO> lancaValidaHorasServidorOutVO = new ArrayList<LancaValidaHoraServidorOutVO>();
    JSONArray jLancamentos = jsonGetHoraCalculada.getJSONArray("Lancamentos");
    for (int i = 0; i < jLancamentos.length(); i++)
    {
      JSONObject jLancamento = jLancamentos.getJSONObject(i);
      
      //  Retorna a hora calculada e o hashcode.
      LancaValidaHoraServidorOutVO lancaValidaHoraServidorOutVO = new LancaValidaHoraServidorOutVO();
      lancaValidaHorasServidorOutVO.add(lancaValidaHoraServidorOutVO);

      lancaValidaHoraServidorOutVO.setContadorLancamento(jLancamento.getInt("CL"));
      boolean ok = jLancamento.getBoolean("OK");
      lancaValidaHoraServidorOutVO.setOk(ok);

      //  Guarda o lançamento e cálculo do hora lançada.
      if (ok)
      {
        lancaValidaHoraServidorOutVO.setHoraLancada(Util.parseDataTransmissaoSemSegundos(jLancamento.getString("HL")));
        lancaValidaHoraServidorOutVO.setHashCode(jLancamento.getString("HC"));
      }
      else
      {
        //  Interpreta e transforma o código e mensagem de erro.
        JSONObject jErro = jLancamento.getJSONObject("Erro");
        int codErro = jErro.getInt("CE");
        RegraDeNegocioException rne = null;
        if (codErro == 1)
          rne = new RegraDeNegocioException(CodigoErro.ERRO_INTERNO);
        else if (codErro == 102)
          rne = new RegraDeNegocioException(CodigoErro.CODIGO_JA_LANCADO);

        lancaValidaHoraServidorOutVO.setExcecao(rne);
      }
    }

    return lancaValidaHorasServidorOutVO;
  }

  
  
  /**
   * Chama o servidor ValidaHora e solicita a execução de uma ação.
   * 
   * @param acao
   */
  private static JSONObject sendGet(String acao) throws HTTPValidaHoraException
  {
    String url = "http://" + SERVIDOR + ":" + PORTA + "/ValidaHora/" + acao;

    //  Abre a conexão com o servidor.
    HttpURLConnection con;
    JSONObject json = null;

    try
    {
      con = (HttpURLConnection) new URL(url).openConnection();

      // optional default is GET
      con.setRequestMethod("GET");

      //add request header
      String USER_AGENT = "Mozilla/5.0";
      con.setRequestProperty("User-Agent", USER_AGENT);

      int responseCode = con.getResponseCode();
      if (responseCode != 200)
        throw new HTTPValidaHoraException(responseCode);

      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
      json = new JSONObject(new JSONTokener(in));
      in.close();
    }
    catch (IOException e)
    {
      throw new HTTPValidaHoraException(CodigoErro.ERRO_INTERNO, e.getMessage());
    }

    return json;
  }

  /**
   * Obtém o contexto do ambiente onde está rodando.
   * 
   * @param servletContext
   */
  public static void getContext(ServletContext servletContext)
  {
    if (servletContext == null)
      return;

    try
    {
      String param;
      param = servletContext.getInitParameter("NomeServidor");
      SERVIDOR = (param == null ? SERVIDOR : param);
      param = servletContext.getInitParameter("PortaServidor");
      PORTA = (param == null ? PORTA : param);
      param = servletContext.getInitParameter("SenhaValidaHora");
      SENHA = (param == null ? SENHA : param);
    }
    catch (Exception e)
    {
      log.error("HTTPValidaHora.Init() com problemas para obter o contexto", e);
    }
  }

  /**
   * Valida o código do para o PassClock no momento atual (Hora de Envio).
   * 
   * @param gerenciaPassClockInVO
   * @return
   * @throws HTTPValidaHoraException
   */
  public static LancamentoMO validaCodigo(GerenciaPassClockInVO gerenciaPassClockInVO) throws HTTPValidaHoraException
  {
    //  Monta a string dos parâmetros.
    String params = "?V=1.0.0&CLI=EstiveAqui&SEN=" + SENHA + 
        "&TOK=" + gerenciaPassClockInVO.getNumPassClock() + "&COD=" + gerenciaPassClockInVO.getCodPassClock() +
        "&HEN=" + Util.formataDataTransmissaoComSegundos(DateTime.now());
    
    //  Busca as sementes no site ValidaHora.
    JSONObject jsonGetHoraCalculada = sendGet("ValidaCodigo" + params);
    
    //  Houve erro?
    if ( !jsonGetHoraCalculada.getBoolean("ValidadoOk"))
    {
      int codigoErro = jsonGetHoraCalculada.getJSONArray("Mensagens").getJSONObject(0).getInt("Codigo");

      if (codigoErro == 102)  //  Código já lançado.
        throw new HTTPValidaHoraException(CodigoErro.CODIGO_INVALIDO);
      if (codigoErro == 101)  //  Código inválido.
        throw new HTTPValidaHoraException(CodigoErro.CODIGO_INVALIDO);
      if (codigoErro == 106)  //  Maximo de lançamentos alcançado
        throw new HTTPValidaHoraException(CodigoErro.ERRO_INTERNO, "Máximo de lançamentos alcançados pelo EstiveAqui.");
      if (codigoErro == 104)  //  Token não existe.
        throw new HTTPValidaHoraException(CodigoErro.ERRO_INTERNO, jsonGetHoraCalculada.getJSONArray("Mensagens").getJSONObject(0).getString("Log"));
      if (codigoErro == 103)  //  Token inválido, não habilitado.
        throw new HTTPValidaHoraException(CodigoErro.ERRO_INTERNO, jsonGetHoraCalculada.getJSONArray("Mensagens").getJSONObject(0).getString("Log"));

      throw new HTTPValidaHoraException(CodigoErro.ERRO_INTERNO, jsonGetHoraCalculada.getJSONArray("Mensagens").getJSONObject(0).getString("Log"));
    }

    //  Retorna a hora calculada e o hashcode.
    LancamentoMO lancamentoMO = new LancamentoMO();
    lancamentoMO.setHashCode(jsonGetHoraCalculada.getString("HashCode"));
    
    return lancamentoMO;
  }
  
//  public static void main(String[] args) throws Exception
//  {
////    JSONObject json = sendGet("ValidaHora/GetSementes?V=1.0.0&CLI=EstiveAqui&SEN=Teste&TOKS=Apple-01&TOKS=Apple-02");
////    System.out.println(json.toString());
////    JSONArray jsonArray = json.getJSONArray("Tokens");
////    for (int i = 0; i < jsonArray.length(); i++)
////    {
////      JSONObject jPassClock = jsonArray.getJSONObject(i);
////      System.out.println(jPassClock.getString("TOK") + " : " + jPassClock.getInt("ALGRTM") + " : " + jPassClock.getString("SMNT"));
////    }
//
//    List<TokenMO> tokensMO = buscaSementes(new ArrayList<PassClockMO>());
//
//    for (TokenMO token : tokensMO)
//    {
//      System.out.println(token.getNumeroToken() + " - " + token.getCodigoAlgoritmo() + " - " + token.getSemente());
//    }
//  }

}
