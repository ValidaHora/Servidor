package estiveaqui.appgestor.gerencia.usuario;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.appgestor.DadosAppGestorInVO;
import estiveaqui.appgestor.servlet.ServletParametrosGerencia;
import estiveaqui.servlet.ServletParametrosException;

public class GerenciaAppUsuarioParametrosSrvlt extends ServletParametrosGerencia
{
  private GerenciaAppUsuarioInVO gerenciaAppUsuarioInVO = (GerenciaAppUsuarioInVO)dadosInVo;
  
  public GerenciaAppUsuarioParametrosSrvlt(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "Gerencia AppUsuario", new GerenciaAppUsuarioInVO());
  }

  @Override
  public DadosAppGestorInVO getParametros() throws ServletParametrosException
  {
    gerenciaAppUsuarioInVO.setIdentificadorAppGestor(getIdentificacaoApp(true));
    gerenciaAppUsuarioInVO.setTz(getTimeZone(true));
    gerenciaAppUsuarioInVO.setIdAppUsuario(getIdAppUsuario(getAcao().equals("DIS") || getAcao().equals("ENA") || getAcao().equals("UPD")));
    gerenciaAppUsuarioInVO.setIdIntegracao(getIdIntegracao(false));
    gerenciaAppUsuarioInVO.setApelido(getApelido(getAcao().equals("CAD")));
    gerenciaAppUsuarioInVO.setMaxLancamentosPorDia(getMaxLancamentosPorDia(getAcao().equals("CAD")));
    
    return gerenciaAppUsuarioInVO;
  }

  protected int getIdAppUsuario(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametroInt("IDAPPUSUARIO", obrigatorio, true);
  }

  protected String getApelido(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametro("APELIDO", obrigatorio, true);
  }

  protected String getIdIntegracao(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametro("IDINTEGRACAO", obrigatorio, true);
  }
  
  protected int getMaxLancamentosPorDia(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametroInt("MAXLANCAMENTOSDIA", obrigatorio, true);
  }
}
