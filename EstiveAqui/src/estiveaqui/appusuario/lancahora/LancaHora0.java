package estiveaqui.appusuario.lancahora;

import haroldo.util.sql.ConexaoDB;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;
import estiveaqui.RegraDeNegocioException;
import estiveaqui.Util;
import estiveaqui.Versao;
import estiveaqui.appusuario.RegraNegocioAppUsuario;
import estiveaqui.sql.AppUsuarioDB;
import estiveaqui.sql.LancamentoDB;
import estiveaqui.sql.PassClockDB;
import estiveaqui.sql.RelatorioDB;
import estiveaqui.sql.mo.AppUsuarioMO;
import estiveaqui.sql.mo.LancamentoMO;
import estiveaqui.sql.mo.PassClockMO;
import estiveaqui.vo.DadosInVO;

@Deprecated
public class LancaHora0  extends RegraNegocioAppUsuario
{
  private static final Logger log = LogManager.getLogger();

  /**
   * Valida e lança a hora.
   * 
   * @param lancaHoraInVO
   * @return
   * @throws RegraDeNegocioException
   */
  @Deprecated
  @Override
  public LancaHoraOutVO acao(DadosInVO dadosInVo) throws EstiveAquiException
  {
    LancaHoraInVO lancaHoraInVO = (LancaHoraInVO) dadosInVo;
    log.info("Entrando em LancaHora.acao({}, {})", lancaHoraInVO.getNumPassClock(), lancaHoraInVO.getHoraCalculada());
    LancaHoraOutVO lancaHoraOutVO = new LancaHoraOutVO();
    
    ConexaoDB connDB = null;
    try
    {
      //  Valida a versão do app.
      Versao.validaVersao(lancaHoraInVO, new Versao(1, 0, 0), new Versao(1, 0, 0));

      //
      //  ValidaçÃµes com acesso ao BD.
      ///////////////////////////////////////////////////////////////////////////
      connDB = new ConexaoDB("jdbc/EstiveAqui");

      //  Busca o usuário no BD.
      AppUsuarioDB appUsuarioDb = new AppUsuarioDB(connDB);
      
      //  Valida o AppUsuario
      AppUsuarioMO appUsuarioMO = validaAppUsuario(appUsuarioDb, lancaHoraInVO);
      lancaHoraOutVO.setAppUsuarioMO(appUsuarioMO);
      
      //  Busca os lançamentos no BD.
      LancamentoDB lancamentoDb = new LancamentoDB(connDB);
      ArrayList<LancamentoMO> lancamentosMO = lancamentoDb.leLancamentosUsuarioHoje(appUsuarioMO.getIdAppUsuario(), lancaHoraInVO.getTzPassClock());
      
      //  Já atingiu o limite de lançamentos no dia?
      int qtd = appUsuarioMO.getMaxLancamentosPorDia();
      if (qtd >= 0 && lancamentosMO.size() >= qtd)
        throw new RegraDeNegocioException(CodigoErro.MAX_LANCAMENTOS_DIARIO, "Atingida a quantidade máxima de lançamentos diário");
      
      //  Busca o PassClock utilizados por para lançar hora.
      PassClockDB passClockDb = new PassClockDB(connDB);
      PassClockMO passClockMO = passClockDb.buscaPorNumeroPassClock(lancaHoraInVO.getNumPassClock());
      
      //  PassClock não encontrado?
      if (passClockMO == null)
        throw new RegraDeNegocioException(CodigoErro.ERRO_INTERNO, "PassClock \"{0}\" não existe", lancaHoraInVO.getNumPassClock());
      
      //  PassClock associado ao usuário? (Do mesmo gestor?)
      if (appUsuarioMO.getIdAppGestor() != passClockMO.getIdAppGestor())
        throw new RegraDeNegocioException(CodigoErro.ERRO_INTERNO, "PassClock \"{0}\" não associado ao AppUsuário \"{1}\"", lancaHoraInVO.getNumPassClock(), lancaHoraInVO.getIdentificacaoApp());

      //  PassClock habilitado?
      if (passClockMO.getStatus() != PassClockMO.STATUS_HABILITADO)
        throw new RegraDeNegocioException(CodigoErro.PASSCLOCK_DESABILITADO, "PassClock \"{0}\" não está habilitado.", lancaHoraInVO.getNumPassClock());
      
      //  Valida se o código já foi lançado e se  hashcode é válido.
      validaCodigoLancado(connDB, passClockMO.getNumPassClock(), lancaHoraInVO.getCodigo(), lancaHoraInVO.getHashCode(), lancaHoraInVO.getHoraCalculada());

      //  Inclui o lançamento no BD.
      LancamentoMO lancamentoMO = new LancamentoMO();
      lancamentoMO.setAppUsuarioMO(appUsuarioMO);
      lancamentoMO.setNumPassClock(lancaHoraInVO.getNumPassClock());
      lancamentoMO.setApelidoPassClock(passClockMO.getApelido());
      lancamentoMO.setCodPassClock(lancaHoraInVO.getCodigo());
      lancamentoMO.setHashCode(lancaHoraInVO.getHashCode());
      lancamentoMO.setNota(lancaHoraInVO.getNota());
      lancamentoMO.setTzPassClock(lancaHoraInVO.getTzPassClock());
      lancamentoMO.setHrLancamento(lancaHoraInVO.getHoraCalculada());
      lancamentoMO.setHrPassClock(lancaHoraInVO.getHoraCalculada());
      lancamentoMO.setHrDigitacao(lancaHoraInVO.getHoraDigitada());
      lancamentoMO.setHrEnvio(lancaHoraInVO.getHoraEnviada());
      lancamentoMO.setIdDispositivo(lancaHoraInVO.getIdDispositivo());
      lancamentoMO.setIpDispositivo(lancaHoraInVO.getIdDispositivo());
      lancamentoMO.setLatitude(lancaHoraInVO.getLatitude());
      lancamentoMO.setLongitude(lancaHoraInVO.getLongitude());

      lancamentoMO = lancamentoDb.insereRegistro(lancamentoMO);
      
      //  Retorna a informação do lançamento incluído.
      lancaHoraOutVO.setLancamentoMO(lancamentoMO);
      
      //  Atualiza a tabela de relatório para esta hora lançada.
      RelatorioDB relatorioDb = new RelatorioDB(connDB);
      relatorioDb.marcaComoNaoGerado(appUsuarioMO.getIdAppGestor(), Util.paraMesAno(lancamentoMO.getHrLancamento()));
      
      //  Encerra a transação.
      connDB.getConn().commit();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw new RegraDeNegocioException(CodigoErro.ERRO_INTERNO, e.getMessage());
    }
    catch (NamingException e)
    {
      e.printStackTrace();
      throw new RegraDeNegocioException(CodigoErro.ERRO_INTERNO, e.getMessage());
    }
    finally
    {
      if (connDB != null)
        connDB.fechaConexao();
    }
    
    log.info("Saindo de LancaHora.acao({}, {})", lancaHoraInVO.getNumPassClock(), lancaHoraInVO.getHoraCalculada());
    return lancaHoraOutVO;
  }
}
