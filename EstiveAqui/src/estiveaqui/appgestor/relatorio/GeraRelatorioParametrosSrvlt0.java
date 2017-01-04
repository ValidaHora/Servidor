package estiveaqui.appgestor.relatorio;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.servlet.ServletParametros0;
import estiveaqui.servlet.ServletParametrosException;

@Deprecated
public class GeraRelatorioParametrosSrvlt0 extends ServletParametros0
{
  private GeraRelatorioInVO geraRelatorioInVO = (GeraRelatorioInVO)dadosInVo;

  @Deprecated
  public GeraRelatorioParametrosSrvlt0(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "Gera Relatórios", new GeraRelatorioInVO());
  }

  @Override
  @Deprecated
  public GeraRelatorioInVO getParametros() throws ServletParametrosException
  {
    geraRelatorioInVO.setIdentificadorAppGestor(getIdentificacaoApp(true));

    return geraRelatorioInVO;
  }
  
  
}
