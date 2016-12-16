package estiveaqui;

/**
 * Define o código de erro.
 * 
 * @author Haroldo
 *
 */
public enum CodigoErro
{
  /** Erro genérico que não deve ocorrer em produção. Falha de programação ou de passagem de parâmetros */
  ERRO_INTERNO(1, "Erro interno"),
  VERSAO_NAO_DISPONIVEL(2, "Versão não disponível"),
  VERSAO_INVALIDA(3, "Versão inválida"),
  DESABILITA_APP_GESTOR(4, "AppGestor desabilitado"),
  
  /** Código já lançado para um PassClock ou hora manual já lançada para um AppUsuario */ 
  CODIGO_JA_LANCADO(102, "Código já lançado"),
  APPUSUARIO_NAO_HABILITADO(101, "AppUsuario não está habilitado para uso"),
  APPGESTOR_NAO_HABILITADO(102, "AppGestor não está habilitado para uso"),
  HASHCODIGO_INVALIDO(103, "Hashcodigo inválido"),
  PASSCLOCK_NAO_EXISTE(106, "PassClock não existe"),
  PASSCLOCK_DESABILITADO(107, "PassClock está desabilitado"),
  MAX_LANCAMENTOS_DIARIO(108, "Sem saldo para lançar horas"),
  NAO_HA_RELATORIOS(109, "Não existem relatórios"),
  NAO_HA_RELATORIOS_NOVOS(110, "Não existem novos relatórios para serem gerados"),
  SENHA_INCORRETA(111, "Senha incorreta"),
  SENHA_INVALIDA(112, "Senha inválida"),
  SENHA_VENCIDA(113, "Senha vencida"),
  EMAIL_JA_CADASTRADO(114, "E-Mail já cadastrado"),
  EMAIL_NAO_CADASTRADO(115, "E-Mail não cadastrado"),
  EMAIL_NAO_VALIDADO(116, "E-Mail ainda não validado"),
  EMAIL_JA_VALIDADO(117, "E-Mail já validado"),
  CODIGO_INVALIDO(118, "Código não é válido"),
  /** Código que não é mais válido por não ter sido usado dentro do período de tempo de sua validade.   */
  CODIGO_VENCIDO(119, "Código perdeu a validade"),
  GESTOR_SEM_HISTORICO(130, "Gestor sem histórico de lançamentos"),
  /** PassClock pertence a outro gestor, não podendo ser cadastrado para o gestor atual */
  PASSCLOCK_DE_OUTRO_GESTOR(131, "PassClock pertence a outro gestor"),
  /** Um apelido com o mesmo nome já está cadastrado para este AppGestor */
  APELIDO_JA_CADASTRADO(132, "Apelido já está cadastrado"),
  /** Uma identificação externa já foi cadastrada para este AppGestor */
  IDENTIFICACAO_JA_CADASTRADA(133, "Identificação externa já cadastrada"),

  
  PASSCLOCK_JA_EXISTE(134, "PassClock já existe"),
  ;

  
  private int codigoErro;
  private String  descricao;
  
  CodigoErro(int codigoErro, String descricao)
  {
    this.codigoErro = codigoErro;
    this.descricao = descricao;
  }

  /**
   * Retorna o código de erro em um número inteiro.
   * 
   * @return
   */
  public int getCodigoErro()
  {
    return codigoErro;
  }
  
 /**
   * Apresenta um texto descritivo do código do erro.
   * 
   * @param codErro
   * @return
   */
  public String getDescricao()
  {
    return descricao;
  }
}
