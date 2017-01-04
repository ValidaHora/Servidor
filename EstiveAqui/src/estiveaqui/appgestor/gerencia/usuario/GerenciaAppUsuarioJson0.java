package estiveaqui.appgestor.gerencia.usuario;

import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno0;
import estiveaqui.dados.JsonResposta0;
import estiveaqui.dados.MapeiaDados;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

@Deprecated
public class GerenciaAppUsuarioJson0 extends JsonResposta0
{
  JsonMsgRetorno0 jsonMsg = new JsonMsgRetorno0("Gerencia AppUsuario");

  @Override
  @Deprecated
  public JsonMsgRetorno0 getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
    GerenciaAppUsuarioOutVO gerenciaAppUsuarioOutVO = (GerenciaAppUsuarioOutVO)dadosOutVo;
    
    jsonMsg.put(ChaveJSON.US, MapeiaDados.mapeiaAppUsuario(gerenciaAppUsuarioOutVO.getAppUsuarioMO()));
    
    return jsonMsg;
  }

}
