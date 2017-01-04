package estiveaqui.sql;

import haroldo.util.sql.ConexaoDB;
import haroldo.util.sql.SqlDB;
import haroldo.util.sql.SqlUtil;
import haroldo.util.sql.SqlUtilMySql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import estiveaqui.Util;
import estiveaqui.sql.mo.LancamentoMO;

public class LancamentoDB extends SqlDB
{
  private static final Logger log = LogManager.getLogger();
  
  static final String SELECT =  
             " LANC.IdLancamento, LANC.Status, LANC.IdAppUsuario, LANC.NumPassClock, LANC.ApelidoPassClock, LANC.CodPassClock, "
        +    " LANC.HashCode, LANC.TzCliente, LANC.HrLancamento, LANC.HrPassClock, LANC.HrServidor, LANC.HrDigitacao, LANC.HrEnvio, "
        +    " LANC.IdDispositivo, LANC.IpDispositivo, LANC.Latitude, LANC.Longitude, LANC.Nota ";
  static final String SELECT_USR =  AppUsuarioDB.SELECT; 

  public LancamentoDB(ConexaoDB conn)
  {
    super(conn);
  }

  /**
   * L� um registro de Lancamento a partir da sua chave prim�ria.
   * 
   * @param idLancamento
   * @return
   * @throws SQLException
   */
  public LancamentoMO leRegistroPK(long idLancamento) throws SQLException
  {
    String query = "SELECT " + SELECT + ", " + SELECT_USR
        + " FROM lancamento LANC, appusuario APUS "
        + " WHERE LANC.IdLancamento = ? "
        + "   AND LANC.IdAppUsuario = APUS.IdAppUsuario ";
    
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setLong(1, idLancamento);
    ResultSet rs = stmt.executeQuery();

    return leResultSet(rs);
  }
  
  /**
   * Insere um lan�amento na tabela.<BR>
   * Atualiza os campos 'HrServidor' e 'IdLancamento'.
   * 
   * @param lancamentoMO
   * @return
   * @throws SQLException
   */
  public LancamentoMO insereRegistro(LancamentoMO lancamentoMO) throws SQLException
  {
    lancamentoMO.setHrServidor(SqlUtilMySql.leHoraServidor(connDB));
    
    //  Monta a query.
    String query = "INSERT INTO lancamento "
                              + "(IdAppUsuario, NumPassClock, ApelidoPassClock, CodPassClock, HashCode, Nota, "
                              + " TzCliente, HrLancamento, HrPassClock, HrServidor, HrDigitacao, HrEnvio, "
                              + " IdDispositivo, IpDispositivo, Latitude, Longitude) "
                              + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, lancamentoMO.getAppUsuarioMO().getIdAppUsuario());
    stmt.setString(2, lancamentoMO.getNumPassClock());
    String apelido = lancamentoMO.getApelidoPassClock();
    apelido = (apelido == null || apelido.isEmpty() ? lancamentoMO.getNumPassClock() : apelido);
    stmt.setString(3, apelido);
    stmt.setString(4, lancamentoMO.getCodPassClock());
    stmt.setString(5, lancamentoMO.getHashCode());
    stmt.setString(6, lancamentoMO.getNota());
    stmt.setString(7, Util.formataTZ(lancamentoMO.getTzPassClock(), lancamentoMO.getHrLancamento()));
    stmt.setTimestamp(8, SqlUtil.toSqlTimestamp(lancamentoMO.getHrLancamento()));
    stmt.setTimestamp(9, SqlUtil.toSqlTimestamp(lancamentoMO.getHrPassClock()));
    stmt.setTimestamp(10, SqlUtil.toSqlTimestamp(lancamentoMO.getHrServidor()));
    stmt.setTimestamp(11, SqlUtil.toSqlTimestamp(lancamentoMO.getHrDigitacao()));
    stmt.setTimestamp(12, SqlUtil.toSqlTimestamp(lancamentoMO.getHrEnvio()));
    stmt.setString(13, lancamentoMO.getIdDispositivo());
    stmt.setString(14, lancamentoMO.getIpDispositivo());
    stmt.setFloat(15, lancamentoMO.getLatitude());
    stmt.setFloat(16, lancamentoMO.getLongitude());

    //  Inclui o registro na tabela.
    stmt.executeUpdate();

    //  Carrega o idLancamento que foi gerado automaticamente durante o INSERT.
    lancamentoMO.setIdLancamento(SqlUtilMySql.leUltimoIdAutoIncremento(connDB));

    return lancamentoMO;
  }

  /**
   * Insere v�rios lan�amentos no BD.
   * 
   * @param lancamentosMO
   * @return
   */
  public List<LancamentoMO> insereLancamentos(List<LancamentoMO> lancamentosMO) //throws SQLException
  {
    List<LancamentoMO> lancamentosRetornoMO = new ArrayList<LancamentoMO>();
    for (LancamentoMO lancamentoMO : lancamentosMO)
    {
      try
      {
        lancamentosRetornoMO.add(insereRegistro(lancamentoMO));
      }
      catch (SQLException e)
      {
        log.warn("Erro durante inser��o de v�rios lan�amentos: {0}", e.getMessage());
      }
    }
    
    return lancamentosRetornoMO;
  }

//  /**
//   * Retorna os c�digos de um usu�rio foram lan�ados no dia definido por <b>dia</b>.
//   * 
//   * @param idAppGestor - Identificacador do gestor.
//   * @param numPassClock - N�mero de identifica��o do PassClock.
//   * @param dia - Dia em que os c�digos foram lan�ados.
//   * @return ArrayList<String> Lista de c�digos lan�ados no dia.
//   * @throws SQLException
//   */
//  @Deprecated
//  public List<String> codigosLancados(int idAppGestor, String numPassClock, DateTime dia) throws SQLException
//  {
//    String query = "SELECT " + SELECT + ", " + SELECT_USR
//        + " LANC.CodPassClock "
//        + " FROM lancamento LANC, appusuario APUS "
//        + " WHERE APUS.IdAppGestor = ? "
//        + "   AND LANC.NumPassClock = ? "
//        + "   AND (LANC.HrLancamento >= ? AND LANC.HrLancamento < ?) "
//        + "   AND APUS.IdAppUsuario = LANC.IdAppUsuario ";
//    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
//    stmt.setInt(1, idAppGestor);
//    stmt.setString(2, numPassClock);
//    DateTime diaIni = dia.dayOfMonth().roundFloorCopy();
//    DateTime diaFim = dia.dayOfMonth().roundCeilingCopy();
//    stmt.setDate(3, new java.sql.Date(diaIni.getMillis()));
//    stmt.setDate(4, new java.sql.Date(diaFim.getMillis()));
//
//    ResultSet rs = stmt.executeQuery();
//
//    /*
//     * Le os registros e guarda no ArrayList.
//     */
//    ArrayList<String> codigos = new ArrayList<String>();
//    while (rs.next())
//    {
//      // Preenche o objeto codigos com as informa��es da tabela.
//      codigos.add(rs.getString("CodPassClock"));
//    }
//
//    //
//    // Retorna o PassClock lido.
//    return codigos;
//  }
//
//

  /**
   * L� os N lan�amentos dos usu�rios de um gestor desde o lan�amento de idLancamento maior que idLancamentoMaisAntigo
   * at� a data a dataFinal.
   * M�todo criado para gera��o de relat�rios.
   * 
   * @param idAppGestor
   * @param idLancamentoMaisAntigo - Id do lan�amento mais antigo
   * @param dataFinal - Data final.
   * @return
   * @throws SQLException
   */
  public List<LancamentoMO> leLancamentosGestorDesdeAte(int idAppGestor, long idLancamentoMaisAntigo) throws SQLException
  {
    String query = "SELECT " + SELECT + ", " + SELECT_USR 
        + " FROM lancamento LANC, appusuario APUS "
        + " WHERE APUS.IdAppGestor = ? "
        + "   AND LANC.IdLancamento > ? "
//        + "   AND LANC.HrLancamento < ? "
        + "   AND LANC.IdAppUsuario = APUS.IdAppUsuario "
        + " ORDER BY LANC.HrLancamento ";
    
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, idAppGestor);
    stmt.setLong(2, idLancamentoMaisAntigo);
//    stmt.setDate(3, new Date(dataFinal.plusDays(1).getMillis()));
    
    ResultSet rs = stmt.executeQuery();

    return leResultsSet(rs);
  }

  /**
   * Retorna o primeiro lan�amento dos usu�rios de um gestor desde o lan�amento de id "idUltLancamento".
   * <BR>Se n�o houver novos lan�amentos, retorna "null".
   * 
   * @param idAppGestor
   * @param idUltLancamento
   * @return
   * @throws SQLException
   */
  public LancamentoMO retornaProximoLancamentoGestor(int idAppGestor, long idUltLancamento) throws SQLException
  {
    String query = "SELECT " + SELECT + ", " + SELECT_USR
        + " FROM lancamento LANC, appusuario APUS "
        + " WHERE APUS.IdAppGestor = ? "
        + "   AND LANC.IdLancamento > ? "
        + "   AND LANC.IdAppUsuario = APUS.IdAppUsuario "
        + " ORDER BY LANC.IdLancamento "
        + " LIMIT 1";

    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, idAppGestor);
    stmt.setLong(2, idUltLancamento);
    
    ResultSet rs = stmt.executeQuery();
    
    return this.leResultSet(rs);
  }

//  /**
//   * L� os �ltimos lan�amentos dos usu�rios de um gestor desde o lan�amento de idLancamento maior que idUltLancamento,
//   * limitado aos 2 �ltimos meses.
//   * 
//   * @param idAppGestor
//   * @param idUltLancamento
//   * @return
//   * @throws SQLException
//   */
//  public List<LancamentoMO> leUltimosLancamentoGestor2Meses(int idAppGestor, long idUltLancamento) throws SQLException
//  {
//    String query = "SELECT " + SELECT + ", " + SELECT_USR
//        + " FROM lancamento LANC, appusuario APUS "
//        + " WHERE APUS.IdAppGestor = ? "
//        + "   AND LANC.IdLancamento > ? "
//        + "   AND LANC.HrLancamento >= ? "
//        + "   AND LANC.IdAppUsuario = APUS.IdAppUsuario "
//        + " ORDER BY LANC.HrLancamento ";
//
//    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
//    stmt.setInt(1, idAppGestor);
//    stmt.setLong(2, idUltLancamento);
//    stmt.setDate(3, new Date(DateTime.now().minusMonths(1).monthOfYear().roundFloorCopy().getMillis()));  //  Primeiro dia do m�s anterior.
//    
//    ResultSet rs = stmt.executeQuery();
//    
//    return leResultsSet(rs);
//  }
//
  /**
   * L� os lan�amentos de um usu�rio para o dia de hoje.<BR>
   * O dia de hoje come�a às 00:00 do fuso hor�rio passado em tz.
   * 
   * @param idAppUsuario
   * @param tz - Fuso hor�rio do usu�rio para determinar quando come�a e acaba o dia.
   * @return
   * @throws SQLException
   */
  public List<LancamentoMO> leLancamentosUsuarioHoje(int idAppUsuario, DateTimeZone tz)
      throws SQLException
  {
    DateTime dtHoje = DateTime.now().withZone(tz);
    return leLancamentosUsuarioPeriodo(idAppUsuario, dtHoje, dtHoje, tz);
  }
  
  /**
   * L� todos os lan�amentos do gestor dentro de um per�odo de tempo.
   * 
   * @param idAppGestor
   * @param diaIni
   * @param diaFim
   * @return
   * @throws SQLException
   */
  public List<LancamentoMO> leLancamentosGestorPeriodo(int idAppGestor, DateTime diaIni, DateTime diaFim, DateTimeZone tz)
      throws SQLException
  {
    String query = "SELECT " + SELECT + ", " + SELECT_USR
        + " FROM lancamento LANC, appusuario APUS "
        + " WHERE LANC.IdAppUsuario = APUS.IdAppUsuario "
        + "   AND APUS.IdAppGestor = ? "
        + "   AND LANC.HrLancamento >= ? AND LANC.HrLancamento < ? "
        ;

    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, idAppGestor);
    stmt.setTimestamp(2, SqlUtil.toSqlTimestamp(diaIni.withZone(tz).dayOfMonth().roundFloorCopy()));
    stmt.setTimestamp(3, SqlUtil.toSqlTimestamp(diaFim.withZone(tz).plusDays(1).dayOfMonth().roundFloorCopy()));
    
    ResultSet rs = stmt.executeQuery();

    return leResultsSet(rs);
  }

  /**
   * L� todos os lan�amentos do m�s para o gestor.
   * 
   * @param idAppGestor
   * @param mes
   * @return
   * @throws SQLException
   */
  public List<LancamentoMO> leLancamentosGestorMes(int idAppGestor, DateTime mes)
      throws SQLException
  {
    String query = "SELECT " + SELECT + ", " + SELECT_USR
        + " FROM lancamento LANC, appusuario APUS "
        + " WHERE LANC.IdAppUsuario = APUS.IdAppUsuario "
        + "   AND APUS.IdAppGestor = ? "
        + "   AND LANC.HrLancamento >= ? AND LANC.HrLancamento < ? "
        ;

    DateTime diaIni = mes.dayOfMonth().roundFloorCopy().minusDays(1);
    DateTime diaFim = mes.dayOfMonth().roundCeilingCopy().plusDays(1);
    
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, idAppGestor);
    stmt.setTimestamp(2, SqlUtil.toSqlTimestamp(diaIni));
    stmt.setTimestamp(3, SqlUtil.toSqlTimestamp(diaFim));
    
    ResultSet rs = stmt.executeQuery();

    List<LancamentoMO> lancamentosMO = leResultsSet(rs);
    for (LancamentoMO lancamentoMO : lancamentosMO)
    {
      //  Remove datas de m�s diferente por causa do fuso hor�rio.
      if (lancamentoMO.getHrLancamento().withZone(lancamentoMO.getTzPassClock()).getMonthOfYear() != mes.getMonthOfYear())
        lancamentosMO.remove(lancamentoMO);
    }

    return lancamentosMO;
  }

  public List<LancamentoMO> leLancamentosGestor2UltimosMeses(int idAppGestor)
  {
    return null;
  }
  
  /**
   * L� os lan�amentos de um PassClock pr�ximo a hora passada.
   * 
   * @param numPassClock
   * @param hora
   * @return
   * @throws SQLException
   */
  public List<LancamentoMO> leLancamentosPassClockEm(String numPassClock, DateTime hora) throws SQLException
  {
    String query = "SELECT " + SELECT + ", " + SELECT_USR
        + " FROM lancamento LANC, appusuario APUS "
        + " WHERE LANC.IdAppUsuario = APUS.IdAppUsuario "
        + "   AND LANC.NumPassClock = ? "
        + "   AND LANC.HrLancamento >= ? AND LANC.HrLancamento < ? "
        + "   AND LANC.Status != " + LancamentoMO.STATUS_ENVIADO_VALIDAHORA;
          ;
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setString(1, numPassClock);
    stmt.setTimestamp(2, SqlUtil.toSqlTimestamp(hora.minusMinutes(10)));
    stmt.setTimestamp(3, SqlUtil.toSqlTimestamp(hora.plusMinutes(10)));
    
    ResultSet rs = stmt.executeQuery();

    return leResultsSet(rs);
  }
  
  /**
   * L� os lan�amentos de um gestor para o dia de hoje.<BR>
   * O dia de hoje come�a às 00:00 do fuso hor�rio passado em tz.
   * 
   * @param idAppGestor
   * @param tz - Fuso hor�rio do usu�rio para determinar quando come�a e acaba o dia.
   * @return
   * @throws SQLException
   */
  public List<LancamentoMO> leLancamentosGestorHoje(int idAppGestor, DateTimeZone tz) throws SQLException
  {
    DateTime dtHoje = DateTime.now().withZone(tz);
    return leLancamentosGestorPeriodo(idAppGestor, dtHoje, dtHoje, tz);
  }

  /**
   * L� todos os lan�amentos de um determinado AppUsuario dentro de um per�odo de tempo.
   * 
   * @param idAppUsuario
   * @param diaIni
   * @param diaFim
   * @return
   * @throws SQLException
   */
  public List<LancamentoMO> leLancamentosUsuarioPeriodo(int idAppUsuario, DateTime diaIni, DateTime diaFim, DateTimeZone tz)
      throws SQLException
  {
    String query = "SELECT " + SELECT + ", " + SELECT_USR
        + " FROM lancamento LANC, appusuario APUS "
        + " WHERE APUS.IdAppUsuario = ? "
        + "   AND LANC.HrLancamento >= ? AND LANC.HrLancamento < ? "
        + "   AND LANC.IdAppUsuario = APUS.IdAppUsuario "
        + "   AND LANC.Status != " + LancamentoMO.STATUS_ENVIADO_VALIDAHORA;

    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, idAppUsuario);
    stmt.setTimestamp(2, SqlUtil.toSqlTimestamp(diaIni.withZone(tz).dayOfMonth().roundFloorCopy()));
    stmt.setTimestamp(3, SqlUtil.toSqlTimestamp(diaFim.withZone(tz).plusDays(1).dayOfMonth().roundFloorCopy()));
    
    ResultSet rs = stmt.executeQuery();

    return leResultsSet(rs);
  }

  /**
   * Verifica se j� existe lan�amento para o hor�rio e AppUsuario passados.
   * 
   * @param idAppUsuario
   * @param hora
   * @return True - Se houver hora lan�ada. False - Se n�o houver hora lan�ada.
   * @throws SQLException
   */
  public boolean horaLancada(int idAppUsuario, DateTime hora) throws SQLException
  {
    String query = "SELECT COUNT(*) CONTADOR " 
        + " FROM lancamento LANC "
        + " WHERE LANC.IdAppUsuario = ? "
        + "   AND LANC.HrLancamento = ? "
        ;

    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, idAppUsuario);
    stmt.setTimestamp(2, SqlUtil.toSqlTimestamp(hora));
    
    ResultSet rs = stmt.executeQuery();
    rs.next();
    
    return rs.getInt("CONTADOR") > 0;
  }

  /**
   * L� os registros de um ResultSet gerado a partir da leitura da tabela Lancamento
   * 
   * @param rs
   * @return
   * @throws SQLException
   */
  private List<LancamentoMO> leResultsSet(ResultSet rs) throws SQLException
  {
    /*
     * Le os registros e guarda no ArrayList.
     */
    ArrayList<LancamentoMO> lancamentos = new ArrayList<LancamentoMO>();
    while (rs.next())
    {
      lancamentos.add(preencheResultSet(rs));
    }
    
    return lancamentos;
  }
  
  /**
   * Preenche um lan�amento de um ResultSet.
   * 
   * @param rs
   * @return
   */
  private LancamentoMO leResultSet(ResultSet rs) throws SQLException
  {
    // Busca o primeiro PassClock.
    if (!rs.next())
      return null;
   
    return preencheResultSet(rs);
  }

  /**
   * Preenche um LancamentoMO com os dados de um ResultSet.
   * 
   * @param rs
   * @return
   * @throws SQLException
   */
  static LancamentoMO preencheResultSet(ResultSet rs) throws SQLException
  {
    LancamentoMO lancamento = new LancamentoMO();
    
    lancamento.setIdLancamento(rs.getLong("IdLancamento"));
    lancamento.setStatus(rs.getInt("Status"));
    lancamento.setNumPassClock(rs.getString("NumPassClock"));
    lancamento.setApelidoPassClock(rs.getString("ApelidoPassClock"));
    lancamento.setCodPassClock(rs.getString("CodPassClock"));
    lancamento.setHashCode(rs.getString("HashCode"));
    lancamento.setNota(rs.getString("Nota"));
    lancamento.setTzPassClock(Util.parseTimeZone(rs.getString("TzCliente")));
    lancamento.setHrLancamento(rs.getTimestamp("HrLancamento"));
    lancamento.setHrPassClock(rs.getTimestamp("HrPassClock"));
    lancamento.setHrDigitacao(rs.getTimestamp("HrDigitacao"));
    lancamento.setHrEnvio(rs.getTimestamp("HrEnvio"));
    lancamento.setHrServidor(rs.getTimestamp("HrServidor"));
    lancamento.setIdDispositivo(rs.getString("IdDispositivo"));
    lancamento.setIpDispositivo(rs.getString("IpDispositivo"));
    lancamento.setLatitude(rs.getFloat("Latitude"));
    lancamento.setLongitude(rs.getFloat("Longitude"));

    lancamento.setAppUsuarioMO(AppUsuarioDB.preencheResultSet(rs));

    return lancamento;
  }
  /**
   * Habilita o lancamento de id idLancamento.
   * 
   * @param idLancamento
   * @return O LancamentoMO do lancamento habilitado.
   * @throws SQLException
   */
  public LancamentoMO habilita(int idLancamento) throws SQLException
  {
    return defineStatus(idLancamento, LancamentoMO.STATUS_HABILITADO);
  }

  /**
   * Desabilita o lancamento de id idLancamento colocando no status inativo.
   * 
   * @param idLancamento
   * @return
   * @throws SQLException
   */
  public LancamentoMO desabilita(int idLancamento) throws SQLException
  {
    return defineStatus(idLancamento, LancamentoMO.STATUS_DESABILITADO);
  }

  /**
   * Altera o status de um lancamento de id idLancamento.
   * 
   * @param idLancamento
   * @param status
   * @return  O LancamentoMO com o status alterado.
   * @throws SQLException
   */
  public LancamentoMO defineStatus(long idLancamento, int status) throws SQLException
  {
    //  Monta a query.
    String query = "UPDATE lancamento SET Status = ? "
              +   "   WHERE IdLancamento = ? ";
    
    PreparedStatement stmt = connDB.getConn().prepareStatement(query);
    stmt.setInt(1, status);
    stmt.setLong(2, idLancamento);
    
    //  Inclui o registro na tabela.
    stmt.executeUpdate();

    return leRegistroPK(idLancamento);
  }

}
