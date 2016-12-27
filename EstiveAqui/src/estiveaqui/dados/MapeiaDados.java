package estiveaqui.dados;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import estiveaqui.EstiveAquiException;
import estiveaqui.Util;
import estiveaqui.sql.mo.AppGestorMO;
import estiveaqui.sql.mo.AppUsuarioMO;
import estiveaqui.sql.mo.LancamentoMO;
import estiveaqui.sql.mo.PassClockMO;
import estiveaqui.sql.mo.RelatorioMO;
import estiveaqui.sql.mo.externo.TokenMO;

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
  public static List<Map<ChaveJSON, Object>> mapeiaAppUsuarios(List<AppUsuarioMO> appUsuariosMO)
  {
    if (appUsuariosMO == null)
      return null;
    
    List<Map<ChaveJSON, Object>> appUsuariosLista = new ArrayList<Map<ChaveJSON, Object>>();
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
  public static List<Map<ChaveJSON, Object>> mapeiaLancamentos(List<LancamentoMO> lancamentosMO)
  {
    if (lancamentosMO == null)
      return null;
    
    List<Map<ChaveJSON, Object>> lancamentosLista = new ArrayList<Map<ChaveJSON, Object>>();
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
    dado.put(ChaveJSON.HL, Util.formataDataTransmissaoSemSegundos(lancamentoMO.getHrLancamento()));
    dado.put(ChaveJSON.HD, Util.formataDataTransmissaoComSegundos(lancamentoMO.getHrDigitacao()));
    dado.put(ChaveJSON.HE, Util.formataDataTransmissaoComSegundos(lancamentoMO.getHrEnvio()));
    dado.put(ChaveJSON.TZ, Util.toStringTimeZone(lancamentoMO.getTzPassClock()));
    dado.put(ChaveJSON.PC, "" + lancamentoMO.getNumPassClock());
    dado.put(ChaveJSON.AP, lancamentoMO.getApelidoPassClock());
    dado.put(ChaveJSON.AU, lancamentoMO.getAppUsuarioMO().getApelido());
    dado.put(ChaveJSON.CD, lancamentoMO.getCodPassClock());
    dado.put(ChaveJSON.DI, lancamentoMO.getIdDispositivo());
    dado.put(ChaveJSON.NT, lancamentoMO.getNota());
    dado.put(ChaveJSON.LA, lancamentoMO.getLatitude());
    dado.put(ChaveJSON.LO, lancamentoMO.getLongitude());
    
    return dado;
  }

  /**
   * 
   * @param appPassClockGestorMO
   * @return
   */
  public static List<Map<ChaveJSON, Object>> mapeiaPassClocks(List<PassClockMO> passClocksMO)
  {

    List<Map<ChaveJSON, Object>> appPassClockGestorLista = new ArrayList<Map<ChaveJSON, Object>>();
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
    dado.put(ChaveJSON.CV, passClockMO.getCodAtivacaoVirtual());
    dado.put(ChaveJSON.TZ, "-0200");  //  TODO: Mapear corretamente o fuso horário.
    
    return dado;
  }

  /**
   * Mapeia as sementes de Tokens para transmissão.
   * 
   * @param tokenMO
   * @return
   */
  public static List<Map<ChaveJSON, Object>> mapeiaSementesToken(List<TokenMO> tokensMO)
  {
    if (tokensMO == null)
      return null;
    
    List<Map<ChaveJSON, Object>> sementesLista = new ArrayList<Map<ChaveJSON, Object>>();
    for(TokenMO tokenMO : tokensMO)
    {
      sementesLista.add(mapeiaSementeToken(tokenMO));
    }

    return sementesLista;
  }
  
  /**
   * Mapeia semente de um Token.
   * 
   * @param tokenMO
   * @return
   */
  public static Map<ChaveJSON, Object> mapeiaSementeToken(TokenMO tokenMO)
  {
    Map<ChaveJSON, Object> dado = new HashMap<ChaveJSON, Object>();
    dado.put(ChaveJSON.PC, tokenMO.getNumeroToken());
    dado.put(ChaveJSON.AL, tokenMO.getCodigoAlgoritmo());
    dado.put(ChaveJSON.SM, tokenMO.getSemente());
    
    return dado;
  }

  
  /**
   * Monta o List de map padrão para transforma em Json.
   * 
   * @param relatoriosMO
   * @return
   */
  public static List<Map<ChaveJSON, Object>> mapeiaRelatorios(List<RelatorioMO> relatoriosMO)
  {
    if (relatoriosMO == null)
      return null;
    
    List<Map<ChaveJSON, Object>> relatorioLista = new ArrayList<Map<ChaveJSON, Object>>();
    for(RelatorioMO relatorioMO : relatoriosMO)
    {
      Map<ChaveJSON, Object> dado = new HashMap<ChaveJSON, Object>();
      
      dado.put(ChaveJSON.MS, Util.formataDataMesAno(new DateTime(relatorioMO.getMes()/100, relatorioMO.getMes()%100, 1, 0, 0)));
      dado.put(ChaveJSON.NA, relatorioMO.getNomeArquivo());
      
      relatorioLista.add(dado);
    }

    return relatorioLista;
  }
  
  /**
   * Mapeia uma exceção.
   * 
   * @param rne
   * @return
   */
  public static Map<ChaveJSON, Object> mapeiaExcecao(EstiveAquiException rne)
  {
    Map<ChaveJSON, Object> dado = new HashMap<ChaveJSON, Object>();
    dado.put(ChaveJSON.CE, rne.getCodigoErro().getCodigoErro());
    dado.put(ChaveJSON.ME, rne.getCodigoErro().getDescricao());
    dado.put(ChaveJSON.LG, rne.getMessage());

    return dado;
  }
}
