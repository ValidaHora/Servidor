package estiveaqui.appgestor.relatorio;

import java.util.ArrayList;
import estiveaqui.sql.mo.RelatorioMO;
import estiveaqui.vo.DadosOutVO;

public class GeraRelatorioOutVO implements DadosOutVO
{
  private ArrayList<RelatorioMO>  relatorioMO  = new ArrayList<RelatorioMO>();

  public ArrayList<RelatorioMO> getRelatoriosMO()
  {
    return relatorioMO;
  }

  public void setRelatoriosMO(ArrayList<RelatorioMO> relatoriosMO)
  {
    this.relatorioMO = relatoriosMO;
  }
  
//  jsonMsg.put("URLArquivos", "http://" + Persiste.AWSS3BucketName);
}
