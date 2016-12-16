package estiveaqui.sql.mo;

public class AppUsuarioMO implements MO
{
  private int    idAppUsuario         = 0;
  private int    idAppGestor          = 0;
  private String apelido              = "";
  private String identificador        = null;
  private String idIntegracao         = null;
  private String codAtivacao          = null;
  private int    status               = STATUS_HABILITADO;
  private int    maxLancamentosPorDia = 4;

  public static final int STATUS_HABILITADO   = 0;
  public static final int STATUS_DESABILITADO = 1;

  /**
   * @return the idAppUsuario
   */
  public int getIdAppUsuario()
  {
    return idAppUsuario;
  }

  /**
   * @param idAppUsuario
   *          the idAppUsuario to set
   */
  public void setIdAppUsuario(int idAppUsuario)
  {
    this.idAppUsuario = idAppUsuario;
  }

  /**
   * @return the idAppGestor
   */
  public int getIdAppGestor()
  {
    return idAppGestor;
  }

  /**
   * @param idAppGestor
   *          the idAppGestor to set
   */
  public void setIdAppGestor(int idAppGestor)
  {
    this.idAppGestor = idAppGestor;
  }

  /**
   * @return the apelido
   */
  public String getApelido()
  {
    return apelido;
  }

  /**
   * @param apelido
   *          the apelido to set
   */
  public void setApelido(String apelido)
  {
    this.apelido = (apelido == null ? "" : apelido);
  }

  /**
   * @return the identificador
   */
  public String getIdentificador()
  {
    return identificador;
  }

  /**
   * @param identificador
   *          the identificador to set
   */
  public void setIdentificador(String identificador)
  {
    this.identificador = identificador;
  }

  public String getIdIntegracao()
  {
    return idIntegracao;
  }

  public void setIdIntegracao(String idIntegracao)
  {
    this.idIntegracao = idIntegracao;
  }

  /**
   * @return the codAtivacao
   */
  public String getCodAtivacao()
  {
    return codAtivacao;
  }

  /**
   * @param codAtivacao
   *          the codAtivacao to set
   */
  public void setCodAtivacao(String codAtivacao)
  {
    this.codAtivacao = codAtivacao;
  }

  /**
   * @return the status
   */
  public int getStatus()
  {
    return status;
  }

  /**
   * @param status
   *          the status to set
   */
  public void setStatus(int status)
  {
    this.status = status;
  }

  /**
   * @return the maxLancamentosPorDia
   */
  public int getMaxLancamentosPorDia()
  {
    return maxLancamentosPorDia;
  }

  /**
   * @param maxLancamentosPorDia
   *          the maxLancamentosPorDia to set
   */
  public void setMaxLancamentosPorDia(int maxLancamentosPorDia)
  {
    this.maxLancamentosPorDia = maxLancamentosPorDia;
  }

}
