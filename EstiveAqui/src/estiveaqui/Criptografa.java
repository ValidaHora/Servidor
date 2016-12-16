package estiveaqui;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Criptografa
{
  private static final Logger log = LogManager.getLogger();

  private static final String ALGORITMO  = "AES";
  private static final String[] sais = {  //  Não alterar nenhum dos caracteres deste array!
      "{y}}>g[*",
      "U9f7UXsV",
      "u|}Rhe6t",
      "(5E?tqQn",
      "[aDR^&4H",
      "RK}DssW2",
      "*K%+?!{x",
      "(UQS8?}w",
      "+NCswpAx",
      "Fe*3r7BD",
      "cy1{[Ke_",
      "zha&4}Tr",
      "MsRG4z~8",
      "'!(mb>is",
      "hrA[C0|)",
      "l\\_;vlRy",
      "DwgKM_W4"
    };
  
  /**
   * Criptografa o texto passado no parâmetro.<BR>
   * Um número qualquer deve ser passado para aumentar a dificuldade de quebra da senha. 
   * Este número tem que ser o mesmo número passado no momento da decriptografia do texto.
   * 
   * @param textoAberto - Texto a ser criptografado.
   * @param numeroQualquer - Número usado para dificultar a quebra. Use o mesmo número para decriptografar.
   * @return
   * @throws Exception
   */
  public static String criptografa(String textoAberto, int numeroQualquer) throws CriptografaException
  {
    Key chave = geraChave();
    Cipher cifra;
    try
    {
      cifra = Cipher.getInstance(ALGORITMO);
      cifra.init(Cipher.ENCRYPT_MODE, chave);

      String sal = sais[Math.abs(numeroQualquer) % 17];
      String textoACodificar = sal + textoAberto;
      byte[] valorCriptografado;
      valorCriptografado = cifra.doFinal(textoACodificar.getBytes());

      return new BASE64Encoder().encode(valorCriptografado);
    }
    catch (NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e)
    {
      e.printStackTrace();
      log.error("Erro interno: " + e.getMessage());
      throw new CriptografaException(CodigoErro.ERRO_INTERNO, e.getMessage());
    }

  }

  /**
   * Decriptografa o texto passado no parâmetro.<BR>
   * Um número qualquer deve ser passado para aumentar a dificuldade de quebra da senha. 
   * Este número tem que ser o mesmo número usado no momento da criptografia do texto.
   * 
   * @param textoCriptografado - Texto a ser decriptografado.
   * @param numeroQualquer - Número usado para dificultar a quebra. Use o mesmo número que foi usado para criptografar.
   * @return
   * @throws CriptografaException
   */
  public static String decriptografa(String textoCriptografado, int numeroQualquer) throws CriptografaException 
  {
    Key chave = geraChave();
    Cipher cifra;
    try
    {
      cifra = Cipher.getInstance(ALGORITMO);
      cifra.init(Cipher.DECRYPT_MODE, chave);

      String sal = sais[Math.abs(numeroQualquer) % 17];
      byte[] valorDecriptografado = new BASE64Decoder().decodeBuffer(textoCriptografado);
      byte[] decValue;
      decValue = cifra.doFinal(valorDecriptografado);

      return new String(decValue).substring(sal.length());
    }
    catch (NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | IOException e)
    {
      e.printStackTrace();
      throw new CriptografaException(CodigoErro.ERRO_INTERNO, e.getMessage());
    }
    catch (StringIndexOutOfBoundsException e)
    {
      throw new CriptografaException(CodigoErro.ERRO_INTERNO, e.getMessage());
    }
  }

  private static Key geraChave()
  {
    String chaveCriptografica = "t*k&m'@DNN#Mj4T)";
    
    Key chave = new SecretKeySpec(chaveCriptografica.getBytes(), ALGORITMO);
    return chave;
  }

//  static void print(String txt, int n) throws Exception
//  {
//    String cript = criptografa(txt, n);
//    System.out.println("(" + txt + ", " + n + ") tam = " + cript.length());
//    System.out.println(cript);
//    System.out.println("(" + decriptografa(txt, n) + ")\n");
//
//  }
//  public static void main(String[] args) throws Exception
//  {
//    print("", 0);
//    print(null, 0);
//    print("", 1);
//    print(null, 2);
//    print("1", 0);
//    print("12", 0);
//    print("123", 0);
//    print("1234", 0);
//    print("12345", 0);
//    print("123456", 0);
//    print("1234567", 0);
//    print("12345678", 0);
//    print("123456789", 0);
//    print("1234567890123456", 0);
//    print("123456789012345", 0);
//    print("12345678901234", 0);
//    print("1234567890123", 0);
//    print("123456789012", 0);
//  }
}
