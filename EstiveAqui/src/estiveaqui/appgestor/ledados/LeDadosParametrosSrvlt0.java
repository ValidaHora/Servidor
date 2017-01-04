package estiveaqui.appgestor.ledados;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.servlet.ServletParametros0;
import estiveaqui.servlet.ServletParametrosException;

@Deprecated
public class LeDadosParametrosSrvlt0 extends ServletParametros0
{
  private LeDadosInVO leDadosInVO = (LeDadosInVO)dadosInVo;

  @Deprecated
  public LeDadosParametrosSrvlt0(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "Le Dados Parametros", new LeDadosInVO());
  }

  /**
   * Lê e retorna os parâmetros para o LeDados.
   * 
   * @return
   * @throws ServletParametrosException
   */
  @Override
  @Deprecated
  public LeDadosInVO getParametros() throws ServletParametrosException  
  {
    leDadosInVO.setIdentificadorAppGestor(getIdentificacaoApp(true));
    leDadosInVO.setIdUltimoLancamento(getUltimoIdLancamento(true));

    return leDadosInVO;
  }
  
  /**
   * Retorna o último Id dos lançamentos que o cliente possui.
   * 
   * @param obrigatorio
   * @return
   * @throws ServletParametrosException
   */
  @Deprecated
  protected int getUltimoIdLancamento(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametroInt("IDULTLANCAMENTO", obrigatorio, true);
  }

}
