package estiveaqui.dados;

public enum ChaveJSON
{
  /** Id do JSON */
  ID("ID"),
  /** Define se a resposta da mensagem � Ok ou com erro */
  @Deprecated
  OK0("ValidadoOk"),
  /** Nova vers�o. Define se a resposta da mensagem � Ok ou com erro */
  OK("OK"),
  /** C�digo de erro  antigo */
  @Deprecated
  CE0("Codigo"),
  /** C�digo de erro */
  CE("CE"),
  /** Descri��o da mensagem de erro */
  DE("DE"),
  /** Mensagem de erro detalhada */
  LG("LG"),
  /** Mensagem de erro ANTIGA */
  @Deprecated
  ME0("Mensagem"),
  /** Mensagem de erro */
  ME("ME"),
  /** Lista com mensagens de erro */
  MES("MES"),
  @Deprecated
  /** Lista com mensagens de erro */
  MES0("Mensagens"),

  /** Apelido do AppUsu�rio */
  @Deprecated
  AU0("Apelido"),
  /** Identificador do AppUsuario */
  @Deprecated
  IA0("IdentificadorAppUsuario"),
  /** C�digo Lan�ado */
  @Deprecated
  CD0("ChaveJSON"),
  /** Lan�amento */
  @Deprecated
  LN0("Lancamento"),
  /** Hora Lan�ada */
  @Deprecated
  HL0("HoraLancada"),
  /** Time Zone do cliente */
  @Deprecated
  TZ0("TimeZone"),
  /** Lista de lan�amentos */
  @Deprecated
  LNS0("Lancamentos"),
  /** Lista de PassClocks */
  @Deprecated
  PCS0("PassClocks"),

  /** Apelido do AppUsu�rio */
  AU("AU"),
  /** Apelido do PassClock */
  AP("AP"),
  /** C�digo de ativa��o AppUsuario */
  CA("CA"),
  /** C�digo Lan�ado */
  CD("CD"),
  /** C�digo de recupera��o de senha */
  CS("CS"),
  /** C�digo de ativa��o virtual */
  CV("CV"),
  /** E-mail validado */
  EH("EH"),
  /** Erro - Objeto que inclui c�digo de erro e mensagem de erro. */
  ER("ER"),
  /** Existe historico */
  EV("EV"),
  /** Hash Code */
  HC("HC"),
  /** Hora Digitada */
  HD("HD"),
  /** Hora Enviada */
  HE("HE"),
  /** Hora Lan�ada */
  HL("HL"),
  /** Hora Digitada com TZ */
  @Deprecated
  HDX("HDX"),
  /** Hora Enviada com TZ */
  @Deprecated
  HEX("HEX"),
  /** Hora Lan�ada com TZ */
  @Deprecated
  HLX("HLX"),
  /** Identidifica��o do Dispositivo */
  DI("DI"),
  /** Identificador do AppUsuario */
  IA("IA"),
  /** Id interno do AppUsuario */
  IU("IU"),
  /** Identifica��o do Lan�amento */
  IL("IL"),
  /** Identificador do AppGestor */
  IG("IG"),
  /** Id de integra��o do AppUsuario com sistemas externos */
  II("II"),
  /** Latitude */
  LA("LA"),
  /** Lan�amento */
  LN("LN"),
  /** Longitude */
  LO("LO"),
  /** M�s */
  MS("MS"),
  /** Nome do arquivo */
  NA("NA"),
  /** Nota */
  NT("NT"),
  /** N�mero do PassClock */
  PC("PC"),
  /** Status */
  ST("ST"),
  /** Time Zone do cliente */
  TZ("TZ"),
  /** URL dos Relat�rios */
  UR("UR"),
  /** Dados de um usu�rio */
  US("US"),
  /** Quantidade m�xima de lan�amentos de um AppUsuario por dia */
  XL("XL"),
  
  /** Lista de lan�amentos */
  LNS("LNS"),
  /** Lista de PassClocks */
  PCS("PCS"),
  /** Lista de relat�rios */
  RLS("RLS"),
  /** Lista de usu�rios */
  USS("USS"),
  
  /** Objeto PassClock */
  PCO("PCO"),
  
  /** Algoritmo do token */
  AL("AL"),
  /** Semente do token */
  SM("SM"),
  /** Sementes do token */
  SMS("SMS"),
  ;

  private String codigo;

  private ChaveJSON(String codigo)
  {
    this.codigo = codigo;
  }

  @Override
  public String toString()
  {
    return codigo;
  }
}
