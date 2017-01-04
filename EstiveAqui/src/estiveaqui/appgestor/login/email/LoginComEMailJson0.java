package estiveaqui.appgestor.login.email;

import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno0;
import estiveaqui.dados.JsonResposta0;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

@Deprecated
public class LoginComEMailJson0 extends JsonResposta0
{
  JsonMsgRetorno0 jsonMsg;

  @Deprecated
  public LoginComEMailJson0(String descricao)
  {
    jsonMsg = new JsonMsgRetorno0(descricao);
  }
  
  @Override
  @Deprecated
  public JsonMsgRetorno0 getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
//    LoginEMailInVO loginComEMailInVO = (LoginEMailInVO)dadosInVo;
    LoginComEMailOutVO loginComEMailOutVO = (LoginComEMailOutVO)dadosOutVo;

    //  Identificador do AppGestor.
    jsonMsg.put(ChaveJSON.IG, loginComEMailOutVO.getIdentificadorAppGestor());
    jsonMsg.put(ChaveJSON.EV, loginComEMailOutVO.isEmailValidado());
    jsonMsg.put(ChaveJSON.EH, loginComEMailOutVO.isExisteHistorico());
    
    return jsonMsg;
  }
}
