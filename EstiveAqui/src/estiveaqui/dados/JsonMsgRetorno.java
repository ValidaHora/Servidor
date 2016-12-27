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
  private JSONObject jMsgErro   = new JSONObject();

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
   * Se o código passado não for 0, a mensagem é de erro.
   * 
   * @param codigo
   *          - Código do erro.
   * @param texto
   *          - Mensagem de erro.
   */
  public void putMsgErro(int codigo, String texto, String textoDetalhado)
  {
    log.info("addMensagem: ({}, {})", new Object[] { codigo, texto });

    jMsgErro.put(ChaveJSON.CE.toString(), codigo);
    jMsgErro.put(ChaveJSON.DE.toString(), texto);
    jMsgErro.put(ChaveJSON.LG.toString(), textoDetalhado);
  }

  public void putMsgErro(CodigoErro cod, Throwable t)
  {
    putMsgErro(cod.getCodigoErro(), cod.getDescricao(), t.getMessage());
  }

  public void putMsgErro(EstiveAquiException e)
  {
    putMsgErro(e.getCodigoErro(), e);
  }

  /**
   * Inclui uma descrição para ser montada em formato JSON.
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
   * Inclui uma data para ser montada em formato JSON com formatação padrão de data.
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

  /**
   * Adiciona uma lista de pares de definição, valor (key, value) para ser montada posteriormente
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

  /**
   * Retorna as mensagens incluídas na validação num formato Json.
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
    jPrincipal.put(ChaveJSON.OK.toString(), ok);
    if (!ok)
      jPrincipal.put(ChaveJSON.ME.toString(), jMsgErro);

    String ret = jPrincipal.toString();
    log.debug(ret);
    return ret;
  }

  /**
   * Valida se foram incluídas mensagens de erro.
   * 
   * @return true - Com mensagens de erro incluídas
   *         false - Sem mensagens de erro.
   */
  public boolean haMensagensErro()
  {
    return jMsgErro.length() > 0;
  }
}
