package estiveaqui.appgestor.login.email;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.CodigoErro;
import estiveaqui.servlet.ServletParametros0;
import estiveaqui.servlet.ServletParametrosException;

public class AlteraSenhaEMailParametrosSrvlt extends ServletParametros0
{
  private String paramSenhaAntiga = "SENHAANTIGA";
  private String paramCodRecuperaSenha = "CODRECUPERASENHA";
  
  private AlteraSenhaEMailInVO srvltInVo = (AlteraSenhaEMailInVO)dadosInVo; 

  public AlteraSenhaEMailParametrosSrvlt(HttpServletRequest request) throws IOException, ServletParametrosException
  {
    super(request, "Altera Senha E-Mail", new AlteraSenhaEMailInVO());
  }

  @Override
  public AlteraSenhaEMailInVO getParametros() throws ServletParametrosException
  {
    srvltInVo.setSenhaNova(getSenhaNova());
    srvltInVo.setSenhaAntiga(getSenhaAntiga());
    srvltInVo.setCodRecuperaSenha(getCodigoRecuperaSenha());

    //  Pelo menos um dos dois parâmetros precisa ser passado.
    if (srvltInVo.getSenhaAntiga() == null && srvltInVo.getCodRecuperaSenha() == null)
      throw new ServletParametrosException(CodigoErro.ERRO_INTERNO, "Parâmetro ''{0}'' ou ''{1}'' é obrigatório em ''{2}''", 
                                            paramSenhaAntiga, paramCodRecuperaSenha, acao);
    
    //  Apenas um dos dois parâmetros pode ser passado
    if (srvltInVo.getSenhaAntiga() != null && srvltInVo.getCodRecuperaSenha() != null)
      throw new ServletParametrosException(CodigoErro.ERRO_INTERNO, "Use apenas um dos parâmetro ''{0}'' ou ''{1}'' em ''{2}''", 
                                            paramSenhaAntiga, paramCodRecuperaSenha, acao);
    
    srvltInVo.setIdentificadorAppGestor(getIdentificacaoApp0(srvltInVo.getSenhaAntiga() != null));

    return srvltInVo;
  }

  String getSenhaNova() throws ServletParametrosException
  {
    String param = "SENHANOVA";
    String val = getParametro(param, true, false);

    return val;
  }

  String getSenhaAntiga() throws ServletParametrosException
  {
    String param = paramSenhaAntiga;
    String val = getParametro(param, false, false);

    return val;
  }

  String getCodigoRecuperaSenha() throws ServletParametrosException
  {
    String param = paramCodRecuperaSenha;
    String val = getParametro(param, false, false);

    return val;
  }

}
