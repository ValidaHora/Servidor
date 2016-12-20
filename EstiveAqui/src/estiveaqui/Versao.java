package estiveaqui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.vo.DadosInVO;

/**
 * Define a vers�o.
 * 
 * @author Haroldo
 *
 */
public class Versao
{
  private static final Logger log       = LogManager.getLogger();

  private int                 versao    = -1;
  private int                 subVersao = -1;
  private int                 fix       = -1;

  public Versao(int versao, int subVersao, int fix)
  {
    this.versao = versao;
    this.subVersao = subVersao;
    this.fix = fix;
  }

  public Versao(String sVersao)
  {
    try
    {
      int pos1 = sVersao.indexOf('.');
      versao = Integer.parseInt(sVersao.substring(0, pos1));
      int pos2 = sVersao.indexOf('.', pos1 + 1);
      subVersao = Integer.parseInt(sVersao.substring(pos1 + 1, pos2));
      fix = Integer.parseInt(sVersao.substring(pos2 + 1));
    }
    catch (NumberFormatException e)
    {
      log.info("Formato vers�o incorreta. Versao = '{}'", sVersao);
      versao = subVersao = fix = -1;
    }
  }

  /**
   * Faz a compara��o padr�o das versões que s�o aceitas por este servidor a partir da vers�o que o App est�
   * pretendendo chamar.
   * 
   * @param dadosInVo App e com vers�o que espera conversar.
   * @param versaoMin Vers�o mais antiga que ser� aceita.
   * @param versaoMax Vers�o mais recente que ser� aceita.
   * @throws EstiveAquiException Se a vers�o n�o for aceita
   */
  public static void validaVersao(DadosInVO dadosInVo, Versao versaoMin, Versao versaoMax) throws EstiveAquiException
  {
    Versao versaoApp = dadosInVo.getVersao();

    if (versaoApp.compareTo(versaoMin) < 0)
      throw new EstiveAquiException(CodigoErro.VERSAO_INVALIDA, "Solicita��o de acesso a vers�o antiga. Vers�o solicitada {0}, vers�o mais antiga {1}",
          dadosInVo.getVersao(), versaoMin);

    if (versaoApp.compareTo(versaoMax) > 0)
      throw new EstiveAquiException(CodigoErro.VERSAO_NAO_DISPONIVEL, "Solicita��o de acesso a vers�o inexistente.  Vers�o solicitada {0}, vers�o mais antiga {1}",
          dadosInVo.getVersao(), versaoMin);

  }

  @Override
  public String toString()
  {
    return versao + "." + subVersao + "." + fix;
  }

  /**
   * Compara vers�o.
   * 
   * @param versao
   * @return - =0 -> Mesma vers�o
   *         >0 -> Vers�o posterior
   *         <0 -> Vers�o anterior
   */
  public int compareTo(Versao versao)
  {
    if (this.versao != versao.versao)
      return this.versao - versao.versao;

    if (this.subVersao != versao.subVersao)
      return this.subVersao - versao.subVersao;

    if (this.fix != versao.fix)
      return this.fix - versao.fix;

    return 0;
  }

  /**
   * Retorna se a vers�o � v�lida ou n�o.
   * 
   * @return
   */
  public boolean isOk()
  {
    return (versao >= 0 && subVersao >= 0 && fix >= 0);
  }

  public int getVersao()
  {
    return versao;
  }

  public void setVersao(int versao)
  {
    this.versao = versao;
  }

  public int getSubVersao()
  {
    return subVersao;
  }

  public void setSubVersao(int subVersao)
  {
    this.subVersao = subVersao;
  }

  public int getFix()
  {
    return fix;
  }

  public void setFix(int fix)
  {
    this.fix = fix;
  }
  
//public static void main(String[] args)
//{
//  VersaoVO v;
//  v = new VersaoVO("1.2.3");
//  System.out.println(v.toString() + " = " + v.isOk());
//  v = new VersaoVO("1..2.3");
//  System.out.println(v.toString() + " = " + v.isOk());
//  v = new VersaoVO(".1.2.3");
//  System.out.println(v.toString() + " = " + v.isOk());
//  v = new VersaoVO("1.2.");
//  System.out.println(v.toString() + " = " + v.isOk());
//  v = new VersaoVO("1.2.3.4");
//  System.out.println(v.toString() + " = " + v.isOk());
//  v = new VersaoVO("1.2.a");
//  System.out.println(v.toString() + " = " + v.isOk());
//  v = new VersaoVO("1.0.3");
//  System.out.println(v.toString() + " = " + v.isOk());
//  v = new VersaoVO("1.-2.3");
//  System.out.println(v.toString() + " = " + v.isOk());
//}
}
