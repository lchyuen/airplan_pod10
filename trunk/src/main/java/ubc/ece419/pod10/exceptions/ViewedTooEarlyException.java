package ubc.ece419.pod10.exceptions;

public class ViewedTooEarlyException extends Exception{

	public ViewedTooEarlyException() {
		super();
	}
	
	public ViewedTooEarlyException(String s) {
		super(s);
	}
	
	public ViewedTooEarlyException(Throwable t, String s) {
		super(s, t);
	}
	
	public ViewedTooEarlyException(Throwable t) {
		super(t);
	}
}
