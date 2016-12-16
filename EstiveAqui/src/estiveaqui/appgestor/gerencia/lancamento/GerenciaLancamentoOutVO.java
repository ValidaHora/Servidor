package estiveaqui.appgestor.gerencia.lancamento;

import estiveaqui.sql.mo.LancamentoMO;
import estiveaqui.vo.DadosOutVO;

public class GerenciaLancamentoOutVO implements DadosOutVO
{

  private LancamentoMO lancamentoMO = null;

  public LancamentoMO getLancamentoMO()
  {
    return lancamentoMO;
  }

  public void setLancamentoMO(LancamentoMO lancamentoMO)
  {
    this.lancamentoMO = lancamentoMO;
  }

}
