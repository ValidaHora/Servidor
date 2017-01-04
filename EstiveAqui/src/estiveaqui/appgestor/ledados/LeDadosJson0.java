package estiveaqui.appgestor.ledados;

import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno0;
import estiveaqui.dados.JsonResposta0;
import estiveaqui.dados.MapeiaDados0;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

@Deprecated
public class LeDadosJson0 extends JsonResposta0
{
  JsonMsgRetorno0 jsonMsg = new JsonMsgRetorno0("LeDados");

  @Override
  @Deprecated
  public JsonMsgRetorno0 getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
    LeDadosOutVO leDadosOutVO = (LeDadosOutVO) dadosOutVo;

    //  Identificador do AppGestor.
    jsonMsg.put(ChaveJSON.EV, leDadosOutVO.isEmailValidado());

    //  Mapeia os PassClocks do AppGestor.
    jsonMsg.put(ChaveJSON.PCS, MapeiaDados0.mapeiaPassClocks(leDadosOutVO.getPassClocksMO()));

    //  Mapeia os usuários do AppGestor.
    jsonMsg.put(ChaveJSON.USS, MapeiaDados0.mapeiaUsuarios(leDadosOutVO.getAppUsuariosMO()));

    //  Mapeia os lançamentos do AppGestor.
    jsonMsg.put(ChaveJSON.LNS, MapeiaDados0.mapeiaLancamentos(leDadosOutVO.getLancamentosMO()));

    return jsonMsg;
  }
}
