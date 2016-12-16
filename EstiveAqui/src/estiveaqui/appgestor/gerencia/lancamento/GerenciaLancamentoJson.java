package estiveaqui.appgestor.gerencia.lancamento;

import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno;
import estiveaqui.dados.JsonResposta;
import estiveaqui.dados.MapeiaDados;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

public class GerenciaLancamentoJson extends JsonResposta
{
  JsonMsgRetorno jsonMsg = new JsonMsgRetorno("Gerencia Lancamento");

  @Override
  public JsonMsgRetorno getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
    GerenciaLancamentoOutVO gerenciaLancamentoOutVO = (GerenciaLancamentoOutVO) dadosOutVo;

    jsonMsg.put(ChaveJSON.LN, MapeiaDados.mapeiaLancamento(gerenciaLancamentoOutVO.getLancamentoMO()));

    return jsonMsg;
  }

}
