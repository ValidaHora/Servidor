package estiveaqui.appgestor.gerencia.lancamento;

import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno0;
import estiveaqui.dados.JsonResposta0;
import estiveaqui.dados.MapeiaDados0;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

@Deprecated
public class GerenciaLancamentoJson0 extends JsonResposta0
{
  JsonMsgRetorno0 jsonMsg = new JsonMsgRetorno0("Gerencia Lancamento");

  @Override
  @Deprecated
  public JsonMsgRetorno0 getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
    GerenciaLancamentoOutVO gerenciaLancamentoOutVO = (GerenciaLancamentoOutVO) dadosOutVo;

    jsonMsg.put(ChaveJSON.LN, MapeiaDados0.mapeiaLancamento(gerenciaLancamentoOutVO.getLancamentoMO()));

    return jsonMsg;
  }

}
