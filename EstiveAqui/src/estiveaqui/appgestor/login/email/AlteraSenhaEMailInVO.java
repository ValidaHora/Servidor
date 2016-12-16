package estiveaqui.appgestor.login.email;

import estiveaqui.appgestor.DadosAppGestorInVO;

public class AlteraSenhaEMailInVO extends DadosAppGestorInVO
{
  private String senhaNova   = null;
  private String senhaAntiga = null;
  private String codRecuperaSenha = null;

  public String getSenhaNova()
  {
    return senhaNova;
  }

  public void setSenhaNova(String senhaNova)
  {
    this.senhaNova = senhaNova;
  }

  public String getSenhaAntiga()
  {
    return senhaAntiga;
  }

  public void setSenhaAntiga(String senhaAntiga)
  {
    this.senhaAntiga = senhaAntiga;
  }

  public String getCodRecuperaSenha()
  {
    return codRecuperaSenha;
  }

  public void setCodRecuperaSenha(String codRecuperaSenha)
  {
    this.codRecuperaSenha = codRecuperaSenha;
  }
}
