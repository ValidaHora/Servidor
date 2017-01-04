package estiveaqui.appusuario.lancahora;

import estiveaqui.Util;
import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno0;
import estiveaqui.dados.JsonResposta0;
import estiveaqui.dados.MapeiaDados0;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

@Deprecated
public class LancaHoraJson0 extends JsonResposta0
{
  @Deprecated
  JsonMsgRetorno0 jsonMsg = new JsonMsgRetorno0("Lanca Hora");

  @Deprecated
  @Override
  public JsonMsgRetorno0 getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
    LancaHoraInVO lancaHoraInVO = (LancaHoraInVO) dadosInVo;
    LancaHoraOutVO lancaHoraOutVO = (LancaHoraOutVO) dadosOutVo;

    jsonMsg.put(ChaveJSON.AU0, lancaHoraOutVO.getAppUsuarioMO().getApelido());
    jsonMsg.put(ChaveJSON.IA0, lancaHoraOutVO.getAppUsuarioMO().getIdentificador());
    jsonMsg.put(ChaveJSON.CD0, lancaHoraInVO.getCodigo());
    jsonMsg.put(ChaveJSON.LN0, MapeiaDados0.mapeiaLancamento(lancaHoraOutVO.getLancamentoMO())); 
    jsonMsg.put(ChaveJSON.HL0, lancaHoraOutVO.getLancamentoMO().getHrLancamento());
    jsonMsg.put(ChaveJSON.TZ0, Util.formataTZ(lancaHoraOutVO.getLancamentoMO().getTzPassClock()));

    return jsonMsg;
  }
}
