package estiveaqui.appgestor.gerencia.usuario;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.appgestor.DadosAppGestorInVO;
import estiveaqui.appgestor.servlet.ServletParametrosGerencia0;
import estiveaqui.servlet.ServletParametrosException;

@Deprecated
public class GerenciaAppUsuarioParametrosSrvlt0 extends ServletParametrosGerencia0
{
  private GerenciaAppUsuarioInVO gerenciaAppUsuarioInVO = (GerenciaAppUsuarioInVO)dadosInVo;
  
  @Deprecated
  public GerenciaAppUsuarioParametrosSrvlt0(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "Gerencia AppUsuario", new GerenciaAppUsuarioInVO());
  }

  @Override
  @Deprecated
  public DadosAppGestorInVO getParametros() throws ServletParametrosException
  {
    gerenciaAppUsuarioInVO.setIdentificadorAppGestor(getIdentificacaoApp(true));
    gerenciaAppUsuarioInVO.setIdAppUsuario(getIdAppUsuario(getAcao().equals("DIS") || getAcao().equals("ENA") || getAcao().equals("UPD")));
    gerenciaAppUsuarioInVO.setIdIntegracao(getIdIntegracao(false));
    gerenciaAppUsuarioInVO.setApelido(getApelido(getAcao().equals("CAD")));
    gerenciaAppUsuarioInVO.setMaxLancamentosPorDia(getMaxLancamentosPorDia(getAcao().equals("CAD")));
    
    return gerenciaAppUsuarioInVO;
  }

  @Deprecated
  protected int getIdAppUsuario(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametroInt("IDAPPUSUARIO", obrigatorio, true);
  }

  @Deprecated
  protected String getApelido(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametro("APELIDO", obrigatorio, true);
  }

  @Deprecated
  protected String getIdIntegracao(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametro("IDINTEGRACAO", obrigatorio, true);
  }
  
  @Deprecated
  protected int getMaxLancamentosPorDia(boolean obrigatorio) throws ServletParametrosException
  {
    return getParametroInt("MAXLANCAMENTOSDIA", obrigatorio, true);
  }
}
