package estiveaqui.sql.mo;

import org.joda.time.DateTime;

public class AppGestorMO implements MO
{
  private int             idAppGestor                 = 0;
  private int             status                      = STATUS_HABILITADO;
  private String          identificadorAppGestor      = "";
  private int             idParceiro    = 0;
  private long            idUltimoLancamentoRelatorio = 0;
  private String          email                       = "";
  private String          senha                       = "";
  private DateTime        dataCadastramento;
  private int             senhaVencida                = 1;
  private String          codValidacaoEMail           = null;
  private String          codRecuperaSenha             = null;
  private DateTime        hrCodRecuperaSenha           = null;  

  public static final int STATUS_HABILITADO           = 0;
  public static final int STATUS_CANCELADO            = 1;

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

  public String getIdentificadorAppGestor()
  {
    return identificadorAppGestor;
  }

  public void setIdentificadorAppGestor(String identificadorAppGestor)
  {
    this.identificadorAppGestor = identificadorAppGestor;
  }

  public int getIdParceiro()
  {
    return idParceiro;
  }

  public void setIdParceiro(int idParceiro)
  {
    this.idParceiro = idParceiro;
  }

  public long getIdUltimoLancamentoRelatorio()
  {
    return idUltimoLancamentoRelatorio;
  }

  public void setIdUltimoLancamentoRelatorio(long idUltimoLancamentoRelatorio)
  {
    this.idUltimoLancamentoRelatorio = idUltimoLancamentoRelatorio;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getSenha()
  {
    return senha;
  }

  public void setSenha(String senha)
  {
    this.senha = senha;
  }

  public DateTime getDataCadastramento()
  {
    return dataCadastramento;
  }

  public void setDataCadastramento(DateTime dataCadastramento)
  {
    this.dataCadastramento = dataCadastramento;
  }

  public int getSenhaVencida()
  {
    return senhaVencida;
  }

  public void setSenhaVencida(int senhaVencida)
  {
    this.senhaVencida = senhaVencida;
  }

  public String getCodValidacaoEMail()
  {
    return codValidacaoEMail;
  }

  public void setCodValidacaoEMail(String codValidacaoEMail)
  {
    this.codValidacaoEMail = codValidacaoEMail;
  }

  public String getCodRecuperaSenha()
  {
    return codRecuperaSenha;
  }

  public void setCodRecuperaSenha(String codRecuperaSenha)
  {
    this.codRecuperaSenha = codRecuperaSenha;
  }

  public DateTime getHrCodRecuperaSenha()
  {
    return hrCodRecuperaSenha;
  }

  public void setHrCodRecuperaSenha(DateTime hrCodRecuperaSenha)
  {
    this.hrCodRecuperaSenha = hrCodRecuperaSenha;
  }
}
