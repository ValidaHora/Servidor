package estiveaqui.dados;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import estiveaqui.Util;
import estiveaqui.sql.mo.AppGestorMO;
import estiveaqui.sql.mo.AppUsuarioMO;
import estiveaqui.sql.mo.LancamentoMO;
import estiveaqui.sql.mo.PassClockMO;
import estiveaqui.sql.mo.RelatorioMO;

/**
 * Mapeia os dados para troca de informações entre o servidor e os apps.
 * 
 * @author Haroldo
 *
 */
public class MapeiaDados
{
  /**
   * Mapeia o gestor para um Mapa <Chave, Valor>
   * 
   * @param appGestorMO
   * @return
   */
  public static Map<ChaveJSON, Object> mapeiaGestor(AppGestorMO appGestorMO)
  {
    Map<ChaveJSON, Object> dado = new HashMap<ChaveJSON, Object>();
    
    dado.put(ChaveJSON.IG, appGestorMO.getIdentificadorAppGestor());
    dado.put(ChaveJSON.ST, "" + appGestorMO.getStatus());
    dado.put(ChaveJSON.IL, "" + appGestorMO.getIdUltimoLancamentoRelatorio());

    return dado;
  }
  
  /**
   * Mapeia uma lista de usuários para pares <Chave, Valor>.
   * 
   * @param appUsuariosMO
   * @return
   */
  public static ArrayList<Map<ChaveJSON, Object>> mapeiaAppUsuarios(ArrayList<AppUsuarioMO> appUsuariosMO)
  {
    if (appUsuariosMO == null)
      return null;
    
    ArrayList<Map<ChaveJSON, Object>> appUsuariosLista = new ArrayList<Map<ChaveJSON, Object>>();
    for (AppUsuarioMO appUsuarioMO : appUsuariosMO)
    {
      appUsuariosLista.add(mapeiaAppUsuario(appUsuarioMO));
    }

    return appUsuariosLista;
  }

  /**
   * 
   * @param appUsuario
   * @return
   */
  public static Map<ChaveJSON, Object> mapeiaAppUsuario(AppUsuarioMO appUsuarioMO)
  {
    Map<ChaveJSON, Object> dado = new HashMap<ChaveJSON, Object>();
    
    dado.put(ChaveJSON.IU, appUsuarioMO.getIdAppUsuario());
    dado.put(ChaveJSON.AU, appUsuarioMO.getApelido());
    dado.put(ChaveJSON.II, appUsuarioMO.getIdIntegracao());
    dado.put(ChaveJSON.ST, appUsuarioMO.getStatus());
    dado.put(ChaveJSON.CA, appUsuarioMO.getCodAtivacao());
    dado.put(ChaveJSON.XL, appUsuarioMO.getMaxLancamentosPorDia());

    return dado;
  }

  /**
   * 
   * @param lancamentosMO
   * @return
   */
  public static ArrayList<Map<ChaveJSON, Object>> mapeiaLancamentos(ArrayList<LancamentoMO> lancamentosMO)
  {
    if (lancamentosMO == null)
      return null;
    
    ArrayList<Map<ChaveJSON, Object>> lancamentosLista = new ArrayList<Map<ChaveJSON, Object>>();
    for(LancamentoMO lancamentoMO : lancamentosMO)
    {
      lancamentosLista.add(mapeiaLancamento(lancamentoMO));
    }

    return lancamentosLista;
  }

  /**
   * Mapeia cada um dos lançamentos.
   * 
   * @param lancamentoMO
   * @return
   */
  public static Map<ChaveJSON, Object> mapeiaLancamento(LancamentoMO lancamentoMO)
  {
    Map<ChaveJSON, Object> dado = new HashMap<ChaveJSON, Object>();

    dado.put(ChaveJSON.IL, lancamentoMO.getIdLancamento());
    dado.put(ChaveJSON.ST, lancamentoMO.getStatus());
    dado.put(ChaveJSON.HL, Util.formataDataTransmissao(lancamentoMO.getHrLancamento()));
    dado.put(ChaveJSON.HD, Util.formataDataTransmissaoComSegundos(lancamentoMO.getHrDigitacao()));
    dado.put(ChaveJSON.HE, Util.formataDataTransmissaoComSegundos(lancamentoMO.getHrEnvio()));
    DateTimeZone tz = lancamentoMO.getTzPassClock();
    dado.put(ChaveJSON.HLX, Util.formataDataTransmissao(lancamentoMO.getHrLancamento().withZone(tz)));
    dado.put(ChaveJSON.HDX, Util.formataDataTransmissaoComSegundos(lancamentoMO.getHrDigitacao().withZone(tz)));
    dado.put(ChaveJSON.HEX, Util.formataDataTransmissaoComSegundos(lancamentoMO.getHrEnvio().withZone(tz)));
    dado.put(ChaveJSON.TZ, Util.toStringTimeZone(lancamentoMO.getTzPassClock()));
    dado.put(ChaveJSON.PC, "" + lancamentoMO.getNumPassClock());
//    dado.put(ChaveJSON.HC, lancamentoMO.getHashCode());
    dado.put(ChaveJSON.AP, lancamentoMO.getApelidoPassClock());
    dado.put(ChaveJSON.AU, lancamentoMO.getAppUsuarioMO().getApelido());
    dado.put(ChaveJSON.CD, lancamentoMO.getCodPassClock());
    String disp = lancamentoMO.getIdDispositivo();
    dado.put(ChaveJSON.DI, (disp == null ? "Manual" : disp));
    String nota = lancamentoMO.getNota();
    dado.put(ChaveJSON.NT, (nota == null ? "" : nota));
    dado.put(ChaveJSON.LA, lancamentoMO.getLatitude());
    dado.put(ChaveJSON.LO, lancamentoMO.getLongitude());
    
    return dado;
  }

  /**
   * 
   * @param appPassClockGestorMO
   * @return
   */
  public static ArrayList<Map<ChaveJSON, Object>> mapeiaPassClocks(ArrayList<PassClockMO> passClocksMO)
  {

    ArrayList<Map<ChaveJSON, Object>> appPassClockGestorLista = new ArrayList<Map<ChaveJSON, Object>>();
    for (PassClockMO passClockMO : passClocksMO)
      appPassClockGestorLista.add(mapeiaPassClock(passClockMO));

    return appPassClockGestorLista;
  }

  /**
   * Mapeia um PassClock para transmissão.
   * 
   * @param passClockMO
   * @return
   */
  public static Map<ChaveJSON, Object> mapeiaPassClock(PassClockMO passClockMO)
  {
    Map<ChaveJSON, Object> dado = new HashMap<ChaveJSON, Object>();
    dado.put(ChaveJSON.PC, passClockMO.getNumPassClock());
    dado.put(ChaveJSON.AP, passClockMO.getApelido());
    dado.put(ChaveJSON.ST, passClockMO.getStatus());
    String codAtivacaoVirtual = passClockMO.getCodAtivacaoVirtual();
    dado.put(ChaveJSON.CV, (codAtivacaoVirtual == null ? "" : codAtivacaoVirtual));
    
    return dado;
  }

  
  /**
   * Monta o ArrayList de map padrão para transforma em Json.
   * 
   * @param relatoriosMO
   * @return
   */
  public static ArrayList<Map<ChaveJSON, Object>> mapeiaRelatorios(ArrayList<RelatorioMO> relatoriosMO)
  {
    if (relatoriosMO == null)
      return null;
    
    ArrayList<Map<ChaveJSON, Object>> relatorioLista = new ArrayList<Map<ChaveJSON, Object>>();
    for(RelatorioMO relatorioMO : relatoriosMO)
    {
      Map<ChaveJSON, Object> dado = new HashMap<ChaveJSON, Object>();
      
      dado.put(ChaveJSON.MS, Util.formataDataMesAno(new DateTime(relatorioMO.getMes()/100, relatorioMO.getMes()%100, 1, 0, 0)));
      dado.put(ChaveJSON.NA, relatorioMO.getNomeArquivo());
      
      relatorioLista.add(dado);
    }

    return relatorioLista;
  }
}
