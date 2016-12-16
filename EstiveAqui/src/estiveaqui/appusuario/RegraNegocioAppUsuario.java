package estiveaqui.appusuario;

import java.sql.SQLException;
import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;
import estiveaqui.RegraDeNegocio;
import estiveaqui.sql.AppUsuarioDB;
import estiveaqui.sql.mo.AppUsuarioMO;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

/**
 * Implementa a identificação padrão para o AppUsuario. <BR>
 * Todas as classes que implementam as regras de negócio do AppUsuario.
 * 
 * @author Haroldo
 *
 */
public abstract class RegraNegocioAppUsuario extends RegraDeNegocio
{
  /**
   * Método a ser desenvolvido dependendo das regras de negócio para cada operação que precisarem ser implementadas.
   * 
   * @param dadosInVo
   * @return
   * @throws EstiveAquiException 
   */
  public abstract DadosOutVO acao(DadosInVO dadosInVo) throws EstiveAquiException;

  /**
   * Implementa a validação padrão para a identificação do gestor.
   * 
   * @param connDB
   * @param leUsuarioInVO
   * @throws SQLException
   * @throws EstiveAquiException 
   */
  protected AppUsuarioMO validaAppUsuario(AppUsuarioDB appUsuarioDb, DadosAppUsuarioInVO leUsuarioInVO) throws SQLException, EstiveAquiException
  {
    AppUsuarioMO appUsuarioMO = appUsuarioDb.buscaRegistroIdentificador(leUsuarioInVO.getIdentificacaoApp());

    //  Usuario encontrado?
    if (appUsuarioMO == null)
      throw new EstiveAquiException(CodigoErro.APPUSUARIO_NAO_HABILITADO, "Usuario não existe");

    //  Usuario habilitado?
    if (appUsuarioMO.getStatus() != AppUsuarioMO.STATUS_HABILITADO)
      throw new EstiveAquiException(CodigoErro.APPUSUARIO_NAO_HABILITADO, "Usuario não habilitado");

    return appUsuarioMO;
  }
}
