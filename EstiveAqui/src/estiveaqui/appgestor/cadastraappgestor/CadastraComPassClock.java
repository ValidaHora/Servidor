package estiveaqui.appgestor.cadastraappgestor;

import haroldo.util.sql.ConexaoDB;
import java.sql.SQLException;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;
import estiveaqui.RegraDeNegocioException;
import estiveaqui.Versao;
import estiveaqui.appgestor.DadosAppGestorInVO;
import estiveaqui.appgestor.RegraNegocioGestor;
import estiveaqui.servidor.util.GeraChaves;
import estiveaqui.sql.AppGestorDB;
import estiveaqui.sql.AppUsuarioDB;
import estiveaqui.sql.LancamentoDB;
import estiveaqui.sql.PassClockDB;
import estiveaqui.sql.mo.AppGestorMO;
import estiveaqui.sql.mo.PassClockMO;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

public class CadastraComPassClock extends RegraNegocioGestor
{
  private static final Logger log = LogManager.getLogger();

  /**
   * Cadastra um gestor a partir de um PassClock e sua senha.
   * 
   * @param cadastraPassClockInVO
   * @return
   * @throws EstiveAquiException 
   */
  @Override
  public DadosOutVO acao(DadosInVO dadosInVo) throws EstiveAquiException
  {
    String idLog = "";
    if (log.isInfoEnabled())
      idLog = ((DadosAppGestorInVO)dadosInVo).getIdentificadorAppGestor().substring(0, 8);
    log.info("Entrando em CadastraComPassClock.acao({}...)", idLog);
    
    CadastraComPassClockInVO cadastraComPassClockInVO = (CadastraComPassClockInVO) dadosInVo;
    CadastraComPassClockOutVO cadastraComPassClockOutVO = new CadastraComPassClockOutVO();

    ConexaoDB connDB = null;
    try
    {
      //  Valida a versão do app.
      Versao.validaVersao(cadastraComPassClockInVO, new Versao(1, 0, 0), new Versao(1, 0, 0));

      //
      //  Validações com acesso ao BD.
      ///////////////////////////////////////////////////////////////////////////
      connDB = new ConexaoDB("jdbc/EstiveAqui");

      //  Busca PassClock.
      PassClockDB passClockDb = new PassClockDB(connDB);
      PassClockMO passClockMO = passClockDb.buscaPorNumeroPassClock(cadastraComPassClockInVO.getNumeroPassClock());
      
      //  PassClock existe?
      if (passClockMO == null)
        throw new RegraDeNegocioException(CodigoErro.PASSCLOCK_NAO_EXISTE, "PassClock não existe");
     
      //  PassClock habilitado para uso?
      if (passClockMO.getStatus() != PassClockMO.STATUS_HABILITADO && passClockMO.getStatus() != PassClockMO.STATUS_ENTREGUE)
        throw new RegraDeNegocioException(CodigoErro.PASSCLOCK_DESABILITADO, "PassClock de número " + cadastraComPassClockInVO.getNumeroPassClock() + " não habilitado para uso");

      //  Senha do PassClock está correta?
      if (!passClockMO.getSenhaCadastro().equals(cadastraComPassClockInVO.getSenhaCadastro()))
        throw new RegraDeNegocioException(CodigoErro.SENHA_INCORRETA);
        
      //  PassClock já tem um gestor? 
      AppGestorDB appGestorDb = new AppGestorDB(connDB);
      AppGestorMO appGestorMO = new AppGestorMO();
      //  Atualiza gestor.
      if (passClockMO.getIdAppGestor() != 0)
      {
        appGestorDb.atualizaIdenficiador(passClockMO.getIdAppGestor(), GeraChaves.identificadorAppGestor());
        appGestorMO = appGestorDb.buscaRegistroPK(passClockMO.getIdAppGestor());
      }
      else  //  Se não, cadastra novo gestor.
      {
        appGestorMO.setIdentificadorAppGestor(GeraChaves.identificadorAppGestor());
        //  Inclui o novo gestor.
        appGestorMO = appGestorDb.insereRegistro(appGestorMO);
        //  Associa o PassClock a este novo gestor.
        passClockDb.defineAppGestor(passClockMO.getIdPassClock(), appGestorMO.getIdAppGestor());
      }

//      //  Habilita o PassClock para uso.
//      if (passClockMO.getStatus() == PassClockMO.STATUS_ENTREGUE)
//        passClockDb.habilita(passClockMO.getIdPassClock());
//
      //  Encerra a transação.
      connDB.getConn().commit();
      
      //
      //  Monta as informações para serem retornadas.
      ///////////////////////////////////////////////////////////////////////////
      
      //  Retorna AppGestorMO
      cadastraComPassClockOutVO.setAppGestorMO(appGestorMO);
      
      //  Busca todos os appUsuarios deste gestor.
      AppUsuarioDB appUsuarioDB = new AppUsuarioDB(connDB);
      cadastraComPassClockOutVO.setAppUsuariosMO(appUsuarioDB.leRegistrosGestor(appGestorMO.getIdAppGestor()));

      //  Busca todos os passClocks deste gestor.
      PassClockDB passClockDB = new PassClockDB(connDB);
      cadastraComPassClockOutVO.setPassClocksMO(passClockDB.leRegistrosGestor(appGestorMO.getIdAppGestor()));

      //  Busca todos os lançamentos desde o mês passado.
      LancamentoDB lancamentoDB = new LancamentoDB(connDB);
      cadastraComPassClockOutVO.setLancamentosMO(lancamentoDB.leLancamentosGestor2UltimosMeses(appGestorMO.getIdAppGestor()));
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
      log.info("Saindo de CadastraComPassClock.acao({}...)", idLog);
      if (connDB != null)
        connDB.fechaConexao();
    }

    return cadastraComPassClockOutVO;
  }
}
