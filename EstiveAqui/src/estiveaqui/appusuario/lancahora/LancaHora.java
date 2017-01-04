package estiveaqui.appusuario.lancahora;

import haroldo.util.sql.ConexaoDB;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;
import estiveaqui.RegraDeNegocioException;
import estiveaqui.Util;
import estiveaqui.Versao;
import estiveaqui.appusuario.RegraNegocioAppUsuario;
import estiveaqui.http.HTTPValidaHora;
import estiveaqui.sql.AppUsuarioDB;
import estiveaqui.sql.LancamentoDB;
import estiveaqui.sql.PassClockDB;
import estiveaqui.sql.RelatorioDB;
import estiveaqui.sql.mo.AppUsuarioMO;
import estiveaqui.sql.mo.LancamentoMO;
import estiveaqui.sql.mo.PassClockMO;
import estiveaqui.vo.DadosInVO;

public class LancaHora extends RegraNegocioAppUsuario
{
  private static final Logger log = LogManager.getLogger();

  /**
   * Valida e lança a hora.
   * 
   * @param lancaHoraInVO
   * @return
   * @throws RegraDeNegocioException
   */
  @Override
  public LancaHoraOutVO acao(DadosInVO dadosInVo) throws EstiveAquiException
  {
    LancaHoraInVO lancaHoraInVO = (LancaHoraInVO) dadosInVo;
    log.info("Entrando em LancaHora.acao({}, {})", lancaHoraInVO.getNumPassClock(), lancaHoraInVO.getHoraDigitada());
    LancaHoraOutVO lancaHoraOutVO = new LancaHoraOutVO();

    ConexaoDB connDB = null;
    try
    {
      //  Valida a versão do app.
      Versao.validaVersao(lancaHoraInVO, new Versao(1, 1, 0), new Versao(1, 1, 0));

      //
      //  Validações com acesso ao BD.
      ///////////////////////////////////////////////////////////////////////////
      connDB = new ConexaoDB("jdbc/EstiveAqui");

      //  Busca o usuário no BD.
      AppUsuarioDB appUsuarioDb = new AppUsuarioDB(connDB);

      //  Valida o AppUsuario
      AppUsuarioMO appUsuarioMO = validaAppUsuario(appUsuarioDb, lancaHoraInVO);
      lancaHoraOutVO.setAppUsuarioMO(appUsuarioMO);

      //  Valida o lançamento.
      PassClockMO passClockMO = verificaValidadeLancamento(connDB, lancaHoraInVO, appUsuarioMO);
      
//      verificaValidadeLancamento(lancaHoraInVO);
//      //  Busca o PassClock utilizados por para lançar hora.
//      PassClockDB passClockDb = new PassClockDB(connDB);
//      PassClockMO passClockMO = passClockDb.buscaPorNumeroPassClock(lancaHoraInVO.getNumPassClock());
//
//      //  PassClock não encontrado?
//      if (passClockMO == null)
//        throw new RegraDeNegocioException(CodigoErro.ERRO_INTERNO, "PassClock \"{0}\" não existe", lancaHoraInVO.getNumPassClock());
//
//      //  PassClock associado ao usuário? (Do mesmo gestor?)
//      if (appUsuarioMO.getIdAppGestor() != passClockMO.getIdAppGestor())
//        throw new RegraDeNegocioException(CodigoErro.ERRO_INTERNO, "PassClock \"{0}\" não associado ao AppUsuário \"{1}\"", lancaHoraInVO.getNumPassClock(),
//            lancaHoraInVO.getIdentificacaoApp());
//
//      //  PassClock habilitado?
//      if (passClockMO.getStatus() != PassClockMO.STATUS_HABILITADO)
//        throw new RegraDeNegocioException(CodigoErro.PASSCLOCK_DESABILITADO, "PassClock \"{0}\" não está habilitado.", lancaHoraInVO.getNumPassClock());
//
//      //  Busca os lançamentos no BD.
//      LancamentoDB lancamentoDb = new LancamentoDB(connDB);
//      ArrayList<LancamentoMO> lancamentosMO = lancamentoDb.leLancamentosUsuarioHoje(appUsuarioMO.getIdAppUsuario(), appUsuarioMO.getTz());
//
//      //  Já atingiu o limite de lançamentos no dia?
//      int qtd = appUsuarioMO.getMaxLancamentosPorDia();
//      if (qtd >= 0 && lancamentosMO.size() >= qtd)
//        throw new RegraDeNegocioException(CodigoErro.MAX_LANCAMENTOS_DIARIO, "Atingida a quantidade máxima de lançamentos diário");
//
//      //  Código já foi lançado?
//      List<LancamentoMO> lancamentosHoje = lancamentoDb.leLancamentosGestorHoje(appUsuarioMO.getIdAppGestor(), appUsuarioMO.getTz());
//      for (LancamentoMO lancMO : lancamentosHoje)
//      {
//        if (lancMO.getCodPassClock().equals(lancaHoraInVO.getCodigo()))
//          throw new RegraDeNegocioException(CodigoErro.CODIGO_JA_LANCADO);
//      }
//
      
      //  Chama o ValidaHora e calcula a hora a partir do código digitado.
      LancamentoMO lancamentoMO = HTTPValidaHora.calculaHora(lancaHoraInVO);

      
      //  Inclui o lançamento no BD.
      lancamentoMO.setAppUsuarioMO(appUsuarioMO);
      lancamentoMO.setNumPassClock(lancaHoraInVO.getNumPassClock());
      lancamentoMO.setApelidoPassClock(passClockMO.getApelido());
      lancamentoMO.setCodPassClock(lancaHoraInVO.getCodigo());
      lancamentoMO.setTzPassClock(passClockMO.getTz());
      lancamentoMO.setHrDigitacao(lancaHoraInVO.getHoraDigitada());
      lancamentoMO.setHrEnvio(lancaHoraInVO.getHoraEnviada());
      lancamentoMO.setIdDispositivo(lancaHoraInVO.getIdDispositivo());
      lancamentoMO.setIpDispositivo(lancaHoraInVO.getIdDispositivo());
      lancamentoMO.setLatitude(lancaHoraInVO.getLatitude());
      lancamentoMO.setLongitude(lancaHoraInVO.getLongitude());
      lancamentoMO.setTzPassClock(passClockMO.getTz());

      //  Insere o lançamento no BD.
      LancamentoDB lancamentoDb = new LancamentoDB(connDB);
      lancamentoMO = lancamentoDb.insereRegistro(lancamentoMO);

      //  Encerra a transação.
      connDB.getConn().commit();

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

    log.info("Saindo de LancaHora.acao({}, {})", lancaHoraInVO.getNumPassClock(), lancaHoraInVO.getHoraDigitada());
    return lancaHoraOutVO;
  }
  
  /**
   * Efetua uma série de verificações com relação a validade do lançamento.
   * 
   * @param connDB
   * @param lancaHoraInVO
   * @param appUsuarioMO
   * @return
   * @throws SQLException
   * @throws RegraDeNegocioException
   */
  public static PassClockMO verificaValidadeLancamento(ConexaoDB connDB, LancaHoraInVO lancaHoraInVO, AppUsuarioMO appUsuarioMO) throws SQLException, RegraDeNegocioException
  {
    //  Busca o PassClock utilizados por para lançar hora.
    PassClockDB passClockDb = new PassClockDB(connDB);
    PassClockMO passClockMO = passClockDb.buscaPorNumeroPassClock(lancaHoraInVO.getNumPassClock());

    //  PassClock não encontrado?
    if (passClockMO == null)
      throw new RegraDeNegocioException(CodigoErro.ERRO_INTERNO, "PassClock \"{0}\" não existe", lancaHoraInVO.getNumPassClock());

    //  PassClock associado ao usuário? (Do mesmo gestor?)
    if (appUsuarioMO.getIdAppGestor() != passClockMO.getIdAppGestor())
      throw new RegraDeNegocioException(CodigoErro.ERRO_INTERNO, "PassClock \"{0}\" não associado ao AppUsuário \"{1}\"", lancaHoraInVO.getNumPassClock(),
          lancaHoraInVO.getIdentificacaoApp());

    //  PassClock habilitado?
    if (passClockMO.getStatus() != PassClockMO.STATUS_HABILITADO)
      throw new RegraDeNegocioException(CodigoErro.PASSCLOCK_DESABILITADO, "PassClock \"{0}\" não está habilitado.", lancaHoraInVO.getNumPassClock());

    //  Busca os lançamentos no BD.
    LancamentoDB lancamentoDb = new LancamentoDB(connDB);
    List<LancamentoMO> lancamentosMO = lancamentoDb.leLancamentosUsuarioHoje(appUsuarioMO.getIdAppUsuario(), appUsuarioMO.getTz());

    //  Já atingiu o limite de lançamentos no dia?
    int qtd = appUsuarioMO.getMaxLancamentosPorDia();
    if (qtd >= 0 && lancamentosMO.size() >= qtd)
      throw new RegraDeNegocioException(CodigoErro.MAX_LANCAMENTOS_DIARIO, "Atingida a quantidade máxima de lançamentos diário");

    //  Código já foi lançado?
    List<LancamentoMO> lancamentosHoje = lancamentoDb.leLancamentosGestorHoje(appUsuarioMO.getIdAppGestor(), appUsuarioMO.getTz());
    for (LancamentoMO lancMO : lancamentosHoje)
    {
      if (lancMO.getCodPassClock().equals(lancaHoraInVO.getCodigo()))
        throw new RegraDeNegocioException(CodigoErro.CODIGO_JA_LANCADO);
    }
    
    return passClockMO;
  }
}
