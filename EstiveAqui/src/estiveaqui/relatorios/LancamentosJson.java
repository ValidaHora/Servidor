package estiveaqui.relatorios;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import estiveaqui.Util;
import estiveaqui.sql.mo.LancamentoMO;

public class LancamentosJson implements Lancamentos
{
  private static final Logger log = LogManager.getLogger();
  
  private JSONObject jsonLancamentos = new JSONObject();

  public LancamentosJson(int idAppGestor, DateTime mes)
  {
    jsonLancamentos.put("Gestor", idAppGestor);
    jsonLancamentos.put("Mes", Util.formataDataMesAno(mes));
    JSONObject jsonObj = new JSONObject();
    jsonObj.put("Data Geração Relatório", Util.formataDataComTZ(DateTime.now()));
  }
  
  /**
   * Gera um documento JSON com os lançamentos ordenados por usuários e data de lançamento
   * 
   * @param lancamentosMO
   * @return
   */
  public JSONArray montaLancamentosMes(ArrayList<LancamentoMO> lancamentosMO)
  {
    log.debug("Montando lançamentos em JSON.");

    JSONArray usuarios = new JSONArray();
    jsonLancamentos.put("Usuarios", usuarios);
    
    JSONObject usuario = null;
    JSONArray lancamentos = null;
    JSONObject lancamento = null;
    int idAppUsuarioAnterior = 0;
    
    //  Para cada usuário.
    for (LancamentoMO lancamentoMO : lancamentosMO)
    {
      //  Outro usuário, novos lançamentos.
      if (idAppUsuarioAnterior != lancamentoMO.getAppUsuarioMO().getIdAppUsuario())
      {
        idAppUsuarioAnterior = lancamentoMO.getAppUsuarioMO().getIdAppUsuario();
        
        usuario = new JSONObject();
        usuarios.put(usuario);
        
        lancamentos = new JSONArray();
        usuario.put("Apelido", lancamentoMO.getAppUsuarioMO().getApelido());
        usuario.put("Lancamentos", lancamentos);
      }
      
      //  Para cada lançamento.
      lancamento = new JSONObject();
      lancamentos.put(lancamento);
      
      lancamento.put("Hora Lancamento", Util.formataDataComTZ(lancamentoMO.getHrLancamento()));
      lancamento.put("ChaveJSON PassClock", lancamentoMO.getCodPassClock());
      lancamento.put("Status", lancamentoMO.getStatus());
      lancamento.put("Apelido PassClock", lancamentoMO.getApelidoPassClock());
      lancamento.put("Dispositivo", lancamentoMO.getIdDispositivo());
      lancamento.put("IP Lancamento", lancamentoMO.getIpDispositivo());
      lancamento.put("Latitude", lancamentoMO.getLatitude());
      lancamento.put("Longitude", lancamentoMO.getLongitude());
    }

    log.debug("Lançamentos em JSON montados: {}", usuarios);
    return usuarios;
  }

  @Override
  public void write(OutputStream out) throws IOException
  {
    out.write(getBytes());
  }

  @Override
  public byte[] getBytes()
  {
    return jsonLancamentos.toString().getBytes(Charset.forName("UTF-8"));
  }
  
  /**
   * Retorna o sufixo para o tipo de arquivo.
   * 
   * @return
   */
  public String getSufixo()
  {
    return "json";
  }
}
