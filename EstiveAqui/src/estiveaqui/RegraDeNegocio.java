package estiveaqui;

import haroldo.util.seguranca.SegUtil;
import haroldo.util.sql.ConexaoDB;
import java.sql.SQLException;
import java.util.ArrayList;
import org.joda.time.DateTime;
import estiveaqui.sql.LancamentoDB;
import estiveaqui.sql.mo.LancamentoMO;

public class RegraDeNegocio
{
  /**
   * Valida o hashcode e se o código já foi lançado no sistema.
   * 
   * @param connDB
   * @param numPassClock
   * @param codigo
   * @param hashCode
   * @param hora
   * @throws RegraDeNegocioException 
   * @throws SQLException 
   */
  @Deprecated
  protected void validaCodigoLancado(ConexaoDB connDB, String numPassClock, String codigo, String hashCode, DateTime hora) throws RegraDeNegocioException, SQLException
  {
    //  Valida o checksum do hashcode.
    validaCheckSum(hashCode);

    //  Verifica se o código já foi lançado.
    LancamentoDB lancamentoDb = new LancamentoDB(connDB);

    ArrayList<LancamentoMO> lancamentosDiaMO = lancamentoDb.leLancamentosPassClockEm(numPassClock, hora);
    for (LancamentoMO lancamentoDiaMO : lancamentosDiaMO)
    {
      if (lancamentoDiaMO.getNumPassClock().equals(numPassClock))
      {
        if (codigo.equals(lancamentoDiaMO.getCodPassClock()) || hashCode.equals(lancamentoDiaMO.getHashCode()))
          throw new RegraDeNegocioException(CodigoErro.CODIGO_JA_LANCADO, "Código já lançado no dia");
      }
    }
    
  }

  /**
   * Valida o checksum de um HashCode do lançamento de hora pelo ValidaHora.
   * 
   * @param hashCode - Hash code a ser validado
   * @throws RegraDeNegocioException - Se não passar pela validação
   */
  @Deprecated
  protected void validaCheckSum(String hashCode) throws RegraDeNegocioException
  {
    //  Valida o checksum do hashcode.
    if (hashCode.length() != 40 || ! calculaCheckSum(hashCode.substring(0, 32)).equals(hashCode.substring(32)))
      throw new RegraDeNegocioException(CodigoErro.ERRO_INTERNO, "Hash code inválido \"{0}\"", hashCode);
  }
  
  /**
   * Calcula o checksum do hashCode.
   * 
   * @param hashCode
   * @return
   */
  @Deprecated
  private String calculaCheckSum(String hashCode)
  {
    return SegUtil.calculaCheckSum(hashCode);
  }
  
//  public static void main(String args[]) throws RegraDeNegocioException
//  {
//    RegraDeNegocio rn = new RegraDeNegocio();
//    
//    rn.validaCheckSum("f6b8ea52b5b8c67ee98c90704dcfbba30a616022");
//  }
}
