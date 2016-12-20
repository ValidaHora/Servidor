package estiveaqui.appgestor.relatorio;

import java.util.ArrayList;
import org.joda.time.DateTime;
import estiveaqui.sql.mo.LancamentoMO;

/**
 * Agrupa por m�s em um ArrayList de ArrayLists de lan�amentos, os lan�amentos de um ArrayList.
 * 
 * @author Haroldo
 *
 */
class LancamentosMesVO
{
  private DateTime mes = null;
  private ArrayList<LancamentoMO> lancamentos = new ArrayList<LancamentoMO>();

  /**
   * 
   * @param lancamentosMO
   */
  LancamentosMesVO(DateTime mes)
  {
    setMes(mes);
  }

  public DateTime getMes()
  {
    return mes;
  }

  public void setMes(DateTime mes)
  {
    this.mes = mes;
  }

  public ArrayList<LancamentoMO> getLancamentos()
  {
    return lancamentos;
  }

  public void setLancamentos(ArrayList<LancamentoMO> lancamentos)
  {
    this.lancamentos = lancamentos;
  }
}
