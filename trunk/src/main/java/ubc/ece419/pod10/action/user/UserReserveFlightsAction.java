package ubc.ece419.pod10.action.user;

import com.opensymphony.xwork2.ActionSupport;

import java.sql.Timestamp;
import java.util.List;

import ubc.ece419.pod10.action.BaseAction;
import ubc.ece419.pod10.domain.Billing;
import ubc.ece419.pod10.domain.Flight;
import ubc.ece419.pod10.domain.Ticket;
import ubc.ece419.pod10.domain.User;
import ubc.ece419.pod10.service.FlightService;
import ubc.ece419.pod10.service.ReservationService;
import ubc.ece419.pod10.service.UserService;

public class UserReserveFlightsAction extends BaseAction{


	//query output
	String passengerName;
	List<Flight> flightList;
	List<Ticket> ticketList;
	
	String seatType;
	//query input
	String flightId;
	String userId;
	String bookingId;

	String mealPreference;
	String seatPreference;
	
	String origin;
	String destination;
	
	Billing billing;
	String creditCardName;
	String creditCardNum;
	String isPayByPoints;
	String canPayByPoints;
	String subtotal;
	String availPoints;

	ReservationService reservationService = new ReservationService();
	UserService userService = new UserService();
	/**
	 * Use Case: Reserve Flights
	 * query database for the reserved Flight information, returned data will be displayed on the website
	 * @return a boolean value indicates query database is successful or not
	 */
	public String execute(){
		
		// If userId is null, or if user cannot be authenticated, re-direct user to the login page.
		if (sessionToken == null || sessionToken.isEmpty()) {
			return LOGIN;
		}
		
		if (!authenticate(sessionToken)) {
			return LOGIN;
		}
				
		if (flightId == null || passengerName == null || seatType == null || seatPreference == null || mealPreference == null) {
			return ERROR;
		}
		Long userId_l = userService.getUserIdByToken(sessionToken);
		
		Long flightId_l = Long.parseLong(flightId);
		
		Long bookingId_l = reservationService.addBooking(userId_l);
		
		bookingId = bookingId_l.toString();
		
		flightList = reservationService.addTickets(userId_l, flightId_l, bookingId_l, passengerName, seatType, seatPreference, mealPreference);
		
		String origin_code = flightList.get(0).getOrigin();
		origin = reservationService.getPlaceByCode(origin_code);
		String destination_code = flightList.get(0).getDestination();
		destination = reservationService.getPlaceByCode(destination_code);
		
		return SUCCESS;
		
	}
	/**
	 * Use Case: Reserve Flights
	 * Add tickets entry to the database and query database for the reserved Flight information, , returned data will be displayed on the website
	 * @return a boolean value indicates query database is successful or not
	 */
	public String executeLoop(){
		
		// If userId is null, or if user cannot be authenticated, re-direct user to the login page.
		if (sessionToken == null || sessionToken.isEmpty()) {
			return LOGIN;
		}
		
		if (!authenticate(sessionToken)) {
			return LOGIN;
		}
				
		if (flightId == null || bookingId == null || passengerName == null || seatType == null || seatPreference == null || mealPreference == null) {
			return ERROR;
		}
		
		Long flightId_l = Long.parseLong(flightId);	
		Long bookingId_l = Long.parseLong(bookingId);
		Long userId_l = userService.getUserIdByToken(sessionToken);
		
		flightList = reservationService.addTickets(userId_l, flightId_l, bookingId_l, passengerName, seatType, seatPreference, mealPreference);
		
		String origin_code = flightList.get(0).getOrigin();
		origin = reservationService.getPlaceByCode(origin_code);
		String destination_code = flightList.get(0).getDestination();
		destination = reservationService.getPlaceByCode(destination_code);
		
		return SUCCESS;
		
	}
	/**
	 * Use Case: Manage Payment
	 * add an entry to Billing Table, and query the bill information for web display
	 * @return a boolean value indicates query database is successful or not
	 */
	public String makePayment() {
		// If userId is null, or if user cannot be authenticated, re-direct user to the login page.
		if (sessionToken == null || sessionToken.isEmpty()) {
			return LOGIN;
		}
		
		if (!authenticate(sessionToken)) {
			return LOGIN;
		}
				
		if (bookingId == null || subtotal == null || isPayByPoints == null)
			return ERROR;
				
		Long userId_l = userService.getUserIdByToken(sessionToken);
		Long bookingId_l = Long.parseLong(bookingId);
		Integer subtotal_i = Integer.parseInt(subtotal);
		int isPayByPoints_i = Integer.parseInt(isPayByPoints);
		boolean isPayByPoints_b = false;
		if (isPayByPoints_i == 1)
			isPayByPoints_b = true;
		
		if (isPayByPoints_b) {
			creditCardName = null;
			creditCardNum = null;
		}
		else
		{
			if (creditCardName == null || creditCardNum == null ) {
				return ERROR;
			}
		}
		
		billing = reservationService.addBilling(userId_l, bookingId_l, subtotal_i, creditCardName, creditCardNum, isPayByPoints_b);
		
		return SUCCESS;
	}
	/**
	 * Use Case: Manage Payments
	 * Get payment input and check if royalpoint can be used to make payment
	 * @return a boolean value indicates query database is successful or not
	 */
	public String getBillingGuiInput() {
		// If userId is null, or if user cannot be authenticated, re-direct user to the login page.
		if (sessionToken == null || sessionToken.isEmpty()) {
			return LOGIN;
		}
		
		if (!authenticate(sessionToken)) {
			return LOGIN;
		}
				
		if (bookingId == null)
			return ERROR;
	
		Long bookingId_l = Long.parseLong(bookingId);
		Long userId_l = userService.getUserIdByToken(sessionToken);
		
		boolean canPayByPoints_b = reservationService.canUsePoints(userId_l, bookingId_l);
		canPayByPoints = Boolean.toString(canPayByPoints_b);
		availPoints = Integer.toString(reservationService.getPointsBalance(userId_l));
		int subtotal_i = reservationService.getBookingSubtotal(bookingId_l);
		subtotal = Integer.toString(subtotal_i);
		
		ticketList = reservationService.getCurrentTickets(bookingId_l);
		
		return SUCCESS;
	}
	/**
	 * Use Case: Reserve Flights
	 * check if userId and flightId are valid to reserve a ticket
	 * @return a boolean value indicates query database is successful or not
	 */
	public String getReserveGuiInput(){
		
		// If userId is null, or if user cannot be authenticated, re-direct user to the login page.
		if (sessionToken == null || sessionToken.isEmpty()) {
			return LOGIN;
		}
		
		if (!authenticate(sessionToken)) {
			return LOGIN;
		}
				
		if (flightId == null ){
			return ERROR;
		}


		return SUCCESS;
	}
	/**
	 * Use Case: Reserve Flights
	 * check if userId and flightId are valid to reserve a ticket
	 * @return a boolean value indicates query database is successful or not
	 */
	public String getReserveGuiInputLoop() {
		// If userId is null, or if user cannot be authenticated, re-direct user to the login page.
		if (sessionToken == null || sessionToken.isEmpty()) {
			return LOGIN;
		}
		
		if (!authenticate(sessionToken)) {
			return LOGIN;
		}
				
		if (flightId == null || bookingId == null){
			return ERROR;
		}


		return SUCCESS;
	}	
	

	public String getMealPreference() {
		return mealPreference;
	}

	public void setMealPreference(String mealPreference) {
		this.mealPreference = mealPreference;
	}

	public String getSeatPreference() {
		return seatPreference;
	}

	public void setSeatPreference(String seatPreference) {
		this.seatPreference = seatPreference;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public List<Flight> getFlightList() {
		return flightList;
	}

	public void setFlightList(List<Flight> flightList) {
		this.flightList = flightList;
	}

	public String getSeatType() {
		return seatType;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}

	public String getFlightId() {
		return flightId;
	}

	public void setFlightId(String flightId) {
		this.flightId = flightId;
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
	
	public Billing getBilling() {
		return billing;
	}

	public void setBilling(Billing billing) {
		this.billing = billing;
	}

	public String getCreditCardName() {
		return creditCardName;
	}

	public void setCreditCardName(String creditCardName) {
		this.creditCardName = creditCardName;
	}

	public String getCreditCardNum() {
		return creditCardNum;
	}

	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}
	
	public String getIsPayByPoints() {
		return isPayByPoints;
	}

	public void setIsPayByPoints(String isPayByPoints) {
		this.isPayByPoints = isPayByPoints;
	}
	public String getCanPayByPoints() {
		return canPayByPoints;
	}

	public void setCanPayByPoints(String canPayByPoints) {
		this.canPayByPoints = canPayByPoints;
	}
	public List<Ticket> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<Ticket> ticketList) {
		this.ticketList = ticketList;
	}
	public String getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
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
	public String getAvailPoints() {
		return availPoints;
	}
	public void setAvailPoints(String availPoints) {
		this.availPoints = availPoints;
	}

}
