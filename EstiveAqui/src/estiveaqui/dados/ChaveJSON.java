package estiveaqui.dados;

public enum ChaveJSON
{
  /** Id do JSON */
  ID("ID"),
  /** Define se a resposta da mensagem é Ok ou com erro */
  @Deprecated
  OK0("ValidadoOk"),
  /** Nova versão. Define se a resposta da mensagem é Ok ou com erro */
  OK("OK"),
  /** Código de erro  antigo */
  @Deprecated
  CE0("Codigo"),
  /** Código de erro */
  CE("CE"),
  /** Descrição da mensagem de erro */
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

  /** Apelido do AppUsuário */
  @Deprecated
  AU0("Apelido"),
  /** Identificador do AppUsuario */
  @Deprecated
  IA0("IdentificadorAppUsuario"),
  /** Código Lançado */
  @Deprecated
  CD0("ChaveJSON"),
  /** Lançamento */
  @Deprecated
  LN0("Lancamento"),
  /** Hora Lançada */
  @Deprecated
  HL0("HoraLancada"),
  /** Time Zone do cliente */
  @Deprecated
  TZ0("TimeZone"),
  /** Lista de lançamentos */
  @Deprecated
  LNS0("Lancamentos"),
  /** Lista de PassClocks */
  @Deprecated
  PCS0("PassClocks"),

  /** Apelido do AppUsuário */
  AU("AU"),
  /** Apelido do PassClock */
  AP("AP"),
  /** Código de ativação AppUsuario */
  CA("CA"),
  /** Código Lançado */
  CD("CD"),
  /** Código de recuperação de senha */
  CS("CS"),
  /** Código de ativação virtual */
  CV("CV"),
  /** E-mail validado */
  EH("EH"),
  /** Erro - Objeto que inclui código de erro e mensagem de erro. */
  ER("ER"),
  /** Existe historico */
  EV("EV"),
  /** Hash Code */
  HC("HC"),
  /** Hora Digitada */
  HD("HD"),
  /** Hora Enviada */
  HE("HE"),
  /** Hora Lançada */
  HL("HL"),
  /** Hora Digitada com TZ */
  @Deprecated
  HDX("HDX"),
  /** Hora Enviada com TZ */
  @Deprecated
  HEX("HEX"),
  /** Hora Lançada com TZ */
  @Deprecated
  HLX("HLX"),
  /** Identidificação do Dispositivo */
  DI("DI"),
  /** Identificador do AppUsuario */
  IA("IA"),
  /** Id interno do AppUsuario */
  IU("IU"),
  /** Identificação do Lançamento */
  IL("IL"),
  /** Identificador do AppGestor */
  IG("IG"),
  /** Id de integração do AppUsuario com sistemas externos */
  II("II"),
  /** Latitude */
  LA("LA"),
  /** Lançamento */
  LN("LN"),
  /** Longitude */
  LO("LO"),
  /** Mês */
  MS("MS"),
  /** Nome do arquivo */
  NA("NA"),
  /** Nota */
  NT("NT"),
  /** Número do PassClock */
  PC("PC"),
  /** Status */
  ST("ST"),
  /** Time Zone do cliente */
  TZ("TZ"),
  /** URL dos Relatórios */
  UR("UR"),
  /** Dados de um usuário */
  US("US"),
  /** Quantidade máxima de lançamentos de um AppUsuario por dia */
  XL("XL"),
  
  /** Lista de lançamentos */
  LNS("LNS"),
  /** Lista de PassClocks */
  PCS("PCS"),
  /** Lista de relatórios */
  RLS("RLS"),
  /** Lista de usuários */
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
