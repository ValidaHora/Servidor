package estiveaqui.appusuario.leappusuario;

import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno;
import estiveaqui.dados.JsonResposta;
import estiveaqui.dados.MapeiaDados;
import estiveaqui.dados.MapeiaDadosOld;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

public class LeAppUsuarioJson extends JsonResposta
{
  JsonMsgRetorno jsonMsg = new JsonMsgRetorno("Le Infos");

  @Override
  public JsonMsgRetorno getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
//    LeAppUsuarioInVO leAppUsuarioInVO = (LeAppUsuarioInVO) dadosInVo;
    LeAppUsuarioOutVO leAppUsuarioOutVO = (LeAppUsuarioOutVO) dadosOutVo;

    jsonMsg.put(ChaveJSON.AU0, leAppUsuarioOutVO.getAppUsuarioMO().getApelido());
    jsonMsg.put(ChaveJSON.AU, leAppUsuarioOutVO.getAppUsuarioMO().getApelido());
    jsonMsg.put(ChaveJSON.IA0, leAppUsuarioOutVO.getAppUsuarioMO().getIdentificador());
    jsonMsg.put(ChaveJSON.XL, leAppUsuarioOutVO.getAppUsuarioMO().getMaxLancamentosPorDia());
    jsonMsg.put(ChaveJSON.PCS, MapeiaDados.mapeiaPassClocks(leAppUsuarioOutVO.getPassClocksMO()));
    jsonMsg.put(ChaveJSON.LNS, MapeiaDados.mapeiaLancamentos(leAppUsuarioOutVO.getLancamentosMO()));
    jsonMsg.putOld(ChaveJSON.PCS0, MapeiaDadosOld.mapeiaPassClocks(leAppUsuarioOutVO.getPassClocksMO()));
    jsonMsg.putOld(ChaveJSON.LNS0, MapeiaDadosOld.mapeiaLancamentos(leAppUsuarioOutVO.getLancamentosMO()));
    
    
    return jsonMsg;
  }
}
