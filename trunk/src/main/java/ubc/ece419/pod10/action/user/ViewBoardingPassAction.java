package ubc.ece419.pod10.action.user;

import java.util.ArrayList;
import java.util.List;

import ubc.ece419.pod10.action.BaseAction;
import ubc.ece419.pod10.domain.BoardingPass;
import ubc.ece419.pod10.exceptions.InvalidBoardingPassException;
import ubc.ece419.pod10.exceptions.InvalidBookingException;
import ubc.ece419.pod10.exceptions.InvalidTicketException;
import ubc.ece419.pod10.exceptions.ViewedTooEarlyException;
import ubc.ece419.pod10.service.ReservationService;

@SuppressWarnings("serial")
public class ViewBoardingPassAction extends BaseAction {

	String userId;
	String bookingId;
	String message;
	List<BoardingPass> boardingPassList = new ArrayList<BoardingPass>();
	
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

		try {
			boardingPassList = reservationService.getBoardingPass(bookingIdLong);
		} catch (InvalidTicketException e){
			setMessage("Invalid boarding ticket ID");
		} catch (ViewedTooEarlyException e){
			setMessage("This boarding pass cannot be printed yet");
		} catch (InvalidBoardingPassException e) {
			e.printStackTrace();
		} catch (InvalidBookingException e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}

	
	// Automatically generated getters and setters

	public List<BoardingPass> getBoardingPassList() {
		return boardingPassList;
	}

	public void setBoardingPassList(List<BoardingPass> boardingPassList) {
		this.boardingPassList = boardingPassList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
