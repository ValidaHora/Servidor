package estiveaqui.appgestor.relatorio;

import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno;
import estiveaqui.dados.JsonResposta;
import estiveaqui.dados.MapeiaDados;
import estiveaqui.relatorios.Persiste;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

public class GeraRelatorioJson extends JsonResposta
{
  JsonMsgRetorno jsonMsg = new JsonMsgRetorno("GeraRelatorio");

  @Override
  public JsonMsgRetorno getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
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
