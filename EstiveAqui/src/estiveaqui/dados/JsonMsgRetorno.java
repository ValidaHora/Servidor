package estiveaqui.dados;

import java.util.ArrayList;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;
import estiveaqui.Util;

public class JsonMsgRetorno
{
  private static final Logger log = LogManager.getLogger();

  private JSONObject jPrincipal = new JSONObject();
  private JSONArray  jMsgsErro0 = new JSONArray();
  private JSONObject jMsgErro  = new JSONObject();

  /**
   * Cria o objeto JSON que controla a montagem da mensagem de resposta ao App.
   * 
   * @param idMensagem
   */
  public JsonMsgRetorno(String idMensagem)
  {
    jPrincipal.put(ChaveJSON.ID.toString(), idMensagem);
  }

  /**
   * Imprime a mensagem de forma padronizada.
   * Se o c�digo passado n�o for 0, a mensagem � de erro.
   * 
   * @param codigo
   *          - C�digo do erro.
   * @param texto
   *          - Mensagem de erro.
   */

  public void addMsgErro(int codigo, String texto, String textoDetalhado)
  {
    log.info("addMensagem: ({}, {})", new Object[]{codigo , texto});

    JSONObject jMsgErro0 = new JSONObject();
    jMsgErro0.put(ChaveJSON.CE0.toString(), codigo);
    jMsgErro0.put(ChaveJSON.ME0.toString(), texto);
    jMsgErro.put(ChaveJSON.CE.toString(), codigo);
    jMsgErro.put(ChaveJSON.DE.toString(), texto);
    jMsgErro.put(ChaveJSON.LG.toString(), textoDetalhado);
    jMsgsErro0.put(jMsgErro0);
  }

  public void addMsgErro(CodigoErro cod, Throwable t)
  {
    addMsgErro(cod.getCodigoErro(), cod.getDescricao(), t.getMessage());
  }

  public void addMsgErro(EstiveAquiException e)
  {
    addMsgErro(e.getCodigoErro(), e);
  }

  /**
   * Inclui uma descri��o para ser montada em formato JSON.
   * 
   * @param descricao
   * @param info
   */

  public void put(ChaveJSON descricao, Object info)
  {
    jPrincipal.put(descricao.toString(), info);
  }
  
  /**
   * 
   * @param descricao
   * @param valor
   */

  public void put(ChaveJSON descricao, boolean valor)
  {
    jPrincipal.put(descricao.toString(), valor);
  }
  
  /**
   * Inclui uma data para ser montada em formato JSON com formata��o padr�o de data.
   * 
   * @param definicao
   * @param hora
   */
  public void put(ChaveJSON definicao, DateTime hora)
  {
    put(definicao, Util.formataDataTransmissaoComSegundos(hora));
  }

  /**
   * Inclui os dados de uma linha da tabela para ser retornado via JSON.
   * 
   * @param lista
   * @param registro
   */

  public void put(ChaveJSON lista, Map<ChaveJSON, Object> registro)
  {
    if (registro == null)
      return;
    
    jPrincipal.put(lista.toString(), registro);
  }
  
  @Deprecated

  public void putOld(ChaveJSON lista, Map<String, String> registro)
  {
    if (registro == null)
      return;
    
    jPrincipal.put(lista.toString(), registro);
  }



  /**
   * Adiciona uma lista de pares de defini��o, valor (key, value) para ser montada posteriormente
   * no formato JSON para envio ao APP
   * 
   * @param definicao
   * @param info
   */

  public void put(ChaveJSON lista, ArrayList<Map<ChaveJSON, Object>> registros)
  {
    if (registros == null)
      return;

    JSONArray jDados = new JSONArray();
    for (Map<ChaveJSON, Object> registro : registros)
    {
      jDados.put(registro);
    }
    
    jPrincipal.put(lista.toString(), jDados);
  }
  
  @Deprecated
  public void putOld(ChaveJSON lista, ArrayList<Map<String, String>> registros)
  {
    put(lista, registros);
  }
  
  /**
   * Retorna as mensagens inclu�das na valida��o num formato Json.
   * 
   * @param idMensagem
   * @return
   */
  public String getJsonMsg()
  {
    return getJsonMsg(null);
  }

  /**
   * Retorna a mensagem de erro no formato JSON.
   * 
   * @return
   */

  public String getJsonMsg(DateTime hrLancada)
  {
    boolean ok = jMsgErro.length() == 0;
    jPrincipal.put(ChaveJSON.OK0.toString(), ok);
    jPrincipal.put(ChaveJSON.MES0.toString(), jMsgsErro0);
    jPrincipal.put(ChaveJSON.OK.toString(), ok);
    if (!ok)
      jPrincipal.put(ChaveJSON.ME.toString(), jMsgErro);

    String ret = jPrincipal.toString();
    log.debug(ret);
    return ret;
  }

  /**
   * Valida se foram inclu�das mensagens de erro.
   * 
   * @return true - Com mensagens de erro inclu�das
   *          false - Sem mensagens de erro.
   */
  public boolean haMensagensErro()
  {
    return jMsgsErro0.length() > 0;
  }
}
