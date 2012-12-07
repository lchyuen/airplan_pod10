package ubc.ece419.pod10.exceptions;

public class InvalidBoardingPassException extends Exception{

	public InvalidBoardingPassException() {
		super();
	}
	
	public InvalidBoardingPassException(String s) {
		super(s);
	}
	
	public InvalidBoardingPassException(Throwable t, String s) {
		super(s, t);
	}
	
	public InvalidBoardingPassException(Throwable t) {
		super(t);
	}
}
