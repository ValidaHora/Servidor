package estiveaqui.appusuario.cadastra;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.servlet.ServletParametros0;
import estiveaqui.servlet.ServletParametrosException;
import estiveaqui.vo.DadosInVO;

public class CadastraAppUsuarioParametrosSrvlt0 extends ServletParametros0
{
  private CadastraAppUsuarioInVO cadastraAppUsuarioInVO = (CadastraAppUsuarioInVO)dadosInVo;

  public CadastraAppUsuarioParametrosSrvlt0(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "Cadastra AppUsuario", new CadastraAppUsuarioInVO());
  }

  @Override
  public DadosInVO getParametros() throws ServletParametrosException
  {
    cadastraAppUsuarioInVO.setTz(getTimeZone(false));
    cadastraAppUsuarioInVO.setCodAtivacao(getCodigoAtivacao(true));

    return cadastraAppUsuarioInVO;
  }
}
