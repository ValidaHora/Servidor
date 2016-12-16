package estiveaqui;

import java.text.MessageFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class RegraDeNegocioException extends EstiveAquiException
{
  private static final long serialVersionUID = 2739538887450636922L;
  private static final Logger log = LogManager.getLogger();

  public RegraDeNegocioException(CodigoErro codigoErro, String mensagem, Object ... params)
  {
    super(codigoErro, mensagem, params);
    log.warn(MessageFormat.format(mensagem, params));
  }

  public RegraDeNegocioException(CodigoErro codigoErro)
  {
    super(codigoErro);
    log.warn(codigoErro.getDescricao());
  }
}
