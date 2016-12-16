package estiveaqui.appgestor;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import estiveaqui.CodigoErro;

/**
 * Classe que implementa o envio de e-mail usando a infraestrutura da AWS como servidor SMTP.
 * 
 * @author Haroldo
 *
 */
public class EMail
{
  private static final Logger log = LogManager.getLogger();
  private static Context initCtx = null;
  private static Context envCtx  = null;
  private Message        message = null;
  private String         assunto;
  private String         usuario = "AKIAJDQ54ABUIGSH564Q";
  private String         senha   = "Ak0g3VXEiMlsdtsRXDSEYDAwCnHJGjtHnSUEV5JYqIVi";
  private String         remetente = "do-not-reply@validahora.com.br"; 

  /**
   * Implementa o envio de e-mail usando SMTP.
   * 
   * @param assunto
   * @throws EMailException
   */
  public EMail(String assunto) throws EMailException
  {
    try
    {
      if (envCtx == null)
        init();

      Session session = (Session) envCtx.lookup("mail/Session");
      message = new MimeMessage(session);
      message.setFrom(new InternetAddress(remetente));
      this.assunto = assunto;
    }
    catch (NamingException | MessagingException e)
    {
      throw new EMailException(CodigoErro.ERRO_INTERNO, e.getMessage());
    }
  }

  /**
   * Inicializa o ambiente.
   * 
   * @throws NamingException
   */
  private void init() throws NamingException
  {
    initCtx = new InitialContext();
    envCtx = (Context) initCtx.lookup("java:comp/env");
  }

  /**
   * Envia o e-mail com mensagem padrão de confirmação do cadastramento do endereço de e-mail.
   * 
   * @param para
   * @throws MessagingException
   */
  public void enviaConfirmacaoEMail(String para, String servidor, String codigoAtivacao) throws EMailException
  {
    log.debug("Enviando e-mail de confirmação para {}", para);
    
    try
    {
      String texto = 
                "<!DOCTYPE html>"
              + "<html>"
              + "  <body>"
              + "    <h1>Confirmação e-mail EstiveAqui</h1>"
              + "     Para usar todas as funcionalidades do EstiveAqui você precisa confirmar o seu e-mail." 
              + "    <p>"
              + "      Para confirmar o e-mail <a href=\"http://" + servidor + "/EstiveAqui/AppGestor/ConfirmaEMail?COD=" + codigoAtivacao + "\">clique aqui</a>"
              + "    </p>"
              + "  </body>"
              + "</html>";

      InternetAddress to[] = new InternetAddress[1];
      to[0] = new InternetAddress(para);
      message.setRecipients(Message.RecipientType.TO, to);
      message.setSubject(assunto);
      message.setContent(texto, "text/html");
      Transport.send(message, usuario, senha);

      log.debug("Enviando e-mail de confirmação enviado para {}", para);
    }
    catch (MessagingException e)
    {
      log.debug("Problema no envio do e-mail de confirmação para {}: ", para, e.getMessage());
      throw new EMailException(CodigoErro.ERRO_INTERNO, e.getMessage());
    }
  }
  
  /**
   * Envia um e-mail com um código para recuperação de senha.
   * 
   * @param para
   * @param codigoRecuperacaoSenha
   * @throws EMailException
   */
  public void enviaEsqueciSenha(String para, String codigoRecuperacaoSenha) throws EMailException
  {
    log.debug("Enviando e-mail de recuperação de senha para {}", para);
    
    try
    {
      String texto = 
                "<!DOCTYPE html>"
              + "<html>"
              + "  <body>"
              + "    <h1>Recuperação de Senha EstiveAqui</h1>"
              + "     Foi solicitada a recuperação de senha do EstiveAqui."
              + "    <p>"
              + "      Para criar uma nova senha, use o código abaixo no App:"
              + "      <h2>"
              +          codigoRecuperacaoSenha.substring(0, 4) 
              +         "-" + codigoRecuperacaoSenha.substring(4, 8) 
              +         "-" + codigoRecuperacaoSenha.substring(8) 
              + "      </h2>"
              + "    </p>"
              + "  </body>"
              + "</html>";

      InternetAddress to[] = new InternetAddress[1];
      to[0] = new InternetAddress(para);
      message.setRecipients(Message.RecipientType.TO, to);
      message.setSubject(assunto);
      message.setContent(texto, "text/html");
      Transport.send(message, usuario, senha);

      log.debug("Enviando e-mail de confirmação enviado para {}", para);
    }
    catch (MessagingException e)
    {
      log.debug("Problema no envio do e-mail de confirmação para {}: ", para, e.getMessage());
      throw new EMailException(CodigoErro.ERRO_INTERNO, e.getMessage());
    }
  }

}
