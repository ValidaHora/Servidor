package estiveaqui.servlet;

import java.text.MessageFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;

public class ServletParametrosException extends EstiveAquiException
{
  private static final long serialVersionUID = -2415234301435733799L;
  private static final Logger log = LogManager.getLogger();

  /**
   * Gera uma exce��o baseada no c�digo passado.
   * 
   * @param codigo - Par�metro definido na classe validahora.Erros.<BR>
   *                  <B>Use apenas os c�digos definidos nessa classe!</B>
   * @param mensagem
   * @param params - Os par�metros necess�rios para completar a informa��o da mensagem de erro cadastrada em <i>Erros</i>.
   */
  public ServletParametrosException(CodigoErro codigo, String mensagem, Object ... params)
  {
    super(codigo, MessageFormat.format(mensagem, params));
    log.warn(MessageFormat.format(mensagem, params));
  }
}
