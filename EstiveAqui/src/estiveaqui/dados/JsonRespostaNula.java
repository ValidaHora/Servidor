package estiveaqui.dados;

import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

/**
 * Use esta classe quando n�o existe nenhuma resposta para ser retornada al�m das informa��es padr�o.
 * 
 * @author Haroldo
 *
 */
public class JsonRespostaNula extends JsonResposta
{
  JsonMsgRetorno jsonMsg = null;

  public JsonRespostaNula(String idMensagem)
  {
    jsonMsg = new JsonMsgRetorno(idMensagem);
  }
  
  /**
   * Use este m�todo quando n�o houver necessidade de resposta.
   */
  @Override
  public JsonMsgRetorno getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
    return jsonMsg;
  }
}
