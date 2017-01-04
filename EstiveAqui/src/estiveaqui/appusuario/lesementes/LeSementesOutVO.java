package estiveaqui.appusuario.lesementes;

import java.util.ArrayList;
import java.util.List;
import estiveaqui.appusuario.DadosAppUsuarioOutVO;
import estiveaqui.sql.mo.externo.TokenMO;

public class LeSementesOutVO extends DadosAppUsuarioOutVO
{
  private List<TokenMO> tokensMO = new ArrayList<TokenMO>();

  public List<TokenMO> getTokensMO()
  {
    return tokensMO;
  }

  public void setTokensMO(List<TokenMO> tokensMO)
  {
    this.tokensMO = tokensMO;
  }

}
