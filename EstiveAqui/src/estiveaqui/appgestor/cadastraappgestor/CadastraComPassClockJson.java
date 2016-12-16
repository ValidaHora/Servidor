package estiveaqui.appgestor.cadastraappgestor;

import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno;
import estiveaqui.dados.JsonResposta;
import estiveaqui.dados.MapeiaDados;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

public class CadastraComPassClockJson extends JsonResposta
{
  JsonMsgRetorno jsonMsg = new JsonMsgRetorno("CadastraPassClock");

  @Override
  public JsonMsgRetorno getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
    CadastraComPassClockOutVO cadastraComPassClockOutVO = (CadastraComPassClockOutVO)dadosOutVo;

    //  Identificador do AppGestor.
    jsonMsg.put(ChaveJSON.IG, MapeiaDados.mapeiaGestor(cadastraComPassClockOutVO.getAppGestorMO()));
    
    //  Mapeia os PassClocks do AppGestor.
    jsonMsg.put(ChaveJSON.PCS, MapeiaDados.mapeiaPassClocks(cadastraComPassClockOutVO.getPassClocksMO()));
    
    //  Mapeia os usuários do AppGestor.
    jsonMsg.put(ChaveJSON.USS, MapeiaDados.mapeiaAppUsuarios(cadastraComPassClockOutVO.getAppUsuariosMO()));

    //  Mapeia os lançamentos do AppGestor.
    jsonMsg.put(ChaveJSON.LNS, MapeiaDados.mapeiaLancamentos(cadastraComPassClockOutVO.getLancamentosMO()));
    
    return jsonMsg;
  }

}
