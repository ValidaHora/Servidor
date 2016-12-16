package estiveaqui.appgestor.gerencia.lancamento;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import estiveaqui.appgestor.servlet.ServletParametrosGerencia;
import estiveaqui.servlet.ServletParametrosException;

public class GerenciaLancamentoParametrosSrvlt extends ServletParametrosGerencia
{
  private GerenciaLancamentoInVO gerenciaLancamentoInVO = (GerenciaLancamentoInVO)dadosInVo;

  public GerenciaLancamentoParametrosSrvlt(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "Gerencia Lancamento", new GerenciaLancamentoInVO());
  }

  @Override
  public GerenciaLancamentoInVO getParametros() throws ServletParametrosException
  {
    gerenciaLancamentoInVO.setIdentificadorAppGestor(getIdentificacaoApp(true));
    gerenciaLancamentoInVO.setTz(getTimeZone(true));
    gerenciaLancamentoInVO.setIdLancamento(getIdLancamento(getAcao().equals("DIS") || getAcao().equals("ENA")));
    gerenciaLancamentoInVO.setHoraManual(getHoraManual(getAcao().equals("CAD"), gerenciaLancamentoInVO.getTz()));
    gerenciaLancamentoInVO.setIdAppUsuario(getIdAppUsuario(getAcao().equals("CAD")));
    gerenciaLancamentoInVO.setNota(getNota(false));
    
    return gerenciaLancamentoInVO;
  }
  
  private long getIdLancamento(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametroLong("IDLANCAMENTO", obrigatorio, true);
  }

  private DateTime getHoraManual(boolean obrigatorio, DateTimeZone tz) throws ServletParametrosException
  {
    return getParametroHora("HORAMANUAL", obrigatorio, tz);
  }

  private int getIdAppUsuario(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametroInt("IDAPPUSUARIO", obrigatorio, true);
  }
}
