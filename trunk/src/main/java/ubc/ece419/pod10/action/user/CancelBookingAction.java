package ubc.ece419.pod10.action.user;

import java.util.ArrayList;
import java.util.List;

import ubc.ece419.pod10.action.BaseAction;
import ubc.ece419.pod10.domain.Billing;
import ubc.ece419.pod10.domain.Ticket;
import ubc.ece419.pod10.service.ReservationService;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class CancelBookingAction extends BaseAction{

	String bookingId;
	int cancelState;
	String userId;
	
	Billing bill = new Billing();
	List<Ticket> ticketList = new ArrayList<Ticket>();
	
	ReservationService reservationService = new ReservationService();

	public String execute() {
		
		// If userId is null, or if user cannot be authenticated, re-direct user to the login page.
		if (sessionToken == null || sessionToken.isEmpty()) {
			return LOGIN;
		}
		
		if (!authenticate(sessionToken)) {
			return LOGIN;
		}
				
		if (bookingId == null) {
			return ERROR;
		}
		Long bookingIdLong = Long.parseLong(bookingId);
		
		cancelState = reservationService.getBookingCancelState(bookingIdLong);
		if (cancelState != 0){
			bill = reservationService.getCurrentBilling(bookingIdLong);
			ticketList = reservationService.getCurrentTickets(bookingIdLong);
		}
		return SUCCESS;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public Billing getBill() {
		return bill;
	}

	public void setBill(Billing bill) {
		this.bill = bill;
	}

	public List<Ticket> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<Ticket> ticketList) {
		this.ticketList = ticketList;
	}

	public ReservationService getReservationService() {
		return reservationService;
	}

	public void setReservationService(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getCancelState() {
		return cancelState;
	}

	public void setCancelState(int cancelState) {
		this.cancelState = cancelState;
	}
	
}
