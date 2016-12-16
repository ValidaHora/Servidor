package estiveaqui.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTimeZone;

/**
 * Servlet implementation class ServletEstiveAqui
 */
public abstract class ServletEstiveAqui extends HttpServlet
{
  private static final long serialVersionUID = -7950727033469075686L;
  private static final Logger log = LogManager.getLogger();

  private static String ambiente = null;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public ServletEstiveAqui()
  {
    super();
  }
  
  /**
   * Configura a variável ambiente das servlets. 
   */
  @Override
  public void init()
  {
    if (ambiente == null)
      ambiente = getServletConfig().getServletContext().getInitParameter("Ambiente");
    
    DateTimeZone.setDefault(DateTimeZone.UTC);
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    
    log.info("Ambiente = " + ambiente);
    log.fatal("Chamada POST! - Use sempre GET");
    response.getWriter().println("Use sempre GET");
  }

}
