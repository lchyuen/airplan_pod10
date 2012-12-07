package ubc.ece419.pod10.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BookingSet {

	public BookingSet() {
		super();
	}
	public BookingSet(Booking booking, List<Ticket> ticketList) {
		super();
		this.booking = booking;
		this.ticketList = ticketList;
	}
	private Booking booking;
	private List<Ticket> ticketList;
	
	public Booking getBooking() {
		return booking;
	}
	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	public List<Ticket> getTicketList() {
		return ticketList;
	}
	public void setTicketList(List<Ticket> ticketList) {
		this.ticketList = ticketList;
	}

	

}

