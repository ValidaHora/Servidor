package estiveaqui.appgestor.relatorio;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.servlet.ServletParametros;
import estiveaqui.servlet.ServletParametrosException;

public class GeraRelatorioParametrosSrvlt extends ServletParametros
{
  private GeraRelatorioInVO geraRelatorioInVO = (GeraRelatorioInVO)dadosInVo;

  public GeraRelatorioParametrosSrvlt(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "Gera Relatórios", new GeraRelatorioInVO());
  }

  @Override
  public GeraRelatorioInVO getParametros() throws ServletParametrosException
  {
    geraRelatorioInVO.setIdentificadorAppGestor(getIdentificacaoApp(true));

    return geraRelatorioInVO;
  }
  
  
}
