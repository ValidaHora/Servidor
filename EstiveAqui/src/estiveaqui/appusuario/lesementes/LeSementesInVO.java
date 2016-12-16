package estiveaqui.appusuario.lesementes;

import java.util.List;
import estiveaqui.appusuario.DadosAppUsuarioInVO;

public class LeSementesInVO extends DadosAppUsuarioInVO
{
  private List<String> numerosPassClock;

  public List<String> getNumerosPassClock()
  {
    return numerosPassClock;
  }

  public void setNumerosPassClock(List<String> numerosPassClock)
  {
    this.numerosPassClock = numerosPassClock;
  }

}
