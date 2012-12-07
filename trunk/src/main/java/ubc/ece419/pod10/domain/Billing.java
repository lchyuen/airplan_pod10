package ubc.ece419.pod10.domain;

import java.sql.Timestamp;

public class Billing {
	
	
	public Billing() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Billing(Long id, Long userId, Timestamp dateCreated,
			Timestamp dateModified, Long bookingId, int amount, String name,
			String creditCardNum, boolean isLoyaltyPointsUsed) {
		super();
		this.id = id;
		this.userId = userId;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.bookingId = bookingId;
		this.amount = amount;
		this.name = name;
		this.creditCardNum = creditCardNum;
		this.isLoyaltyPointsUsed = isLoyaltyPointsUsed;
	}
	private Long id;
	private Long userId;
	private Timestamp dateCreated;
	private Timestamp dateModified;
	private Long bookingId;
	private int amount;
	private String name;
	private String creditCardNum;
	private boolean isLoyaltyPointsUsed;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreditCardNum() {
		return creditCardNum;
	}
	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}
	public boolean isLoyaltyPointsUsed() {
		return isLoyaltyPointsUsed;
	}
	public boolean getIsLoyaltyPointsUsed() {
		return isLoyaltyPointsUsed;
	}
	public void setLoyaltyPointsUsed(boolean isLoyaltyPointsUsed) {
		this.isLoyaltyPointsUsed = isLoyaltyPointsUsed;
	}

	

}
