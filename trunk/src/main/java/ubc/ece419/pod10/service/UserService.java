package ubc.ece419.pod10.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import ubc.ece419.pod10.domain.Staff;
import ubc.ece419.pod10.domain.Transactions;
import ubc.ece419.pod10.domain.User;
import ubc.ece419.pod10.exceptions.UnableToCreateUserException;

import com.opensymphony.xwork2.ActionSupport;

public class UserService extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	private static final String URL = "jdbc:mysql://localhost:3306/pod10_db?zeroDateTimeBehavior=convertToNull";
	private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static final String DB_USER_NAME = "root";
	private static final String DB_PASSWORD = "ECE419_pod10";
	Connection con = null;
	Statement stmt = null;
	MessageDigest messageDigest = null;

	public UserService() {
		super();
		try { 
			// Set up database connection
			Class.forName(DRIVER_NAME).newInstance();
			con = DriverManager.getConnection(URL, DB_USER_NAME, DB_PASSWORD);
			
			// Set up MessageDigest
			messageDigest = MessageDigest.getInstance("MD5");
		} catch(InstantiationException e) {
			e.printStackTrace();
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks whether email address is already in database or not.
	 * @param emailAddress The email address to check for. 
	 * @return False if email address is not already used, true otherwise. 
	 */
	public Boolean isEmailAddressAlreadyUsed(String emailAddress) {
		try {
			ResultSet rs = null;
			PreparedStatement ps = con.prepareStatement("SELECT * FROM User WHERE email_address = ?");
			ps.setString(1, emailAddress);
			rs = ps.executeQuery();
			if (rs == null) {
				// getting a null back is illegal, so returning true. 
				return true;
			} else {
				if (rs.next()) {
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * Creates a user with given name, password and email address. 
	 * @param name The name of the user.
	 * @param password The password that the user selects. 
	 * @param emailAddress The email address of the user. 
	 * @throws UnableToCreateUserException Thrown when user cannot be created. This could be because of a non-unique
	 * email address.
	 */
	public Long createUser(String name, String password, String emailAddress) throws UnableToCreateUserException {
		try {
			if (isEmailAddressAlreadyUsed(emailAddress)) {
				throw new UnableToCreateUserException("Email address is already used. Please try some other email address.");
			}
			String salt = UUID.randomUUID().toString().substring(0,16);
			String saltedPassword = salt + password;
			messageDigest.update(saltedPassword.getBytes(), 0, saltedPassword.length());
			String password_hashed = (new BigInteger(1, messageDigest.digest())).toString(16).substring(0,16);
			String sessionToken = generateRandomSessionToken();
			Timestamp tomorrow = new Timestamp(System.currentTimeMillis() + 86400000);
			PreparedStatement ps = con.prepareStatement("INSERT INTO User (date_created, date_modified, name, password, salt, email_address, session_token, session_expiry_date) VALUES (now(),now(),?, ?, ?, ?, ?, ?)");
			ps.setString(1, name);
			ps.setString(2, password_hashed);
			ps.setString(3, salt);
			ps.setString(4, emailAddress);
			ps.setString(5, sessionToken);
			ps.setTimestamp(6, tomorrow);
			int result = ps.executeUpdate();
			if (result != 1) {
				throw new UnableToCreateUserException("Unable to create user for " + name);
			} 
			
			// Get userId and return it.
			PreparedStatement ps2 = con.prepareStatement("SELECT * from User WHERE email_address=?");
			ps2.setString(1, emailAddress);
			ResultSet rs = ps2.executeQuery();
			rs.next();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String now = dateFormat.format(cal.getTime());
			System.out.println("Create New User");
			System.out.println("Modified Table: User");
			System.out.println("Modified Time: " + now);
			System.out.println("New Account Name: " + name);
			System.out.println("New Account Email: " + emailAddress);
			System.out.println("New Account password: " + password);
			
			return rs.getLong("id");
		} catch(SQLException e) {
			  System.out.println("Exception caught: " + e);
		}

		return null;
	}
	
	/**
	 * Logs in a user and returns user id if successful.
	 * @param emailAddress Email Address of user.
	 * @param password Password that user entered.
	 * @return userId if the matching email address and password are entered. Null otherwise. 
	 */
	public Long loginCheck(String emailAddress, String password) {
		
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM User WHERE email_address = ?");
			ps.setString(1, emailAddress);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String db_password = rs.getString("password");
			String db_salt = rs.getString("salt");
			String saltedPassword = db_salt + password;
			messageDigest.update(saltedPassword.getBytes(), 0, saltedPassword.length());
			String passwordHashed = (new BigInteger(1, messageDigest.digest())).toString(16).substring(0,16);
			if (passwordHashed.equals(db_password)) {
				return rs.getLong("id");
			}
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		
		return null;
	}
	
	/**
	 * Get session token from user id.
	 * @param userId.
	 * @return session token. 
	 */
	public String getUserTokenById(Long userId) {
		
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM User WHERE id = ?");
			ps.setLong(1, userId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getString("session_token");
			
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		
		return null;
	}
	
	/**
	 * Get session token from user id.
	 * @param userId.
	 * @return session token. 
	 */
	public Long getUserIdByToken(String token) {
		
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM User WHERE session_token = ?");
			ps.setString(1, token);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getLong("id");
			
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		
		return null;
	}

	/**
	 * 
	 * @param userId
	 * @return List of transactions returned from database. Null if no valid transactions are found.
	 */
	public List<Transactions> getTransactions(Long userId) {
		
		List<Transactions> transactionList = new ArrayList<Transactions>(); 
		
		try{
			PreparedStatement ps, ps1 = null;
			ResultSet rs, rs1 = null;
				
			ps = con.prepareStatement("SELECT * FROM billing WHERE user_id = ?");
			ps.setLong(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Transactions trans = new Transactions();
				
				Long billingId = rs.getLong("id");
				trans.setBillingId(billingId);
				trans.setBookingId(rs.getLong("booking_id"));
				trans.setDateCreated(rs.getTimestamp("date_created"));
				
				if(!rs.getBoolean("is_loyalty_points_used")) {
					trans.setAmount(rs.getLong("amount"));
					String fullCard = rs.getString("credit_card_number");
					trans.setCreditCardNumber("XXXX XXXX XXXX " + fullCard.substring(fullCard.length() - 4));
					trans.setName(rs.getString("name"));
				} else{
					trans.setCreditCardNumber(null);
					trans.setName(null);
				}
				
				ps1 = con.prepareStatement("SELECT * FROM points WHERE billing_id = ?");
				ps1.setLong(1, billingId);
				rs1 = ps1.executeQuery();
				
				if (rs.getBoolean("is_loyalty_points_used")) {
					// If Loyalty points have been used, query the points table for the amount of points used, and the reason.					
					rs1.next();		
					
					trans.setAmount(rs1.getLong("amount"));
					trans.setReason(rs1.getString("reason"));					
				} else {
					// If loyalty points have not been used, check the points table for any entries for that billing. If there are any entries, 
					// then get amount and reason from that entry, else display message saying that you will be awarded when you print the boarding pass.
					if (rs1.next()) {
						trans.setPointsAwarded(new Long((long)rs1.getInt("amount")));
						trans.setReason(rs1.getString("reason"));
					} else {
						trans.setPointsAwarded(null);
						if (trans.getAmount() < 0)
							trans.setReason("Booking Cancelled (70% of the original amount refunded)");
						else
							trans.setReason("Points will be awarded after boarding pass is printed.");
					}
					
				}
					
					transactionList.add(trans);
				}
			return transactionList;
				
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		
		return null;
	}
	/**
	 * Use Case: View transactions.
	 * Query database for user's current points balance.  
	 * @param userId of a user.
	 * @return total number of points. 
	 */
	public int getPoints(Long userId) {
		
		int points;
		
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE id = ?");
			ps.setLong(1, userId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			points = rs.getInt("current_point_balance");
			
			System.out.println(points);
			return points;
			
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		
		return -1;
	}
	
	/**
	 * Checks if a user's session has expired or not.
	 * @param userId Identifies the user.
	 * @return True if session has expired, false otherwise
	 */
	public Boolean hasValidSession(String token) {
		//User user = getUserById(userId);
		User user = getUserByToken(token);
		Long now = System.currentTimeMillis();
		if (user.getSessionToken() != null && user.getSessionExpirationDate().getTime()  > (now)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Updates the session token for user.
	 * @param userId Identifies the user.
	 * @throws Exception When no user can be found for userId
	 */
	public void updateSessionToken(Long userId) throws Exception {
		
		String sessionToken = generateRandomSessionToken();
		Timestamp tomorrow = new Timestamp(System.currentTimeMillis() + 86400000);
		try  {
			PreparedStatement ps = con.prepareStatement("UPDATE User SET session_token=?, session_expiry_date=?, date_modified = now() WHERE id=?");
			ps.setString(1, sessionToken);
			ps.setTimestamp(2, tomorrow);
			ps.setLong(3, userId);
			int result = ps.executeUpdate();
			if (result != 1) {
				throw new Exception("Could not find user " + userId);
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String now = dateFormat.format(cal.getTime());
			System.out.println("Update User Session Token");
			System.out.println("Modified Table: User");
			System.out.println("Modified Time: " + now);
			System.out.println("New sessionToken: " + sessionToken);
			System.out.println("New sessionToken expire date: " + tomorrow);

		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get a User object from userId.
	 * @param userId Identifies the user.
	 * @return User object
	 */
	public User getUserById(Long userId) {
		User user = null;
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM user where id = ?");
			ps.setLong(1, userId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			user = new User();
			user.setId(rs.getLong("id"));
			user.setEmailAddress(rs.getString("email_address"));
			user.setPassword(rs.getString("password"));
			user.setName(rs.getString("name"));
			user.setSessionToken(rs.getString("session_token"));
			user.setSessionExpirationDate(rs.getTimestamp("session_expiry_date"));
			user.setCurrentPointsBalance(rs.getLong("current_point_balance"));
			user.setUserType(rs.getLong("user_type"));
			user.setDateCreated(rs.getTimestamp("date_created"));
			user.setDateModified(rs.getTimestamp("date_modified"));						
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	//gets the entry from the staff table associated with a user
	//an entry only exists if the user_type is 1 or 2 (staff or management)
	/**
	 * Get a Staff object from userId.
	 * @param userId Identifies the staff.
	 * @return Staff object
	 */
	public Staff getStaffByUserId(long userId)
	{
		Staff staff = new Staff();
		try{
			//query the staff table
			PreparedStatement ps = con.prepareStatement("SELECT * FROM staff where user_id = ?");
			ps.setLong(1, userId);
			ResultSet rs = ps.executeQuery();
			if(!rs.next())
				return null;
			staff.setId(rs.getLong("id"));
			staff.setPlaceId(rs.getLong("place_id"));
			staff.setUserId(userId);
			
			//query the place table for the place name
			ps = con.prepareStatement("SELECT * FROM place where id = ?");
			ps.setLong(1, staff.getPlaceId());
			rs = ps.executeQuery();
			if(!rs.next())
				return null;
			staff.setPlace(rs.getString("code"));
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return staff;
	}
	
	/**
	 * Get a User object from session token.
	 * @param token Identifies the user.
	 * @return User object
	 */
	public User getUserByToken(String token) {
		User user = null;
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM user where session_token = ?");
			ps.setString(1, token);
			ResultSet rs = ps.executeQuery();
			rs.next();
			user = new User();
			user.setId(rs.getLong("id"));
			user.setEmailAddress(rs.getString("email_address"));
			user.setPassword(rs.getString("password"));
			user.setName(rs.getString("name"));
			user.setSessionToken(rs.getString("session_token"));
			user.setSessionExpirationDate(rs.getTimestamp("session_expiry_date"));
			user.setCurrentPointsBalance(rs.getLong("current_point_balance"));
			user.setUserType(rs.getLong("user_type"));
			user.setDateCreated(rs.getTimestamp("date_created"));
			user.setDateModified(rs.getTimestamp("date_modified"));						
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	//gets the entry from the staff table associated with a user
	//an entry only exists if the user_type is 1 or 2 (staff or management)
	/**
	 * Get a Staff object from session token.
	 * @param token Identifies the staff.
	 * @return Staff object
	 */
	public Staff getStaffByUserToken(String token)
	{
		Staff staff = new Staff();
		try{
			//query the staff table
			PreparedStatement ps = con.prepareStatement("SELECT staff.id, staff.user_id, staff.place_id FROM staff, user WHERE user.session_token = ? AND user.id = staff.user_id");
			ps.setString(1, token);
			ResultSet rs = ps.executeQuery();
			if(!rs.next())
				return null;
			staff.setId(rs.getLong("id"));
			staff.setPlaceId(rs.getLong("place_id"));
			staff.setUserId(rs.getLong("user_id"));
			
			//query the place table for the place name
			ps = con.prepareStatement("SELECT * FROM place where id = ?");
			ps.setLong(1, staff.getPlaceId());
			rs = ps.executeQuery();
			if(!rs.next())
				return null;
			staff.setPlace(rs.getString("code"));
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return staff;
	}
	
	/**
	 * Generate a random session token to keep current session active
	 * @return Session token as String
	 */
	public String generateRandomSessionToken() {
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String sessionTokenString = now.toString();
		messageDigest.update(sessionTokenString.getBytes(), 0, sessionTokenString.length());
		return (new BigInteger(1, messageDigest.digest())).toString(16).substring(0,16);
	}
	
	public long getUserIdByEmail(String email) {
		long userId = -1;
		try {
			PreparedStatement ps = con.prepareStatement("SELECT id, user_type FROM user where email_address = ?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) 
			{	
			userId = rs.getLong("id");
			// Set the userId to -2 if the user_type isn't User (we can't log in as admin or managers)
			if (rs.getInt("user_type") != 0) userId = -2;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return userId;
	}
	
	
}
