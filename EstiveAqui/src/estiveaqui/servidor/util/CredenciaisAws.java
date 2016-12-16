package estiveaqui.servidor.util;

import com.amazonaws.auth.AWSCredentials;

/**
 * Classe controla as credenciais para acesso ao AWS.
 * 
 * @author Haroldo
 *
 */
public class CredenciaisAws implements AWSCredentials
{
  private String AWSChaveDeAcesso = "";
  private String AWSSenha = "";

  /**
   * Classe que controla as credencias para acesso ao AWS.
   * Se este construtor for utilizado, deve-se definir a chave de acesso e senha 
   * com os métodos setAWSAccessKeyId() e setAWSSecretKey().
   */
  public CredenciaisAws()
  {
    //  Nada a fazer;
  }
  
  /**
   * Classe que controla as credencias para acesso ao AWS.
   * 
   * @param AWSChaveDeAcesso
   * @param AWSSenha
   */
  public CredenciaisAws(String AWSChaveDeAcesso, String AWSSenha)
  {
    setAWSAccessKeyId(AWSChaveDeAcesso);
    setAWSSecretKey(AWSSenha);
  }
  
  public void setAWSAccessKeyId(String AWSChaveDeAcesso)
  {
    this.AWSChaveDeAcesso = (AWSChaveDeAcesso == null ? "" : AWSChaveDeAcesso);
  }
  
  @Override
  public String getAWSAccessKeyId()
  {
    return AWSChaveDeAcesso;
  }

  public void setAWSSecretKey(String AWSSenha)
  {
    this.AWSSenha = (AWSSenha == null ? "" : AWSSenha);
  }

  @Override
  public String getAWSSecretKey()
  {
    return AWSSenha;
  }

}
