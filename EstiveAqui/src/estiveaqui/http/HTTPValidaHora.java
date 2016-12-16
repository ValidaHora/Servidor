package estiveaqui.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import estiveaqui.CodigoErro;
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
  public static ArrayList<TokenMO> buscaSementes(ArrayList<PassClockMO> passClocksMO) throws HTTPValidaHoraException
  {
    ArrayList<TokenMO> tokensMO = new ArrayList<TokenMO>();

    String params = "?V=1.0.0&CLI=EstiveAqui&SEN=" + SENHA;
    
    for (PassClockMO passClockMO : passClocksMO)
      params += "&TOKS=" + passClockMO.getNumPassClock();
    
    //  Busca as sementes no site ValidaHora.
    JSONObject jsonGetSementes = sendGet("ValidaHora/GetSementes" + params);
    
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
   * Chama o servidor ValidaHora e solicita a execução de uma ação.
   * 
   * @param acao
   */
  private static JSONObject sendGet(String acao) throws HTTPValidaHoraException
  {
    String url = "http://" + SERVIDOR + ":" + PORTA + "/" + acao;

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
//    ArrayList<TokenMO> tokensMO = buscaSementes(new ArrayList<PassClockMO>());
//
//    for (TokenMO token : tokensMO)
//    {
//      System.out.println(token.getNumeroToken() + " - " + token.getCodigoAlgoritmo() + " - " + token.getSemente());
//    }
//  }

}
