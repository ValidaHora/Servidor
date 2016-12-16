package estiveaqui.appusuario.lesementes;

import java.util.ArrayList;
import estiveaqui.appusuario.DadosAppUsuarioOutVO;
import estiveaqui.sql.mo.externo.TokenMO;

public class LeSementesOutVO extends DadosAppUsuarioOutVO
{
  private ArrayList<TokenMO> tokensMO = new ArrayList<TokenMO>();

  public ArrayList<TokenMO> getTokensMO()
  {
    return tokensMO;
  }

  public void setTokensMO(ArrayList<TokenMO> tokensMO)
  {
    this.tokensMO = tokensMO;
  }

}
