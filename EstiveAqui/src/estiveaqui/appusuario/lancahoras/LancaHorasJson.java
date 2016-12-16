package estiveaqui.appusuario.lancahoras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import estiveaqui.RegraDeNegocioException;
import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno;
import estiveaqui.dados.JsonResposta;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

public class LancaHorasJson extends JsonResposta
{
  JsonMsgRetorno jsonMsg = new JsonMsgRetorno("Lanca Horas");

  @Override
  public JsonMsgRetorno getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
//    LancaHorasInVO lancaHorasInVO = (LancaHorasInVO) dadosInVo;
    LancaHorasOutVO lancaHorasOutVO = (LancaHorasOutVO) dadosOutVo;

    ArrayList<Map<ChaveJSON, Object>> mapLancamentos = new ArrayList<Map<ChaveJSON, Object>>();

    for (LancamentoVO lancamentoOutVo : lancaHorasOutVO.getLancamentos())
    {
      Map<ChaveJSON, Object> mapLancamento = new HashMap<ChaveJSON, Object>();
      mapLancamentos.add(mapLancamento);
      
      mapLancamento.put(ChaveJSON.IL, lancamentoOutVo.getIdLancamento());
      mapLancamento.put(ChaveJSON.OK, lancamentoOutVo.getExcecao() == null);
      if (lancamentoOutVo.getExcecao() != null)
      {
        RegraDeNegocioException excecao = lancamentoOutVo.getExcecao();
        Map<ChaveJSON, Object> mapErro = new HashMap<ChaveJSON, Object>();
        mapErro.put(ChaveJSON.CE, excecao.getCodigoErro().getCodigoErro());
        mapErro.put(ChaveJSON.ME, excecao.getCodigoErro().getDescricao());
        mapErro.put(ChaveJSON.LG, excecao.toString());
        
        mapLancamento.put(ChaveJSON.ER, mapErro);
      }
    }
    
    jsonMsg.put(ChaveJSON.LNS, mapLancamentos);
    
    return jsonMsg;
  }

}
