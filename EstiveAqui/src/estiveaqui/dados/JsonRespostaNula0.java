package estiveaqui.dados;

import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

/**
 * Use esta classe quando não existe nenhuma resposta para ser retornada além das informações padrão.
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
   * Use este método quando não houver necessidade de resposta.
   */
  @Override
  @Deprecated
  public JsonMsgRetorno0 getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
    return jsonMsg;
  }
}
