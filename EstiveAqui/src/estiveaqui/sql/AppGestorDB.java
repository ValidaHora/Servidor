package estiveaqui.sql;

import haroldo.util.sql.ConexaoDB;
import haroldo.util.sql.SqlDB;
import haroldo.util.sql.SqlUtil;
import haroldo.util.sql.SqlUtilMySql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import estiveaqui.sql.mo.AppGestorMO;

public class AppGestorDB extends SqlDB
{
  public static final String SELECT = 
      " APGS.IDAPPGESTOR, APGS.IDENTIFICADOR, APGS.STATUS, APGS.IDPARCEIRO, APGS.IDLANCAMENTOULTREL, APGS.EMAIL, APGS.SENHA,"
    + " APGS.DTCADASTRAMENTO, APGS.SENHAVENCIDA, APGS.CODVALIDACAOEMAIL, APGS.CODRECUPERASENHA, APGS.DTCODRECUPERASENHA ";

  public AppGestorDB(ConexaoDB conn)
  {
    super(conn);
  }

  /**
   * Insere um registro da tabela.<BR>
   * o campo IdAppGestor não é incluído no BD neste insert<BR> 
   * O MO appGestorMO, passado como parâmetro, é atualizado incluindo o IdAppGestor!
   * 
   * @param appGestorMO
   * @throws SQLException
   */
  public AppGestorMO insereRegistro(AppGestorMO appGestorMO) throws SQLException
  {
    //  Monta a query.
    String query = "INSERT INTO appgestor (IDENTIFICADOR, STATUS, IDLANCAMENTOULTREL, "
        + " EMAIL, SENHA, SENHAVENCIDA, CODVALIDACAOEMAIL, CODRECUPERASENHA, DTCODRECUPERASENHA)"
        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setString(1, appGestorMO.getIdentificadorAppGestor());
    stmt.setInt(2, appGestorMO.getStatus());
    stmt.setLong(3, appGestorMO.getIdUltimoLancamentoRelatorio());
    stmt.setString(4, appGestorMO.getEmail());
    stmt.setString(5, appGestorMO.getSenha());
    stmt.setInt(6, appGestorMO.getSenhaVencida());
    stmt.setString(7, appGestorMO.getCodValidacaoEMail());
    stmt.setString(8, appGestorMO.getCodRecuperaSenha());
    stmt.setDate(9, SqlUtil.toSqlDate(appGestorMO.getHrCodRecuperaSenha()));
    
    //  Inclui o registro na tabela.
    stmt.executeUpdate();

    //  Carrega o idAppGestor que foi gerado automaticamente durante o INSERT.
    appGestorMO.setIdAppGestor((int)SqlUtilMySql.leUltimoIdAutoIncremento(connDB));
    
    return appGestorMO;
  }
  
  /**
   * Lê um registro da tabela a partir do Idenficador
   * 
   * @param identificador
   * @return
   * @throws SQLException
   */
  public AppGestorMO buscaRegistroPK(int idAppGestor) throws SQLException
  {
    String query = "SELECT " + SELECT
        + "FROM appgestor APGS "
        + "WHERE IDAPPGESTOR = ? ";
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, idAppGestor);
    
    //  Executa a query
    ResultSet rs = stmt.executeQuery();
    
    if (!rs.next())
      return null;

    return preencheResultSet(rs);
  }

  /**
   * Lê um registro da tabela a partir do Idenficador
   * 
   * @param identificador
   * @return
   * @throws SQLException
   */
  public AppGestorMO buscaRegistroIdentificador(String identificador) throws SQLException
  {
    String query = "SELECT " + SELECT
        + "FROM appgestor APGS "
        + "WHERE BINARY IDENTIFICADOR = ? ";
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setString(1, identificador);
    //  Executa a query
    ResultSet rs = stmt.executeQuery();
    
    if (!rs.next())
      return null;
    
    return preencheResultSet(rs);
  }

  /**
   * Retorna o registro do AppGestorMO que utiliza o e-mail passado como parâmetro.
   *  
   * @param email
   * @return
   * @throws SQLException
   */
  public AppGestorMO buscaRegistroEmail(String email) throws SQLException
  {
    String query = "SELECT " + SELECT
        + "FROM appgestor APGS "
        + "WHERE EMAIL = ? ";
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setString(1, email);
    
    //  Executa a query
    ResultSet rs = stmt.executeQuery();
    
    if (!rs.next())
      return null;
    
    return preencheResultSet(rs);
  }

  /**
   * Retorna o registro do AppGestorMO que tem o código de recuperação de senha passado.
   * 
   * @param codRecuperacaoSenha
   * @return
   * @throws SQLException
   */
  public AppGestorMO buscaPorCodigoRecuperacaoSenha(String codRecuperacaoSenha) throws SQLException
  {
    String query = "SELECT " + SELECT
        + "FROM appgestor APGS "
        + "WHERE CodRecuperaSenha = ? ";
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setString(1, codRecuperacaoSenha);
    
    //  Executa a query
    ResultSet rs = stmt.executeQuery();
    
    if (!rs.next())
      return null;
    
    return preencheResultSet(rs);
  }
  
  /**
   * Valida um e-mail de um AppGestor a partir do seu código de validação.<BR>
   * Ao chamar este método, o registro do AppGestor é atualizado com código de validação atribuído com o valor nulo.
   * 
   * @param codigoValidacaoEmail
   * @return
   * @throws SQLException
   */
  public boolean validaEMail(String codigoValidacaoEmail) throws SQLException
  {
    //  Monta a query.
    String query = "UPDATE appgestor APGS SET CODVALIDACAOEMAIL = null WHERE CODVALIDACAOEMAIL = ?";
    
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setString(1, codigoValidacaoEmail);
    
    //  Atualiza a tabela.
    int cnt = stmt.executeUpdate();
    
    return (cnt > 0);

//    String query = "SELECT IdAppGestor "
//        + "FROM appgestor APGS "
//        + "WHERE CODVALIDACAOEMAIL = ? ";
//    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
//    stmt.setString(1, codigoValidacaoEmail);
//    
//    //  Executa a query
//    ResultSet rs = stmt.executeQuery();
//    
//    if (!rs.next())
//      return false;
    
  }
  
  /**
   * Atualiza o Identificador do AppEmpregado a partir do seu IdAppEmpregado.<BR>
   * (O Identificador é o códiogo de 40 dígitos que é utilizado para identificar o AppEmpregado no smartphone do cliente)<BR>
   * Atribui NULL para o código de ativação de e-mail. (Se conseguiu se logar, não precisa mais do código).
   * 
   * @param idAppGestor
   * @param identificadorApp
   * @throws SQLException
   */
  public void atualizaIdenficiador(int idAppGestor, String identificadorApp) throws SQLException
  {
    //  Monta a query.
    String query = "UPDATE appgestor APGS "
        + " SET IDENTIFICADOR = ? "
        + ",    CODVALIDACAOEMAIL = NULL "
        + " WHERE IDAPPGESTOR = ?";
    
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setString(1, identificadorApp);
    stmt.setInt(2, idAppGestor);
    
    //  Inclui o registro na tabela.
    stmt.executeUpdate();
  }
  
  /**
   * Atualiza o id do último lançamento que gerou relatório para um determinado idAppGestor.
   * 
   * @param idAppGestor
   * @param idUltimoLancamento
   * @throws SQLException
   */
  public void atualizaUltimoLancamento(int idAppGestor, long idUltimoLancamento) throws SQLException
  {
    //  Monta a query.
    String query = "UPDATE appgestor APGS SET IdLancamentoUltRel = ? WHERE IDAPPGESTOR = ?";
    
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setLong(1, idUltimoLancamento);
    stmt.setInt(2, idAppGestor);
    
    //  Inclui o registro na tabela.
    stmt.executeUpdate();
  }

  /**
   * Atualiza o e-mail e a senha de um AppGestor.<BR>
   * Atribui nulo ao código de recuperação de senha.
   * 
   * @param appGestorMO
   * @throws SQLException
   */
  public void atualizaEMail(AppGestorMO appGestorMO) throws SQLException
  {
    //  Monta a query.
    String query = "UPDATE appgestor APGS SET EMail = ?, Senha = ?, "
            + "   CODRECUPERASENHA = null, DTCODRECUPERASENHA = null"
            + " WHERE IDAPPGESTOR = ?";
    
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setString(1, appGestorMO.getEmail());
    stmt.setString(2, appGestorMO.getSenha());
    stmt.setInt(3, appGestorMO.getIdAppGestor());
    
    //  Inclui o registro na tabela.
    stmt.executeUpdate();
  }

  /**
   * Atualiza o código de recuperação de senha, sobrepondo o código anterior com um código novo.
   * <BR>Como o código de recuperação de senha é enviado para o e-mail do gestor,
   * o código de validação de senha também é atualizado. 
   * 
   * @param appGestorMO
   * @throws SQLException
   */
  public void atualizaCodigoEsqueciSenha(AppGestorMO appGestorMO) throws SQLException
  {
    //  Monta a query.
    String query = "UPDATE appgestor APGS "
        + " SET CODRECUPERASENHA = ?, DTCODRECUPERASENHA = NOW(), CODVALIDACAOEMAIL = NULL "
        + " WHERE IDAPPGESTOR = ?";
    
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setString(1, appGestorMO.getCodRecuperaSenha());
    stmt.setInt(2, appGestorMO.getIdAppGestor());
    
    //  Inclui o registro na tabela.
    stmt.executeUpdate();
  }
  
  /**
   * Verifica se o AppGestor tem histórico de usuários, passclocks ou lançamentos.
   * 
   * @param idAppGestor
   * @return
   * @throws SQLException
   */
  public boolean existeHistorico(int idAppGestor) throws SQLException
  {
    //
    //  Verifica se existem usuários deste gestor.
    String query = "SELECT COUNT(IDAPPGESTOR) USUARIOS "
        + "FROM appusuario "
        + "WHERE IDAPPGESTOR = ? ";

    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, idAppGestor);
    
    //  Executa a query
    ResultSet rs = stmt.executeQuery();
    if (!rs.next())
      return false;
    if (rs.getInt("USUARIOS") > 0)
      return true;
    
    //
    //  Verifica se existem passclocks deste gestor.
    query = "SELECT COUNT(IDAPPGESTOR) PASSCLOCKS "
        + "FROM passclock "
        + "WHERE IDAPPGESTOR = ? ";

    stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, idAppGestor);
    
    //  Executa a query
    rs = stmt.executeQuery();
    if (!rs.next())
      return false;
    if (rs.getInt("PASSCLOCKS") > 0)
      return true;

    return false;
  }
  
  /**
   * Preenche um AppGestorMO com os dados de um ResultSet.
   * 
   * @param rs
   * @return
   * @throws SQLException
   */
  private AppGestorMO preencheResultSet(ResultSet rs) throws SQLException
  {
    //  Lê os resultados.
    AppGestorMO appGestorMO = new AppGestorMO();

    appGestorMO.setIdAppGestor(rs.getInt("IDAPPGESTOR"));
    appGestorMO.setIdentificadorAppGestor(rs.getString("IDENTIFICADOR"));
    appGestorMO.setStatus(rs.getInt("STATUS"));
    appGestorMO.setIdParceiro(rs.getInt("IDPARCEIRO"));
    appGestorMO.setIdUltimoLancamentoRelatorio(rs.getLong("IDLANCAMENTOULTREL"));
    appGestorMO.setEmail(rs.getString("EMAIL"));
    appGestorMO.setSenha(rs.getString("SENHA"));
    appGestorMO.setDataCadastramento(SqlUtil.fromSqlDate(rs.getDate("DTCADASTRAMENTO")));
    appGestorMO.setSenhaVencida(rs.getInt("SENHAVENCIDA"));
    appGestorMO.setCodValidacaoEMail(rs.getString("CODVALIDACAOEMAIL"));
    appGestorMO.setCodRecuperaSenha(rs.getString("CODRECUPERASENHA"));
    appGestorMO.setHrCodRecuperaSenha(SqlUtil.fromSqlDate(rs.getDate("DTCODRECUPERASENHA")));

    return appGestorMO;
  }
}
