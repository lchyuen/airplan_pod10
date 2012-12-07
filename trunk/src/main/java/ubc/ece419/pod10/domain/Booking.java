package ubc.ece419.pod10.domain;

import java.sql.Timestamp;

public class Booking {	
	
	public Booking() {
		super();
	}
	public Booking(Long iD, Timestamp dateCreated, Timestamp dateModified,
			Long userID, String passengerName, Long flightMappingID, Long cancelState) {
		super();
		this.id = iD;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.userID = userID;
		this.cancelState = cancelState;
	}
	private Long id;
	private Timestamp dateCreated;
	private Timestamp dateModified;
	private Long userID;
	private Long cancelState;
	
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
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public Long getCancelState() {
		return cancelState;
	}
	public void setCancelState(Long cancelState) {
		this.cancelState = cancelState;
	}

}
