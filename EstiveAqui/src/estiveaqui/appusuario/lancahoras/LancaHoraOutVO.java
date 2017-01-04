package estiveaqui.appusuario.lancahoras;

import org.joda.time.DateTime;
import estiveaqui.EstiveAquiException;
import estiveaqui.appusuario.DadosAppUsuarioOutVO;

public class LancaHoraOutVO extends DadosAppUsuarioOutVO
{
  private int                 contadorLancamento;
  private boolean             ok = false;
  private DateTime            hrLancada;
  private EstiveAquiException excecao;

  public int getContadorLancamento()
  {
    return contadorLancamento;
  }

  public void setContadorLancamento(int contadorLancamento)
  {
    this.contadorLancamento = contadorLancamento;
  }

  public boolean isOk()
  {
    return ok;
  }

  public void setOk(boolean ok)
  {
    this.ok = ok;
  }

  public DateTime getHrLancada()
  {
    return hrLancada;
  }

  public void setHrLancada(DateTime hrLancada)
  {
    this.hrLancada = hrLancada;
  }

  public EstiveAquiException getExcecao()
  {
    return excecao;
  }

  public void setExcecao(EstiveAquiException excecao)
  {
    this.excecao = excecao;
  }
}
