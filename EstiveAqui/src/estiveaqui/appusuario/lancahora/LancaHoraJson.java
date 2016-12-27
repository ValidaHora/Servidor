package estiveaqui.appusuario.lancahora;

import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno;
import estiveaqui.dados.JsonResposta;
import estiveaqui.dados.MapeiaDados;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

public class LancaHoraJson extends JsonResposta
{
  JsonMsgRetorno jsonMsg = new JsonMsgRetorno("Lanca Hora");

  @Override
  public JsonMsgRetorno getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
//    LancaHoraInVO lancaHoraInVO = (LancaHoraInVO) dadosInVo;
    LancaHoraOutVO lancaHoraOutVO = (LancaHoraOutVO) dadosOutVo;

    jsonMsg.put(ChaveJSON.LN, MapeiaDados.mapeiaLancamento(lancaHoraOutVO.getLancamentoMO())); 

    return jsonMsg;
  }
}
