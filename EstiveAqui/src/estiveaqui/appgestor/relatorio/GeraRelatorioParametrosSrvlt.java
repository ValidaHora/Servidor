package estiveaqui.appgestor.relatorio;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.appgestor.servlet.ServletParametrosAppGestor;
import estiveaqui.servlet.ServletParametrosException;

public class GeraRelatorioParametrosSrvlt extends ServletParametrosAppGestor
{
  private GeraRelatorioInVO geraRelatorioInVO = (GeraRelatorioInVO)dadosInVo;

  public GeraRelatorioParametrosSrvlt(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "Gera Relatórios", new GeraRelatorioInVO());
  }

  @Override
  public GeraRelatorioInVO getParametros() throws ServletParametrosException
  {
    geraRelatorioInVO.setIdentificadorAppGestor(getIdentificacaoAppGestor(true));

    return geraRelatorioInVO;
  }
  
  
}
