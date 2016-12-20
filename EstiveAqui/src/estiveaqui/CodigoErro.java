package estiveaqui;

/**
 * Define o c�digo de erro.
 * 
 * @author Haroldo
 *
 */
public enum CodigoErro
{
  /** Erro gen�rico que n�o deve ocorrer em produ��o. Falha de programa��o ou de passagem de par�metros */
  ERRO_INTERNO(1, "Erro interno"),
  VERSAO_NAO_DISPONIVEL(2, "Vers�o n�o dispon�vel"),
  VERSAO_INVALIDA(3, "Vers�o inv�lida"),
  DESABILITA_APP_GESTOR(4, "AppGestor desabilitado"),
  
  /** C�digo j� lan�ado para um PassClock ou hora manual j� lan�ada para um AppUsuario */ 
  CODIGO_JA_LANCADO(102, "C�digo j� lan�ado"),
  APPUSUARIO_NAO_HABILITADO(101, "AppUsuario n�o est� habilitado para uso"),
  APPGESTOR_NAO_HABILITADO(102, "AppGestor n�o est� habilitado para uso"),
  HASHCODIGO_INVALIDO(103, "Hashcodigo inv�lido"),
  PASSCLOCK_NAO_EXISTE(106, "PassClock n�o existe"),
  PASSCLOCK_DESABILITADO(107, "PassClock est� desabilitado"),
  MAX_LANCAMENTOS_DIARIO(108, "Sem saldo para lan�ar horas"),
  NAO_HA_RELATORIOS(109, "N�o existem relat�rios"),
  NAO_HA_RELATORIOS_NOVOS(110, "N�o existem novos relat�rios para serem gerados"),
  SENHA_INCORRETA(111, "Senha incorreta"),
  SENHA_INVALIDA(112, "Senha inv�lida"),
  SENHA_VENCIDA(113, "Senha vencida"),
  EMAIL_JA_CADASTRADO(114, "E-Mail j� cadastrado"),
  EMAIL_NAO_CADASTRADO(115, "E-Mail n�o cadastrado"),
  EMAIL_NAO_VALIDADO(116, "E-Mail ainda n�o validado"),
  EMAIL_JA_VALIDADO(117, "E-Mail j� validado"),
  CODIGO_INVALIDO(118, "C�digo n�o � v�lido"),
  /** C�digo que n�o � mais v�lido por n�o ter sido usado dentro do per�odo de tempo de sua validade.   */
  CODIGO_VENCIDO(119, "C�digo perdeu a validade"),
  GESTOR_SEM_HISTORICO(130, "Gestor sem hist�rico de lan�amentos"),
  /** PassClock pertence a outro gestor, n�o podendo ser cadastrado para o gestor atual */
  PASSCLOCK_DE_OUTRO_GESTOR(131, "PassClock pertence a outro gestor"),
  /** Um apelido com o mesmo nome j� est� cadastrado para este AppGestor */
  APELIDO_JA_CADASTRADO(132, "Apelido j� est� cadastrado"),
  /** Uma identifica��o externa j� foi cadastrada para este AppGestor */
  IDENTIFICACAO_JA_CADASTRADA(133, "Identifica��o externa j� cadastrada"),

  
  PASSCLOCK_JA_EXISTE(134, "PassClock j� existe"),
  ;

  
  private int codigoErro;
  private String  descricao;
  
  CodigoErro(int codigoErro, String descricao)
  {
    this.codigoErro = codigoErro;
    this.descricao = descricao;
  }

  /**
   * Retorna o c�digo de erro em um n�mero inteiro.
   * 
   * @return
   */
  public int getCodigoErro()
  {
    return codigoErro;
  }
  
 /**
   * Apresenta um texto descritivo do c�digo do erro.
   * 
   * @param codErro
   * @return
   */
  public String getDescricao()
  {
    return descricao;
  }
}
