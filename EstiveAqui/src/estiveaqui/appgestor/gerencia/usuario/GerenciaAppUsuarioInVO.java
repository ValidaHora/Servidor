package estiveaqui.appgestor.gerencia.usuario;

import org.joda.time.DateTimeZone;
import estiveaqui.appgestor.DadosGerenciaisInVO;

public class GerenciaAppUsuarioInVO extends DadosGerenciaisInVO
{
  private DateTimeZone tz = null;
  private int          idAppUsuario;
  private String       apelido;
  private String       idIntegracao;
  private int          maxLancamentosPorDia;

  public DateTimeZone getTz()
  {
    return tz;
  }

  public void setTz(DateTimeZone tz)
  {
    this.tz = tz;
  }

  public int getIdAppUsuario()
  {
    return idAppUsuario;
  }

  public void setIdAppUsuario(int idAppUsuario)
  {
    this.idAppUsuario = idAppUsuario;
  }

  public String getApelido()
  {
    return apelido;
  }

  public void setApelido(String apelido)
  {
    this.apelido = apelido;
  }

  public String getIdIntegracao()
  {
    return idIntegracao;
  }

  public void setIdIntegracao(String idIntegracao)
  {
    this.idIntegracao = idIntegracao;
  }

  public int getMaxLancamentosPorDia()
  {
    return maxLancamentosPorDia;
  }

  public void setMaxLancamentosPorDia(int maxLancamentosPorDia)
  {
    this.maxLancamentosPorDia = maxLancamentosPorDia;
  }

}
