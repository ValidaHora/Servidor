package estiveaqui.apppassclockvirtual;

import java.sql.SQLException;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;
import estiveaqui.RegraDeNegocioException;
import estiveaqui.Versao;
import haroldo.util.sql.ConexaoDB;
import estiveaqui.sql.PassClockDB;
import estiveaqui.sql.mo.PassClockMO;

public class AtivaPassClockVirtual
{
  private static final Logger log = LogManager.getLogger();

  public AtivaPassClockVirtualOutVO cadastra(AtivaPassClockVirtualInVO ativaPassClockVirtualInVO) throws EstiveAquiException
  {
    log.info("Entrando em AtivaPassClockVirtual.cadastra()");
    AtivaPassClockVirtualOutVO ativaPassClockVirtualOutVO = new AtivaPassClockVirtualOutVO();

    ConexaoDB connDB = null;
    try
    {
      //  Valida a versão do app.
      
      Versao.validaVersao(ativaPassClockVirtualInVO, new Versao(1, 0, 0), new Versao(1, 0, 0));

      //
      //  ValidaçÃµes com acesso ao BD.
      ///////////////////////////////////////////////////////////////////////////
      connDB = new ConexaoDB("jdbc/EstiveAqui");

      PassClockDB passClockDB = new PassClockDB(connDB);
      PassClockMO passClockMO = passClockDB.buscaCodigoAtivacao(ativaPassClockVirtualInVO.getCodigoAtivacao());
      
      //  Código de ativação encontrado?
      if (passClockMO == null)
        throw new RegraDeNegocioException(CodigoErro.ERRO_INTERNO, "Código de ativação não encontrado");
        
      //  Habilita o PassClock.
      passClockDB.habilita(passClockMO.getIdPassClock());
      passClockMO.setStatus(PassClockMO.STATUS_HABILITADO);
      ativaPassClockVirtualOutVO.setPassClockMO(passClockMO);
      
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

    log.info("Saindo de AtivaPassClockVirtual.cadastra()");
    return ativaPassClockVirtualOutVO;
  }
}
