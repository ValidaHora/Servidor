package estiveaqui.sql;

import haroldo.util.sql.ConexaoDB;
import haroldo.util.sql.SqlDB;
import haroldo.util.sql.SqlUtilMySql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import estiveaqui.sql.mo.AppUsuarioMO;

public class AppUsuarioDB extends SqlDB
{
  static final String SELECT = "APUS.IdAppUsuario, APUS.IdAppGestor, APUS.Apelido, APUS.Identificador, "
                + "APUS.IdIntegracao, APUS.CodAtivacao, APUS.Status, APUS.MaxLancamentosDia "; 

  public AppUsuarioDB(ConexaoDB conn)
  {
    super(conn);
  }
  
  /**
   * Busca todos os AppUsuario de um detereminado AppGestor.
   * 
   * @param idAppGestor
   * @return
   * @throws SQLException
   */
  public AppUsuarioMO leRegistroPK(int idAppUsuario) throws SQLException
  {
    String query = "SELECT " + SELECT 
                    + "FROM appusuario APUS "
                    + "WHERE APUS.IDAPPUSUARIO = ? "
                    ;
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, idAppUsuario);

    //  Executa a query
    ResultSet rs = stmt.executeQuery();

    if (!rs.next())
      return null;

    return preencheResultSet(rs);
  }

  /**
   * Busca todos os AppUsuario de um detereminado AppGestor.
   * 
   * @param idAppGestor
   * @return
   * @throws SQLException
   */
  public ArrayList<AppUsuarioMO> leRegistrosGestor(int idAppGestor) throws SQLException
  {
    String query = "SELECT " + SELECT 
                    + "FROM appusuario APUS "
                    + "WHERE APUS.IDAPPGESTOR = ? "
                    ;
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, idAppGestor);

    //  Executa a query
    ResultSet rs = stmt.executeQuery();

    //  Lê os resultados.
    ArrayList<AppUsuarioMO> appUsuariosMO = new ArrayList<AppUsuarioMO>();
    while (rs.next())
    {
      appUsuariosMO.add(preencheResultSet(rs));
    }

    return appUsuariosMO;
  }
  
  /**
   * Insere um novo usuário no BD. 
   * 
   * @param usuarioMO
   * @return
   * @throws SQLException
   */
  public AppUsuarioMO insereRegistro(AppUsuarioMO appUsuarioMO) throws SQLException
  {
    //  Monta a query.
    String query = "INSERT INTO appusuario (IDAPPGESTOR, APELIDO, IDENTIFICADOR, IDINTEGRACAO, CODATIVACAO, STATUS, MaxLancamentosDia) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, appUsuarioMO.getIdAppGestor());
    stmt.setString(2, appUsuarioMO.getApelido());
    stmt.setString(3, appUsuarioMO.getIdentificador());
    stmt.setString(4, appUsuarioMO.getIdIntegracao());
    stmt.setString(5, appUsuarioMO.getCodAtivacao());
    stmt.setInt(6, appUsuarioMO.getStatus());
    stmt.setInt(7, appUsuarioMO.getMaxLancamentosPorDia());

    //  Inclui o registro na tabela.
    stmt.executeUpdate();

    //  Carrega o idAppUsuario que foi gerado automaticamente durante o INSERT.
    appUsuarioMO.setIdAppUsuario((int)SqlUtilMySql.leUltimoIdAutoIncremento(connDB));
    
    return appUsuarioMO;
  }
  
  /**
   * Atualiza o registro de um usuário.
   * 
   * @param appUsuarioMO
   * @throws SQLException
   */
  public AppUsuarioMO atualizaRegistroPK(AppUsuarioMO appUsuarioMO) throws SQLException
  {
    //  Monta a query.
    String query = "UPDATE appusuario "
        + " SET APELIDO = ?,  CODATIVACAO = ?, IDINTEGRACAO = ?, MAXLANCAMENTOSDIA = ? "
        + " WHERE IDAPPUSUARIO = ? ";
    
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setString(1, appUsuarioMO.getApelido());
    stmt.setString(2, appUsuarioMO.getCodAtivacao());
    stmt.setString(3, appUsuarioMO.getIdIntegracao());
    stmt.setInt(4, appUsuarioMO.getMaxLancamentosPorDia());
    stmt.setInt(5, appUsuarioMO.getIdAppUsuario());
    
    //  Inclui o registro na tabela.
    stmt.executeUpdate();
    
    return leRegistroPK(appUsuarioMO.getIdAppUsuario());
  }
  
  /**
   * Apaga um registro usando a sua chave primária.
   * 
   * @param idAppUsuario
   * @throws SQLException
   */
  public void apagaRegistroPK(int idAppUsuario) throws SQLException
  {
    String query = "DELETE FROM appusuario WHERE IDAPPUSUARIO = ? ";
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, idAppUsuario);

    stmt.executeUpdate();
}
  
  /**
   * 
   * @param identificadorAppUsuario
   * @return
   * @throws SQLException
   */
  public AppUsuarioMO buscaRegistroIdentificador(String identificadorAppUsuario) throws SQLException
  {
    String query = "SELECT " + SELECT 
        + "FROM appusuario APUS "
        + "WHERE BINARY Identificador = ? "
        ;
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setString(1, identificadorAppUsuario);
    //  Executa a query
    ResultSet rs = stmt.executeQuery();
    
    if (!rs.next())
      return null;

    //  Lê os resultados.
    return preencheResultSet(rs);
  }
  
  /**
   * Busca o código de ativação para cadastramento de AppUsuario.
   * 
   * @param codAtivacao
   * @return
   * @throws SQLException
   */
  public AppUsuarioMO buscaCodigoAtivacao(String codAtivacao) throws SQLException
  {
    String query = "SELECT " + SELECT 
        + "FROM appusuario APUS "
        + "WHERE BINARY CodAtivacao = ? "
        ;
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setString(1, codAtivacao);
    //  Executa a query
    ResultSet rs = stmt.executeQuery();
    
    if (!rs.next())
      return null;

    return preencheResultSet(rs);
  }

  /**
   * Atribui valor nulo para a coluna CODATIVACAO da table APPUSUARIO a partir da sua chave primária idAppUsuario.
   * 
   * @param idAppUsuario
   * @return
   * @throws SQLException
   */
  public AppUsuarioMO habilitaAppUsuario(int idAppUsuario, String identificador) throws SQLException
  {
    //  Monta a query.
    String query = "UPDATE appusuario "
        + " SET IDENTIFICADOR = ? "
        + ",    CODATIVACAO = null "
        + ",    STATUS = " + AppUsuarioMO.STATUS_HABILITADO
        + " WHERE IDAPPUSUARIO = ? ";
    
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setString(1, identificador);
    stmt.setInt(2, idAppUsuario);
    
    //  Inclui o registro na tabela.
    stmt.executeUpdate();
    
    return leRegistroPK(idAppUsuario);
  }
  
  /**
   * Atualiza identificacao do AppUsuario juntamente com o CODATIVACAO.
   * Este método é chamado quando o AppGestor vai gerar um novo SMS para envio ao Usuário.
   * 
   * @param idAppUsuario
   * @param identificador
   * @param codAtivacao
   * @return
   * @throws SQLException
   */
  public AppUsuarioMO atualizaAppUsuarioIdentificacao(int idAppUsuario, String identificador, String codAtivacao) throws SQLException
  {
    //  Monta a query.
    String query = "UPDATE appusuario "
                    + " SET IDENTIFICADOR = ? "
                    + ",    CODATIVACAO = ? "
                    + " WHERE IDAPPUSUARIO = ? ";
    
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setString(1, identificador);
    stmt.setString(2, codAtivacao);
    stmt.setInt(3, idAppUsuario);
    
    //  Inclui o registro na tabela.
    stmt.executeUpdate();
    
    return leRegistroPK(idAppUsuario);
  }

  /**
   * Desabilita o usuário identificado pelo seu 'idAppUsuario'.
   * 
   * @param idAppUsuario
   * @param codAtivacao
   * @return
   * @throws SQLException
   */
  public AppUsuarioMO desabilitaAppUsuario(int idAppUsuario, String codAtivacao) throws SQLException
  {
    //  Monta a query.
    String query = "UPDATE appusuario "
                    + " SET CODATIVACAO = ? "
                    + ",    IDENTIFICADOR = NULL "
                    + ",    STATUS = " + AppUsuarioMO.STATUS_DESABILITADO 
                    + " WHERE IDAPPUSUARIO = ? ";
    
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setString(1, codAtivacao);
    stmt.setInt(2, idAppUsuario);

    //  Inclui o registro na tabela.
    stmt.executeUpdate();
    
    return leRegistroPK(idAppUsuario);
  }
  
  /**
   * Preenche o AppUsuarioMO com os dados de um ResultSet.
   * 
   * @param rs
   * @return
   * @throws SQLException
   */
  static AppUsuarioMO preencheResultSet(ResultSet rs) throws SQLException
  {
    AppUsuarioMO appUsuarioMO = new AppUsuarioMO();

    appUsuarioMO.setIdAppUsuario(rs.getInt("IdAppUsuario"));
    appUsuarioMO.setIdAppGestor(rs.getInt("IdAppGestor"));
    appUsuarioMO.setIdentificador(rs.getString("Identificador"));
    appUsuarioMO.setIdIntegracao(rs.getString("IdIntegracao"));
    appUsuarioMO.setCodAtivacao(rs.getString("CodAtivacao"));
    appUsuarioMO.setApelido(rs.getString("Apelido"));
    appUsuarioMO.setStatus(rs.getInt("STATUS"));
    appUsuarioMO.setMaxLancamentosPorDia(rs.getInt("MaxLancamentosDia"));

    return appUsuarioMO;
  }
}
