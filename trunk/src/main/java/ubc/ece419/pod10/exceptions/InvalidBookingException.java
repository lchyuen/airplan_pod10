package ubc.ece419.pod10.exceptions;

public class InvalidBookingException extends Exception{

	public InvalidBookingException() {
		super();
	}
	
	public InvalidBookingException(String s) {
		super(s);
	}
	
	public InvalidBookingException(Throwable t, String s) {
		super(s, t);
	}
	
	public InvalidBookingException(Throwable t) {
		super(t);
	}
}
