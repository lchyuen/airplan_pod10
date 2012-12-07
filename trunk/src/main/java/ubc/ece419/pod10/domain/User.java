package ubc.ece419.pod10.domain;

import java.sql.Timestamp;

public class User {

	private String emailAddress, password, sessionToken, name, salt;
	private Long id, currentPointsBalance;
	private Long userType;
	private Timestamp dateCreated, dateModified, sessionExpirationDate;
	
	public Long getUserType() {
		return userType;
	}

	public void setUserType(Long l) {
		this.userType = l;
		if(this.userType == null) throw new NullPointerException();
	}

	public Timestamp getSessionExpirationDate() {
		return sessionExpirationDate;
	}

	public void setSessionExpirationDate(Timestamp sessionExpirationDate) {
		this.sessionExpirationDate = sessionExpirationDate;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

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

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public Long getCurrentPointsBalance() {
		return currentPointsBalance;
	}

	public void setCurrentPointsBalance(Long currentPointsBalance) {
		this.currentPointsBalance = currentPointsBalance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	
}
