package estiveaqui;

import java.time.format.DateTimeParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import estiveaqui.servidor.util.GeraChaves;

public class Util
{
  private static final Logger log = LogManager.getLogger();

  /**
   * Formata para transmissão com o App. Formato Ano, Mês, Dia, Hora (24h), Minutos e segundos (AAAAMMDDHHmm).<BR>
   * Use com o método <b>print()</b>.
   */
  private static final DateTimeFormatter  FMT_TRANSMISSAO_DATA_SS = DateTimeFormat.forPattern("yyyyMMddHHmmss");
  /**
   * Formata para transmissão com o App. Formato Ano, Mês, Dia, Hora (24h), Minutos e segundos (AAAAMMDDHHmm).<BR>
   * Use com o método <b>print()</b>.
   */
  private static final DateTimeFormatter  FMT_TRANSMISSAO_DATA    = DateTimeFormat.forPattern("yyyyMMddHHmm");
  /**
   * Formata para formato Ano, Mês, Dia, Hora (24h), Minutos e Fuso horário (AAAAMMDDHHmmZ).<BR>
   * Use com o método <b>print()</b>.
   */
  private static final DateTimeFormatter  fmtTZ                   = DateTimeFormat.forPattern("yyyyMMddHHmmZ");
  /**
   * Formata para o foramto Ano, Mês e Dia (AAAAMMDD).<BR>
   * Use com o método <b>print()</b>.
   */
  private static final DateTimeFormatter  fmtAMD                  = DateTimeFormat.forPattern("yyyyMMdd");
  /**
   * Formata para o foramto Ano e Mês (AAAAMM).<BR>
   * Use com o método <b>print()</b>.
   */
  public static final DateTimeFormatter fmtAM  = DateTimeFormat.forPattern("yyyyMM");
  private static final DateTimeFormatter DTZF = DateTimeFormat.forPattern("Z");
  
  /**
   * Formata uma data passando o formato desejado.
   * 
   * @param dt
   * @param dtf
   * @return
   */
  private static String formataData(DateTime dt, DateTimeFormatter dtf)
  {
    if (dt == null)
      return null;

    return dtf.print(dt);
  }
  
  /**
   * Formata a data de transmissão.
   * 
   * @param dt
   * @return
   */
  public static String formataDataTransmissao(DateTime dt)
  {
    return formataData(dt, FMT_TRANSMISSAO_DATA);
  }
  
  public static String formataDataTransmissaoComSegundos(DateTime dt)
  {
    return formataData(dt, FMT_TRANSMISSAO_DATA_SS);
  }
  
  public static String formataDataComTZ(DateTime dt)
  {
    return formataData(dt, fmtTZ);
  }
  
  public static String formataDataMesAno(DateTime dt)
  {
    return formataData(dt, fmtAM);
  }

  public static String formataDataMesAnoDia(DateTime dt)
  {
    return formataData(dt, fmtAMD);
  }

  /**
   * Transforma a string em um longo.
   * Retorna 0 quando a string não for convertível para longo.
   * 
   * @param sLong
   * @return - Número transformado ou 0 em caso de erro.
   */
  public static long parseLong(String sLong)
  {
    try
    {
      return Long.parseLong(sLong);
    }
    catch (NumberFormatException e)
    {
      log.error("Formatando idPassClock", e);
    }

    return 0;
  }

  /**
   * Transforma String em um inteiro.
   * Retorna 0 quando a string não for convertível para inteiro.
   * 
   * @param sInt
   * @return
   */
  public static int parseInt(String sInt)
  {
    try
    {
      return Integer.parseInt(sInt);
    }
    catch (NumberFormatException e)
    {
      log.error("Formatando idPassClock", e);
    }

    return 0;
  }

  /**
   * Faz o parser de uma data no formato padrão interno do EstiveAqui.
   * 
   * @param data
   * @return A data no formato DateTime ou
   *          o valor nulo.
   */
  public static DateTime parseDate(String data)
  {
    if (data == null)
      return null;
    
    try
    {
      return FMT_TRANSMISSAO_DATA_SS.parseDateTime(data);
    }
    catch (UnsupportedOperationException | IllegalArgumentException e)
    {
      log.error("Trasformação de datas", e);
    }
    
    return null;
  }

  /**
   * Transforma e valida a hora.
   * 
   * @param hora - Em String.
   * @return - No formado DateTime do Joda.
   */
  public static DateTime parseHoraSegundos(String hora)
  {
    try
    {
      return Util.FMT_TRANSMISSAO_DATA_SS.parseDateTime(hora);
    }
    catch (DateTimeParseException | IllegalArgumentException de)
    {
      log.debug("Erro no parser de hora", de);
      return null;
    }
  }

  /**
   * Transforma e valida a hora.
   * 
   * @param hora
   * @return
   */
  public static DateTime parseHora(String hora)
  {
    try
    {
      return Util.FMT_TRANSMISSAO_DATA.parseDateTime(hora);
    }
    catch (DateTimeParseException | IllegalArgumentException de)
    {
      log.debug("Erro no parser de hora", de);
      return null;
    }
  }

  /**
   * Transforma o fuso horário no formato org.joda.time.DateTimeZone para o formato em String 
   * SHHMM, onde S é o sinal (+ ou -), HH a hora entre 00 e 23 e MM os minutos entre 00 e 59.
   * 
   * @param tz - Data no formato org.joda.time.DateTimeZone
   * @return String representando o fuso horário.
   */
  public static String toStringTimeZone(DateTimeZone tz)
  {
    return DTZF.withZone(tz).print(DateTime.now());
  }

  /**
   * Transforma o fuso horário no formato String para o formato org.joda.time.DateTimeZone.
   * 
   * @param sTz Fuso horário no formato em String SHHMM, 
   *    onde S é o sinal (+ ou -), HH a hora entre 00 e 23 e MM os minutos entre 00 e 59.
   * @return
   */
  public static DateTimeZone parseTimeZone(String sTz)
  {
    if (sTz.length() != 5)
      return null;
    
    return DateTimeZone.forID(sTz);
  }
  
  /**
   * Define o timezone a partir da string formato "+HHMM".
   * 
   * @param sTz
   * @return
   */
  public static DateTimeZone defineTimeZone(String sTz)
  {
    int hrTz = Util.parseInt(sTz.substring(0, 3));
    int minTz = Util.parseInt(sTz.substring(3, 5));
    
    return DateTimeZone.forOffsetHoursMinutes(hrTz, minTz);
  }
  
  /**
   *  Calcula a diferença em minutos entre duas datas no formato DateTime do Joda.
   *   
   * @param dt1
   * @param dt2
   * @return Diferença de minutos entre dt1 e dt2 (dt1-dt2)
   */
  public static int difMinutos(DateTime dt1, DateTime dt2)
  {
    return (int) ((dt1.getMillis() - dt2.getMillis()) / (1000 * 60));
  }

  /**
   * Retorna null se a String for nula ou estiver vazia, ou se só tiver caracteres brancos.
   * 
   * @param valor
   * @return
   */
  public static String nulifica(String valor)
  {
    if (valor == null)
      return null;
    if (valor.trim().isEmpty())
      return null;

    return valor;
  }

  /**
   * Transforma uma data para um inteiro no formato AAAAMM.
   * 
   * @param dt
   * @return
   */
  static public int paraMesAno(DateTime dt)
  {
    return dt.getYear() * 100 + dt.getMonthOfYear();
  }

  /**
   * Monta e retorna um nome único para o relatório do gestor a partir do seu
   * idAppGestor e do mês.<BR>
   * O nome gerado é sempre o mesmo para o par (idAppGestor, Mes).
   *   
   * @param idAppGestor - Identificador do gestor.
   * @param meses - Mês no formato AAAAMM (Ano e mês).
   * @return
   */
  public static String nomeRelatorio(int idAppGestor, int mesAno)
  {
    byte tipoCaracteres = GeraChaves.MAIUSCULAS + GeraChaves.NUMEROS;
    
    String nomeRelatorio = mesAno + "-" +  GeraChaves.geraChave(idAppGestor, 7, tipoCaracteres) +
        GeraChaves.geraChave(idAppGestor * 10000L + (mesAno % 10000), 6, tipoCaracteres);

    log.debug("Nome de relatório gerado: ({}, {}) -> {}", idAppGestor, mesAno, nomeRelatorio);
    return nomeRelatorio;
  }

}
