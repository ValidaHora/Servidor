package estiveaqui.relatorios;

import java.text.MessageFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;

public class RelatorioException extends EstiveAquiException
{
  private static final long serialVersionUID = 7783048985372662816L;
  private static final Logger log = LogManager.getLogger();

  public RelatorioException(CodigoErro codigoErro, String mensagem, Object... params)
  {
    super(codigoErro, mensagem, params);
    log.warn(MessageFormat.format(mensagem, params));
  }

  public RelatorioException(CodigoErro codigoErro)
  {
    super(codigoErro);
    log.warn(codigoErro.getDescricao());
  }

}
