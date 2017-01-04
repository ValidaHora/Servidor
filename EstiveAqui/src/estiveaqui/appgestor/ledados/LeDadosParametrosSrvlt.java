package estiveaqui.appgestor.ledados;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.appgestor.servlet.ServletParametrosAppGestor;
import estiveaqui.servlet.NomeParametroServlet;
import estiveaqui.servlet.ServletParametrosException;

public class LeDadosParametrosSrvlt extends ServletParametrosAppGestor
{
  private LeDadosInVO leDadosInVO = (LeDadosInVO)dadosInVo;

  public LeDadosParametrosSrvlt(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "Le Dados Parametros", new LeDadosInVO());
  }

  /**
   * L� e retorna os par�metros para o LeDados.
   * 
   * @return
   * @throws ServletParametrosException
   */
  @Override
  public LeDadosInVO getParametros() throws ServletParametrosException  
  {
    leDadosInVO.setIdentificadorAppGestor(getIdentificacaoAppGestor(true));
    leDadosInVO.setIdUltimoLancamento(getUltimoIdLancamento(true));

    return leDadosInVO;
  }
  
  /**
   * Retorna o �ltimo Id dos lan�amentos que o cliente possui.
   * 
   * @param obrigatorio
   * @return
   * @throws ServletParametrosException
   */
  protected int getUltimoIdLancamento(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametroInt(NomeParametroServlet.IdUltimoLancamento, obrigatorio, true);
  }

}
