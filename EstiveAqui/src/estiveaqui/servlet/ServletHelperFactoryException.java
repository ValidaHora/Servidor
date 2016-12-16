package estiveaqui.servlet;

import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;


public class ServletHelperFactoryException extends EstiveAquiException
{
  private static final long serialVersionUID = -5636425081677676713L;

  public ServletHelperFactoryException(String operacao)
  {
    super(CodigoErro.ERRO_INTERNO, "Operacao não existente: \"" + operacao + "\"");
  }
}
