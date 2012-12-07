package ubc.ece419.pod10.action.user;

import java.util.ArrayList;
import java.util.List;

import ubc.ece419.pod10.action.BaseAction;
import ubc.ece419.pod10.domain.Booking;
import ubc.ece419.pod10.service.ReservationService;
import ubc.ece419.pod10.service.UserService;

@SuppressWarnings("serial")
public class ViewBookingListAction extends BaseAction{

	String userId;

	List<Booking> bookingList = new ArrayList<Booking>();

	ReservationService reservationService = new ReservationService();
	UserService userService = new UserService();

	public String execute() {

		// If userId is null, or if user cannot be authenticated, re-direct user to the login page.
		if (sessionToken == null || sessionToken.isEmpty()) {
			return LOGIN;
		}
		
		if (!authenticate(sessionToken)) {
			return LOGIN;
		}
		
		Long userIdLong = userService.getUserIdByToken(sessionToken);
		
		bookingList = reservationService.getBookingList(userIdLong);

		return SUCCESS;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Booking> getBookingList() {
		return bookingList;
	}

	public void setBookingList(List<Booking> bookingList) {
		this.bookingList = bookingList;
	}

	public ReservationService getReservationService() {
		return reservationService;
	}

	public void setReservationService(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	

}
