package estiveaqui.vo;

import estiveaqui.Versao;

public class DadosInVO
{
  private Versao versao = null;
  private String ip = "";
  private String servidor = "";

  public Versao getVersao()
  {
    return versao;
  }

  public void setVersao(Versao versao)
  {
    this.versao = versao;
  }

  public String getIp()
  {
    return ip;
  }

  public void setIp(String ip)
  {
    this.ip = ip;
  }

  public String getServidor()
  {
    return servidor;
  }

  public void setServidor(String servidor)
  {
    this.servidor = servidor;
  }
}
