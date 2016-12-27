package estiveaqui.appusuario.lancahoras;

import haroldo.util.sql.ConexaoDB;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.CodigoErro;
import estiveaqui.EstiveAquiException;
import estiveaqui.RegraDeNegocioException;
import estiveaqui.appusuario.RegraNegocioAppUsuario;
import estiveaqui.appusuario.lancahora.LancaHora;
import estiveaqui.appusuario.lancahora.LancaHoraInVO;
import estiveaqui.http.HTTPValidaHora;
import estiveaqui.sql.AppUsuarioDB;
import estiveaqui.sql.LancamentoDB;
import estiveaqui.sql.mo.AppUsuarioMO;
import estiveaqui.sql.mo.LancamentoMO;
import estiveaqui.sql.mo.PassClockMO;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

/**
 * Lança um conjunto de horas.<BR>
 * Difere do LancaHora porque esta classe lança várias horas ao mesmo tempo, 
 * enquanto aquele lança apenas uma hora.
 * 
 * @author Haroldo
 *
 */
public class LancaHoras extends RegraNegocioAppUsuario
{
  private static final Logger log = LogManager.getLogger();

  @Override
  public DadosOutVO acao(DadosInVO dadosInVo) throws EstiveAquiException
  {
    log.info("Entrando em LancaHoras.acao()");

    //  Prepara a resposta de todos os lançamentos de hora.
    LancaHorasInVO lancaHorasInVO = (LancaHorasInVO)dadosInVo;
    
    LancaHorasOutVO lancaHorasOutVO = new LancaHorasOutVO();

    Map<String, PassClockMO> passClocksMO = new HashMap<String, PassClockMO>();
    
    ConexaoDB connDB = null;
    try
    {
      connDB = new ConexaoDB("jdbc/EstiveAqui");

      //  Valida o AppUsuario
      AppUsuarioDB appUsuarioDb = new AppUsuarioDB(connDB);
      AppUsuarioMO appUsuarioMO = validaAppUsuario(appUsuarioDb, lancaHorasInVO);

      //
      //  Valida cada lançamento.
      for (HoraEnviadaVO horaEnviadaVO : lancaHorasInVO.getHorasEnviadas())
      {
        //  Preenche o LancaHoraInVO para validar os lançamentos.
        LancaHoraInVO lancaHoraInVo = new LancaHoraInVO();
        lancaHoraInVo.setHoraEnviada(lancaHorasInVO.getHoraEnviada());
        lancaHoraInVo.setIdDispositivo(lancaHorasInVO.getIdDispositivo());
        lancaHoraInVo.setVersao(lancaHorasInVO.getVersao());
        lancaHoraInVo.setIp(lancaHorasInVO.getIp());
        lancaHoraInVo.setIdentificacaoAppUsuario(lancaHorasInVO.getIdentificacaoApp());
        lancaHoraInVo.setNumPassClock(horaEnviadaVO.getNumeroPassClock());
        lancaHoraInVo.setCodigo(horaEnviadaVO.getCodigoPassClock());
        lancaHoraInVo.setHoraDigitada(horaEnviadaVO.getHrDigitacao());
        lancaHoraInVo.setLatitude(horaEnviadaVO.getLatitude());
        lancaHoraInVo.setLongitude(horaEnviadaVO.getLongitude());

        try
        {
          //  Valida um lançamento.
          PassClockMO passClockMO = LancaHora.verificaValidadeLancamento(connDB, lancaHoraInVo, appUsuarioMO);
          passClocksMO.put(passClockMO.getNumPassClock(), passClockMO);
        }
        catch (RegraDeNegocioException e)
        {
          //  Marca as horas para não serem enviadas para o ValidaHora.
          LancaHoraOutVO lancaHoraOutVO = new LancaHoraOutVO();
          lancaHorasOutVO.addLancamento(lancaHoraOutVO);
          
          lancaHoraOutVO.setContadorLancamento(horaEnviadaVO.getContadorLancamento());
          lancaHoraOutVO.setOk(false);
          lancaHoraOutVO.setExcecao(e);
          
        }
      }
      

      ////////////////////////////////////////////////////////////////////////////////
      //
      //  Calcula as horas dos lançamentos válidos.
      //
      ////////////////////////////////////////////////////////////////////////////////
      List<LancaValidaHoraServidorOutVO> lancaValidaHorasServidorOutVO = HTTPValidaHora.calculaHoras(lancaHorasInVO);
      ////////////////////////////////////////////////////////////////////////////////

      
      //  Atualiza o BD.
      LancamentoDB lancamentoDB = new LancamentoDB(connDB);

      //  Prepara o MO para o BD e os dados para o retorno.
      List<LancamentoMO> lancamentosMO = new ArrayList<LancamentoMO>();
      for (LancaValidaHoraServidorOutVO lancaValidaHoraServidorOutVO : lancaValidaHorasServidorOutVO)
      {
        //  Sincroniza com o lançamento do usuário.
        HoraEnviadaVO horaEnviadaVO = encontraHoraEnviadaVO(lancaHorasInVO, lancaValidaHoraServidorOutVO.getContadorLancamento());

        //  Se é um lançamento válido.
        if (lancaValidaHoraServidorOutVO.getExcecao() == null)
        {
          //  Prepara para inserir no BD.
          LancamentoMO lancamentoMO = new LancamentoMO();
          lancamentosMO.add(lancamentoMO);

          PassClockMO passClockMO = passClocksMO.get(horaEnviadaVO.getNumeroPassClock());
          
          lancamentoMO.setStatus(LancamentoMO.STATUS_HABILITADO);
          lancamentoMO.setAppUsuarioMO(appUsuarioMO);
          lancamentoMO.setLancadoPor(LancamentoMO.LANCADO_POR_USUARIO);
          lancamentoMO.setNumPassClock(horaEnviadaVO.getNumeroPassClock());
          lancamentoMO.setApelidoPassClock(passClockMO.getApelido());
          lancamentoMO.setCodPassClock(horaEnviadaVO.getCodigoPassClock());
          lancamentoMO.setHashCode(lancaValidaHoraServidorOutVO.getHashCode());
          lancamentoMO.setNota(horaEnviadaVO.getNota());
          lancamentoMO.setTzPassClock(passClockMO.getTz());
          lancamentoMO.setHrLancamento(lancaValidaHoraServidorOutVO.getHoraLancada());
          lancamentoMO.setHrPassClock(lancaValidaHoraServidorOutVO.getHoraLancada());
          lancamentoMO.setHrDigitacao(horaEnviadaVO.getHrDigitacao());
          lancamentoMO.setHrEnvio(lancaHorasInVO.getHoraEnviada());
          lancamentoMO.setIdDispositivo(lancaHorasInVO.getIdDispositivo());
          lancamentoMO.setIpDispositivo(lancaHorasInVO.getIp());
          lancamentoMO.setLatitude(horaEnviadaVO.getLatitude());
          lancamentoMO.setLongitude(horaEnviadaVO.getLongitude());

          try
          {
            lancamentoDB.insereRegistro(lancamentoMO);
          }
          catch (SQLException e)
          {
            lancaValidaHoraServidorOutVO.setOk(false);
            lancaValidaHoraServidorOutVO.setExcecao(new RegraDeNegocioException(CodigoErro.ERRO_INTERNO, e.toString()));
          }

        }

        //  Prepara para retornar ao App.
        LancaHoraOutVO lancaHoraOutVO = new LancaHoraOutVO();
        lancaHorasOutVO.addLancamento(lancaHoraOutVO);

        lancaHoraOutVO.setContadorLancamento(lancaValidaHoraServidorOutVO.getContadorLancamento());
        lancaHoraOutVO.setOk(lancaValidaHoraServidorOutVO.isOk());

        //  Hora lançada corretamente?
        if (lancaValidaHoraServidorOutVO.isOk())
          lancaHoraOutVO.setHrLancada(lancaValidaHoraServidorOutVO.getHoraLancada());
        else
          lancaHoraOutVO.setExcecao(lancaValidaHoraServidorOutVO.getExcecao());
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
    }

    //  Retorna a resposta de todas as horas lançadas ou não.
    log.info("Saindo de LancaHoras.acao()");
    return lancaHorasOutVO;
  }
  
  
  private HoraEnviadaVO encontraHoraEnviadaVO(LancaHorasInVO lancaHorasInVO, int contadorLancamento)
  {
    for (HoraEnviadaVO horaEnviadaVO : lancaHorasInVO.getHorasEnviadas())
    {
      if (horaEnviadaVO.getContadorLancamento() == contadorLancamento)
        return horaEnviadaVO;
    }

    return null;
  }

}
