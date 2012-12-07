package ubc.ece419.pod10.domain;

public class TicketSet {

	public TicketSet() {
		super();
	}
	public TicketSet(Ticket ticket, Flight flight) {
		super();
		this.ticket = ticket;
		this.flight = flight;
	}
	private Ticket ticket;
	private Flight flight;
	
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}

}

