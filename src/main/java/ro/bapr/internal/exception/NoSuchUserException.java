package ro.bapr.internal.exception;

public class NoSuchUserException extends Exception{
	private static final long serialVersionUID = 1L;

	public NoSuchUserException(String mesaj){
		super(mesaj);
	}
}
