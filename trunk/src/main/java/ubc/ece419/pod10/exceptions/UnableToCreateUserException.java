package ubc.ece419.pod10.exceptions;

public class UnableToCreateUserException extends Exception {

	public UnableToCreateUserException() {
		super();
	}
	
	public UnableToCreateUserException(String s) {
		super(s);
	}
	
	public UnableToCreateUserException(Throwable t, String s) {
		super(s, t);
	}
	
	public UnableToCreateUserException(Throwable t) {
		super(t);
	}
}
