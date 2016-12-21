package estiveaqui.servlet;

/**
 * Parâmetros passados para o EstiveAqui via requisição de HTTP.
 * 
 * @author Haroldo
 *
 */
public enum NomeParametroServlet
{
  Versao("V"),
  NumeroPassClock("PC"),
  Codigo("CD"),
  IdentificacaoApp("ID"),
  Cliente("CL"),
  Senha("SH"),
  TimeZone("TZ"),
  Nota("NT"),
  CodigoAtivacao("CA"),
  IdDispositivo("DS"),
  Latitude("LA"),
  Longitude("LO"),
  HoraLancada("HL"),
  HoraDigitada("HD"),
  HoraEnviada("HE")
  ;

  private String  nome;
  
  NomeParametroServlet(String nome)
  {
    this.nome = nome;
  }

  public String getNome()
  {
    return nome;
  }
  
  @Override
  public String toString()
  {
    return nome;
  }
}
