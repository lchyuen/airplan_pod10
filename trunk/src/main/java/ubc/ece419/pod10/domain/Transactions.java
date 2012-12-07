package ubc.ece419.pod10.domain;

import java.sql.Timestamp;

public class Transactions {
	
	public Transactions() {
		super();
	}
	public Transactions(Long billingId, Long amount, String name, String creditCardNumber, Timestamp dateCreated, String reason, Long pointsAwarded, Long bookingId) {
		super();
		this.billingId = billingId;
		this.amount = amount;
		this.name = name;
		this.creditCardNumber = creditCardNumber;
		this.dateCreated = dateCreated;
		this.reason = reason;
		this.pointsAwarded = pointsAwarded;
		this.bookingId = bookingId;
	}
	private Long billingId;
	private Long amount;
	private String name;
	private String creditCardNumber;
	private Timestamp dateCreated;
	private String reason;
	private Long pointsAwarded;
	private Long bookingId;
	
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}
	public Long getBillingId() {
		return billingId;
	}
	public void setBillingId(Long billingId) {
		this.billingId = billingId;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Long getPointsAwarded() {
		return pointsAwarded;
	}
	public void setPointsAwarded(Long pointsAwarded) {
		this.pointsAwarded = pointsAwarded;
	}
	
}

	
