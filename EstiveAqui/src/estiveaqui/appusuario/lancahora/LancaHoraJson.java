package estiveaqui.appusuario.lancahora;

import estiveaqui.Util;
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
    LancaHoraInVO lancaHoraInVO = (LancaHoraInVO) dadosInVo;
    LancaHoraOutVO lancaHoraOutVO = (LancaHoraOutVO) dadosOutVo;

    jsonMsg.put(ChaveJSON.AU, lancaHoraOutVO.getAppUsuarioMO().getApelido());
    jsonMsg.put(ChaveJSON.IA, lancaHoraOutVO.getAppUsuarioMO().getIdentificador());
    jsonMsg.put(ChaveJSON.CD, lancaHoraInVO.getCodigo());
    jsonMsg.put(ChaveJSON.LN, MapeiaDados.mapeiaLancamento(lancaHoraOutVO.getLancamentoMO())); 
    jsonMsg.put(ChaveJSON.HL, lancaHoraOutVO.getLancamentoMO().getHrLancamento());
    jsonMsg.put(ChaveJSON.TZ, Util.toStringTimeZone(lancaHoraOutVO.getLancamentoMO().getTzPassClock()));

    return jsonMsg;
  }
}
