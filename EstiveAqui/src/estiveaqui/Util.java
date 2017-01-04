package estiveaqui;

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
   */
  private static final String             PADRAO_TRANSMISSAO_DATA_SS = "yyyyMMddHHmmss";
  private static final DateTimeFormatter  FMT_TRANSMISSAO_DATA_SS = DateTimeFormat.forPattern(PADRAO_TRANSMISSAO_DATA_SS).withZoneUTC();
  /**
   * Formata para transmissão com o App. Formato Ano, Mês, Dia, Hora (24h), Minutos e segundos (AAAAMMDDHHmm).<BR>
   */
  private static final String             PADRAO_TRANSMISSAO_DATA = "yyyyMMddHHmm";
  private static final DateTimeFormatter  FMT_TRANSMISSAO_DATA    = DateTimeFormat.forPattern(PADRAO_TRANSMISSAO_DATA).withZoneUTC();
  /**
   * Formata para formato Ano, Mês, Dia, Hora (24h), Minutos e Fuso horário (AAAAMMDDHHmmZ).<BR>
   */
  private static final String             PADRAO_TZ                = "yyyyMMddHHmmZ";
  private static final DateTimeFormatter  FMT_TZ                   = DateTimeFormat.forPattern(PADRAO_TZ).withZoneUTC();
  /**
   * Formata para o foramto Ano, Mês e Dia (AAAAMMDD).<BR>
   */
  private static final String             PADRAO_AMD               = "yyyyMMdd";
  private static final DateTimeFormatter  FMT_AMD                  = DateTimeFormat.forPattern(PADRAO_AMD).withZoneUTC();
  /**
   * Formata para o foramto Ano e Mês (AAAAMM).<BR>
   */
  private static final DateTimeFormatter FMT_AM  = DateTimeFormat.forPattern("yyyyMM").withZoneUTC();
//  private static final DateTimeFormatter DTZF = DateTimeFormat.forPattern("Z");
  
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
   * Transforma uma data para o formato de transmissão sem os segundos.<BR>
   * A data de transmissão tem o formato AAAAMMDDHHmm no GMT-0.
   * 
   * @param dt
   * @return
   */
  public static String formataDataTransmissaoSemSegundos(DateTime dt)
  {
    return formataData(dt, FMT_TRANSMISSAO_DATA);
  }
  
  /**
   * Transforma uma data para o formato de transmissão com segundos.<BR>
   * A data de transmissão tem o formato AAAAMMDDHHmmSS no GMT-0.
   * 
   * @param dt
   * @return
   */
  public static String formataDataTransmissaoComSegundos(DateTime dt)
  {
    return formataData(dt, FMT_TRANSMISSAO_DATA_SS);
  }
  
  @Deprecated
  public static String formataDataTransmissaoSemSegundosTzLocal(DateTime dt, DateTimeZone tz)
  {
    DateTimeFormatter fmt = DateTimeFormat.forPattern(PADRAO_TRANSMISSAO_DATA_SS).withZone(tz);
    return formataData(dt, fmt);
  }

  /**
   * Formata uma data no formato yyyyMMddHHmmZ
   * 
   * @param dt
   * @param tz
   * @return
   */
  public static String formataDataComTZ(DateTime dt, DateTimeZone tz)
  {
    return formataData(dt.withZone(tz), FMT_TZ);
  }
  
  /**
   * Formata um fuso horário no formato +HHMM.
   * 
   * @param tz
   * @return
   */
  public static String formataTZ(DateTimeZone tz)
  {
    return formataTZ(tz, DateTime.now());
  }
  
  /**
   * Transforma um DateTimeZone para o formato +HHMM.
   * 
   * @param tz
   * @param hora
   * @return
   */
  public static String formataTZ(DateTimeZone tz, DateTime hora)
  {
    if (tz == null || hora == null)
      return null;
    
    int offset = tz.getOffset(hora.getMillis())/1000;
    String sTz = "-";
    if (offset > 0)
      sTz = "+";
    else
      offset = -offset;
    sTz += String.format("%02d%02d", offset/3600, offset%60);
    return sTz;
  }
  
  /**
   * Formata o DateTimeZone para o formato canônico. Ex.: America/Sao_Paulo.
   * 
   * @param tz
   * @return
   */
  public static String formataTZCanonico(DateTimeZone tz)
  {
    return tz.getID();
  }

  
  /**
   * Formata uma data para o formato AAAAMM.
   *  
   * @param dt
   * @return
   */
  public static String formataDataMesAno(DateTime dt)
  {
    return formataData(dt, FMT_AM);
  }

  /**
   * Formata uma data para o formado AAAAMMDD.
   * 
   * @param dt
   * @return
   */
  public static String formataDataMesAnoDia(DateTime dt)
  {
    return formataData(dt, FMT_AMD);
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

  public static DateTime parseData(String data, DateTimeFormatter formato)
  {
    if (data == null)
      return null;
    
    try
    {
      return formato.parseDateTime(data);
    }
    catch (UnsupportedOperationException | IllegalArgumentException e)
    {
      log.error("Trasformação de datas", e);
    }
    
    return null;
  }

  
  /**
   * Faz o parser de uma data no formato padrão interno do EstiveAqui.
   * 
   * @param data
   * @return A data no formato DateTime ou
   *          o valor nulo.
   */
  public static DateTime parseDataTransmissaoComSegundos(String data)
  {
    return parseData(data, FMT_TRANSMISSAO_DATA_SS);
  }

  /**
   * Retorna o formato usado para o parser e a formatação da data de transmissão.
   * 
   * @return
   */
  static public String padraoDataTransmissaoComSegundos()
  {
    return PADRAO_TRANSMISSAO_DATA_SS;
  }

  /**
   * Transforma e valida a hora.
   * 
   * @param hora
   * @return
   */
  public static DateTime parseDataTransmissaoSemSegundos(String hora)
  {
    return parseData(hora, FMT_TRANSMISSAO_DATA);
  }

  /**
   * Retorna o formato usado para o parser e a formatação da data de transmissão.
   * 
   * @return
   */
  static public String padraoDataTransmissaoSemSegundos()
  {
    return PADRAO_TRANSMISSAO_DATA;
  }

  /**
   * Retorna o formato usado para o parser e a formatação da data de transmissão.
   * 
   * @return
   */
  static public String padraoDataHora()
  {
    return PADRAO_TRANSMISSAO_DATA_SS;
  }

  /**
   * 
   * @param hora
   * @return
   */
  public static DateTime parseDataMesAno(String hora)
  {
    return parseData(hora, FMT_AM);
  }

  /**
   * 
   * @param hora
   * @return
   */
  public static DateTime parseDataMesAnoDia(String hora)
  {
    return parseData(hora, FMT_AMD);
  }

  static public String padraoDataMesAnoDia()
  {
    return PADRAO_AMD;
  }

//  /**
//   * Transforma o fuso horário no formato org.joda.time.DateTimeZone para o formato em String 
//   * SHHMM, onde S é o sinal (+ ou -), HH a hora entre 00 e 23 e MM os minutos entre 00 e 59.
//   * 
//   * @param tz - Data no formato org.joda.time.DateTimeZone
//   * @return String representando o fuso horário.
//   */
//  public static String toStringTimeZone(DateTimeZone tz)
//  {
//    return DTZF.withZone(tz).print(DateTime.now());
//  }

  /**
   * Transforma o fuso horário no formato String para o formato org.joda.time.DateTimeZone.
   * 
   * @param sTz Fuso horário no formato em String SHHMM, 
   *    onde S é o sinal (+ ou -), HH a hora entre 00 e 23 e MM os minutos entre 00 e 59.
   * @return
   */
  public static DateTimeZone parseTimeZone(String sTz)
  {
    if (sTz == null)
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
    if (sTz == null)
      return null;
    
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
    if (dt1 == null || dt2 == null)
      return 0;
    
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
    if (dt == null)
      return 0;
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


  public static void main(String[] args)
  {
    try
    {
      DateTimeZone tz = DateTimeZone.UTC;

      tz = DateTimeZone.forID("UTC");
      tz = DateTimeZone.forID("America/Los_Angeles");
      System.out.println(tz.getShortName(0));
      System.out.println(formataTZCanonico(tz));
      System.out.println(DateTime.now().withZone(tz));
//      System.out.println(toStringTimeZone(tz));
//      System.out.println(tz.getOffset(DateTime.now().getMillis()));
      System.out.println("");
      tz = DateTimeZone.forID("America/Sao_Paulo");
      System.out.println(formataTZCanonico(tz));
      System.out.println(tz.getShortName(DateTime.now().getMillis()));
      System.out.println(formataTZ(tz, DateTime.now().minusMonths(4)));
      System.out.println(formataTZ(tz));
      System.out.println(DateTime.now().withZone(tz));
//      System.out.println(toStringTimeZone(tz));
//      System.out.println(tz.getStandardOffset(DateTime.now().getMillis()));
      System.out.println("");
      tz = DateTimeZone.forID("Asia/Tokyo");
      System.out.println(formataTZCanonico(tz));
      System.out.println(tz.getShortName(DateTime.now().getMillis()));
      System.out.println(formataTZ(tz, DateTime.now()));
      System.out.println(DateTime.now().withZone(tz).withZone(tz).withZone(DateTimeZone.UTC));
      System.out.println(DateTime.now().withZone(tz));
//      System.out.println(toStringTimeZone(tz));
//      System.out.println(tz.getStandardOffset(DateTime.now().getMillis()));
      System.out.println("");
    }
    catch (IllegalArgumentException e)
    {

    }
  }
}

