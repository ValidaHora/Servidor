package estiveaqui.appusuario.lesementes;

import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno;
import estiveaqui.dados.JsonResposta;
import estiveaqui.dados.MapeiaDados;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

public class LeSementesJson extends JsonResposta
{
  JsonMsgRetorno jsonMsg = new JsonMsgRetorno("Le Sementes");

  @Override
  public JsonMsgRetorno getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
//    LeSementesInVO leSementeInVO = (LeSementesInVO) dadosInVo;
    LeSementesOutVO leSementeOutVO = (LeSementesOutVO) dadosOutVo;

    jsonMsg.put(ChaveJSON.SMS, MapeiaDados.mapeiaSementesToken(leSementeOutVO.getTokensMO()));

    return jsonMsg;
  }

}
