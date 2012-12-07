package ubc.ece419.pod10.exceptions;

public class InvalidTicketModificationException extends Exception{

	public InvalidTicketModificationException() {
		super();
	}
	
	public InvalidTicketModificationException(String s) {
		super(s);
	}
	
	public InvalidTicketModificationException(Throwable t, String s) {
		super(s, t);
	}
	
	public InvalidTicketModificationException(Throwable t) {
		super(t);
	}
}
