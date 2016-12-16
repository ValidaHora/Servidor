package estiveaqui.appusuario.lancahora;

import estiveaqui.appusuario.DadosAppUsuarioOutVO;
import estiveaqui.sql.mo.LancamentoMO;

public class LancaHoraOutVO extends DadosAppUsuarioOutVO
{
  private LancamentoMO lancamentoMO = new LancamentoMO();

  public LancamentoMO getLancamentoMO()
  {
    return lancamentoMO;
  }

  public void setLancamentoMO(LancamentoMO lancamentoMO)
  {
    this.lancamentoMO = lancamentoMO;
  }


}
