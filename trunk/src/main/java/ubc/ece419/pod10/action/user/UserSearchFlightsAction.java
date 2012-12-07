package ubc.ece419.pod10.action.user;

import java.sql.Timestamp;
import java.util.List;

import ubc.ece419.pod10.action.BaseAction;
import ubc.ece419.pod10.domain.Flight;
import ubc.ece419.pod10.domain.User;
import ubc.ece419.pod10.service.FlightService;

import com.opensymphony.xwork2.ActionSupport;

public class UserSearchFlightsAction extends BaseAction {

	String origin, destination, requiredSeats, departureTime, seatType, bookingId, userId, returnTime;


	List<Flight> flightList;
	List<Flight> flightList_Return;


	FlightService flightService = new FlightService();
	/**
	 * Use Case: Search Flights
	 * Use userInput to query database for Flights, a list of Flight object is returned for web display
	 * @return a boolean value indicates query database is successful or not
	 */
	public String execute() {
		
		// If userId is null, or if user cannot be authenticated, re-direct user to the login page.
		if (sessionToken == null || sessionToken.isEmpty()) {
			return LOGIN;
		}
		
		if (!authenticate(sessionToken)) {
			return LOGIN;
		}
		
		if (origin == null || destination == null || requiredSeats == null || departureTime == null || seatType == null
				|| origin.isEmpty() || destination.isEmpty() || requiredSeats.isEmpty() || departureTime.isEmpty() || seatType.isEmpty()) {
			return ERROR;
		}
		
		Timestamp departureTimestamp = Timestamp.valueOf(departureTime+" 00:00:00");
		Integer requiredSeatsInt = Integer.parseInt(requiredSeats);
		boolean isFirstClass_b = false;
		if (seatType.equals("1"))
			isFirstClass_b = true;
		flightList = flightService.getFlights(origin, destination, departureTimestamp, requiredSeatsInt, isFirstClass_b);
		
		// if returnTime is null, return flights are not set
		if (returnTime != null && !returnTime.isEmpty()){
			
			Timestamp returnTimestamp = Timestamp.valueOf(returnTime+" 00:00:00");
			flightList_Return = flightService.getFlights(destination, origin, returnTimestamp, requiredSeatsInt, isFirstClass_b);
		
		}
		return SUCCESS;
	}
	/**
	 * Use Case: Search Flights
	 * Use userInput to query database for Flights, a list of Flight object is returned for web display
	 * @return a boolean value indicates query database is successful or not
	 */
	public String executeLoop() {
		
		// If userId is null, or if user cannot be authenticated, re-direct user to the login page.
		if (sessionToken == null || sessionToken.isEmpty()) {
			return LOGIN;
		}
		
		if (!authenticate(sessionToken)) {
			return LOGIN;
		}
		
		if (origin == null || destination == null || bookingId == null || requiredSeats == null || departureTime == null || seatType == null
				|| bookingId.isEmpty() || origin.isEmpty() || destination.isEmpty() || requiredSeats.isEmpty() || departureTime.isEmpty() || seatType.isEmpty()) {
			return ERROR;
		}
		
		Timestamp departureTimestamp = Timestamp.valueOf(departureTime+" 00:00:00");
		Integer requiredSeatsInt = Integer.parseInt(requiredSeats);
		boolean isFirstClass_b = false;
		if (seatType.equals("1"))
			isFirstClass_b = true;

		flightList = flightService.getFlights(origin, destination, departureTimestamp, requiredSeatsInt, isFirstClass_b);
		// if returnTime is null, return flights are not set
		if (returnTime != null && !returnTime.isEmpty()){
			
			Timestamp returnTimestamp = Timestamp.valueOf(returnTime+" 00:00:00");
			flightList_Return = flightService.getFlights(destination, origin, returnTimestamp, requiredSeatsInt, isFirstClass_b);
		
		}
		
		return SUCCESS;
	}
	/**
	 * Use Case: Search Flights
	 * check if userId is valid
	 * @return a boolean value indicates valid or not
	 */
	public String getSearchGuiInput() {
		// If userId is null, or if user cannot be authenticated, re-direct user to the login page.
		if (sessionToken == null || sessionToken.isEmpty()) {
			return LOGIN;
		}
		
		if (!authenticate(sessionToken)) {
			return LOGIN;
		}

		return SUCCESS;
	}
	/**
	 * Use Case: Search Flights
	 * check if userId and bookingId are valid
	 * @return a boolean value indicates userId and bookingId validation
	 */
	public String getSearchGuiInputLoop() {
		
		// If userId is null, or if user cannot be authenticated, re-direct user to the login page.
		if (sessionToken == null || sessionToken.isEmpty()) {
			return LOGIN;
		}
		
		if (!authenticate(sessionToken)) {
			return LOGIN;
		}
		
		if (bookingId == null){
			return ERROR;
		}

		return SUCCESS;
	}
	
	public String getSearchReturnGuiInput() {
		
		// If userId is null, or if user cannot be authenticated, re-direct user to the login page.
		if (sessionToken == null || sessionToken.isEmpty()) {
			return LOGIN;
		}
		
		if (!authenticate(sessionToken)) {
			return LOGIN;
		}
		
		if (bookingId == null || origin == null || destination == null || origin.isEmpty() || destination.isEmpty()){
			return ERROR;
		}

		return SUCCESS;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public List<Flight> getFlightList() {
		return flightList;
	}

	public void setFlightList(List<Flight> flightList) {
		this.flightList = flightList;
	}

	public String getRequiredSeats() {
		return requiredSeats;
	}

	public void setRequiredSeats(String requiredSeats) {
		this.requiredSeats = requiredSeats;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	

	public String getSeatType() {
		return seatType;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
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
	public String getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}
	public List<Flight> getFlightList_Return() {
		return flightList_Return;
	}
	public void setFlightList_Return(List<Flight> flightList_Return) {
		this.flightList_Return = flightList_Return;
	}
	

}
