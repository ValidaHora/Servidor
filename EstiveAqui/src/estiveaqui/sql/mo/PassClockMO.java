package estiveaqui.sql.mo;

import org.joda.time.DateTimeZone;


public class PassClockMO implements MO
{
  private int    idPassClock        = 0;
  private String numPassClock       = "";
  private String apelido            = null;
  private int    idAppGestor        = 0;
  private int    status             = STATUS_HABILITADO;
  private DateTimeZone  tz          = null;
  private String senhaCadastro      = "";
  private String codAtivacaoVirtual = null;

  public static final int STATUS_HABILITADO = 0;
  public static final int STATUS_DESABILITADO = 1;
  public static final int STATUS_CADASTRADO = 2;
  public static final int STATUS_RECEBIDO = 3;
  public static final int STATUS_ENTREGUE = 4;
  public static final int STATUS_CANCELADO = 5;
  
  public static final int COD_ALGORITMO_SEAMON = 1;
  public static final int COD_ALGORITMO_FEITIAN = 2;
  public static final int COD_ALGORITMO_VIRTUAL = 3;
  public static final int COD_ALGORITMO_TESTE = 1000;
  
  /**
   * Para encontrar PassClocks em um ArrayList. 
   */
  @Override
  public boolean equals(Object passClock)
  {
    if (passClock == null)
      return false;
    if (passClock instanceof String)
      return passClock.equals(this.numPassClock);
    if (!(passClock instanceof PassClockMO))
      return false;
    
    return ((PassClockMO)passClock).numPassClock.equals(this.numPassClock);
  }
  
  @Override
  public int hashCode()
  {
    return this.numPassClock.hashCode();
  }

  public int getIdPassClock()
  {
    return idPassClock;
  }

  public String getNumPassClock()
  {
    return numPassClock;
  }

  public void setNumPassClock(String numPassClock)
  {
    this.numPassClock = (numPassClock == null ? "" : numPassClock);
  }

  public void setIdPassClock(int idPassClock)
  {
    this.idPassClock = idPassClock;
  }

  public String getApelido()
  {
    return apelido;
  }

  public void setApelido(String apelido)
  {
    this.apelido = apelido;
  }

  public int getIdAppGestor()
  {
    return idAppGestor;
  }

  public void setIdAppGestor(int idAppGestor)
  {
    this.idAppGestor = idAppGestor;
  }

  public int getStatus()
  {
    return status;
  }

  public void setStatus(int status)
  {
    this.status = status;
  }

  public DateTimeZone getTz()
  {
    return tz;
  }

  public void setTz(DateTimeZone tz)
  {
    this.tz = tz;
  }

  public String getSenhaCadastro()
  {
    return senhaCadastro;
  }

  public void setSenhaCadastro(String senha)
  {
    this.senhaCadastro = senha;
  }

  public String getCodAtivacaoVirtual()
  {
    return codAtivacaoVirtual;
  }

  public void setCodAtivacaoVirtual(String codAtivacaoVirtual)
  {
    this.codAtivacaoVirtual = codAtivacaoVirtual;
  }
}
