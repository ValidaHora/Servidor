package estiveaqui.appgestor.login.email;

import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno;
import estiveaqui.dados.JsonResposta;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;


public class LoginComEMailJson extends JsonResposta
{
  JsonMsgRetorno jsonMsg;

  public LoginComEMailJson(String descricao)
  {
    jsonMsg = new JsonMsgRetorno(descricao);
  }
  
  @Override
  public JsonMsgRetorno getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
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
