package estiveaqui.appgestor.gerencia.passclock;

import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno0;
import estiveaqui.dados.JsonResposta0;
import estiveaqui.dados.MapeiaDados0;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

@Deprecated
public class GerenciaPassClockJson0 extends JsonResposta0
{

  JsonMsgRetorno0 jsonMsg = new JsonMsgRetorno0("Gerencia PassClock");

  @Override
  @Deprecated
  public JsonMsgRetorno0 getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
    GerenciaPassClockOutVO gerenciaPassClockOutVO = (GerenciaPassClockOutVO)dadosOutVo;
    
    jsonMsg.put(ChaveJSON.PCO, MapeiaDados0.mapeiaPassClock(gerenciaPassClockOutVO.getPassClockMO()));
    
    return jsonMsg;
  }

}
