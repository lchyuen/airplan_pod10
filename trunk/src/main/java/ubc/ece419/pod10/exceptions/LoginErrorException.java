package ubc.ece419.pod10.exceptions;

public class LoginErrorException extends Exception {

	public LoginErrorException() {
		super();
	}
	
	public LoginErrorException(String s) {
		super(s);
	}
	
	public LoginErrorException(Throwable t, String s) {
		super(s, t);
	}
	
	public LoginErrorException(Throwable t) {
		super(t);
	}
}
