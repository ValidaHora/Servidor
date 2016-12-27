package estiveaqui.dados;

import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

/**
 * Use esta classe quando não existe nenhuma resposta para ser retornada além das informações padrão.
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
   * Use este método quando não houver necessidade de resposta.
   */
  @Override
  public JsonMsgRetorno getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
    return jsonMsg;
  }
}
