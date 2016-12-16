package estiveaqui.sql.mo;

import java.sql.Date;

class LogPassClockMO implements MO
{
	private int idPassClock = 0;
	private Date horaLog = null;
	private String codigoLog = "";
	private String valor = "";
	private int idCliente = 0;

	LogPassClockMO(int idPassClock)
	{
		this.idPassClock = idPassClock;
	}


	/**
	 * @return the idPassClock
	 */
	public int getIdPassClock()
	{
		return idPassClock;
	}

	/**
	 * @return the horaLog
	 */
	public Date getHoraLog()
	{
		return horaLog;
	}

	/**
	 * @return the codigoLog
	 */
	public String getCodigoLog()
	{
		return codigoLog;
	}

	/**
	 * @param codigoLog the codigoLog to set
	 */
	public void setCodigoLog(String codigoLog)
	{
		this.codigoLog = codigoLog;
	}

	/**
	 * @return the valor
	 */
	public String getValor()
	{
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor)
	{
		this.valor = valor;
	}

	/**
	 * @return the idCliente
	 */
	public int getIdCliente()
	{
		return idCliente;
	}

	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(int idCliente)
	{
		this.idCliente = idCliente;
	}
}
