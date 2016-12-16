package estiveaqui.appgestor.ledados;

import estiveaqui.appgestor.DadosAppGestorInVO;

public class LeDadosInVO extends DadosAppGestorInVO
{
  private int idUltimoLancamento;

  public int getIdUltimoLancamento()
  {
    return idUltimoLancamento;
  }

  public void setIdUltimoLancamento(int idUltimoLancamento)
  {
    this.idUltimoLancamento = idUltimoLancamento;
  }
}
