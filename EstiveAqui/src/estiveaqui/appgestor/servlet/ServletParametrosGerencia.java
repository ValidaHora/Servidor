package estiveaqui.appgestor.servlet;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import estiveaqui.appgestor.DadosAppGestorInVO;
import estiveaqui.appgestor.DadosGerenciaisInVO;
import estiveaqui.servlet.NomeParametroServlet;
import estiveaqui.servlet.ServletParametros;
import estiveaqui.servlet.ServletParametrosException;

public abstract class ServletParametrosGerencia extends ServletParametros
{
  public ServletParametrosGerencia(HttpServletRequest request, String acao, DadosGerenciaisInVO dadosGerenciaisInVo) throws IOException, ServletParametrosException
  {
    super(request, acao, dadosGerenciaisInVo);
    
    dadosGerenciaisInVo.setAcao(getAcao());
  }

  @Override
  public abstract DadosAppGestorInVO getParametros() throws ServletParametrosException;
  
  
  /**
   * Busca e retorna o par�metro que define a a��o a ser tomada pelo gerenciador.
   * 
   * ACAO=<Acao> - A��o a ser tomada.<BR>
   * <LI>C - Create, cria novo</LI>
   * <LI>R - Retrieve, l�</LI>
   * <LI>U - Update, atualiza</LI>
   * <LI>D - Delete, apaga</LI>
   * <LI>O - On - habilita</LI>
   * <LI>X - Off - desabilita</LI>
   * 
   * @return 
   * @throws ServletParametrosException
   */
  protected String getAcao() throws ServletParametrosException
  {
    return getParametro(NomeParametroServlet.Acao, true, true);
  }

}
