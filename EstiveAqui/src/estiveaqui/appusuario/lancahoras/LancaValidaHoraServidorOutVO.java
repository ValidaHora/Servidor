package estiveaqui.appusuario.lancahoras;

import org.joda.time.DateTime;
import estiveaqui.RegraDeNegocioException;

public class LancaValidaHoraServidorOutVO
{
  private int                     contadorLancamento;
  private boolean                 ok      = false;
  private DateTime                horaLancada;
  private String                  hashCode;
  private RegraDeNegocioException excecao = null;

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

  public DateTime getHoraLancada()
  {
    return horaLancada;
  }

  public void setHoraLancada(DateTime horaLancada)
  {
    this.horaLancada = horaLancada;
  }

  public String getHashCode()
  {
    return hashCode;
  }

  public void setHashCode(String hashCode)
  {
    this.hashCode = hashCode;
  }

  public RegraDeNegocioException getExcecao()
  {
    return excecao;
  }

  public void setExcecao(RegraDeNegocioException excecao)
  {
    this.excecao = excecao;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof Integer || obj instanceof Long)
      return contadorLancamento == ((Integer) obj);

    if (!(obj instanceof LancaValidaHoraServidorOutVO))
      return false;

    return contadorLancamento == ((LancaValidaHoraServidorOutVO) obj).contadorLancamento;
  }
}
