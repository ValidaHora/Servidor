package estiveaqui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.vo.DadosInVO;

/**
 * Define a versão.
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
      log.info("Formato versão incorreta. Versao = '{}'", sVersao);
      versao = subVersao = fix = -1;
    }
  }

  /**
   * Faz a comparação padrão das versÃµes que são aceitas por este servidor a partir da versão que o App está
   * pretendendo chamar.
   * 
   * @param dadosInVo App e com versão que espera conversar.
   * @param versaoMin Versão mais antiga que será aceita.
   * @param versaoMax Versão mais recente que será aceita.
   * @throws EstiveAquiException Se a versão não for aceita
   */
  public static void validaVersao(DadosInVO dadosInVo, Versao versaoMin, Versao versaoMax) throws EstiveAquiException
  {
    Versao versaoApp = dadosInVo.getVersao();

    if (versaoApp.compareTo(versaoMin) < 0)
      throw new EstiveAquiException(CodigoErro.VERSAO_INVALIDA, "Solicitação de acesso a versão antiga. Versão solicitada {0}, versão mais antiga {1}",
          dadosInVo.getVersao(), versaoMin);

    if (versaoApp.compareTo(versaoMax) > 0)
      throw new EstiveAquiException(CodigoErro.VERSAO_NAO_DISPONIVEL, "Solicitação de acesso a versão inexistente.  Versão solicitada {0}, versão mais antiga {1}",
          dadosInVo.getVersao(), versaoMin);

  }

  @Override
  public String toString()
  {
    return versao + "." + subVersao + "." + fix;
  }

  /**
   * Compara versão.
   * 
   * @param versao
   * @return - =0 -> Mesma versão
   *         >0 -> Versão posterior
   *         <0 -> Versão anterior
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
   * Retorna se a versão é válida ou não.
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
