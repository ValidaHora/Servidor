package estiveaqui.appgestor.gerencia.lancamento;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import estiveaqui.appgestor.servlet.ServletParametrosGerencia;
import estiveaqui.servlet.NomeParametroServlet;
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
    gerenciaLancamentoInVO.setIdentificadorAppGestor(getIdentificacaoAppGestor(true));
    gerenciaLancamentoInVO.setIdLancamento(getIdLancamento(getAcao().equals("DIS") || getAcao().equals("ENA")));
    gerenciaLancamentoInVO.setHoraManual(getHoraManual(getAcao().equals("CAD")));
    gerenciaLancamentoInVO.setIdAppUsuario(getIdAppUsuario(getAcao().equals("CAD")));
    gerenciaLancamentoInVO.setNumeroPassClock(getNumeroPassClock(getAcao().equals("CAD")));
    gerenciaLancamentoInVO.setNota(getNota(false));
    
    return gerenciaLancamentoInVO;
  }
  
  private long getIdLancamento(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametroLong(NomeParametroServlet.IdLancamento, obrigatorio, true);
  }

  private DateTime getHoraManual(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametroHora(NomeParametroServlet.HoraManual, obrigatorio);
  }

  private int getIdAppUsuario(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametroInt(NomeParametroServlet.IdAppUsuario, obrigatorio, true);
  }
}
