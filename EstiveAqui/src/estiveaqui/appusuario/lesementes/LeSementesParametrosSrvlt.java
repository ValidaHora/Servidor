package estiveaqui.appusuario.lesementes;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.servlet.ServletParametros0;
import estiveaqui.servlet.ServletParametrosException;
import estiveaqui.vo.DadosInVO;

public class LeSementesParametrosSrvlt extends ServletParametros0
{
  private LeSementesInVO leSementeInVO = (LeSementesInVO)dadosInVo;

  public LeSementesParametrosSrvlt(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "Lê Sementes", new LeSementesInVO());
  }

  @Override
  public DadosInVO getParametros() throws ServletParametrosException
  {
    leSementeInVO.setIdentificacaoApp(getIdentificacaoApp0(true));
    
    return leSementeInVO;
  }

}
