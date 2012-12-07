package ubc.ece419.pod10.domain;

import java.sql.Timestamp;

// The boarding pass object takes a Ticket, Booking, and Flight object to generate a boarding pass

public class BoardingPass {	
	
	public BoardingPass() {
		super();
	}

	public BoardingPass(Ticket myTicket, Booking myBooking, Flight myFlight) {
		super();
		this.myTicket = myTicket;
		this.myBooking = myBooking;
		this.myFlight = myFlight;
	}
	private Ticket myTicket;
	private Booking myBooking;
	private Flight myFlight;
	
	
	// Auto generated getters and setters
	public Ticket getMyTicket() {
		return myTicket;
	}
	public void setMyTicket(Ticket myTicket) {
		this.myTicket = myTicket;
	}
	public Booking getMyBooking() {
		return myBooking;
	}
	public void setMyBooking(Booking myBooking) {
		this.myBooking = myBooking;
	}
	public Flight getMyFlight() {
		return myFlight;
	}
	public void setMyFlight(Flight myFlight) {
		this.myFlight = myFlight;
	}
	
}
