package ubc.ece419.pod10.domain;

import java.sql.Timestamp;

public class Flight {	

	
	public Flight() {
		super();
	}
	
	public Flight(Long id, Timestamp dateCreated, Timestamp dateModified,
			String origin, String originName, String destination,
			String destinationName, Timestamp departureTime,
			Timestamp arrivalTime, Timestamp actualDepartureTime,
			Timestamp actualArrivalTime, String airlineName,
			Integer totalSeats, Integer availableSeats, Integer cost) {
		super();
		this.id = id;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.origin = origin;
		this.originName = originName;
		this.destination = destination;
		this.destinationName = destinationName;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.actualDepartureTime = actualDepartureTime;
		this.actualArrivalTime = actualArrivalTime;
		this.airlineName = airlineName;
		this.totalSeats = totalSeats;
		this.availableSeats = availableSeats;
		this.cost = cost;
	}

	private Long id;
	private Timestamp dateCreated;
	private Timestamp dateModified;
	private String origin;
	private String originName;
	private String destination;
	private String destinationName;
	private Timestamp departureTime;
	private Timestamp arrivalTime;
	private Timestamp actualDepartureTime;
	private Timestamp actualArrivalTime;
	private String airlineName;
	private Integer totalSeats;
	private Integer availableSeats;
	private Integer cost;	
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
	public Timestamp getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(Timestamp departureTime) {
		this.departureTime = departureTime;
	}
	public Timestamp getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(Timestamp arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public Timestamp getActualDepartureTime() {
		return actualDepartureTime;
	}
	public void setActualDepartureTime(Timestamp actualDepartureTime) {
		this.actualDepartureTime = actualDepartureTime;
	}
	public Timestamp getActualArrivalTime() {
		return actualArrivalTime;
	}
	public void setActualArrivalTime(Timestamp actualArrivalTime) {
		this.actualArrivalTime = actualArrivalTime;
	}
	public String getAirlineName() {
		return airlineName;
	}
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	public Integer getTotalSeats() {
		return totalSeats;
	}
	public void setTotalSeats(Integer totalSeats) {
		this.totalSeats = totalSeats;
	}
	public Integer getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(Integer availableSeats) {
		this.availableSeats = availableSeats;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}
	public String getOriginName() {
		return originName;
	}
	public void setOriginName(String originName) {
		this.originName = originName;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	

}
