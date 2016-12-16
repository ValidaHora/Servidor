package estiveaqui.appgestor;

/**
 * Implementa o Value Object que todas as funcionalidades as classes Gerencia tem que conter.
 * 
 * @author Haroldo
 *
 */
public class DadosGerenciaisInVO extends DadosAppGestorInVO
{
  private String acao = "";

  public String getAcao()
  {
    return acao;
  }

  public void setAcao(String acao)
  {
    this.acao = acao;
  }

}
