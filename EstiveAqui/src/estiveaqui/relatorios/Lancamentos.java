package estiveaqui.relatorios;

import java.io.IOException;
import java.io.OutputStream;

public interface Lancamentos
{
  public String getSufixo();
  
  public void write(OutputStream out) throws IOException;
  public byte[] getBytes();
}
