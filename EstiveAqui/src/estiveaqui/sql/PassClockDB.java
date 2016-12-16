package estiveaqui.sql;

import haroldo.util.sql.ConexaoDB;
import haroldo.util.sql.SqlDB;
import haroldo.util.sql.SqlUtilMySql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import estiveaqui.sql.mo.PassClockMO;

public class PassClockDB extends SqlDB
{
  public static final String SELECT = 
      "PACL.IdPassClock, PACL.NumPassClock, PACL.Apelido, PACL.IdAppGestor, "
    + " PACL.StatusPassClock, PACL.SenhaCadastro, PACL.CodAtivacaoVirtual ";

  public PassClockDB(ConexaoDB conn)
  {
    super(conn);
  }

  /**
   * Lê um registro de PassClock a partir da sua chave primária.
   * 
   * @param idPassClock
   * @return
   * @throws SQLException
   */
  public PassClockMO leRegistroPK(int idPassClock) throws SQLException
  {
    String query = "SELECT " + SELECT  
        +   " FROM  passclock PACL "
        +   " WHERE PACL.IdPassClock = ? ";
    
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, idPassClock);
    ResultSet rs = stmt.executeQuery();

    return leResultSet(rs);
  }

  /**
   * Insere um registro na tabela PassClock.
   * 
   * @param passClockMO
   * @return
   * @throws SQLException
   */
  public PassClockMO insereRegistro(PassClockMO passClockMO) throws SQLException
  {
    //  Monta a query.
    String query = "INSERT INTO passclock "
        + "(Numpassclock, Apelido, SenhaCadastro, IdAppGestor) "
        + " VALUES (?, ?, ?, ?)";
    
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setString(1, passClockMO.getNumPassClock());
    String apelido = passClockMO.getApelido();
    apelido = (apelido == null || apelido.isEmpty() ? passClockMO.getNumPassClock() : apelido);
    passClockMO.setApelido(apelido);
    stmt.setString(2, apelido);
    stmt.setString(3, passClockMO.getSenhaCadastro());
    stmt.setInt(4, passClockMO.getIdAppGestor());

    //  Inclui o registro na tabela.
    stmt.executeUpdate();

    //  Carrega o idLancamento que foi gerado automaticamente durante o INSERT.
    passClockMO.setIdPassClock((int)SqlUtilMySql.leUltimoIdAutoIncremento(connDB));

    return passClockMO;
  }

  /**
   * Lê todos os PassClocks de um determinado Gestor.
   * 
   * @param idAppGestor
   * @return
   */
  public ArrayList<PassClockMO>leRegistrosGestor(int idAppGestor) throws SQLException
  {
    String query = "SELECT " + SELECT  
                +   " FROM  passclock PACL "
                +   " WHERE PACL.IdAppGestor = ? ";
    
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, idAppGestor);
    ResultSet rs = stmt.executeQuery();

    ArrayList<PassClockMO> passClocksMO = new ArrayList<PassClockMO>();
    while (rs.next())
      passClocksMO.add(preencheResultSet(rs));
    
    return passClocksMO;
  }

  /**
   * Le um PassClock da tabela buscando pelo seu número.
   * 
   * @param numPassClock
   *          - Número do PassClock.
   * @return O objeto PassClockMO com as informaçÃµes encontradas.
   * @throws SQLException
   */
  public PassClockMO buscaPorNumeroPassClock(String numPassClock) throws SQLException
  {
    String query = "SELECT " + SELECT  
        +   " FROM  passclock PACL "
        +   " WHERE BINARY PACL.NumPassClock = ? ";
    
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setString(1, numPassClock);
    ResultSet rs = stmt.executeQuery();

    return leResultSet(rs);
  }

  /**
   * Retorna o PassClock identificado pelo seu apelido de um determinado AppGestor.
   * 
   * @param idAppEmpregdor
   * @param apelidoPassClock
   * @return
   */
  public PassClockMO buscaPassClockPeloNome(int idAppEmpregdor, String apelidoPassClock) throws SQLException
  {
    String query = "SELECT " + SELECT  
        +   " FROM  passclock PACL "
        +   " WHERE PACL.IdAppGestor = ? "
        +   "   AND PACL.Apelido = ? ";

    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, idAppEmpregdor);
    stmt.setString(2, apelidoPassClock);
    ResultSet rs = stmt.executeQuery();

    return leResultSet(rs);
  }

  /**
   * Desabilita o PassClock de id idPassClock colocando no status desabilitado e define o código de ativação.
   * 
   * @param idPassClock
   * @throws SQLException
   */
  public void desabilita(int idPassClock, String codigoAtivacao) throws SQLException
  {
    defineStatus(idPassClock, PassClockMO.STATUS_DESABILITADO, codigoAtivacao);
  }

  /**
   * Altera o status de um PassClock de id idPassClock, e anula o seu código de ativação virtual
   * 
   * @param idPassClock
   * @param status
   * @throws SQLException
   */
  public void defineStatus(int idPassClock, int status, String codigoAtivacao) throws SQLException
  {
    //  Monta a query.
    String query = "UPDATE passclock SET StatusPassClock = ?, CodAtivacaoVirtual = ? "
              +   "   WHERE IdPassClock = ? ";
    
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, status);
    stmt.setString(2, codigoAtivacao);
    stmt.setInt(3, idPassClock);
    
    //  Inclui o registro na tabela.
    stmt.executeUpdate();
  }

  /**
   * Habilita um PassClock virtual e nulifica o seu código de ativação.
   * 
   * @param idPassClock
   * @return PassClockMO
   * @throws SQLException
   */
  public void habilita(int idPassClock) throws SQLException
  {
    defineStatus(idPassClock, PassClockMO.STATUS_HABILITADO, null);
  }

  /**
   * Define a que AppGestor pertence o PassClock identificado por idPassClock.
   * 
   * @param idPassClock
   * @param idAppGestor
   * @throws SQLException
   */
  public void defineAppGestor(int idPassClock, int idAppGestor) throws SQLException
  {
    String query = "UPDATE passclock SET IdAppGestor = ? "
        +   "   WHERE IdPassClock = ? ";

    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, idAppGestor);
    stmt.setInt(2, idPassClock);
    
    //  Inclui o registro na tabela.
    stmt.executeUpdate();
  }

  /**
   * Muda o apelido do PassClock para "apelidoNovo".
   * 
   * @param idPassClock
   * @param apelidoNovo
   * @throws SQLException
   */
  public PassClockMO atualizaApelido(int idPassClock, String apelidoNovo) throws SQLException
  {
    String query = "UPDATE passclock SET Apelido = ? "
                +  "   WHERE IdPassClock = ? ";

    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setString(1, apelidoNovo);
    stmt.setInt(2, idPassClock);
    
    //  Inclui o registro na tabela.
    stmt.executeUpdate();

    return leRegistroPK(idPassClock);
  }

  /**
   * Atualiza o IdAppGestor de um PassClock.
   * 
   * @param idPassClock
   * @param idAppGestor
   * @return
   * @throws SQLException
   */
  public PassClockMO atualizaGestor(int idPassClock, int idAppGestor) throws SQLException
  {
    String query = "UPDATE passclock SET IdAppGestor = ? "
                +  "   WHERE IdPassClock = ? ";

    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    if (idAppGestor == 0)
      stmt.setNull(1, Types.INTEGER);
    else
      stmt.setInt(1, idAppGestor);
    stmt.setInt(2, idPassClock);
    
    //  Inclui o registro na tabela.
    stmt.executeUpdate();

    return leRegistroPK(idPassClock);
  }

  /**
   * Inclui um PassClock virtual.
   * 
   * @param passClockMO
   * @return
   * @throws SQLException
   */
  public PassClockMO incluiPassClockVirtual(PassClockMO passClockMO) throws SQLException
  {
    String query = "INSERT INTO passclock (NumPassClock, Apelido, IdAppGestor, StatusPassClock, "
                  + " SenhaCadastro, CodAtivacaoVirtual) "
                  + " VALUES (?, ?, ?, ?, ?, ?) ";

    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setString(1, passClockMO.getNumPassClock());
    stmt.setString(2, passClockMO.getApelido());
    stmt.setInt(3, passClockMO.getIdAppGestor());
    stmt.setInt(4, passClockMO.getStatus());
    stmt.setString(5, passClockMO.getSenhaCadastro());
    stmt.setString(6, passClockMO.getCodAtivacaoVirtual());
    
    //  Inclui o registro na tabela.
    stmt.executeUpdate();

    passClockMO.setIdPassClock((int)SqlUtilMySql.leUltimoIdAutoIncremento(connDB));
    
    return leRegistroPK(passClockMO.getIdPassClock());
  }
  
  /**
   * Busca o PassClockVirtual a partir do seu código de ativação.
   * 
   * @param codAtivacao
   * @return
   * @throws SQLException
   */
  public PassClockMO buscaCodigoAtivacao(String codAtivacaoVirtual) throws SQLException
  {
    String query = "SELECT " + SELECT  
        +   " FROM  passclock PACL "
        +   " WHERE BINARY PACL.CodAtivacaoVirtual = ? ";

    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setString(1, codAtivacaoVirtual);
    ResultSet rs = stmt.executeQuery();

    return leResultSet(rs);
  }
  
  /**
   * Libera o código de ativação virtual atribuindo um valor nulo.
   * 
   * @param idPassClock
   * @throws SQLException
   */
  public PassClockMO liberaCodigoAtivacaoVirtual(int idPassClock) throws SQLException
  {
    String query = "UPDATE passclock SET CodAtivacaoVirtual = null "
                +  "   WHERE IdPassClock = ? ";

    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, idPassClock);
    
    //  Inclui o registro na tabela.
    stmt.executeUpdate();

    return leRegistroPK(idPassClock);
  }

  /**
   * Preenche o PassClockMO com o resultado de um resultSet.
   * 
   * @param rs
   * @return
   * @throws SQLException
   */
  private PassClockMO leResultSet(ResultSet rs) throws SQLException
  {
    // Busca o primeiro PassClock.
    if (!rs.next())
      return null;
   
    return preencheResultSet(rs);
  }
  
  /**
   * Preenche um PassClockMO com os dados de um ResultSet.
   * 
   * @param rs
   * @return
   * @throws SQLException
   */
  private PassClockMO preencheResultSet(ResultSet rs) throws SQLException
  {
    // Preenche o objeto PassClockMO com as informaçÃµes da tabela.
    int idPassClock = rs.getInt("IdPassClock");
    PassClockMO passClockMO = new PassClockMO();
    passClockMO.setIdPassClock(idPassClock);
    passClockMO.setNumPassClock(rs.getString("NumPassClock"));
    passClockMO.setApelido(rs.getString("Apelido"));
    passClockMO.setIdAppGestor(rs.getInt("IdAppGestor"));
    passClockMO.setStatus(rs.getInt("StatusPassClock"));
    passClockMO.setSenhaCadastro(rs.getString("SenhaCadastro"));
    passClockMO.setCodAtivacaoVirtual(rs.getString("CodAtivacaoVirtual"));

    //
    // Retorna o PassClock lido.
    return passClockMO;
  }
}
