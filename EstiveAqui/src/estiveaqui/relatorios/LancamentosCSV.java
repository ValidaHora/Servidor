package estiveaqui.relatorios;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.Util;
import estiveaqui.sql.mo.LancamentoMO;

/**
 * Gera arquivo com os lançamentos no formato CSV (Comma Separated Values), ou seja, separados por vírgula.
 * 
 * @author Haroldo
 *
 */
public class LancamentosCSV implements Lancamentos
{
  private static final Logger log = LogManager.getLogger();

  private ArrayList<String>lancamentos = new ArrayList<String>();
  
  /**
   * Gera lançamentos em CSV. Cada Map representa um usuário. Cada String do ArrayList representa mu lançamento.
   * 
   * @param lancamentosMO
   */
  public LancamentosCSV(ArrayList<LancamentoMO> lancamentosMO)
  {
    log.debug("Montando lançamentos em CSV.");
    for (LancamentoMO lancamentoMO : lancamentosMO)
    {
      //  Para cada lançamento.
      StringBuffer lancamento = new StringBuffer();
      lancamento.append(lancamentoMO.getAppUsuarioMO().getApelido());
      lancamento.append(",");
      lancamento.append(Util.formataDataComTZ(lancamentoMO.getHrLancamento()));
      lancamento.append(",");
      lancamento.append(lancamentoMO.getStatus());
      lancamento.append(",");
      lancamento.append(lancamentoMO.getCodPassClock());
      lancamento.append(",");
      lancamento.append(lancamentoMO.getApelidoPassClock());
      lancamento.append(",");
      lancamento.append(lancamentoMO.getIdDispositivo());
      lancamento.append(",");
      lancamento.append(lancamentoMO.getIpDispositivo());
      lancamento.append(",");
      lancamento.append(lancamentoMO.getLatitude());
      lancamento.append(",");
      lancamento.append(lancamentoMO.getLongitude());

      lancamentos.add(lancamento.toString());
    }
    log.debug("Lançamentos em CSV montados.");
  }

  /**
   * Retorna o relatório no formato ArrayList<String>, onde cada elemento do ArrayList é um registro de lançamento de hora.
   * 
   * @return
   */
  public ArrayList<String> relatorioArrayList()
  {
    return lancamentos;
  }
  
  /**
   * Retorna uma string com todos os lançamentos, separados por "\n".
   * 
   * @return
   */
  public String relatorioString()
  {
    StringBuffer relatorio = new StringBuffer();
    relatorio.append("Apelido,HoraLancamento,Status,CodigoPassClock,ApelidoPassClock,IdDispositivo,IPDispositivo,Latitude,Longitude\n");
    for (String lancamento : lancamentos)
      relatorio.append(lancamento).append("\n");
    
    return relatorio.toString();
  }
  
  /**
   * Persiste o arquivo CSV.
   * 
   * @param out
   * @throws IOException
   */
  public void write(OutputStream out) throws IOException
  {
    out.write(getBytes());
  }

  @Override
  public byte[] getBytes()
  {
    return relatorioString().getBytes(Charset.forName("UTF-8"));
  }
  /**
   * Retorna o sufixo para o tipo de arquivo.
   * 
   * @return
   */
  public String getSufixo()
  {
    return "csv";
  }
}
