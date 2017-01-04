package estiveaqui.appusuario.lancahoras;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.EstiveAquiException;
import estiveaqui.RegraDeNegocioException;
import estiveaqui.appusuario.RegraNegocioAppUsuario;
import estiveaqui.appusuario.lancahora.LancaHora0;
import estiveaqui.appusuario.lancahora.LancaHoraInVO;
import estiveaqui.vo.DadosInVO;
import estiveaqui.vo.DadosOutVO;

/**
 * Lan�a um conjunto de horas.<BR>
 * Difere do LancaHora porque esta classe lan�a v�rias horas ao mesmo tempo, 
 * enquanto aquele lan�a apenas uma hora.
 * 
 * @author Haroldo
 *
 */
@Deprecated
public class LancaHoras0 extends RegraNegocioAppUsuario
{
  private static final Logger log = LogManager.getLogger();

  @Override
  @Deprecated
  public DadosOutVO acao(DadosInVO dadosInVo) throws EstiveAquiException
  {
    log.info("Entrando em LancaHoras.acao()");
    
    //  Prepara a resposta de todos os lan�amentos de hora.
    LancaHorasInVO lancaHorasInVO = (LancaHorasInVO) dadosInVo;
    List<LancaHoraOutVO>lancaHorasOutVo = new ArrayList<LancaHoraOutVO>();
    LancaHorasOutVO lancaHorasOutVO = new LancaHorasOutVO();
    lancaHorasOutVO.setLancamentos(lancaHorasOutVo);

    //
    //  Para cada hora lan�ada, chama LancaHora.acao().
    LancaHora0 lancaHora = new LancaHora0();
    for (HoraEnviadaVO horaEnviadaInVo : lancaHorasInVO.getHorasEnviadas())
    {
      //  Preenche o LancaHoraInVO para chamara o m�todo para lan�ar horas em LancaHora.
      LancaHoraInVO lancaHoraInVo = new LancaHoraInVO();

      lancaHoraInVo.setHoraEnviada(lancaHorasInVO.getHoraEnviada());
      lancaHoraInVo.setIdDispositivo(lancaHorasInVO.getIdDispositivo());
      lancaHoraInVo.setVersao(lancaHorasInVO.getVersao());
      lancaHoraInVo.setIp(lancaHorasInVO.getIp());
      lancaHoraInVo.setIdentificacaoAppUsuario(lancaHorasInVO.getIdentificacaoApp());

      lancaHoraInVo.setNumPassClock(horaEnviadaInVo.getNumeroPassClock());
      lancaHoraInVo.setCodigo(horaEnviadaInVo.getCodigoPassClock());
      lancaHoraInVo.setNota(horaEnviadaInVo.getNota());
      lancaHoraInVo.setHashCode(horaEnviadaInVo.getHashCode());
      lancaHoraInVo.setHoraDigitada(horaEnviadaInVo.getHrDigitacao());
      lancaHoraInVo.setHoraCalculada(horaEnviadaInVo.getHrLancada());
      lancaHoraInVo.setLatitude(horaEnviadaInVo.getLatitude());
      lancaHoraInVo.setLongitude(horaEnviadaInVo.getLongitude());
   
      //  Prepara a resposta para este lan�amento de hora.
      LancaHoraOutVO lancaHoraOutVo = new LancaHoraOutVO();
      lancaHorasOutVo.add(lancaHoraOutVo);
      lancaHoraOutVo.setContadorLancamento(horaEnviadaInVo.getContadorLancamento());
      lancaHoraOutVo.setExcecao(null);
      
      //  Chama o m�todo para lan�ar hora.
      try
      {
        estiveaqui.appusuario.lancahora.LancaHoraOutVO lancaHoraAcaoOutVo = lancaHora.acao(lancaHoraInVo);
        lancaHoraOutVo.setAppUsuarioMO(lancaHoraAcaoOutVo.getAppUsuarioMO());
        lancaHoraOutVo.setHrLancada(lancaHoraAcaoOutVo.getLancamentoMO().getHrLancamento());
      }
      catch (RegraDeNegocioException e)
      {
        lancaHoraOutVo.setExcecao(e);
      }
    }

    //  Retorna a resposta de todas as horas lan�adas ou n�o.
    log.info("Saindo de LancaHoras.acao()");
    return lancaHorasOutVO;
  }
}
