package estiveaqui.appusuario.lancahora;

import estiveaqui.Util;
import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno;
import estiveaqui.dados.JsonResposta;
import estiveaqui.dados.MapeiaDadosOld;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

@Deprecated
public class LancaHoraJson0 extends JsonResposta
{
  @Deprecated
  JsonMsgRetorno jsonMsg = new JsonMsgRetorno("Lanca Hora");

  @Deprecated
  @Override
  public JsonMsgRetorno getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
    LancaHoraInVO lancaHoraInVO = (LancaHoraInVO) dadosInVo;
    LancaHoraOutVO lancaHoraOutVO = (LancaHoraOutVO) dadosOutVo;

    jsonMsg.put(ChaveJSON.AU0, lancaHoraOutVO.getAppUsuarioMO().getApelido());
    jsonMsg.put(ChaveJSON.IA0, lancaHoraOutVO.getAppUsuarioMO().getIdentificador());
    jsonMsg.put(ChaveJSON.CD0, lancaHoraInVO.getCodigo());
    jsonMsg.putOld(ChaveJSON.LN0, MapeiaDadosOld.mapeiaLancamento(lancaHoraOutVO.getLancamentoMO())); 
    jsonMsg.put(ChaveJSON.HL0, lancaHoraOutVO.getLancamentoMO().getHrLancamento());
    jsonMsg.put(ChaveJSON.TZ0, Util.toStringTimeZone(lancaHoraOutVO.getLancamentoMO().getTzPassClock()));

    return jsonMsg;
  }
}
