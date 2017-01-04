package estiveaqui.appgestor.gerencia.lancamento;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import estiveaqui.appgestor.servlet.ServletParametrosGerencia0;
import estiveaqui.servlet.ServletParametrosException;

@Deprecated
public class GerenciaLancamentoParametrosSrvlt0 extends ServletParametrosGerencia0
{
  private GerenciaLancamentoInVO gerenciaLancamentoInVO = (GerenciaLancamentoInVO)dadosInVo;

  @Deprecated
  public GerenciaLancamentoParametrosSrvlt0(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "Gerencia Lancamento", new GerenciaLancamentoInVO());
  }

  @Override
  @Deprecated
  public GerenciaLancamentoInVO getParametros() throws ServletParametrosException
  {
    gerenciaLancamentoInVO.setIdentificadorAppGestor(getIdentificacaoApp(true));
    gerenciaLancamentoInVO.setTz(getTimeZone(true));
    gerenciaLancamentoInVO.setIdLancamento(getIdLancamento(getAcao().equals("DIS") || getAcao().equals("ENA")));
    gerenciaLancamentoInVO.setHoraManual(getHoraManual(getAcao().equals("CAD")));
    gerenciaLancamentoInVO.setIdAppUsuario(getIdAppUsuario(getAcao().equals("CAD")));
    gerenciaLancamentoInVO.setNota(getNota(false));
    
    return gerenciaLancamentoInVO;
  }
  
  @Deprecated
  private long getIdLancamento(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametroLong("IDLANCAMENTO", obrigatorio, true);
  }

  @Deprecated
  private DateTime getHoraManual(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametroHora("HORAMANUAL", obrigatorio, DateTimeZone.UTC);
  }

  @Deprecated
  private int getIdAppUsuario(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametroInt("IDAPPUSUARIO", obrigatorio, true);
  }
}
