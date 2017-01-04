package estiveaqui.servlet;

import estiveaqui.dados.ChaveJSON;

/**
 * Parâmetros passados para o EstiveAqui via requisição de HTTP.
 * 
 * @author Haroldo
 *
 */
public enum NomeParametroServlet
{
  Versao("V"),
  NumeroPassClock(ChaveJSON.PC.toString()),
  ApelidoPassClock(ChaveJSON.AP.toString()),
  Codigo(ChaveJSON.CD.toString()),
  IdentificacaoAppUsuario(ChaveJSON.IA.toString()),
  ApelidoAppUsuario(ChaveJSON.AU.toString()),
  TimeZone(ChaveJSON.TZ.toString()),
  TimeZoneName(ChaveJSON.ZZ.toString()),
  Nota(ChaveJSON.NT.toString()),
  CodigoAtivacao(ChaveJSON.CA.toString()),
  IdLancamento(ChaveJSON.IL.toString()),
  IdDispositivo(ChaveJSON.DI.toString()),
  IdIntegracao(ChaveJSON.II.toString()),
  IdAppUsuario(ChaveJSON.IU.toString()),
  Latitude(ChaveJSON.LA.toString()),
  Longitude(ChaveJSON.LO.toString()),
  HoraLancada(ChaveJSON.HL.toString()),
  HoraDigitada(ChaveJSON.HD.toString()),
  HoraEnviada(ChaveJSON.HE.toString()),
  HoraManual(ChaveJSON.HM.toString()),
  
  Lancamentos(ChaveJSON.LNS.toString()),
  
  Acao(ChaveJSON.ACAO.toString()),
  
  IdentificacaoAppGestor(ChaveJSON.IG.toString()),
  MaxLancamentosDia(ChaveJSON.XL.toString()),
  
  Senha(ChaveJSON.SE.toString()),
  Email(ChaveJSON.EM.toString()),
  CodigoValidacaoEmail(ChaveJSON.VE.toString()),
  IdUltimoLancamento(ChaveJSON.UL.toString()),
  SenhaNova(ChaveJSON.SN.toString()),
  CodigoRecuperacaoSenha(ChaveJSON.CS.toString()),
  
  //  Da API do ValidaHora. Para chamada ao ValidaHora.
  VHCliente("CLI"),
  VHSenha("SEN"),
  VHToken("TOK"),
  VHCodigoToken("COD"),
  VHHoraEnviada("HEN"),
  VHTokens("TOKS"),
  VHLancamentos("LANS"),
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
