package estiveaqui.appusuario.lancahoras;

import estiveaqui.RegraDeNegocioException;
import estiveaqui.sql.mo.LancamentoMO;

public class LancamentoVO
{
  private int                     idLancamento;
  private LancamentoMO            lancamentoMO;
  private RegraDeNegocioException excecao = null;

  public int getIdLancamento()
  {
    return idLancamento;
  }

  public void setIdLancamento(int idLancamento)
  {
    this.idLancamento = idLancamento;
  }

  public RegraDeNegocioException getExcecao()
  {
    return excecao;
  }

  public void setExcecao(RegraDeNegocioException excecao)
  {
    this.excecao = excecao;
  }

  public LancamentoMO getLancamentoMO()
  {
    return lancamentoMO;
  }

  public void setLancamentoMO(LancamentoMO lancamentoMO)
  {
    this.lancamentoMO = lancamentoMO;
  }

}
