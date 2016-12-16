package estiveaqui.relatorios;

import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;

public class PosicaoExcelException extends EstiveAquiException
{

  public PosicaoExcelException(CodigoErro codigoErro, String mensagem, Object... params)
  {
    super(codigoErro, mensagem, params);
  }

  /**
   * 
   */
  private static final long serialVersionUID = 8969465920500973311L;

}
