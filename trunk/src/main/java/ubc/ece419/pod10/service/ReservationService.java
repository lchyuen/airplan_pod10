package ubc.ece419.pod10.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ubc.ece419.pod10.domain.*;
import ubc.ece419.pod10.exceptions.*;

import com.opensymphony.xwork2.ActionSupport;

public class ReservationService extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private static final String URL = "jdbc:mysql://localhost:3306/pod10_db?zeroDateTimeBehavior=convertToNull";
	private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static final String DB_USER_NAME = "root";
	private static final String DB_PASSWORD = "ECE419_pod10";
	Connection con = null;
	Statement stmt = null;

	public ReservationService() {
		super();
		try { 
			Class.forName(DRIVER_NAME).newInstance();
			con = DriverManager.getConnection(URL, DB_USER_NAME, DB_PASSWORD);
		} catch(InstantiationException e) {
			e.printStackTrace();
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Use Case: Reserve Flights
	 * add a booking entry  in Booking Table to associate all tickets booked with this userId
	 * @param userID used to associate with the generated bookingId
	 * @return generated bookingId
	 */
	public Long addBooking(Long userId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int cancelState = 1;//allowed to cancel
		Long bookingId = null;
		
		try {
			ps = con.prepareStatement("INSERT INTO booking(date_created, date_modified, user_id, cancel_state) VALUES(now(), now(), ?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, userId);
			ps.setInt(2, cancelState);
			int val = ps.executeUpdate();
			if (val == 0) {
				return null;
			} else {				
				rs = ps.getGeneratedKeys();
				if (rs == null) {
					return null;
				} else {
					while (rs.next()) {
						bookingId = rs.getLong(1);						
					}
					// add log
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Calendar cal = Calendar.getInstance();
					String now = dateFormat.format(cal.getTime());
					System.out.println("INSERT INTO booking @" + now + " & userId = " + userId + " & cancel_state = " + cancelState);
					// add log end
					return bookingId;
				}
			}
						
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		
		return null;
	}
	
	/**
	 * Use Case: Reserve Flights
	 * Add a new ticket instance into Ticket Table
	 * @param ps the object holds and precompiles the SQL statement
	 * @param bookingId has the ID of the lasted booking
	 * @param flightId has the ID of the flight associated with this ticket
	 * @param bookingId all booked tickets of a single userId are associated with this bookingId
	 * @param passengerName is the name of the person who uses this ticket
	 * @param seatType is user's choice for the seat type of this ticket
	 * @param seatPref is user's choice for the seat class of this ticket
	 * @param mealPref is user's choice for the meal of this ticket
	 * @return a Flight object with all tickets and information of each ticket booked with this userId
	 */
	public List<Flight> addTickets(Long userId, Long flightId, Long bookingId, String passengerName, String seatType, String seatPref, String mealPref){
		PreparedStatement ps = null;
		ResultSet rs = null;	
		
		List<Flight> flightList = new ArrayList<Flight>(); 
		
		int state = 0;
		int cost = 0;
			
		try {
			
			// get flight cost
			ps = con.prepareStatement("SELECT base_cost FROM flight WHERE id = ?");
			ps.setLong(1, flightId);
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {
					cost = rs.getInt("base_cost");
					if (cost == 0) {
						return null;
					}
				}
			}
			
			if (seatType.equals("1"))
				cost = cost * 5;
			
			// add entry to ticket table
			if (bookingId == null) {
				return null;
			} else {
				int val = addTicket(ps,bookingId, flightId, passengerName, seatType,seatPref, mealPref, state, cost);
				if (val == 0) {
					return null;
				} else { 
					ps = con.prepareStatement("SELECT * FROM Flight WHERE id = ?");
					ps.setLong(1, flightId);
					rs = ps.executeQuery();
					if (rs == null) {
						return null;
					} else {
						while (rs.next()) {

							Flight fl = new Flight();
							fl.setId(rs.getLong("id"));
							fl.setOrigin(rs.getString("origin"));
							fl.setOriginName(getPlaceByCode(fl.getOrigin()));
							fl.setDestination(rs.getString("destination"));
							fl.setDestinationName(getPlaceByCode(fl.getDestination()));
							fl.setDepartureTime(rs.getTimestamp("departure_time"));
							fl.setArrivalTime(rs.getTimestamp("arrival_time"));
							fl.setAirlineName(rs.getString("airline_name"));
							if (seatType.equals("1"))
								fl.setCost(rs.getInt("base_cost")*5);
							else
								fl.setCost(rs.getInt("base_cost"));
							
							flightList.add(fl);
						}
						return flightList;
					}
				}
			}

		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		
		return null;
		
	}
	
	/**
	 * Use Case: search.
	 * Query database to map place name to code.  
	 * @param code of a place.
	 * @return name of the place. 
	 */
	public String getPlaceByCode(String code) {
		
		try{
			
			PreparedStatement ps = null;
			ResultSet rs = null;

			// get origin code
			ps = con.prepareStatement("SELECT name FROM place WHERE code = ?");
			ps.setString(1, code);
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {
					String name = rs.getString("name");
					return name;
				}
			}
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		
		return null;
	}
	
	/**
	 * Use Case: Reserve Flights
	 * Add a new ticket instance into Ticket Table
	 * @param ps the object holds and precompiles the SQL statement
	 * @param bookingId has the ID of the lasted booking
	 * @param flightId has the ID of the flight associated with this ticket
	 * @param passengerName is the name of the person who uses this ticket
	 * @param seatType is user's choice for the seat type of this ticket
	 * @param seatPref is user's choice for the seat class of this ticket
	 * @param mealPref is user's choice for the meal of this ticket
	 * @param state indicates if the ticked is reserved, paid, or unpaid
	 * @param cost is the charge of this ticket
	 * @return an int result indicates if update ticket table is successful, 0=failed, otherwise= success
	 */
	public int addTicket(PreparedStatement ps, Long bookingId, Long flightId, String passengerName, String seatType, String seatPref, String mealPref, int state, int cost){
		try {
			ps = con.prepareStatement("INSERT INTO ticket (flight_id, booking_id, date_created, date_modified, seat_type, seat_pref, meal_pref, passenger_name, cost, state) VALUES(?, ?, now(), now(), ?, ?,?,?,?,?)");
			ps.setLong(1, flightId);
			ps.setLong(2, bookingId);
			ps.setString(3, seatType);
			ps.setString(4, seatPref);
			ps.setString(5, mealPref);
			ps.setString(6, passengerName);
			ps.setInt(7, cost);
			ps.setInt(8, state);			
			int val = ps.executeUpdate();
			// add log
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String now = dateFormat.format(cal.getTime());
			System.out.println("INSERT INTO ticket @" + now + " & flight_id = " + flightId + " & booking_id = " + bookingId + " & seat_type = " + seatType + " & seat_pref = " + seatPref + " & meal_pref = " + mealPref + " & passenger_name = " + passengerName + " & cost = " + cost + " & state = " + state);
			// add log end
			return val;
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		return 0;	
		
	}

	/**
	 * Use Case: Cancel Booking
	 * Cancels a Booking based on booking Id
	 * @param bookingId has the ID of the booking to cancel
	 * @return an int result indicates if the booking cancel is successful, 1=success, otherwise= failed
	 */
	public int cancelBooking(Long bookingId) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("UPDATE booking SET cancel_state = 2, date_modified = now() WHERE id = ?");
			ps.setLong(1, bookingId);		
			int val = ps.executeUpdate();
			
			if (val != 1) {
				// To few or too many records
				throw new InvalidBookingException("This booking is invalid. Contact an agent at 1-800-POD-2020");
			}
			
			// add log
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String now = dateFormat.format(cal.getTime());
			System.out.println("UPDATE booking @" + now + " & cancel_state = 2");
			// add log end
						
			return val;

		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		return 0;	
		
	}
	
	/**
	 * Use Case: Cancel Booking
	 * Cancels all tickets associated with the booking with booking Id
	 * @param bookingId has the ID of the booking for which all tickets shall be cancelled
	 * @return an int result indicates how many tickets have been cancelled, 0=failed, 1 or more= success
	 */
	public int cancelTickets(Long bookingId) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("UPDATE ticket SET state = 3, date_modified = now() WHERE booking_id = ?");
			ps.setLong(1, bookingId);		
			int val = ps.executeUpdate();
			if (val < 1) {
				// To few or too many records
				throw new InvalidBookingException("This booking is invalid. Contact an agent at 1-800-POD-2020");
			}
			// add log
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String now = dateFormat.format(cal.getTime());
			System.out.println("UPDATE ticket @" + now + " & state = 3");
			// add log end
			return val;
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		return 0;
		
	}
	
	/**
	 * Use Case: Cancel Booking
	 * Refunds a bill based on a billing Id
	 * @param billing has the ID of the bill to refund
	 * @return an int result indicates if the billing refund is successful, 1=success, otherwise= failed
	 */
	public int refundBill(Long billingId) {
		Billing bill = getBill(billingId);
		Integer refundAmount = (int) (-0.7 * bill.getAmount());
		int var;
		
		try {
			
			ArrayList<Long> res = addBill(bill, refundAmount);
			var = res.get(0).intValue();

		if (bill.isLoyaltyPointsUsed()){
			// Add a new points entry if this was a points payment (to refund the points paid)
			updateUserPoints(bill.getUserId(), refundAmount, res.get(1), "Refund Points (70%)");
		}
		
		return var;
		
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		return 0;
	}
	
	/**
	 * Use Case: Cancel booking
	 * get a billing entry in Billing Table
	 * @param billId used to associate with the generated billingId
	 * @return Billing object
	 */
	public Billing getBill(Long billId){
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Billing bill = new Billing(); 
		
		try {
			
			ps = con.prepareStatement("SELECT id, user_id, booking_id, date_created, amount, name, credit_card_number, is_loyalty_points_used FROM billing WHERE id = ?");
			ps.setLong(1, billId);
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {
					bill.setId(rs.getLong("id"));
					bill.setUserId(rs.getLong("user_id"));
					bill.setBookingId(rs.getLong("booking_id"));
					bill.setDateCreated(rs.getTimestamp("date_created"));
					bill.setAmount(rs.getInt("amount"));
					bill.setLoyaltyPointsUsed(rs.getBoolean("is_loyalty_points_used"));
					if (!bill.getIsLoyaltyPointsUsed()) {
						bill.setName(rs.getString("name"));	
						bill.setCreditCardNum(rs.getString("credit_card_number"));
					}
					
				}
				return bill;
			}
			
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		return null;
		
	}
	
	/**
	 * Use Case: Cancel booking
	 * add a billing entry in Billing Table
	 * @param billId used to associate with the generated billingId
	 * @param refund used to associate with the refund total
	 * @return List of billingId
	 */
	public ArrayList<Long> addBill(Billing bill, Integer refund){
		
		ArrayList<Long> resList = new ArrayList<Long>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("INSERT INTO billing (date_created, date_modified, user_id, booking_id, amount, name, credit_card_number, is_loyalty_points_used) VALUES(now(), now(), ?, ?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
			
			ps.setLong(1, bill.getUserId());
			ps.setLong(2, bill.getBookingId());
			ps.setInt(3, refund);
			
			if (bill.getIsLoyaltyPointsUsed()){
				ps.setString(4, null);
				ps.setString(5, null);
			} else {
				ps.setString(4, bill.getName());
				ps.setString(5, bill.getCreditCardNum());
			}
			
			ps.setBoolean(6, bill.getIsLoyaltyPointsUsed());
			
			int val = ps.executeUpdate();
			resList.add((long)val);
			if (val != 1) {
				// To few or too many records
				throw new InvalidBookingException("This booking is invalid. Contact an agent at 1-800-POD-2020");
			}
			else if (val == 1)
			{
				Long billingId = null;
				rs = ps.getGeneratedKeys();
				if (rs == null) {
					return null;
				} else {
					while (rs.next()) {
						billingId = rs.getLong(1);	
						break;
					}
					resList.add(billingId);
				}
			}
			
			// add log
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String now = dateFormat.format(cal.getTime());
			System.out.println("INSERT INTO billing @" + now + " & user_id = " + bill.getUserId() + " & booking_id = " + bill.getBookingId() + " & amount = " + refund + " & is_loyalty_points_used = " + bill.getIsLoyaltyPointsUsed());
			// add log end
			
			return resList;
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		return null;	
		
	}

	
	/**
	 * Use Case: reserve Flights
	 * check if users can make payment with their loyal point
	 * @param userId current logged in account userId
	 * @param bookingId associated with current account balance
	 * @return returns a boolean value indicates if users can pay with loyal points
	 */
	
	public boolean canUsePoints(Long userId, Long bookingId) {
		int currBalance = getCurrBalance(userId);
		int actualCost = getBookingSubtotal(bookingId);
		return actualCost <= currBalance;
	}
	
	/**
	 * Use Case: reserve Flights
	 * Get the current points balance of a user
	 * @param userId current logged in account userId
	 * @return returns a total points balance
	 */
	public int getPointsBalance(Long userId) {
		return getCurrBalance(userId);
	}
	/**
	 * Use Case: Manage Payment
	 * get current account loyalty points balance 
	 * @param userId current logged in account userId
	 * @return returns an integer type variable of current account loyalty points balance
	 */
	public int getBookingCancelState(Long bookingId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int cancelState = -1;
		try {
			ps = con.prepareStatement("SELECT cancel_state FROM booking WHERE id = ?");
			ps.setLong(1, bookingId);
			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
				cancelState = rs.getInt("cancel_state");
				}
			}
			
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		return cancelState;
		
	}
	
	/**
	 * Use Case: reserve Flights
	 * Get the current points balance of a user
	 * @param userId current logged in account userId
	 * @return returns a total points balance
	 */
	private int getCurrBalance(Long userId){
		PreparedStatement ps = null;
		ResultSet rs = null;
		int currBalance=0;
		try {
			// check if user have enough points to pay for the bill
			ps = con.prepareStatement("SELECT current_point_balance FROM user WHERE id = ?");
			ps.setLong(1, userId);
			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
				currBalance = rs.getInt("current_point_balance");
				}
			}
			
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		return currBalance;
	}
	
	/**
	 * Use Case: reserve Flights
	 * get current account balance 
	 * @param userId current logged in account userId
	 * @return returns an integer type variable of current account balance
	 */	
	public Integer getBookingSubtotal(Long bookingId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Integer total = null;
		
		try {
			
			ps = con.prepareStatement("SELECT sum(cost) as total FROM ticket WHERE booking_id = ?");
			ps.setLong(1, bookingId);			
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {
					total = rs.getInt("total");
				}
				return total;
			}
			
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		return null;
	}
	
	/**
	 * Get current booking object 
	 * @param bookingId associates a booking
	 * @return returns a booking object
	 */	
	public Booking getCurrentBooking(Long bookingId) {
		Booking bk = new Booking();
		
		try {
			PreparedStatement ps = con.prepareStatement("SELECT id, date_created, date_modified, user_id, cancel_state FROM booking WHERE id=?");
			ps.setLong(1, bookingId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bk.setId(rs.getLong("id"));
				bk.setDateCreated(rs.getTimestamp("date_created"));
				bk.setDateModified(rs.getTimestamp("date_modified"));
				bk.setUserID(rs.getLong("user_id"));
				bk.setCancelState(rs.getLong("cancel_state"));
			}
			return bk;

		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		return null;
	}
	
	/**
	 * Get all bookings from a user
	 * @param userId associates a user
	 * @return returns a list of bookings
	 */	
	public List<Booking> getBookingList(Long userId){
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Booking> bookingList = new ArrayList<Booking>(); 
		
		try {
			
			ps = con.prepareStatement("SELECT id, date_created, date_modified, user_id, cancel_state FROM booking WHERE user_id = ?");
			ps.setLong(1, userId);
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {
					Booking bk = new Booking();
					bk.setId(rs.getLong("id"));
					bk.setDateCreated(rs.getTimestamp("date_created"));
					bk.setDateModified(rs.getTimestamp("date_modified"));
					bk.setUserID(rs.getLong("user_id"));
					bk.setCancelState(rs.getLong("cancel_state"));
					
					bookingList.add(bk);
				}
				return bookingList;
			}
			
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		return null;
		
	}

	/**
	 * Get current billing object 
	 * @param bookingId associates a billing
	 * @return returns a billing object
	 */	
	public Billing getCurrentBilling(Long bookingId){
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Billing bill = new Billing(); 
		
		try {
			
			ps = con.prepareStatement("SELECT id, booking_id, date_created, amount, name, credit_card_number, is_loyalty_points_used FROM billing WHERE booking_id = ?");
			ps.setLong(1, bookingId);
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {
					bill.setId(rs.getLong("id"));
					bill.setBookingId(rs.getLong("booking_id"));
					bill.setDateCreated(rs.getTimestamp("date_created"));
					bill.setAmount(rs.getInt("amount"));									
					bill.setLoyaltyPointsUsed(rs.getBoolean("is_loyalty_points_used"));
					if (!bill.getIsLoyaltyPointsUsed()) {
						bill.setName(rs.getString("name"));	
						bill.setCreditCardNum(rs.getString("credit_card_number"));
					}		
					
				}
				return bill;
			}
			
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		return null;
		
	}
	
	
	/**
	 * Use Case: reserve flights
	 * get all tickets associated with the bookingId
	 * @param bookingId used to get all associated tickets
	 * @return a list of ticket objects
	 */
	public List<Ticket> getCurrentTickets(Long bookingId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Ticket> ticketList = new ArrayList<Ticket>(); 
		
		try {
			
			ps = con.prepareStatement("SELECT * FROM ticket WHERE booking_id = ?");
			ps.setLong(1, bookingId);			
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {
					Ticket t = new Ticket();
					t.setId(rs.getLong("id"));
					t.setFlightId(rs.getLong("flight_id"));
					t.setSeatType(rs.getString("seat_type"));
					t.setSeatPref(rs.getString("seat_pref"));
					t.setMealPref(rs.getString("meal_pref"));
					t.setPassengerName(rs.getString("passenger_name"));
					t.setCost(rs.getLong("cost"));
					t.setState(rs.getLong("state"));
					ticketList.add(t);
				}
				return ticketList;
			}
			
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		return null;
	}
	
	/**
	 * Get all tickets from a booking
	 * @param bookingId associates a booking
	 * @return returns a list of tickets from the booking
	 */	
	public List<TicketSet> getTicketSetList(Long bookingId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<TicketSet> ticketList = new ArrayList<TicketSet>(); 
		
		try {
			
			ps = con.prepareStatement("SELECT t.*, f.origin, f.destination, f.departure_time, f.arrival_time, f.airline_name FROM ticket t, flight f WHERE t.booking_id = ? AND f.id = t.flight_id");
			ps.setLong(1, bookingId);			
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {
					Ticket t = new Ticket();
					Flight fl = new Flight();
					TicketSet ticketSet = new TicketSet();
					t.setId(rs.getLong("id"));
					t.setFlightId(rs.getLong("flight_id"));
					t.setSeatType(rs.getString("seat_type"));
					t.setSeatPref(rs.getString("seat_pref"));
					t.setMealPref(rs.getString("meal_pref"));
					t.setPassengerName(rs.getString("passenger_name"));
					t.setCost(rs.getLong("cost"));
					t.setState(rs.getLong("state"));
					fl.setOrigin(rs.getString("origin"));
					fl.setOriginName(getPlaceByCode(fl.getOrigin()));
					fl.setDestination(rs.getString("destination"));
					fl.setDestinationName(getPlaceByCode(fl.getDestination()));
					fl.setDepartureTime(rs.getTimestamp("departure_time"));
					fl.setArrivalTime(rs.getTimestamp("arrival_time"));
					fl.setAirlineName(rs.getString("airline_name"));
					ticketSet.setTicket(t);
					ticketSet.setFlight(fl);
					ticketList.add(ticketSet);
				}
				return ticketList;
			}
			
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		return null;
	}
	
	/**
	 * Use Case: reserve Flights
	 * add a new entry into Billing Table
	 * @param userId current logged in account id
	 * @param bookingId the bookingId that associates with the retrieved billing information
	 * @param amount the cost of this billing entry
	 * @param cardHolder name of the credit card holder
	 * @param cardNum number of the credit card
	 * @param isPaidByPoints indicates if this bill is already paid by using loyal points
	 * @return a Billing object contains information of this billing
	 */
	public Billing addBilling(Long userId, Long bookingId, int amount, String cardHolder, String cardNum, boolean isPaidByPoints){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Billing b = null;
		
		Long billingId = null;
			
		try {
			
			ps = con.prepareStatement("INSERT INTO billing(date_created, date_modified, user_id, booking_id, amount, name, credit_card_number, is_loyalty_points_used) VALUES (now(), now(), ?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, userId);
			ps.setLong(2, bookingId);
			ps.setInt(3, amount);
			ps.setString(4, cardHolder);
			ps.setString(5, cardNum);
			ps.setBoolean(6, isPaidByPoints);
			int val = ps.executeUpdate();
			if (val == 0) {
				return null;
			} else {
				rs = ps.getGeneratedKeys();
				if (rs == null) {
					return null;
				} else {					
					while (rs.next()) {
						billingId = rs.getLong(1);
						b = new Billing();
						b.setId(billingId);
						b.setAmount(amount);
						b.setBookingId(bookingId);						
						b.setLoyaltyPointsUsed(isPaidByPoints);
						if (!isPaidByPoints) {
							b.setName(cardHolder);
							b.setCreditCardNum(cardNum);
						}
						break;
					}
					updateTicketState(bookingId);
					updateFlightSeat(bookingId);
					if (isPaidByPoints) {
						updateUserPoints(userId, amount, billingId,"Pay by Points");
					}
					// add log
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Calendar cal = Calendar.getInstance();
					String now = dateFormat.format(cal.getTime());
					System.out.println("INSERT INTO billing @" + now + " & user_id = " + userId + " & booking_id = " + bookingId + " & amount = " + amount + " & is_loyalty_points_used = " + isPaidByPoints);
					// add log end
					return b;
				}
			}
			
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		return null;
	}
	/**
	 * Use Case: reserve Flights
	 * update current user's loyalty points
	 * @param userId current account Id
	 * @param amount balance of the paid ticket
	 */
	private void updateUserPoints(Long userId, int amount, Long billingId, String reason) {
		PreparedStatement ps = null;
		int res = 0;
		try {
			ps = con.prepareStatement("UPDATE user SET current_point_balance = current_point_balance-?, date_modified = now() WHERE id=?");
			ps.setInt(1, amount);
			ps.setLong(2, userId);
			res = ps.executeUpdate();
			// add log
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String now = dateFormat.format(cal.getTime());
			System.out.println("UPDATE user @" + now + " & current_point_balance = current_point_balance-" + amount);
			// add log end
			
			if (res != 1){
				throw new InvalidBoardingPassException("Points update was not successful. Contact an agent at 1-800-POD-1013");
			}
			
			// in points table
			ps = con.prepareStatement("INSERT INTO points (date_created,date_modified,user_id,billing_id,amount,reason) VALUES (now(), now(), ?, ?, ?,?)");
			ps.setLong(1, userId);
			ps.setLong(2, billingId);
			ps.setInt(3, -amount);
			ps.setString(4, reason);
			res = ps.executeUpdate();
			// add log
			System.out.println("INSERT INTO points @" + now + " & user_id = " + userId + " & billing_id = " + billingId + " & amount = " + (-amount) + " & reason = " + reason);
			// add log end
			
			if(res != 1) {
				throw new InvalidBoardingPassException("Points update was not successful. Contact an agent at 1-800-POD-1013");
			}
			
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
	}
	/**
	 * Use Case: reserve Flights
	 * save user choice of seat type
	 * @param bookingId the updated ticket's bookingId 
	 */
	private void updateFlightSeat(Long bookingId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT flight_id,seat_type FROM ticket WHERE booking_id = ?");
			ps.setLong(1, bookingId);
			rs = ps.executeQuery();
			if (rs == null) {
				// throw exception
			} else {
				while (rs.next()) {
					Long flightId = rs.getLong("flight_id");
					String seatType = rs.getString("seat_type");
					
					PreparedStatement ps1 = null;
					if (seatType.equals("1"))
						ps1 = con.prepareStatement("UPDATE flight SET first_class_avail = first_class_avail-1, date_modified = now() WHERE id = ?");
					else
						ps1 = con.prepareStatement("UPDATE flight SET economy_avail = economy_avail-1, date_modified = now() WHERE id = ?");
					ps1.setLong(1, flightId);
					int res = ps1.executeUpdate();
					
					// add log
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Calendar cal = Calendar.getInstance();
					String now = dateFormat.format(cal.getTime());
					System.out.println("UPDATE flight @" + now + " & first_class_avail = first_class_avail-1 | economy_avail = economy_avail-1" );
					// add log end
					
					if (res != 1){
						// throw exception here
					}
				}
			}
		
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
	}
	/**
	 * Use Case: reserve Flights
	 * update ticket state if ticket if reserved or paid

	 * @param bookingId the updated ticket's bookingId
	 */
	private void updateTicketState(Long bookingId) {
		PreparedStatement ps = null;
		int res = 0;
		try {
			ps = con.prepareStatement("UPDATE ticket SET state = 1, date_modified = now() WHERE booking_id = ?");
			ps.setLong(1, bookingId);
			res = ps.executeUpdate();
			
			// add log
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String now = dateFormat.format(cal.getTime());
			System.out.println("UPDATE ticket @" + now + " & state = 1" );
			// add log end
			
			if (res != 1){
				// throw exception here
			}
		
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
	}
	
	/**
	 * updates bookings state from previousState to newState
	 * @param ticketId Identifies the ticket
	 * @param newState Identifies the new state to be updated to
	 * @param previousState Identifies the previous state to be update from.
	 */
	private void updateTicketState(Long ticketId, Long newState, Long previousState) throws InvalidTicketException {
		try {
			PreparedStatement ps = con.prepareStatement("UPDATE ticket SET state = ?, date_modified = now() WHERE id = ? AND state = ?");
			ps.setLong(1, newState);
			ps.setLong(2, ticketId);
			ps.setLong(3, previousState);
			
			Integer result = ps.executeUpdate();
			//add log
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String now = dateFormat.format(cal.getTime());
			System.out.println("Update ticket state");
			System.out.println("Modified Table: ticket");
			System.out.println("Modified Time: " + now);
			System.out.println("Modified ticket ID: " + ticketId);
			System.out.println("New ticket state: " + newState);
			System.out.println("Old ticket state: " + previousState);
			//add log end
			if (!result.equals(1)) {
				throw new InvalidTicketException("Could not update ticket with new state for ticket id " + ticketId);
			}
		} catch(SQLException e) {
			throw new InvalidTicketException("Could not update ticket with new state for ticket id " + ticketId);
		}
	}
	
	/**
	 * Updates booking cancel state from previousState to newState
	 * @param bookingId Identifies the booking
	 * @param newState Identifies the new state to be updated to
	 * @param previousState Identifies the previous state to be updated from
	 * @throws InvalidBookingException when booking cannot be updated.
	 */
	//updateBookingCancelState(bookingId, 0L, 1L);
	private void updateBookingCancelState(Long bookingId, Long newState, Long previousState) throws InvalidBookingException{
		try {
			PreparedStatement ps = con.prepareStatement("UPDATE booking SET cancel_state=?, date_modified = now() where id = ? AND cancel_state = ?");
			ps.setLong(1, newState);
			ps.setLong(2, bookingId);
			ps.setLong(3, previousState);
			
			Integer result = ps.executeUpdate();
			//add log
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String now = dateFormat.format(cal.getTime());
			System.out.println("Update booking Table");
			System.out.println("Modified Table: booking");
			System.out.println("Modified Time: " + now);
			System.out.println("Modified booking ID: " + bookingId);
			System.out.println("New cancel state: " + newState);
			System.out.println("Old cancel state: " + previousState);
			//add log end
			
			if (!result.equals(1)) {
				throw new InvalidBookingException ("Could not update booking's cancel state for booking Id " + bookingId);
			}
		} catch(SQLException e) {
			throw new InvalidBookingException ("Could not update booking's cancel state for booking Id " + bookingId);
		}
	}
	
	/**
	 * Use Case: Get Boarding Pass
	 * Generate a printable boarding pass for a given Ticket, showing information from the Flight and Booking
	 * @param bookingId The ID of the booking for which we want to generate boarding passes
	 * @return A boarding pass object, with Ticket, Booking, and Flight information
	 */
	public List<BoardingPass> getBoardingPass(Long bookingId) throws ViewedTooEarlyException, InvalidTicketException, InvalidBookingException, InvalidBoardingPassException{
		List<BoardingPass> boardingPassList = new ArrayList<BoardingPass>();
		
		// 1. Get a list of tickets for that booking Id. 
		// 2. Print boarding passes for each ticket.
		// 3. If the state of the ticket is 1, update it to 0.
		// 4. If the booking's is cancelState is 1 (cancel allowed), update it to 1 and award any relevant points.
		
		Booking booking = getBookingById(bookingId);
		List<Ticket> ticketList = getTicketsForBookingId(bookingId);
		
		for (Ticket ticket: ticketList) {
			BoardingPass boardingPass = generateBoardingPass(ticket.getId());
			
			if(ticket.getState() == 1L) {
				// Update ticket state to 2.
				updateTicketState(ticket.getId(), 2L, 1L);
			}
			
			boardingPassList.add(boardingPass);
		}
		
		if (booking.getCancelState() == 1L) {
			// If booking = 1 meaning we can cancel it, set cancel state to 0 so we cannot cancel it after printing boarding pass
			// Update booking's cancel state to 0.
			// Award points if necessary.
			updateBookingCancelState(bookingId, 0L, 1L);
			
			int billingAmount = getBillingAmount(bookingId);
			Long billingId = getBillingId(bookingId);
			Billing myBill = getBill(billingId);
			if (!myBill.isLoyaltyPointsUsed()) {			
				updateUserPoints(booking.getUserID(),-billingAmount,billingId,"Points Reward");
			}
		}
		
		// Display the boarding pass
		return boardingPassList;
	}
	
	/**
	 * Get total amount of a bill
	 * @param bookingId associates a bill
	 * @return returns total amount of the bill
	 */	
	private int getBillingAmount(Long bookingId) {
		// TODO Auto-generated method stub
		int billingAmount = 0;
		try {
			PreparedStatement ps = con.prepareStatement("SELECT amount FROM billing WHERE booking_id = ?");
			ps.setLong(1, bookingId);
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					billingAmount = rs.getInt("amount");
				}
			}
			
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		return billingAmount;
	}
	
	/**
	 * Get billingId from a bookingId
	 * @param bookingId associates a bill
	 * @return returns billingId
	 */	
	private Long getBillingId(Long bookingId) {
		// TODO Auto-generated method stub
		Long billingId = null;
		try {
			PreparedStatement ps = con.prepareStatement("SELECT id FROM billing WHERE booking_id = ?");
			ps.setLong(1, bookingId);
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					billingId = rs.getLong("id");
					return billingId;
				}
			}
			
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		return billingId;
	}

	/**
	 * Get all tickets from a booking
	 * @param bookingId associates a booking
	 * @return returns a list of tickets from the booking
	 */	
	private List<Ticket> getTicketsForBookingId(Long bookingId) throws InvalidBookingException {
		List<Ticket> ticketList = new ArrayList<Ticket>();
		
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM ticket WHERE booking_id = ?");
			ps.setLong(1, bookingId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Ticket ticket = new Ticket();
				ticket.setId(rs.getLong("id"));
				ticket.setDateCreated(rs.getTimestamp("date_created"));
				ticket.setDateModified(rs.getTimestamp("date_modified"));
				ticket.setFlightId(rs.getLong("flight_id"));
				ticket.setBookingId(rs.getLong("booking_id"));
				ticket.setSeatType(rs.getString("seat_type"));
				ticket.setSeatPref(rs.getString("seat_pref"));
				ticket.setMealPref(rs.getString("meal_pref"));
				ticket.setPassengerName(rs.getString("passenger_name"));
				ticket.setCost(rs.getLong("cost"));
				ticket.setState(rs.getLong("state"));
				ticketList.add(ticket);
			}
		
			return ticketList;
			
		} catch(SQLException e) {
			  throw new InvalidBookingException("Could not fetch tickets for Booking ID" + bookingId);
		}
		
	}
	
	/**
	 * Get a booking
	 * @param bookingId associates a booking
	 * @return returns booking object
	 */	
	private Booking getBookingById(Long bookingId) throws InvalidBookingException {
		Booking booking = null;
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM booking WHERE id=?");
			ps.setLong(1, bookingId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				booking = new Booking();
				booking.setId(rs.getLong("id"));
				booking.setDateCreated(rs.getTimestamp("date_created"));
				booking.setDateModified(rs.getTimestamp("date_modified"));
				booking.setUserID(rs.getLong("user_id"));
				booking.setCancelState(rs.getLong("cancel_state"));
			}
			return booking;

		} catch(SQLException e) {
			throw new InvalidBookingException("Could not fetch tickets for Booking Id" + bookingId);
		}
	}
	
	/**
	 * Generate a boarding Pass
	 * @param ticketId associates a ticket
	 * @return returns a BoardingPass object
	 */	
	private BoardingPass generateBoardingPass(Long ticketId) throws InvalidTicketException {
		PreparedStatement ps = null;
		ResultSet rs = null;	
		
		try {
			// get user name
			ps = con.prepareStatement("SELECT t.id, t.seat_type, t.seat_pref, t.meal_pref, t.passenger_name, b.id, f.id, f.origin, f.destination, f.departure_time, f.arrival_time, f.airline_name FROM ticket t, booking b, flight f WHERE t.id = ? AND b.id = t.booking_id AND f.id = t.flight_id");
			ps.setLong(1, ticketId);
			rs = ps.executeQuery();
			
			rs.beforeFirst();
			rs.last();
			// Check to see if rs size is 0
			if (rs.getRow() == 0) {
				// We didn't return any data, our ticketId was invalid
				throw new InvalidTicketException("Ticket ID was invalid.");
			}
			
			rs.beforeFirst();
			
			while (rs.next()) {
				Booking bk = new Booking();
				Flight fl = new Flight();
				Ticket tk = new Ticket();
				
				// The following sets take the args in order from the SELECT statement above, order cannot be changed
				tk.setId(rs.getLong(1));
				tk.setSeatType(rs.getString(2));
				tk.setSeatPref(rs.getString(3));
				tk.setMealPref(rs.getString(4));
				tk.setPassengerName(rs.getString(5));
				bk.setId(rs.getLong(6));
				fl.setId(rs.getLong(7));
				fl.setOrigin(rs.getString(8));
				fl.setDestination(rs.getString(9));
				fl.setDepartureTime(rs.getTimestamp(10));
				fl.setArrivalTime(rs.getTimestamp(11));
				fl.setAirlineName(rs.getString(12));
				
				System.out.println(tk);
				System.out.println(fl);
				System.out.println(bk);
				BoardingPass myPass = new BoardingPass(tk, bk, fl);
				
				return myPass;
		}
			
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		
		return null;
		
	}
	
	/**
	 * Use Case: modify tickets
	 * @param ticketId associates a ticket
	 * @param seatPreference user's choice of seat
	 * @param mealPreference user's choice of mean plan
	 * @param passengerName user's specified passenger name
	 * @return returns the updated ticket object
	 */	
	public Ticket modifyTicket(Long ticketId, String seatPreference, String mealPreference, String passengerName) throws InvalidTicketModificationException {
		
		try {
			
			PreparedStatement ps = null;
			int rs;
	
			ps = con.prepareStatement("UPDATE ticket SET seat_pref = ?, meal_pref = ?, passenger_name = ?, date_modified = now() WHERE id = ?");
			ps.setString(1, seatPreference);
			ps.setString(2, mealPreference);
			ps.setString(3, passengerName);
			ps.setLong(4, ticketId);
			rs = ps.executeUpdate();
			//add log
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String now = dateFormat.format(cal.getTime());
			System.out.println("Update ticket");
			System.out.println("Modified Table: ticket");
			System.out.println("Modified Time: " + now);
			System.out.println("Modified Ticket ID: " + ticketId);
			System.out.println("Set seat preference: " + seatPreference);
			System.out.println("Set meal preference: " + mealPreference);
			System.out.println("Passenger Name:" + passengerName);
			//add log end
			if (rs != 1) {
				throw new InvalidTicketModificationException("There was an error updating this ticket. Call tech support 1-800-POD-1011");
			}
			else {
				ps = con.prepareStatement("SELECT * FROM ticket WHERE id = ?");
				ps.setLong(1, ticketId);
				ResultSet rs2 = ps.executeQuery();
				while (rs2.next()) {
					Ticket t = new Ticket();
					t.setId(rs2.getLong("id"));
					t.setFlightId(rs2.getLong("flight_id"));
					t.setBookingId(rs2.getLong("booking_id"));
					t.setSeatType(rs2.getString("seat_type"));
					t.setSeatPref(rs2.getString("seat_pref"));
					t.setMealPref(rs2.getString("meal_pref"));
					t.setPassengerName(rs2.getString("passenger_name"));
					t.setCost(rs2.getLong("cost"));
					t.setState(rs2.getLong("state"));
					return t;
				}
			}
			return null;

		} catch(SQLException e) {
			  System.out.println("Exception caught: " + e);
			  throw new InvalidTicketModificationException("There was an error updating this ticket. Call tech support 1-800-POD-1011");
		}
	}

}
