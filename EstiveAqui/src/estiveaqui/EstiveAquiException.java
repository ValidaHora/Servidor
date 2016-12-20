package estiveaqui;

import java.text.MessageFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EstiveAquiException extends Exception
{
  private static final long serialVersionUID = 6060750033735871552L;
  private static final Logger log = LogManager.getLogger();

  public CodigoErro codigoErro;

  /**
   * Classe default de exce��o. 
   * @param codigo - O c�digo � um n�mero que descreve o erro gerado para identificar o erro para o usu�rio final no App.
   * @param mensagem - Mensagem gen�rica que explica o erro.
   */
  public EstiveAquiException(CodigoErro codigoErro, String mensagem, Object ... params)
  {
    super(codigoErro.getCodigoErro() + ") " + codigoErro.getDescricao() + " [" + MessageFormat.format(mensagem, params) + "]");
    this.codigoErro = codigoErro;
    log.info(MessageFormat.format(mensagem, params));
  }

  /**
   * 
   * @param codigoErro
   */
  public EstiveAquiException(CodigoErro codigoErro)
  {
    super(codigoErro.getDescricao());
    this.codigoErro = codigoErro;
    log.info(codigoErro.getDescricao());
  }

  public CodigoErro getCodigoErro()
  {
    return codigoErro;
  }
}
