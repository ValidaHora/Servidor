package estiveaqui.appgestor.relatorio;

import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno0;
import estiveaqui.dados.JsonResposta0;
import estiveaqui.dados.MapeiaDados;
import estiveaqui.relatorios.Persiste;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

@Deprecated
public class GeraRelatorioJson0 extends JsonResposta0
{
  JsonMsgRetorno0 jsonMsg = new JsonMsgRetorno0("GeraRelatorio");

  @Override
  @Deprecated
  public JsonMsgRetorno0 getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
//    GeraRelatorioInVO geraRelatorioInVO = (GeraRelatorioInVO)dadosInVo;
    GeraRelatorioOutVO geraRelatorioOutVO = (GeraRelatorioOutVO)dadosOutVo;

    //  Mapeia os relatórios .
    jsonMsg.put(ChaveJSON.RLS, MapeiaDados.mapeiaRelatorios(geraRelatorioOutVO.getRelatoriosMO()));
    
    //  Retorna a URL onde os relatórios podem ser baixados.
    jsonMsg.put(ChaveJSON.UR, "http://" + Persiste.AWSS3BucketName);

    return jsonMsg;
  }

}
