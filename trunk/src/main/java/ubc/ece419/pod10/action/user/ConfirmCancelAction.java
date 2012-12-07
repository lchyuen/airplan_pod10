package ubc.ece419.pod10.action.user;

import java.util.ArrayList;
import java.util.List;

import ubc.ece419.pod10.action.BaseAction;
import ubc.ece419.pod10.domain.Billing;
import ubc.ece419.pod10.domain.Ticket;
import ubc.ece419.pod10.service.ReservationService;

import com.opensymphony.xwork2.ActionSupport;

public class ConfirmCancelAction extends BaseAction {

	String bookingId, billingId, userId;
	int cancelBooking, cancelTickets, refundBill;
	
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
		Long billingIdLong = Long.parseLong(billingId);

		cancelBooking = reservationService.cancelBooking(bookingIdLong);
		cancelTickets = reservationService.cancelTickets(bookingIdLong);
		refundBill = reservationService.refundBill(billingIdLong);
		return SUCCESS;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getBillingId() {
		return billingId;
	}

	public void setBillingId(String billingId) {
		this.billingId = billingId;
	}

	public int getCancelBooking() {
		return cancelBooking;
	}

	public void setCancelBooking(int cancelBooking) {
		this.cancelBooking = cancelBooking;
	}

	public int getCancelTickets() {
		return cancelTickets;
	}

	public void setCancelTickets(int cancelTickets) {
		this.cancelTickets = cancelTickets;
	}

	public int getRefundBill() {
		return refundBill;
	}

	public void setRefundBill(int refundBill) {
		this.refundBill = refundBill;
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
	
	
	
}
