package estiveaqui.appusuario.lancahoras;

import java.util.ArrayList;
import java.util.List;
import estiveaqui.vo.DadosOutVO;

public class LancaHorasOutVO implements DadosOutVO
{
  private List<LancaHoraOutVO> lancamentos = new ArrayList<LancaHoraOutVO>();

  public List<LancaHoraOutVO> getLancamentos()
  {
    return lancamentos;
  }

  @Deprecated
  public void setLancamentos(List<LancaHoraOutVO> lancamentos)
  {
    this.lancamentos = lancamentos;
  }

  public void addLancamento(LancaHoraOutVO lancamento)
  {
    lancamentos.add(lancamento);
  }
}
