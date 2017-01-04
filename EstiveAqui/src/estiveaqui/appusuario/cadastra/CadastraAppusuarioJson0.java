package estiveaqui.appusuario.cadastra;

import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno0;
import estiveaqui.dados.JsonResposta0;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

public class CadastraAppusuarioJson0 extends JsonResposta0
{
  JsonMsgRetorno0 jsonMsg = new JsonMsgRetorno0("Cadastra AppUsuario");

  @Override
  public JsonMsgRetorno0 getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
//    CadastraAppUsuarioInVO cadastraAppUsuarioInVO = (CadastraAppUsuarioInVO)dadosInVo;
    CadastraAppUsuarioOutVO cadastraAppUsuarioOutVO = (CadastraAppUsuarioOutVO)dadosOutVo;

    jsonMsg.put(ChaveJSON.AU0, cadastraAppUsuarioOutVO.getAppUsuarioMO().getApelido());
    jsonMsg.put(ChaveJSON.IA0, cadastraAppUsuarioOutVO.getAppUsuarioMO().getIdentificador());

    return jsonMsg;
  }

}
