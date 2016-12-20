package estiveaqui.appgestor;

import java.sql.SQLException;
import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;
import estiveaqui.RegraDeNegocio;
import estiveaqui.RegraDeNegocioException;
import estiveaqui.sql.AppGestorDB;
import haroldo.util.sql.ConexaoDB;
import estiveaqui.sql.mo.AppGestorMO;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

/**
 * Implementa a identifica��o padr�o para o gestor. <BR>
 * Todas as classes que implementam as regras de neg�cio do AppGestor e que usam o identificador de AppGestor devem extender esta classe.
 * 
 * @author Haroldo
 *
 */
public abstract class RegraNegocioGestor extends RegraDeNegocio
{
  /**
   * M�todo a ser desenvolvido dependendo das regras de neg�cio para cada opera��o que precisarem ser implementadas.
   * 
   * @param dadosInVo
   * @return
   * @throws EstiveAquiException 
   */
  public abstract DadosOutVO acao(DadosInVO dadosInVo) throws EstiveAquiException;

  /**
   * Implementa a valida��o padr�o para a identifica��o do gestor.
   * 
   * @param connDB
   * @param leGestorInVO
   * @throws RegraDeNegocioException
   * @throws SQLException
   */
  protected AppGestorMO validaGestor(ConexaoDB connDB, DadosAppGestorInVO leGestorInVO) throws RegraDeNegocioException, SQLException
  {
    AppGestorDB appGestorDb = new AppGestorDB(connDB);
    AppGestorMO appGestorMO = appGestorDb.buscaRegistroIdentificador(leGestorInVO.getIdentificadorAppGestor());

    //  Gestor encontrado?
    if (appGestorMO == null)
      throw new RegraDeNegocioException(CodigoErro.DESABILITA_APP_GESTOR, "Gestor n�o existe");

    //  Gestor habilitado?
    if (appGestorMO.getStatus() != AppGestorMO.STATUS_HABILITADO)
      throw new RegraDeNegocioException(CodigoErro.APPGESTOR_NAO_HABILITADO, "Gestor n�o habilitado");

    //  Gestor j� validou a senha?
    if (appGestorMO.getCodValidacaoEMail() != null)
      throw new RegraDeNegocioException(CodigoErro.EMAIL_NAO_VALIDADO, "Gestor n�o validou a senha");
    
    return appGestorMO;
  }
}
