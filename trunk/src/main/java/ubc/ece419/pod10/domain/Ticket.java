package ubc.ece419.pod10.domain;

import java.sql.Timestamp;

public class Ticket {
	
	public Ticket() {
		super();
	}
	public Ticket(Long id, Timestamp dateCreated, Timestamp dateModified,
			Long flightId, Long bookingId, String seatType, String seatPref,
			String mealPref, String passengerName, Long cost, Long state) {
		super();
		this.id = id;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.flightId = flightId;
		this.bookingId = bookingId;
		this.seatType = seatType;
		this.seatPref = seatPref;
		this.mealPref = mealPref;
		this.passengerName = passengerName;
		this.cost = cost;
		this.state = state;
	}
	private Long id;
	private Timestamp dateCreated;
	private Timestamp dateModified;
	private Long flightId;
	private Long bookingId;
	private String seatType;
	private String seatPref;
	private String mealPref;
	private String passengerName;
	private Long cost;
	private Long state;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Timestamp getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Timestamp getDateModified() {
		return dateModified;
	}
	public void setDateModified(Timestamp dateModified) {
		this.dateModified = dateModified;
	}
	public Long getFlightId() {
		return flightId;
	}
	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}
	public String getSeatType() {
		return seatType;
	}
	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}
	public String getSeatPref() {
		return seatPref;
	}
	public void setSeatPref(String seatPref) {
		this.seatPref = seatPref;
	}
	public String getMealPref() {
		return mealPref;
	}
	public void setMealPref(String mealPref) {
		this.mealPref = mealPref;
	}
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public Long getCost() {
		return cost;
	}
	public void setCost(Long cost) {
		this.cost = cost;
	}
	public Long getState() {
		return state;
	}
	public void setState(Long state) {
		this.state = state;
	}
	
	
}
	
	
