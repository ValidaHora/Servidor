package estiveaqui.relatorios;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.ServletContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.joda.time.DateTime;
import estiveaqui.sql.mo.LancamentoMO;

/**
 * Gera uma planilha Excel com os lançamentos.
 * 
 * @author Haroldo
 *
 */
public class LancamentosExcel implements Lancamentos
{
  private static final Logger log                  = LogManager.getLogger();

  public static PosicaoExcel posicao0Default = posicaoDefault("C6");
  public static PosicaoExcel posicaoMesDefault = posicaoDefault("C3");
  public static PosicaoExcel posicaoApelidoDefault = posicaoDefault("C2");
  public static PosicaoExcel posicaoIdentificadorDefault = posicaoDefault("I2");

  private PosicaoExcel  posicao0             = posicao0Default.clone();
  private PosicaoExcel  posicaoMes           = posicaoMesDefault.clone();
  private PosicaoExcel  posicaoApelido       = posicaoApelidoDefault.clone();
  private PosicaoExcel  posicaoIdentificador = posicaoIdentificadorDefault.clone();

  public static String        arquivoXls           = "/WEB-INF/Planilha Horas.xls"; // Preenchido em SrvltGeraRelatorio.init();

  public InputStream          isModeloXls          = null;
  private Workbook            workBook             = null;

  /**
   * Construtor que utiliza os valores de coluna e linha inicial passados como parâmetro.
   * 
   * @param posicao0
   *          .getColuna()
   * @param posicao0
   *          .getLinha()
   */
  public LancamentosExcel(PosicaoExcel posicao0, PosicaoExcel posicaoMes)
  {
    init();
    this.posicao0 = posicao0;
    this.posicaoMes = posicaoMes;
  }

  /**
   * Construtor que utiliza os valores default de coluna e linha.
   */
  public LancamentosExcel()
  {
    init();
  }

  /**
   * Inicializa a classe.
   */
  private void init()
  {
    log.debug("Ini - LancamentosExcel()");
    try
    {
      isModeloXls = new FileInputStream(arquivoXls);
      workBook = WorkbookFactory.create(isModeloXls);
      isModeloXls.close();
    }
    catch (Exception e)
    {
      log.warn("Problema na criação do WorkBook usando o modelo XLS. Usando modelo XLS default.", e);

      //  Cria workbook manualmente.
      workBook = new HSSFWorkbook();
      Sheet sheet = workBook.createSheet("Pagina 1");
      CellStyle fmtMes = workBook.createCellStyle();
      fmtMes.setDataFormat(workBook.getCreationHelper().createDataFormat().getFormat("mmm/yyyy"));
      CellStyle fmtHora = workBook.createCellStyle();
      fmtHora.setDataFormat(workBook.getCreationHelper().createDataFormat().getFormat("hh:mm"));
      for (int i = 1; i <= 31 + posicao0.getLinha(); i++)
      {
        Row row = sheet.createRow(i);
        for (int j = 0; j < posicao0.getColuna() + 2 * 4; j++)
        {
          Cell cell = row.createCell(j);
          if (i > posicao0.getLinha() && j == 1)
            cell.setCellValue(i - posicao0.getLinha());
          if (j >= posicao0.getColuna())
          {
            if (i == posicao0.getLinha())
              if (((j - posicao0.getColuna()) % 2) == 0)
                cell.setCellValue("Código");
              else
                cell.setCellValue("Hora");
            if (i > posicao0.getLinha() && ((j - posicao0.getColuna()) % 2 == 1))
              cell.setCellStyle(fmtHora);
          }
        }
      }
      sheet.getRow(posicaoApelido.getLinha()).getCell(posicaoApelido.getColuna() - 1).setCellValue("Apelido");
      sheet.getRow(posicaoMes.getLinha()).getCell(posicaoMes.getColuna() - 1).setCellValue("Mês");
      sheet.getRow(posicaoMes.getLinha()).getCell(posicaoMes.getColuna()).setCellStyle(fmtMes);
    }

    log.debug("Fim - LancamentosExcel()");
  }

  /**
   * Para inicialização das posições default, evitando o lançamento de exceção. <BR>
   * Retorna uma posição, em caso de entrada correta, ou a posição "A0" em caso de entrada incorreta.
   * 
   * @param posicao
   * @return
   */
  private static PosicaoExcel posicaoDefault(String posicao)
  {
    try
    {
      return new PosicaoExcel(posicao);
    }
    catch (PosicaoExcelException e)
    {
      log.warn("Posição {} inválida. Retornando posição default.", posicao);
      return PosicaoExcel.PosicaoDefault;
    }
  }

  /**
   * Monta os lançamentos do mês em uma planilha Excel.
   * 
   * @param lancamentosPorUsuarioMO - Lançamentos ordenados por usuário.
   * @return
   */
  public Workbook montaLancamentosMes(List<LancamentoMO> lancamentosPorUsuarioMO)
  {
    log.debug("Ini - Montando lançamentos em Excel.");
    Sheet sheetMes = null;
    PosicaoExcel pos = PosicaoExcel.PosicaoDefault;
    int idAppUsuarioAnterior = 0;
    DateTime diaAnt = new DateTime(0L);

    //
    //  Define o mês na planilha modelo.
    DateTime dtPlanilha = lancamentosPorUsuarioMO.get(0).getHrLancamento();
    Sheet sheetModelo = workBook.getSheetAt(0);
    atribui(sheetModelo, posicaoMes, dtPlanilha.monthOfYear().roundFloorCopy());
    
    //  Remove da planilha os últimos dias do mês para meses com até 30 dias.
    int ultimoDia = dtPlanilha.dayOfMonth().withMaximumValue().getDayOfMonth();
    for (int i = ultimoDia + 1; i <= 31; i++)
      sheetModelo.removeRow(sheetModelo.getRow(i - 1 + posicao0.getLinha()));

    //
    //  Para cada usuário.
    for (LancamentoMO lancamentoMO : lancamentosPorUsuarioMO)
    {
      //  Trata apenas lançamentos habilitados.
      if (lancamentoMO.getStatus() != LancamentoMO.STATUS_HABILITADO)
        continue;

      //  Outro usuário, novos lançamentos.
      if (idAppUsuarioAnterior != lancamentoMO.getAppUsuarioMO().getIdAppUsuario())
      {
        //  Controle do loop.
        log.debug("Lançamentos Excel de {}", lancamentoMO.getAppUsuarioMO().getApelido());
        idAppUsuarioAnterior = lancamentoMO.getAppUsuarioMO().getIdAppUsuario();

        //  Define o nome da planilha do Excel de preferência com o I
        String nomeAba = lancamentoMO.getAppUsuarioMO().getApelido();
        if (lancamentoMO.getAppUsuarioMO().getIdentificador() != null && !lancamentoMO.getAppUsuarioMO().getIdentificador().isEmpty())
          nomeAba = lancamentoMO.getAppUsuarioMO().getIdentificador();

        //  Clona planilha com novo nome.
        sheetMes = workBook.cloneSheet(0);
        atribui(sheetMes, posicaoApelido, lancamentoMO.getAppUsuarioMO().getApelido());
        atribui(sheetMes, posicaoApelido, lancamentoMO.getAppUsuarioMO().getApelido());
        atribui(sheetMes, posicaoIdentificador, lancamentoMO.getAppUsuarioMO().getApelido());
        atribui(sheetMes, posicaoMes, lancamentoMO.getHrLancamento().monthOfYear().roundFloorCopy().plusDays(1));
        int nSheet = workBook.getSheetIndex(sheetMes);
        workBook.setSheetName(nSheet, nomeAba);
        workBook.setForceFormulaRecalculation(true);
      }

      //  Mudou de dia, muda de linha na planilha
      if (!lancamentoMO.getHrLancamento().dayOfMonth().equals(diaAnt.dayOfMonth()))
      {
        diaAnt = lancamentoMO.getHrLancamento();
        pos.setPosicao(diaAnt.getDayOfMonth() + posicao0.getLinha(), posicao0.getColuna());
      }

      //  Para cada lançamento.
      copiaFormato(sheetMes, pos, posicao0);
      atribui(sheetMes, pos, lancamentoMO.getCodPassClock());
      pos.incrementaColuna();
      atribui(sheetMes, pos, lancamentoMO.getHrLancamento());
      copiaFormato(sheetMes, pos, posicao0);
      pos.incrementaColuna();
    }

    workBook.removeSheetAt(0);

    log.debug("Fim - Montando lançamentos em Excel.");
    return workBook;
  }

  /**
   * Atribui um dado a uma célula da planilha.<BR>
   * Cria uma linha ou uma coluna caso não exisam.
   * 
   * @param sheet
   * @param posicao
   * @param dado
   */
  private void atribui(Sheet sheet, PosicaoExcel posicao, String dado)
  {
    celula(sheet, posicao).setCellValue(dado);
  }

  /**
   * Atribui um dado a uma célula da planilha.<BR>
   * Cria uma linha ou uma coluna caso não exisam.
   * 
   * 
   * @param sheet
   * @param posicao
   * @param data
   */
  private void atribui(Sheet sheet, PosicaoExcel posicao, DateTime data)
  {
    celula(sheet, posicao).setCellValue(data.toDate());
  }

  /**
   * Retorna a célula da posição posicao.<BR>
   * Cria uma linha ou uma coluna caso não exisam.
   * 
   * @param sheet
   * @param posicao
   * @return
   */
  private Cell celula(Sheet sheet, PosicaoExcel posicao)
  {
    Row linha = linha(sheet, posicao);
    return coluna(linha, posicao);
  }

  /**
   * Retorna a linha da posicao passada<BR>
   * Cria a linha, caso ela não exista.
   * 
   * @param sheet
   * @param posicao
   * @return
   */
  private Row linha(Sheet sheet, PosicaoExcel posicao)
  {
    return linha(sheet, posicao.getLinha());
  }

  /**
   * Retorna a linha da posicao passada<BR>
   * Cria a linha, caso ela não exista.
   * 
   * @param sheet
   * @param linha
   * @return
   */
  private Row linha(Sheet sheet, int linha)
  {
    //  Cria linha, se não existir.
    if (linha >= sheet.getLastRowNum())
      sheet.createRow(linha);

    return sheet.getRow(linha);
  }

  /**
   * Cria a coluna caso não exista.
   * 
   * @param linha
   * @param posicao
   * @return
   */
  private Cell coluna(Row linha, PosicaoExcel posicao)
  {
    return coluna(linha, posicao.getColuna());
  }

  /**
   * Cria a coluna caso não exista.
   * 
   * @param linha
   * @param coluna
   * @return
   */
  private Cell coluna(Row linha, int coluna)
  {
    //  Cria coluna, se não existir.
    if (coluna >= linha.getLastCellNum())
      linha.createCell(coluna);

    return linha.getCell(coluna);
  }

  /**
   * 
   * @return
   */
  public Workbook getWorkBook()
  {
    if (workBook == null)
      return null;

    return workBook;
  }

  /**
   * Copia o formato da célula na posição posicaoCopiada para a célula na posição posicao.
   * 
   * @param sheet
   * @param posicao
   * @param posicaoCopiada
   */
  public void copiaFormato(Sheet sheet, PosicaoExcel posicao, PosicaoExcel posicaoCopiada)
  {
//    celula(sheet, posicao).setCellType(celula(sheet, posicaoCopiada).getCellType());
    celula(sheet, posicao).setCellStyle(celula(sheet, posicaoCopiada).getCellStyle());
  }

  /**
   * Retorna o workbook em byte[]
   * 
   * @return
   */
  @Override
  public byte[] getBytes()
  {
    log.debug("Ini - Criando byte[] dos lançamentos em Excel");
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try
    {
      workBook.write(bos);
    }
    catch (IOException e)
    {
      log.error("Problemas na transformação dos lançamentos em Excel para byte[]", e);
    }
    finally
    {
      try
      {
        bos.close();
      }
      catch (Exception e)
      { /* Ignorar */}
    }

    log.debug("Fim - Criando byte[] dos lançamentos em Excel");
    return bos.toByteArray();
  }

  /**
   * Persiste a planilha Excel.
   * 
   * @param out
   * @throws IOException
   */
  public void write(OutputStream out) throws IOException
  {
    workBook.write(out);
  }

  /**
   * Retorna o sufixo para o tipo de arquivo.
   * 
   * @return
   */
  public String getSufixo()
  {
    return "xls";
  }

  /**
   * Este método finalize foi criado apenas como um capricho.
   */
  public void finalize()
  {
    try
    {
      workBook.close();
    }
    catch (Exception e)
    {
      //  Nada a fazer. Ignorar exceção!
    }
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
      String param;
      param = servletContext.getRealPath("/WEB-INF/Planilha Horas.xls");
      arquivoXls = (param == null ? arquivoXls : param);
      posicao0Default = new PosicaoExcel(servletContext.getInitParameter("Posicao0Excel"));
      posicaoMesDefault = new PosicaoExcel(servletContext.getInitParameter("PosicaoMesExcel"));
    }
    catch (Exception e)
    {
      log.error("HTTPValidaHora.Init() com problemas para obter o contexto", e);
    }
  }

}
