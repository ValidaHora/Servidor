package estiveaqui.sql.mo;


public class RelatorioMO
{
  private int       idAppGestor           = 0;
  private int  mes;
  private String    nomeArquivo           = "";
  private int       recriarRelatorio      = 1;

  public static int RECRIAR_RELATORIO_NAO = 0;
  public static int RECRIAR_RELATORIO_SIM = 1;
  
  public int getIdAppGestor()
  {
    return idAppGestor;
  }

  public void setIdAppGestor(int idAppGestor)
  {
    this.idAppGestor = idAppGestor;
  }

  public int getMes()
  {
    return mes;
  }

  public void setMes(int mes)
  {
    this.mes = mes;
  }

  public String getNomeArquivo()
  {
    return nomeArquivo;
  }

  public void setNomeArquivo(String nomeArquivo)
  {
    this.nomeArquivo = nomeArquivo;
  }

  @Override
  public boolean equals(Object object)
  {
    RelatorioMO relatorioMO = (RelatorioMO) object;
    return (relatorioMO.mes == this.mes) && (relatorioMO.idAppGestor == this.idAppGestor);
  }

  public int getRecriarRelatorio()
  {
    return recriarRelatorio;
  }

  public void setRecriarRelatorio(int recriarRelatorio)
  {
    this.recriarRelatorio = recriarRelatorio;
  }

//  public static void main(String[] args)
//  {
//    ArrayList<RelatorioMO> relatoriosMO = new ArrayList<RelatorioMO>();
//    RelatorioMO relatorioMO = new RelatorioMO();
//    relatoriosMO.add(relatorioMO);
//    relatorioMO.setIdAppGestor(1);
//    relatorioMO.setMes(2);
//
//    relatoriosMO.add(relatorioMO);
//    relatorioMO.setIdAppGestor(2);
//    relatorioMO.setMes(4);
//
//    relatorioMO = new RelatorioMO();
//    relatorioMO.setMes(3);
//    
//    System.out.println(relatoriosMO.contains(relatorioMO));
//    
//    relatorioMO.setIdAppGestor(1);
//    relatorioMO.setMes(2);
//
//    System.out.println(relatoriosMO.contains(relatorioMO));
//    System.out.println(relatoriosMO.contains(new RelatorioMO()));
//  }
}
