package estiveaqui.relatorios;

import estiveaqui.CodigoErro;

/**
 * Trata uma posi��o definida por coluna e linha de uma planilha Excel.
 * 
 * @author Haroldo
 *
 */
public class PosicaoExcel implements Cloneable
{
  private int coluna = 0;
  private int linha  = 0;
  public static final PosicaoExcel PosicaoDefault = new PosicaoExcel(0, 0); 

  /**
   * Instancia a classe recebendo um par�metro definindo a posi��o de uma coluna no formato CCCLLLLLL, onde
   * CCC s�o at� tr�s letras que representam uma coluna da planilha e
   * LLLLLL s�o at� 6 d�gitos que representam uma linha da planilha. <BR>
   * <BR>
   * Exemplo: B2 � a coluna B da linha 2
   * 
   * @throws PosicaoExcelException - Exce��o quando o formato n�o est� correto.
   * @param posicao
   */
  public PosicaoExcel(String posicao) throws PosicaoExcelException
  {
    setPosicao(posicao);
  }
  
  private PosicaoExcel(int linha, int coluna)
  {
    this.linha = linha;
    this.coluna = coluna;
  }

  /**
   * Define a posi��o de uma coluna no formato CCCLLLLLL, onde
   * CCC s�o at� tr�s letras que representam uma coluna da planilha e
   * LLLLLL s�o at� 6 d�gitos que representam uma linha da planilha. <BR>
   * <BR>
   * Exemplo: B2 � a coluna B da linha 2
   * 
   * @throws PosicaoExcelException - Exce��o quando o formato n�o est� correto.
   * @param posicao
   */
  public void setPosicao(String posicao) throws PosicaoExcelException
  {
    //  Valida o par�metro.
    if (posicao == null)
      throw new PosicaoExcelException(CodigoErro.ERRO_INTERNO, "Posi��o da c�lula Excel com valor nulo.");
    if (posicao.length() < 2 || posicao.length() > 8)
      throw new PosicaoExcelException(CodigoErro.ERRO_INTERNO,
          "Posi��o da c�lula Excel com tamanho inv�lido. Tamanhos v�lidos de 2 a 8 caracteres. PosicaoExcel = \''{0}\''", posicao);

    //  Prepara vari�veis para o parser.
    posicao = posicao.toUpperCase();
    int nChr = 0;

    //  Calcula a posi��o relativa da coluna.
    for (nChr = 0; nChr < posicao.length(); nChr++)
    {
      char chr = posicao.charAt(nChr);
      if (chr < 'A' || chr > 'Z')
        break;

      coluna = chr - 'A' + 1 + 26 * coluna;
    }

    //  Calcula a posi��o relativa da linha.
    for (; nChr < posicao.length(); nChr++)
    {
      char chr = posicao.charAt(nChr);
      if (chr < '0' || chr > '9')
        throw new PosicaoExcelException(CodigoErro.ERRO_INTERNO, "PosicaoExcel da c�lula Excel com formato num�rico incorreto. PosicaoExcel = \''{0}\''", posicao);

      linha = chr - '0' + 10 * linha;
    }

    //  Posi��o v�lida passada como par�metro?
    if (coluna <= 0 || linha <= 0)
      throw new PosicaoExcelException(CodigoErro.ERRO_INTERNO, "PosicaoExcel da c�lula Excel com formato incorreto. PosicaoExcel = \''{0}\''", posicao);

    if (coluna >= 16384 || linha > 1048576)
      throw new PosicaoExcelException(CodigoErro.ERRO_INTERNO, "C�lula fora do intervalo m�ximo permitido. PosicaoExcel = \''{0}\''", posicao);

    //  Ajusta a posi��o para come�ar do n�mero zero.
    linha--;
    coluna--;
  }

  /**
   * Redefine a posi��o usando as coordenadas relativas como inteiros.
   * 
   * @param linha
   * @param coluna
   */
  public void setPosicao(int linha, int coluna)
  {
    this.linha = linha;
    this.coluna = coluna;
  }
  
  public void incrementaColuna()
  {
    coluna++;
  }
  
  public void incrementaLinha()
  {
    linha++;
  }
  
  public int getColuna()
  {
    return coluna;
  }

  public void setColuna(int coluna)
  {
    this.coluna = coluna;
  }

  public int getLinha()
  {
    return linha;
  }

  public void setLinha(int linha)
  {
    this.linha = linha;
  }

  @Override
  public String toString()
  {
    return "(" + coluna + ", " + linha + ")";
  }

  @Override
  public PosicaoExcel clone()
  {
    return new PosicaoExcel(linha, coluna);
    
  }
//  public static void testaErro(String posicao)
//  {
//    try
//    {
//      System.out.println(posicao + " = " + new PosicaoExcel(posicao));
//    }
//    catch (PosicaoExcelException e)
//    {
//      System.out.println("Erro: posi��o (\"" + posicao + "\") : " + e.getMessage());
//    }
//  }
//
//  public static void main(String args[]) throws PosicaoExcelException
//  {
//    testaErro("A10");
//    testaErro("B1");
//    testaErro("C01");
//    testaErro("AA101");
//    testaErro("a1");
//    testaErro("AA");
//    testaErro("12");
//    testaErro("A 1");
//    testaErro("A1:b2");
//    testaErro("AA");
//    testaErro("AA");
//    testaErro("AAAA1234");
//    testaErro("ABC12345678");
//    testaErro("");
//    testaErro("0");
//    testaErro(".");
//  }
}
