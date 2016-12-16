package estiveaqui.appgestor.gerencia.usuario;

import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno;
import estiveaqui.dados.JsonResposta;
import estiveaqui.dados.MapeiaDados;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

public class GerenciaAppUsuarioJson extends JsonResposta
{
  JsonMsgRetorno jsonMsg = new JsonMsgRetorno("Gerencia AppUsuario");

  @Override
  public JsonMsgRetorno getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
    GerenciaAppUsuarioOutVO gerenciaAppUsuarioOutVO = (GerenciaAppUsuarioOutVO)dadosOutVo;
    
    jsonMsg.put(ChaveJSON.US, MapeiaDados.mapeiaAppUsuario(gerenciaAppUsuarioOutVO.getAppUsuarioMO()));
    
    return jsonMsg;
  }

}
