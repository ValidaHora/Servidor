package estiveaqui.appgestor.cadastraappgestor;

import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno0;
import estiveaqui.dados.JsonResposta0;
import estiveaqui.dados.MapeiaDados0;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

public class CadastraComPassClockJson0 extends JsonResposta0
{
  JsonMsgRetorno0 jsonMsg = new JsonMsgRetorno0("CadastraPassClock");

  @Override
  public JsonMsgRetorno0 getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
    CadastraComPassClockOutVO cadastraComPassClockOutVO = (CadastraComPassClockOutVO)dadosOutVo;

    //  Identificador do AppGestor.
    jsonMsg.put(ChaveJSON.IG, MapeiaDados0.mapeiaGestor(cadastraComPassClockOutVO.getAppGestorMO()));
    
    //  Mapeia os PassClocks do AppGestor.
    jsonMsg.put(ChaveJSON.PCS, MapeiaDados0.mapeiaPassClocks(cadastraComPassClockOutVO.getPassClocksMO()));
    
    //  Mapeia os usuários do AppGestor.
    jsonMsg.put(ChaveJSON.USS, MapeiaDados0.mapeiaUsuarios(cadastraComPassClockOutVO.getAppUsuariosMO()));

    //  Mapeia os lançamentos do AppGestor.
    jsonMsg.put(ChaveJSON.LNS, MapeiaDados0.mapeiaLancamentos(cadastraComPassClockOutVO.getLancamentosMO()));
    
    return jsonMsg;
  }

}
