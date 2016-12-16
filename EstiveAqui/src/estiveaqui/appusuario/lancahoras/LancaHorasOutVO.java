package estiveaqui.appusuario.lancahoras;

import java.util.ArrayList;
import estiveaqui.vo.DadosOutVO;

public class LancaHorasOutVO implements DadosOutVO
{
  private ArrayList<LancamentoVO> lancamentos;

  public ArrayList<LancamentoVO> getLancamentos()
  {
    return lancamentos;
  }

  public void setLancamentos(ArrayList<LancamentoVO> lancamentos)
  {
    this.lancamentos = lancamentos;
  }
}
