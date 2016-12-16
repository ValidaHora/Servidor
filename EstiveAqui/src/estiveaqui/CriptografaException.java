package estiveaqui;

public class CriptografaException extends EstiveAquiException
{
  private static final long serialVersionUID = -8748202885854038485L;

  public CriptografaException(CodigoErro codigo, String msg)
  {
    super(codigo, msg);
  }

  public CriptografaException(CodigoErro codigo)
  {
    super(codigo);
  }
}
