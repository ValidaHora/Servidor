package estiveaqui.dados;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
public class MapeiaDadosOld
{
  /**
   * Mapeia o gestor para um Mapa <Chave, Valor>
   * 
   * @param appGestorMO
   * @return
   */
  public static Map<String, String> mapeiaGestor(AppGestorMO appGestorMO)
  {
    Map<String, String> dado = new HashMap<String, String>();
    
    dado.put("Identificador", appGestorMO.getIdentificadorAppGestor());
    dado.put("Status", "" + appGestorMO.getStatus());
    dado.put("IdUltLanc", "" + appGestorMO.getIdUltimoLancamentoRelatorio());

    return dado;
  }
  
  /**
   * Mapeia uma lista de usuários para pares <Chave, Valor>.
   * 
   * @param appUsuariosMO
   * @return
   */
  public static ArrayList<Map<String, String>> mapeiaUsuarios(ArrayList<AppUsuarioMO> appUsuariosMO)
  {
    if (appUsuariosMO == null)
      return null;
    
    ArrayList<Map<String, String>> appUsuariosLista = new ArrayList<Map<String, String>>();
    for (AppUsuarioMO appUsuarioMO : appUsuariosMO)
    {
      appUsuariosLista.add(mapeiaUsuario(appUsuarioMO));
    }

    return appUsuariosLista;
  }

  /**
   * 
   * @param appUsuario
   * @return
   */
  public static Map<String, String> mapeiaUsuario(AppUsuarioMO appUsuarioMO)
  {
    Map<String, String> dado = new HashMap<String, String>();
    
//    dado.put("IdUsuario", "" + appUsuarioMO.getIdAppUsuario());
    dado.put("Identificador", appUsuarioMO.getIdentificador());
    dado.put("Apelido", appUsuarioMO.getApelido());
//    dado.put("CodAtivacao", appUsuarioMO.getCodAtivacao());
    dado.put("Status", "" + appUsuarioMO.getStatus());

    return dado;
  }

  /**
   * 
   * @param lancamentosMO
   * @return
   */
  public static ArrayList<Map<String, String>> mapeiaLancamentos(ArrayList<LancamentoMO> lancamentosMO)
  {
    if (lancamentosMO == null)
      return null;
    
    ArrayList<Map<String, String>> lancamentosLista = new ArrayList<Map<String,String>>();
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
  public static Map<String, String> mapeiaLancamento(LancamentoMO lancamentoMO)
  {
    Map<String, String> dado = new HashMap<String, String>();

    dado.put("ID", "" + lancamentoMO.getIdLancamento());
    dado.put("ST", "" + lancamentoMO.getStatus());
    dado.put("HL", "" + Util.formataDataTransmissao(lancamentoMO.getHrLancamento()));
    dado.put("HD", "" + Util.formataDataTransmissaoComSegundos(lancamentoMO.getHrDigitacao()));
    dado.put("HE", "" + Util.formataDataTransmissaoComSegundos(lancamentoMO.getHrEnvio()));
    dado.put("TZ", Util.toStringTimeZone(lancamentoMO.getTzPassClock()));
    dado.put("PC", "" + lancamentoMO.getNumPassClock());
    dado.put("HC", lancamentoMO.getHashCode());
    dado.put("PA", lancamentoMO.getApelidoPassClock());
    dado.put("AP", lancamentoMO.getAppUsuarioMO().getApelido());
    dado.put("IU", "" + lancamentoMO.getAppUsuarioMO().getIdAppUsuario());
    String codigoLancado = lancamentoMO.getCodPassClock();
    dado.put("CD", (codigoLancado == null ? "" : codigoLancado));
    String disp = lancamentoMO.getIdDispositivo();
    dado.put("DI", (disp == null ? "Manual" : disp));
    dado.put("LAT", "" + lancamentoMO.getLatitude());
    dado.put("LON", "" + lancamentoMO.getLongitude());
    
    return dado;
  }

  /**
   * 
   * @param appPassClockGestorMO
   * @return
   */
  public static ArrayList<Map<String, String>> mapeiaPassClocks(ArrayList<PassClockMO> passClocksMO)
  {

    ArrayList<Map<String, String>> appPassClockGestorLista = new ArrayList<Map<String, String>>();
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
  public static Map<String, String> mapeiaPassClock(PassClockMO passClockMO)
  {
    Map<String, String> dado = new HashMap<String, String>();
    dado.put("NumeroPassClock", "" + passClockMO.getNumPassClock());
    dado.put("Apelido", passClockMO.getApelido());
    dado.put("Status", "" + passClockMO.getStatus());
    dado.put("CodAtivacaoVirtual", passClockMO.getCodAtivacaoVirtual());
    
    return dado;
  }

  
  /**
   * Monta o ArrayList de map padrão para transforma em Json.
   * 
   * @param relatoriosMO
   * @return
   */
  public static ArrayList<Map<String, String>> mapeiaRelatorios(ArrayList<RelatorioMO> relatoriosMO)
  {
    if (relatoriosMO == null)
      return null;
    
    ArrayList<Map<String, String>> relatorioLista = new ArrayList<Map<String,String>>();
    for(RelatorioMO relatorioMO : relatoriosMO)
    {
      Map<String, String> dado = new HashMap<String, String>();
      
      dado.put("Mes", "" + Util.fmtAM.print(relatorioMO.getMes()));
      dado.put("NomeArquivo", relatorioMO.getNomeArquivo());
      
      relatorioLista.add(dado);
    }

    return relatorioLista;
  }
}
