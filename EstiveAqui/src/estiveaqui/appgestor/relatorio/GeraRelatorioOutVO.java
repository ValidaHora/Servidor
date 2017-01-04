package estiveaqui.appgestor.relatorio;

import java.util.ArrayList;
import java.util.List;
import estiveaqui.sql.mo.RelatorioMO;
import estiveaqui.vo.DadosOutVO;

public class GeraRelatorioOutVO implements DadosOutVO
{
  private List<RelatorioMO>  relatorioMO  = new ArrayList<RelatorioMO>();

  public List<RelatorioMO> getRelatoriosMO()
  {
    return relatorioMO;
  }

  public void setRelatoriosMO(List<RelatorioMO> relatoriosMO)
  {
    this.relatorioMO = relatoriosMO;
  }
  
//  jsonMsg.put("URLArquivos", "http://" + Persiste.AWSS3BucketName);
}
