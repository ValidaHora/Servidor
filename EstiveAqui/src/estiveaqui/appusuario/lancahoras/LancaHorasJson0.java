package estiveaqui.appusuario.lancahoras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import estiveaqui.EstiveAquiException;
import estiveaqui.dados.ChaveJSON;
import estiveaqui.dados.JsonMsgRetorno0;
import estiveaqui.dados.JsonResposta0;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

@Deprecated
public class LancaHorasJson0 extends JsonResposta0
{
  JsonMsgRetorno0 jsonMsg = new JsonMsgRetorno0("Lanca Horas");

  @Override
  public JsonMsgRetorno0 getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo)
  {
//  LancaHorasInVO lancaHorasInVO = (LancaHorasInVO) dadosInVo;
    LancaHorasOutVO lancaHorasOutVO = (LancaHorasOutVO) dadosOutVo;

    List<Map<ChaveJSON, Object>> mapLancamentos = new ArrayList<Map<ChaveJSON, Object>>();

    for (LancaHoraOutVO lancaHoraOutVo : lancaHorasOutVO.getLancamentos())
    {
      Map<ChaveJSON, Object> mapLancamento = new HashMap<ChaveJSON, Object>();
      mapLancamentos.add(mapLancamento);

      mapLancamento.put(ChaveJSON.IL, lancaHoraOutVo.getContadorLancamento());
      mapLancamento.put(ChaveJSON.OK, lancaHoraOutVo.getExcecao() == null);
      if (lancaHoraOutVo.getExcecao() != null)
      {
        EstiveAquiException excecao = lancaHoraOutVo.getExcecao();
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
