package estiveaqui.sql;

import haroldo.util.sql.ConexaoDB;
import haroldo.util.sql.SqlDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.joda.time.DateTime;
import estiveaqui.Util;
import estiveaqui.sql.mo.RelatorioMO;

public class RelatorioDB extends SqlDB
{
  private static final String SELECT = "IdAppGestor, Mes, NomeRelatorio, RecriarRelatorio ";
      
  public RelatorioDB(ConexaoDB conn)
  {
    super(conn);
  }

  /**
   * Retorna o registro da Primary Key.
   * 
   * @param idAppGestor
   * @param mesAno
   * @return
   * @throws SQLException
   */
  public RelatorioMO leRegistroPK(int idAppGestor, int mesAno) throws SQLException
  {
    String query = "SELECT " + SELECT 
        +           " FROM estiveaqui.relatorio  "
        +           " WHERE IdAppGestor = ? "
        +           " AND   Mes = ?";
                    ;
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, idAppGestor);
    stmt.setInt(2, mesAno);

    //  Executa a query
    ResultSet rs = stmt.executeQuery();

    if (!rs.next())
      return null;

    return preencheResultSet(rs);
  }
  
  /**
   * Le o nome dos relat�rios que foram gerados para um detereminado appGestor at� o m�s passado.
   * <BR> Retorna os relat�rios ordenados por m�s.
   * 
   * @param idAppGestor
   * @return
   * @throws SQLException
   */
  public ArrayList<RelatorioMO> leRelatoriosGestor(int idAppGestor) throws SQLException
  {
    String query = "SELECT " + SELECT
        +           " FROM estiveaqui.relatorio  "
        +           " WHERE IdAppGestor = ? "
        +           "   AND MES < ? "
        +           " ORDER BY Mes ";

    int mesPassado = DateTime.now().getYear() * 100 + DateTime.now().getMonthOfYear();
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, idAppGestor);
    stmt.setInt(2, mesPassado);
    
    ResultSet rs = stmt.executeQuery();

    /*
     * Le nome dos registros. e guarda no ArrayList.
     */
    ArrayList<RelatorioMO> relatoriosMO = new ArrayList<RelatorioMO>();
    while (rs.next())
    {
      relatoriosMO.add(preencheResultSet(rs));
    }
    
    //
    // Retorna os relat�rios.
    return relatoriosMO;
  }

  /**
   * Verifica se o relat�rio j� foi criado.
   * 
   * @param idAppGestor
   * @param mesAno
   * @return
   * @throws SQLException
   */
  public RelatorioMO relatorioExiste(int idAppGestor, int mesAno) throws SQLException
  {
    String query = "SELECT " + SELECT 
        +           " FROM estiveaqui.relatorio  "
        +           " WHERE IdAppGestor = ? "
        +           "   AND Mes = ? ";

    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, idAppGestor);
    stmt.setInt(2, mesAno);
    
    ResultSet rs = stmt.executeQuery();
    
    if (!rs.next())
      return null;

    return preencheResultSet(rs);
  }

  /**
   * Insere um registro de relat�rio na tabela relatorio
   * 
   * @param relatorioMO
   * @return
   * @throws SQLException
   */
  public RelatorioMO insereRelatorio(RelatorioMO relatorioMO) throws SQLException
  {
    String query =  "INSERT INTO estiveaqui.relatorio "
        +           " (IdAppGestor, Mes, NomeRelatorio, RecriarRelatorio) "
        +           " VALUES (?, ?, ?, ?)";

    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, relatorioMO.getIdAppGestor());
    stmt.setInt(2, relatorioMO.getMes());
    stmt.setString(3, relatorioMO.getNomeArquivo());
    stmt.setInt(4, relatorioMO.getRecriarRelatorio());
    
    stmt.executeUpdate();

    return relatorioMO;
  }
  
  /**
   * Marca o relat�rio como n�o gerado porque um novo lan�amento foi inclu�do ou porque um lan�amento antigo foi atualizado.
   * <BR>
   * Cria uma entrada na tabela Relatorio ou atualiza uma entrada, se j� existir, marcando como n�o gerado.
   * 
   * @param idAppGestor
   * @param mesAno
   * @return
   * @throws SQLException
   */
  public RelatorioMO marcaComoNaoGerado(int idAppGestor, int mesAno) throws SQLException
  {
    return marcaGerado(idAppGestor, mesAno, RelatorioMO.RECRIAR_RELATORIO_SIM);
  }
  
  /**
   * Marca o relat�rio como gerado.
   * <BR>
   * Cria uma entrada na tabela Relatorio ou atualiza uma entrada, se j� existir, marcando como gerado.
   * 
   * @param idAppGestor
   * @param mesAno
   * @return
   * @throws SQLException
   */
  public RelatorioMO marcaComoGerado(int idAppGestor, int mesAno) throws SQLException
  {
    return marcaGerado(idAppGestor, mesAno, RelatorioMO.RECRIAR_RELATORIO_NAO);
  }

  private RelatorioMO marcaGerado(int idAppGestor, int mesAno, int statusGerado) throws SQLException
  {
    RelatorioMO relatorioMO = relatorioExiste(idAppGestor, mesAno);
    
    if (relatorioMO == null)
    {
      //  Inclui um registro no BD.
      relatorioMO = new RelatorioMO();
      relatorioMO.setIdAppGestor(idAppGestor);
      relatorioMO.setMes(mesAno);
      relatorioMO.setNomeArquivo(Util.nomeRelatorio(idAppGestor, mesAno));
      relatorioMO.setRecriarRelatorio(statusGerado);
      
      insereRelatorio(relatorioMO);
    }
    else if (relatorioMO.getRecriarRelatorio() != statusGerado)
    {
      //  Atualiza o registro no BD.
      relatorioMO.setRecriarRelatorio(statusGerado);
      atualizaRelatorio(relatorioMO);
    }
   
    return relatorioMO;
  }

  /**
   * Atualiza o relat�rio com o flag de recriar relat�rio definido no MO.
   * 
   * @param relatorioMO
   * @return
   * @throws SQLException
   */
  public RelatorioMO atualizaRelatorio(RelatorioMO relatorioMO) throws SQLException
  {
    //  Monta a query.
    String query = "UPDATE relatorio SET RecriarRelatorio = ? "
              +   "   WHERE IdAppGestor = ? AND Mes = ? ";
    
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, relatorioMO.getRecriarRelatorio());
    stmt.setInt(2, relatorioMO.getIdAppGestor());
    stmt.setInt(3, relatorioMO.getMes());
    
    //  Inclui o registro na tabela.
    stmt.executeUpdate();

    return leRegistroPK(relatorioMO.getIdAppGestor(), relatorioMO.getMes());
  }
  
  
  /**
   * Preenche um RelatorioMO com os dados de um ResultSet.
   * 
   * @param rs
   * @return
   * @throws SQLException
   */
  private RelatorioMO preencheResultSet(ResultSet rs) throws SQLException
  {
    RelatorioMO relatorioMO = new RelatorioMO();
    
    relatorioMO.setIdAppGestor(rs.getInt("IdAppGestor"));
    relatorioMO.setMes(rs.getInt("Mes"));
    relatorioMO.setNomeArquivo(rs.getString("NomeRelatorio"));
    relatorioMO.setRecriarRelatorio(rs.getInt("RecriarRelatorio"));

    return relatorioMO;
  }
}
