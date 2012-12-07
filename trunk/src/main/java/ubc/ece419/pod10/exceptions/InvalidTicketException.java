package ubc.ece419.pod10.exceptions;

public class InvalidTicketException extends Exception{

	public InvalidTicketException() {
		super();
	}
	
	public InvalidTicketException(String s) {
		super(s);
	}
	
	public InvalidTicketException(Throwable t, String s) {
		super(s, t);
	}
	
	public InvalidTicketException(Throwable t) {
		super(t);
	}
}
