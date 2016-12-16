package estiveaqui.appusuario.cadastra;

import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno;
import estiveaqui.dados.JsonResposta;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

public class CadastraAppusuarioJson extends JsonResposta
{
  JsonMsgRetorno jsonMsg = new JsonMsgRetorno("Cadastra AppUsuario");

  @Override
  public JsonMsgRetorno getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
//    CadastraAppUsuarioInVO cadastraAppUsuarioInVO = (CadastraAppUsuarioInVO)dadosInVo;
    CadastraAppUsuarioOutVO cadastraAppUsuarioOutVO = (CadastraAppUsuarioOutVO)dadosOutVo;
;
    jsonMsg.put(ChaveJSON.AU0, cadastraAppUsuarioOutVO.getAppUsuarioMO().getApelido());
    jsonMsg.put(ChaveJSON.IA0, cadastraAppUsuarioOutVO.getAppUsuarioMO().getIdentificador());

    return jsonMsg;
  }

}
