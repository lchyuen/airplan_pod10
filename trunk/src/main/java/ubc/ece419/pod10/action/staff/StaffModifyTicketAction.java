package ubc.ece419.pod10.action.staff;

import java.util.List;

import ubc.ece419.pod10.action.BaseAction;
import ubc.ece419.pod10.domain.Ticket;
import ubc.ece419.pod10.exceptions.InvalidTicketModificationException;
import ubc.ece419.pod10.service.ReservationService;

import com.opensymphony.xwork2.ActionSupport;

public class StaffModifyTicketAction extends BaseAction {
	
	String ticketId, seatPreference, mealPreference, passengerName, userId;
	String customerId, customerToken;
	
	public String getCustomerToken() {
		return customerToken;
	}

	public void setCustomerToken(String customerToken) {
		this.customerToken = customerToken;
	}
	
	Ticket ticket;
	
	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	ReservationService reservationService = new ReservationService();
	
	public String execute() {
		
		// If userId is null, or if user cannot be authenticated, re-direct user to the login page.
		if (sessionToken == null || sessionToken.isEmpty()) {
			return LOGIN;
		}
		
		if (!authenticate(sessionToken)) {
			return LOGIN;
		}
		
		if(ticketId == null || seatPreference == null || mealPreference == null || passengerName == null) {
			return ERROR;
		}
		
		Long ticketIdInt = Long.parseLong(ticketId);
		
		try {
			ticket = reservationService.modifyTicket(ticketIdInt, seatPreference, mealPreference, passengerName);
		} catch (InvalidTicketModificationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String getModifyTicketGuiInput(){
		// If userId is null, or if user cannot be authenticated, re-direct user to the login page.
		if (sessionToken == null || sessionToken.isEmpty()) {
			return LOGIN;
		}
		
		if (!authenticate(sessionToken)) {
			return LOGIN;
		}
		
		if (ticketId == null){
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String getSeatPreference() {
		return seatPreference;
	}

	public void setSeatPreference(String seatPreference) {
		this.seatPreference = seatPreference;
	}
	
	public String getMealPreference() {
		return mealPreference;
	}
	
	public void setMealPreference(String mealPreference) {
		this.mealPreference = mealPreference;
	}
	
	public String getPassengerName() {
		return passengerName;
	}
	
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	
	public String getticketId() {
		return ticketId;
	}

	public void setticketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public ReservationService getReservationService() {
		return reservationService;
	}

	public void setReservationService(ReservationService reservationService) {
		this.reservationService = reservationService;
	}
	
}
