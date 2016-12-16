package estiveaqui.appgestor;

import java.text.MessageFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;

public class EMailException extends EstiveAquiException
{
  private static final long serialVersionUID = -8222388233018800717L;
  private static final Logger log = LogManager.getLogger();

  public EMailException(CodigoErro codigoErro)
  {
    super(codigoErro);
    log.info(codigoErro.getDescricao());
  }

  public EMailException(CodigoErro codigoErro, String mensagem, Object... params)
  {
    super(codigoErro, mensagem, params);
    log.info(MessageFormat.format(mensagem, params));
  }

}
