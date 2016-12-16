package estiveaqui.appgestor.gerencia.usuario;

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
import estiveaqui.appgestor.DadosAppGestorInVO;
import estiveaqui.appgestor.DadosGerenciaisInVO;
import estiveaqui.appgestor.RegraNegocioGestor;
import estiveaqui.servidor.util.GeraChaves;
import estiveaqui.sql.AppUsuarioDB;
import haroldo.util.sql.ConexaoDB;
import estiveaqui.sql.mo.AppGestorMO;
import estiveaqui.sql.mo.AppUsuarioMO;
import estiveaqui.vo.DadosInVO;

public class GerenciaAppUsuario extends RegraNegocioGestor
{
  private static final Logger log = LogManager.getLogger();

  @Override
  public GerenciaAppUsuarioOutVO acao(DadosInVO dadosInVo) throws EstiveAquiException
  {
    String idLog = "";
    if (log.isInfoEnabled())
      idLog = ((DadosAppGestorInVO)dadosInVo).getIdentificadorAppGestor().substring(0, 8);
    log.info("Entrando em GerenciaAppUsuario.acao({}..., {})", idLog, ((DadosGerenciaisInVO)dadosInVo).getAcao());

    GerenciaAppUsuarioInVO gerenciaAppUsuarioInVo = (GerenciaAppUsuarioInVO) dadosInVo;
    GerenciaAppUsuarioOutVO gerenciaAppUsuarioOutVo = new GerenciaAppUsuarioOutVO();

    ConexaoDB connDB = null;
    try
    {
      //  Valida a versão do app.
      Versao.validaVersao(gerenciaAppUsuarioInVo, new Versao(1, 0, 0), new Versao(1, 0, 0));

      //
      //  ValidaçÃµes com acesso ao BD.
      ///////////////////////////////////////////////////////////////////////////
      connDB = new ConexaoDB("jdbc/EstiveAqui");

      //  Gestor existe e está habilitado?
      AppGestorMO appGestorMO = validaGestor(connDB, gerenciaAppUsuarioInVo);

      //  Gestor pode gerenciar este AppUsuario?
      AppUsuarioMO appUsuarioMO = null;
      if (!gerenciaAppUsuarioInVo.getAcao().equals("CAD"))  //  Se não é um cadastro de AppUsuario.
        appUsuarioMO = gerenciaAppUsuario(connDB, appGestorMO.getIdAppGestor(), gerenciaAppUsuarioInVo.getIdAppUsuario());

      //  Qual a ação?
      log.debug("Gerenciando AppUsuario com a ação \"{}\"", gerenciaAppUsuarioInVo.getAcao());
      switch (gerenciaAppUsuarioInVo.getAcao())
      {
        //  Cadastra um AppUsuario físico.
        case "CAD":
          gerenciaAppUsuarioOutVo = cadastra(connDB, appGestorMO.getIdAppGestor(), gerenciaAppUsuarioInVo);
          break;
        //  Atualiza AppUsuario.
        case "UPD":
          gerenciaAppUsuarioOutVo = atualiza(connDB, appGestorMO.getIdAppGestor(), appUsuarioMO, gerenciaAppUsuarioInVo);
          break;
        //  Desabilita AppUsuario.
        case "DIS":
          gerenciaAppUsuarioOutVo = desabilita(connDB, appUsuarioMO);
          break;
      }

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
      log.info("Saindo de GerenciaAppUsuario.acao({}...)", idLog);
    }

    return gerenciaAppUsuarioOutVo;
  }

  /**
   * Valida o AppUsuario.
   * 
   * @param connDB
   * @param idAppGestor
   * @param idAppUsuario
   * @return
   * @throws SQLException
   * @throws RegraDeNegocioException
   */
  private AppUsuarioMO gerenciaAppUsuario(ConexaoDB connDB, int idAppGestor, int idAppUsuario) throws SQLException, RegraDeNegocioException
  {
    AppUsuarioDB appUsuarioDb = new AppUsuarioDB(connDB);
    AppUsuarioMO appUsuarioMO = appUsuarioDb.leRegistroPK(idAppUsuario);

    //  AppUsuario existe.
    if (appUsuarioMO == null)
      throw new RegraDeNegocioException(CodigoErro.ERRO_INTERNO, "AppUsuario não existe");

    //  AppUsuario gerenciado?
    if (appUsuarioMO.getIdAppGestor() != 0)
    {
      //  O AppUsuario é gerenciado pelo gestor?  
      if (appUsuarioMO.getIdAppGestor() != idAppGestor)
        throw new RegraDeNegocioException(CodigoErro.ERRO_INTERNO, "AppUsuario gerenciado por outro gestor");
    }

    return appUsuarioMO;
  }

  /**
   * Cadastra um novo AppUsuario.
   * 
   * @param connDB
   * @param idAppGestor
   * @param gerenciaAppUsuarioInVo
   * @return
   * @throws SQLException
   * @throws RegraDeNegocioException
   */
  private GerenciaAppUsuarioOutVO cadastra(ConexaoDB connDB, int idAppGestor, GerenciaAppUsuarioInVO gerenciaAppUsuarioInVo) throws SQLException,
      RegraDeNegocioException
  {
    AppUsuarioDB appUsuarioDb = new AppUsuarioDB(connDB);

    //  ValidaçÃµes.
    validaAppUsuario(appUsuarioDb, idAppGestor, gerenciaAppUsuarioInVo);
    
    //  Prepara inserir usuário.
    AppUsuarioMO appUsuarioMO = new AppUsuarioMO();
    appUsuarioMO.setApelido(gerenciaAppUsuarioInVo.getApelido().trim());
    appUsuarioMO.setCodAtivacao(GeraChaves.codigoAtivacaoUsuario());
    appUsuarioMO.setIdAppGestor(idAppGestor);
    appUsuarioMO.setIdentificador(GeraChaves.identificadorAppUsuario());
    appUsuarioMO.setIdIntegracao(Util.nulifica(gerenciaAppUsuarioInVo.getIdIntegracao()));
    appUsuarioMO.setMaxLancamentosPorDia(gerenciaAppUsuarioInVo.getMaxLancamentosPorDia());
    appUsuarioMO.setStatus(AppUsuarioMO.STATUS_DESABILITADO);

    //  Insere o AppUsuario no BD.
    appUsuarioMO = appUsuarioDb.insereRegistro(appUsuarioMO);
    
    //  Atualiza o retorno.
    GerenciaAppUsuarioOutVO gerenciaAppUsuarioOutVo = new GerenciaAppUsuarioOutVO();
    gerenciaAppUsuarioOutVo.setAppUsuarioMO(appUsuarioMO);

    return gerenciaAppUsuarioOutVo;
  }

  /**
   * Atualiza o apelido, identificação externa e quantidade máxima  de lançamentos por dia.
   * 
   * @param connDB
   * @param appUsuarioMO
   * @param gerenciaAppUsuarioInVo
   * @return
   * @throws SQLException
   * @throws RegraDeNegocioException
   */
  private GerenciaAppUsuarioOutVO atualiza(ConexaoDB connDB, int idAppGestor, AppUsuarioMO appUsuarioMO, GerenciaAppUsuarioInVO gerenciaAppUsuarioInVo) 
                throws SQLException, RegraDeNegocioException
  {
    GerenciaAppUsuarioOutVO gerenciaAppUsuarioOutVo = new GerenciaAppUsuarioOutVO();
    AppUsuarioDB appUsuarioDb = new AppUsuarioDB(connDB);

    //  AppUsuario existe?
    if (appUsuarioMO == null)
      throw new RegraDeNegocioException(CodigoErro.ERRO_INTERNO, "AppUsuario não encontrado");

    //  ValidaçÃµes.
    validaAppUsuario(appUsuarioDb, idAppGestor, gerenciaAppUsuarioInVo);
    
    //  Atualia o MO.
    appUsuarioMO.setApelido(gerenciaAppUsuarioInVo.getApelido());
    appUsuarioMO.setMaxLancamentosPorDia(gerenciaAppUsuarioInVo.getMaxLancamentosPorDia());
    appUsuarioMO.setIdIntegracao(Util.nulifica(gerenciaAppUsuarioInVo.getIdIntegracao()));
    
    //  Atualiza o AppUsuario no BD.
    appUsuarioMO = appUsuarioDb.atualizaRegistroPK(appUsuarioMO);

    //  Prepara a resposta.
    gerenciaAppUsuarioOutVo.setAppUsuarioMO(appUsuarioMO);

    return gerenciaAppUsuarioOutVo;
  }

  /**
   * Desabilita o AppUsuario.
   * 
   * @param connDB
   * @param appUsuarioMO
   * @return
   * @throws SQLException
   * @throws RegraDeNegocioException
   */
  private GerenciaAppUsuarioOutVO desabilita(ConexaoDB connDB, AppUsuarioMO appUsuarioMO) throws SQLException, RegraDeNegocioException
  {
    GerenciaAppUsuarioOutVO gerenciaAppUsuarioOutVo = new GerenciaAppUsuarioOutVO();
    AppUsuarioDB appUsuarioDb = new AppUsuarioDB(connDB);

    //  AppUsuario existe?
    if (appUsuarioMO == null)
      throw new RegraDeNegocioException(CodigoErro.ERRO_INTERNO, "AppUsuario não encontrado");

    //  Define o status do AppUsuario.
    appUsuarioMO = appUsuarioDb.desabilitaAppUsuario(appUsuarioMO.getIdAppUsuario(), GeraChaves.codigoAtivacaoUsuario());

    //  Prepara o retorno.
    gerenciaAppUsuarioOutVo.setAppUsuarioMO(appUsuarioMO);

    return gerenciaAppUsuarioOutVo;
  }
  
  /**
   * Valida se o apelido ou identificação externa de AppUsuario já existe.
   * 
   * @param appUsuarioDb
   * @param idAppGestor
   * @param gerenciaAppUsuarioInVo
   * @throws RegraDeNegocioException
   * @throws SQLException
   */
  private void validaAppUsuario(AppUsuarioDB appUsuarioDb, int idAppGestor, GerenciaAppUsuarioInVO gerenciaAppUsuarioInVo) throws RegraDeNegocioException, SQLException
  {
    //  Busca os appusuarios do gestor.
    ArrayList<AppUsuarioMO> appUsuariosMO = appUsuarioDb.leRegistrosGestor(idAppGestor);

    //  Valida AppUsuario.
    for (AppUsuarioMO appUsuarioMO : appUsuariosMO)
    {
      if (appUsuarioMO.getIdAppUsuario() == gerenciaAppUsuarioInVo.getIdAppUsuario())
        continue;
      
      //  Apelido para AppUsuario já existe?
      if (appUsuarioMO.getApelido().equals(gerenciaAppUsuarioInVo.getApelido()))
        throw new RegraDeNegocioException(CodigoErro.APELIDO_JA_CADASTRADO);
      //  Identificação externa já existe?
      if (appUsuarioMO.getIdIntegracao() != null)
        if (appUsuarioMO.getIdIntegracao().equals(gerenciaAppUsuarioInVo.getIdIntegracao()))
          throw new RegraDeNegocioException(CodigoErro.IDENTIFICACAO_JA_CADASTRADA);
    }
  }

}
