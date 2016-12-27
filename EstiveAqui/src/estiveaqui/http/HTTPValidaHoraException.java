package estiveaqui.http;

import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;

public class HTTPValidaHoraException extends EstiveAquiException
{
  private static final long serialVersionUID = -3677684497319470012L;

  public HTTPValidaHoraException(int responseCode)
  {
    super(CodigoErro.ERRO_INTERNO, "Erro ao acesso ao ValidaHora com response code = {0}", responseCode);
  }
  public HTTPValidaHoraException(CodigoErro codigoErro, String mensagem, Object... params)
  {
    super(codigoErro, mensagem, params);
  }

  public HTTPValidaHoraException(CodigoErro codigoErro)
  {
    super(codigoErro);
  }

}
