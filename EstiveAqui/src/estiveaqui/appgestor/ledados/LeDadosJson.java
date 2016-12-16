package estiveaqui.appgestor.ledados;

import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno;
import estiveaqui.dados.JsonResposta;
import estiveaqui.dados.MapeiaDados;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

public class LeDadosJson extends JsonResposta
{
  JsonMsgRetorno jsonMsg = new JsonMsgRetorno("LeDados");

  @Override
  public JsonMsgRetorno getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
    LeDadosOutVO leDadosOutVO = (LeDadosOutVO) dadosOutVo;

    //  Identificador do AppGestor.
    jsonMsg.put(ChaveJSON.EV, leDadosOutVO.isEmailValidado());

    //  Mapeia os PassClocks do AppGestor.
    jsonMsg.put(ChaveJSON.PCS, MapeiaDados.mapeiaPassClocks(leDadosOutVO.getPassClocksMO()));

    //  Mapeia os usuários do AppGestor.
    jsonMsg.put(ChaveJSON.USS, MapeiaDados.mapeiaAppUsuarios(leDadosOutVO.getAppUsuariosMO()));

    //  Mapeia os lançamentos do AppGestor.
    jsonMsg.put(ChaveJSON.LNS, MapeiaDados.mapeiaLancamentos(leDadosOutVO.getLancamentosMO()));

    return jsonMsg;
  }
}
