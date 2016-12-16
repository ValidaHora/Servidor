package estiveaqui.appgestor.login.email;

import estiveaqui.CodigoErro;
import estiveaqui.Criptografa;
import estiveaqui.CriptografaException;
import estiveaqui.RegraDeNegocioException;
import estiveaqui.sql.mo.AppGestorMO;

public class UtilEMail
{
  /**
   * Valida as regras de formação de senhas.<BR>
   * 
   * @param senha
   * @throws RegraDeNegocioException
   */
  protected static void validaRegrasParaSenha(String senha) throws RegraDeNegocioException
  {
    int tamMin = 6;
    int tamMax = 44;
    
    if (senha.length() < tamMin)
      throw new RegraDeNegocioException(CodigoErro.SENHA_INVALIDA, "Senha tem que ter no mínimo " + tamMin + " caracteres");

    if (senha.length() > tamMax)
      throw new RegraDeNegocioException(CodigoErro.SENHA_INCORRETA, "Senha tem que ter no máximo " + tamMax + " caracteres");
  }
  
  /**
   * Criptografa e atribui a senha 'senhaAberta' ao 'appGestorMO'.
   * 
   * @param appGestorMO
   * @param senhaAberta
   */
  protected static void setSenhaCriptografada(AppGestorMO appGestorMO, String senhaAberta) throws CriptografaException
  {
    if (senhaAberta == null || senhaAberta.isEmpty() || appGestorMO.getIdAppGestor() == 0)
      appGestorMO.setSenha(null);

    appGestorMO.setSenha(Criptografa.criptografa(senhaAberta, appGestorMO.getIdAppGestor()));
  }

  /**
   * Decriptografa a senha que se encontra em 'appGestorMO'.
   * 
   * @param appGestorMO
   * @return senha decriptografada.
   */
  protected static String getSenhaDecriptografada(AppGestorMO appGestorMO) throws CriptografaException
  {
    if (appGestorMO.getSenha() == null || appGestorMO.getSenha().isEmpty() || appGestorMO.getIdAppGestor() == 0)
      return null;

    return Criptografa.decriptografa(appGestorMO.getSenha(), appGestorMO.getIdAppGestor());
  }
}
