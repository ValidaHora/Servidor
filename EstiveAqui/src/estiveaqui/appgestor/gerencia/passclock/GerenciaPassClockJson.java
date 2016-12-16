package estiveaqui.appgestor.gerencia.passclock;

import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno;
import estiveaqui.dados.JsonResposta;
import estiveaqui.dados.MapeiaDados;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

public class GerenciaPassClockJson extends JsonResposta
{

  JsonMsgRetorno jsonMsg = new JsonMsgRetorno("Gerencia PassClock");

  @Override
  public JsonMsgRetorno getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
    GerenciaPassClockOutVO gerenciaPassClockOutVO = (GerenciaPassClockOutVO)dadosOutVo;
    
    jsonMsg.put(ChaveJSON.PCO, MapeiaDados.mapeiaPassClock(gerenciaPassClockOutVO.getPassClockMO()));
    
    return jsonMsg;
  }

}
