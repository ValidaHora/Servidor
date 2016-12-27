package estiveaqui.dados;

import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

public abstract class JsonResposta
{
  public abstract JsonMsgRetorno getJson(DadosInVO dadosInVo, DadosOutVO dadosOutVo);
}
