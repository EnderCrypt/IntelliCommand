package net.ddns.endercrypt.intellicommand.exception;

/**
 * @author EnderCrypt
 * 
 * generic exception for when an argument is malformed
 */
@SuppressWarnings("serial")
public class MalformedArgumentException extends RuntimeException
{
	public MalformedArgumentException()
	{
		// TODO Auto-generated constructor stub
	}

	public MalformedArgumentException(String message)
	{
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MalformedArgumentException(Throwable cause)
	{
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public MalformedArgumentException(String message, Throwable cause)
	{
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MalformedArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
