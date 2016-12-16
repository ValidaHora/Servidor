package estiveaqui.appgestor;

import estiveaqui.vo.DadosInVO;

/**
 * Implementa o Value Object que todas as funcionalidades Gestor tem que conter.
 * 
 * @author Haroldo
 *
 */
public class DadosAppGestorInVO extends DadosInVO
{
  private String identificadorAppGestor = "";

  public final String getIdentificadorAppGestor()
  {
    return identificadorAppGestor;
  }

  public final void setIdentificadorAppGestor(String identificadorAppGestor)
  {
    this.identificadorAppGestor = identificadorAppGestor;
  }

}
