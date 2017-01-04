package estiveaqui.appusuario.leappusuario;

import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno0;
import estiveaqui.dados.JsonResposta0;
import estiveaqui.dados.MapeiaDados0;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

public class LeAppUsuarioJson0 extends JsonResposta0
{
  JsonMsgRetorno0 jsonMsg = new JsonMsgRetorno0("Le Infos");

  @Override
  public JsonMsgRetorno0 getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
//  LeAppUsuarioInVO leAppUsuarioInVO = (LeAppUsuarioInVO) dadosInVo;
    LeAppUsuarioOutVO leAppUsuarioOutVO = (LeAppUsuarioOutVO) dadosOutVo;

    jsonMsg.put(ChaveJSON.AU0, leAppUsuarioOutVO.getAppUsuarioMO().getApelido());
    jsonMsg.put(ChaveJSON.IA0, leAppUsuarioOutVO.getAppUsuarioMO().getIdentificador());
    jsonMsg.put(ChaveJSON.XL, leAppUsuarioOutVO.getAppUsuarioMO().getMaxLancamentosPorDia());
    jsonMsg.put(ChaveJSON.PCS0, MapeiaDados0.mapeiaPassClocks(leAppUsuarioOutVO.getPassClocksMO()));
    jsonMsg.put(ChaveJSON.LNS0, MapeiaDados0.mapeiaLancamentos(leAppUsuarioOutVO.getLancamentosMO()));

    return jsonMsg;
  }
}
