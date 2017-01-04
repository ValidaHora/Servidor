package estiveaqui.dados;

import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

/**
 * Use esta classe quando n�o existe nenhuma resposta para ser retornada al�m das informa��es padr�o.
 * 
 * @author Haroldo
 *
 */
@Deprecated
public class JsonRespostaNula0 extends JsonResposta0
{
  JsonMsgRetorno0 jsonMsg = null;

  @Deprecated
  public JsonRespostaNula0(String idMensagem)
  {
    jsonMsg = new JsonMsgRetorno0(idMensagem);
  }
  
  /**
   * Use este m�todo quando n�o houver necessidade de resposta.
   */
  @Override
  @Deprecated
  public JsonMsgRetorno0 getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
    return jsonMsg;
  }
}
