package estiveaqui.relatorios;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import javax.servlet.ServletContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import estiveaqui.CodigoErro;
import estiveaqui.servidor.util.CredenciaisAws;

/**
 * Persiste os relatórios que são escritos em arquivos em diferentes formatos.
 * 
 * @author Haroldo
 *
 */
public class Persiste
{
  private static final Logger log = LogManager.getLogger();

  public static String AWSS3BucketName = "";
  public static CredenciaisAws AWSCredenciais = new CredenciaisAws();

  private String nomeRelatorio = "";
  
  public Persiste(String nomeRelatorio)
  {
    log.debug("Persistindo os relatórios {}", nomeRelatorio);
    this.nomeRelatorio = nomeRelatorio;
  }
  
  /**
   * Retorna o nome do relatório gerado.
   * 
   * @return
   */
  public String getNomeRelatorio()
  {
    return nomeRelatorio;
  }
  
  /**
   * Retorna a URL de acesso aos arquivos gerados.
   * 
   * @return
   */
  public URL getUrlAcesso()
  {
    File arqs;
    try
    {
      arqs = new File(nomeRelatorio);
      return arqs.getAbsoluteFile().toURI().toURL();
    }
    catch (Exception e)
    {
      log.error(e);
      return null;
    }
  }
  
  /**
   * Persiste os dados gerados pelo tipo de arquivo com os lançamentos.
   * 
   * @param lancamentos
   * @return
   */
  public void persiste(Lancamentos lancamentos) throws RelatorioException
  {
    persisteS3(lancamentos);
  }

  /**
   * 
   * @param lancamentos
   * @return
   */
  public void persisteS3(Lancamentos lancamentos) throws RelatorioException
  {
    log.debug("Ini - Armazenando {}.{} no S3", nomeRelatorio, lancamentos.getSufixo());
    AmazonS3 s3client = new AmazonS3Client(AWSCredenciais);
    
    try
    {
      ObjectMetadata metaData = new ObjectMetadata();
      byte[] lancBytes = lancamentos.getBytes();
      metaData.setContentLength(lancBytes.length);
      s3client.putObject(AWSS3BucketName, nomeRelatorio + "." + lancamentos.getSufixo(), new ByteArrayInputStream(lancBytes), metaData);
      log.info("Armazenado em S3 '{}' o relatório '{}.{}'", AWSS3BucketName, nomeRelatorio, lancamentos.getSufixo());
    }
    catch (AmazonServiceException ase)
    {
      log.warn("Erro ao armazenar em S3 '{}' o relatório '{}.{}' : ({},{},{},{},{})",
          AWSS3BucketName, nomeRelatorio, lancamentos.getSufixo(), 
          ase.getMessage(), ase.getStatusCode(), ase.getErrorCode(), ase.getErrorType(), ase.getRequestId(), ase);
      throw new RelatorioException(CodigoErro.ERRO_INTERNO, "Erro ao armazenar em S3 {1} o relatório {2}.{3} : ({4},{5},{6},{7},{8})", 
          AWSS3BucketName, nomeRelatorio, lancamentos.getSufixo(), 
          ase.getMessage(), "" + ase.getStatusCode(), ase.getErrorCode(), ase.getErrorType(), ase.getRequestId(), ase);
    }
    catch (AmazonClientException ace)
    {
      log.error("Erro ao se comunicar com S3 '{}'. Relatório '{}.{}' - ",
          AWSS3BucketName, nomeRelatorio, lancamentos.getSufixo(), ace.getMessage());
      throw new RelatorioException(CodigoErro.ERRO_INTERNO, "Erro ao se comunicar com S3 '{1}'. Relatório '{2}.{3}' - ",
              AWSS3BucketName, nomeRelatorio, lancamentos.getSufixo(), ace.getMessage());
    }
    
    log.debug("Fim - Armazenando {}.{} no S3", nomeRelatorio, lancamentos.getSufixo());
  }
  
  /**
   * Persistes no sistema de arquivos.
   * 
   * @param lancamentos
   * @return
   */
  public boolean persisteSistemaDeArquivos(Lancamentos lancamentos)
  {
    File arqNome = new File(nomeRelatorio + "." + lancamentos.getSufixo());
    FileOutputStream arq = null;
    try
    {
      arq = new FileOutputStream(arqNome);
      lancamentos.write(arq);
    }
    catch (IOException e)
    {
      log.error("Erro ao persistir lançamentos", e);
      return false;
    }
    finally
    {
      try
      {
        if (arq != null)
          arq.close();
      }
      catch (IOException e)
      {
        //  Nada
      }
    }

    return true;
  }
  
  /**
   * Obtém o contexto do ambiente onde está rodando.
   * 
   * @param servletContext
   */
  public static void getContext(ServletContext servletContext)
  {
    if (servletContext == null)
      return;

    try
    {
      AWSCredenciais.setAWSAccessKeyId(servletContext.getInitParameter("AWSChaveDeAcesso"));
      AWSCredenciais.setAWSSecretKey(servletContext.getInitParameter("AWSSenha"));
      AWSS3BucketName = servletContext.getInitParameter("AWSS3BucketName");
    }
    catch (Exception e)
    {
      log.error("Persiste.Init() com problemas para obter o contexto", e);
    }
  }

}
