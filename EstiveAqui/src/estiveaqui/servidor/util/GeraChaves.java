package estiveaqui.servidor.util;

import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Objeto especializado em gerar chaves aleat�rias.
 * 
 */
public class GeraChaves
{
  private static final Logger log = LogManager.getLogger();
  
  public static final byte  MAIUSCULAS = 1,
                            MINUSCULAS = 2,
                            NUMEROS = 4,
                            ARITMETICOS = 8,
                            PONTUACAO = 16,
                            DEMAIS = 32;
  public static final byte  LETRAS = MAIUSCULAS | MINUSCULAS,
                            TODOS = 63;

  private static final String sMaiusculas = "ABCDEFGHIJKLMNOPQRSTUVYWXZ",
                              sMinusculas = "abcdefghijklmnopqrstuvwxyz",
                              sNumeros = "0123456789",
                              sAritmeticos = "+-*/=<>(){}[]",
                              sPontuacao = "!?,.;:'\"",
                              sDemais = "@#$%&_\\";
//  private static final String sHexadecimal = "0123456789ABCDEF";
  private static final String sLetras = sMaiusculas + sMinusculas,
                              sTodas = sMaiusculas + sMinusculas + sNumeros + sAritmeticos + sPontuacao + sDemais;
  
  /**
   * Gera uma chave aleat�ria composta de caracteres leg�veis.
   * 
   * @param tamanho
   *          - Tamanho da chave que ser� gerada.
   * 
   * @return String com a chave aleat�ria gerada.
   */
  public static String geraChave(int tamanho)
  {
    return geraChave(0L, tamanho, sTodas);
  }

  /**
   * Gera uma chave aleat�ria compostas de caracteres leg�veis.
   * Os caracteres que formam a chave s�o escolhidos com base no tipo composto.
   * 
   * @param tamanho - Tamanho da chave que ser� gerada.
   * @param tipoComposto - Tipo de caracter que deseja usar na gera��o da chave
   * 
   * @return String com a chave aleat�ria gerada.
   */
  public static String geraChave(int tamanho, int tipoComposto)
  {
    return geraChave(tamanho, (byte)tipoComposto);
  }
  
  /**
   * Gera uma chave aleat�ria compostas de caracteres leg�veis.
   * Os caracteres que formam a chave s�o escolhidos com base no tipo.
   * 
   * @param tamanho - Tamanho da chave que ser� gerada.
   * @param tipo - Tipo de caracter que deseja usar na gera��o da chave
   * 
   * @return String com a chave aleat�ria gerada.
   */
  public static String geraChave(int tamanho, byte tipo)
  {
    return geraChave(0L, tamanho, tipo);
  }

  /**
   * Gera uma chave aleat�ria compostas de caracteres leg�veis e com uso de uma semente.
   * Os caracteres que formam a chave s�o escolhidos com base no tipo.
   * 
   * @param tamanho - Tamanho da chave que ser� gerada.
   * @param caracteres - String contendo o conjunto de caracteres que compor�o a chave. 
   * 
   * @return String com a chave aleat�ria gerada.
   */
  public static String geraChave(int tamanho, String caracteres)
  {
    return geraChave(0L, tamanho, caracteres);
  }

  /**
   * Gera uma chave aleat�ria compostas de caracteres leg�veis e com uso de uma semente.
   * Os caracteres que formam a chave s�o escolhidos com base no tipo.
   * 
   * @param tamanho - Tamanho da chave que ser� gerada.
   * @param caracteres - String contendo o conjunto de caracteres que compor�o a chave. 
   * @param tipo - Tipo de caracter que deseja usar na gera��o da chave
   * 
   * @return String com a chave aleat�ria gerada.
   */
  public static String geraChave(long semente, int tamanho, byte tipo)
  {
    StringBuffer caracteres = new StringBuffer(100);
    if ((tipo & MAIUSCULAS) != 0)
      caracteres.append(sMaiusculas);
    if ((tipo & MINUSCULAS) != 0)
      caracteres.append(sMinusculas);
    if ((tipo & NUMEROS) != 0)
      caracteres.append(sNumeros);
    if ((tipo & ARITMETICOS) != 0)
      caracteres.append(sAritmeticos);
    if ((tipo & PONTUACAO) != 0)
      caracteres.append(sPontuacao);
    if ((tipo & DEMAIS) != 0)
      caracteres.append(sDemais);
    
    return geraChave(semente, tamanho, caracteres.toString());
  }
  
  /**
   * Gera uma chave aleat�ria compostas de caracteres leg�veis e com uso de uma semente.
   * Os caracteres que formam a chave s�o escolhidos com base no tipo.
   * 
   * @param semente - Se for 0, n�o usa a semente!
   * @param tamanho - Tamanho da chave que ser� gerada.
   * @param caracteres - String contendo o conjunto de caracteres que compor�o a chave. 
   * 
   * @return String com a chave aleat�ria gerada.
   */
  public static String geraChave(long semente, int tamanho, String caracteres)
  {
    Random aleatorio = null;
    if (semente == 0)
      aleatorio = new Random();
    else
      aleatorio = new Random(semente);

    StringBuffer chave = new StringBuffer(tamanho);
    int pos = -1;
    for (int i = 0; i < tamanho; i++)
    {
      pos = aleatorio.nextInt(caracteres.length());
      chave.append(caracteres.substring(pos, pos + 1));
    }

    log.debug("Chave gerada = {}", chave.toString());

    return chave.toString();
  }

  //=============================================================================================
  //  Chaves pr�-definidas.
  //=============================================================================================

  /**
   * Gera uma chave para identifica��o do AppGestor
   * 
   * @return
   */
  public static String identificadorAppGestor()
  {
    return geraChave(40, sLetras + sNumeros);
  }
  
  /**
   * Gera uma chave para identifica��o do AppUsuario
   * 
   * @return
   */
  public static String identificadorAppUsuario()
  {
    return geraChave(50, sLetras + sNumeros);
  }

  /**
   * Gera uma chave para identifica��o da URL do Usuario.
   * 
   * @return
   */
  public static String codigoAtivacaoUsuario()
  {
    return geraChave(4, sMaiusculas + sNumeros);
  }
  
  /**
   * Gera uma chave para identifica��o do AppGestor
   * 
   * @return
   */
  public static String codigoValidacaoEMail()
  {
    return geraChave(16, sLetras + sNumeros);
  }

  /**
   * Gera uma chave para a recupera��o da senha de um email.
   * 
   * @return
   */
  public static String codigoRecuperacaoSenhaEMail()
  {
    return geraChave(12, sMaiusculas + sNumeros);
  }
  
  /**
   * Gera uma senha para cadastramento do PassClock.
   * 
   * @return
   */
  public static String senhaPassClock()
  {
    log.debug("Gerando senha para PassClock");
    return geraChave(8, sMaiusculas);
  }

  /**
   * Retorna um c�digo de ativa��o aleat�rio com 12 letras mai�sculas e n�meros.
   * 
   * @return String com letras mai�sculas e n�meros.
   */
  public static String codigoAtivacaoPassClockVirtual()
  {
    return geraChave(12, sMaiusculas + sNumeros);
  }
  
  /**
   * Retorna o sufixo do n�mero de um PassClock virtual.
   * 
   * @return String com 12 letras e n�meros.
   */
  public static String sufixoPassClockVirtual()
  {
    return geraChave(12, sLetras + sNumeros);
  }
  
//  public static void main(String[] arg)
//  {
//
//  }
  
//  public static void main(String[] arg)
//  {
//    System.out.println(GeraChaves.sementePassClockVirtual());
//    System.out.println(GeraChaves.nomeRelatorio(12345, 201509));
//    System.out.println(GeraChaves.nomeRelatorio(12346, 201509));
//    System.out.println("In�cio:\ny:SN}]1U.Qt}!k?V\\u8}");
//    System.out.println(GeraChaves.geraChave(50L* 10000 + 1511, 20, sTodas));
//    System.out.println(GeraChaves.geraChave(50L* 10000 + 1511, 20, sTodas));
//    System.out.println(GeraChaves.geraChave(0, 20, sTodas));
//    
//    System.out.println(GeraChaves.geraChave(50));
//    System.out.println(GeraChaves.geraChave(50, LETRAS));
//    System.out.println(GeraChaves.geraChave(50, "Haroldo"));
//    System.out.println(GeraChaves.geraChave(50, MAIUSCULAS|NUMEROS));
//    System.out.println(GeraChaves.geraChave(50, DEMAIS+ARITMETICOS+NUMEROS));
//    
//    int nIteracoes = 1000000;
//    System.out.println("Iniciando " + nIteracoes + ".");
//    long ini = System.currentTimeMillis();
//    for (int i = 0; i < nIteracoes; i++)
//      GeraChaves.geraChave(100, DEMAIS+ARITMETICOS+NUMEROS+LETRAS);
//    long fim = System.currentTimeMillis();
//    
//    System.out.println("Tempo de execu��o de " + nIteracoes + " = " + (fim - ini) + " milisegundos.");
//    
//  }
}
