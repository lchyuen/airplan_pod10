package ubc.ece419.pod10.action.user;

import java.util.ArrayList; 
import java.util.List;

import ubc.ece419.pod10.action.BaseAction;
import ubc.ece419.pod10.domain.Booking;
import ubc.ece419.pod10.domain.TicketSet;
import ubc.ece419.pod10.service.ReservationService;

@SuppressWarnings("serial")
public class ViewBookingAction extends BaseAction{

	String userId, bookingId;

	Booking booking = new Booking();
	List<TicketSet> ticketSetList = new ArrayList<TicketSet>();

	ReservationService reservationService = new ReservationService();

	public String execute() {

		// If userId is null, or if user cannot be authenticated, re-direct user to the login page.
		if (sessionToken == null || sessionToken.isEmpty()) {
			return LOGIN;
		}
		
		if (!authenticate(sessionToken)) {
			return LOGIN;
		}
		
		Long bookingIdLong = Long.parseLong(bookingId);
		
		booking = reservationService.getCurrentBooking(bookingIdLong);
		ticketSetList = reservationService.getTicketSetList(bookingIdLong);
		
		return SUCCESS;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public List<TicketSet> getTicketSetList() {
		return ticketSetList;
	}

	public void setTicketSetList(List<TicketSet> ticketSetList) {
		this.ticketSetList = ticketSetList;
	}

	public ReservationService getReservationService() {
		return reservationService;
	}

	public void setReservationService(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

}
