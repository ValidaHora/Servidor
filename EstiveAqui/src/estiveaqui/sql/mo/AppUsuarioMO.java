package estiveaqui.sql.mo;

import org.joda.time.DateTimeZone;

public class AppUsuarioMO implements MO
{
  private int    idAppUsuario         = 0;
  private int    idAppGestor          = 0;
  private String apelido              = "";
  private String identificador        = null;
  private DateTimeZone  tz            = null;
  private String idIntegracao         = null;
  private String codAtivacao          = null;
  private int    status               = STATUS_HABILITADO;
  private int    maxLancamentosPorDia = 4;

  public static final int STATUS_HABILITADO   = 0;
  public static final int STATUS_DESABILITADO = 1;

  public int getIdAppUsuario()
  {
    return idAppUsuario;
  }

  public void setIdAppUsuario(int idAppUsuario)
  {
    this.idAppUsuario = idAppUsuario;
  }

  public int getIdAppGestor()
  {
    return idAppGestor;
  }

  public void setIdAppGestor(int idAppGestor)
  {
    this.idAppGestor = idAppGestor;
  }

  public String getApelido()
  {
    return apelido;
  }

  public void setApelido(String apelido)
  {
    this.apelido = apelido;
  }

  public String getIdentificador()
  {
    return identificador;
  }

  public void setIdentificador(String identificador)
  {
    this.identificador = identificador;
  }

  public DateTimeZone getTz()
  {
    return tz;
  }

  public void setTz(DateTimeZone tz)
  {
    this.tz = tz;
  }

  public String getIdIntegracao()
  {
    return idIntegracao;
  }

  public void setIdIntegracao(String idIntegracao)
  {
    this.idIntegracao = idIntegracao;
  }

  public String getCodAtivacao()
  {
    return codAtivacao;
  }

  public void setCodAtivacao(String codAtivacao)
  {
    this.codAtivacao = codAtivacao;
  }

  public int getStatus()
  {
    return status;
  }

  public void setStatus(int status)
  {
    this.status = status;
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
