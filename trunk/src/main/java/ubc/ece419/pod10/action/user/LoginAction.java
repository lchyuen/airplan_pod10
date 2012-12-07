package ubc.ece419.pod10.action.user;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ubc.ece419.pod10.action.BaseAction;
import ubc.ece419.pod10.service.UserService;


public class LoginAction extends BaseAction {
	
	UserService userService = new UserService();
	String password, emailAddress, errorMessage;
	Boolean loggedIn = false;
	String userId;
	String sessionToken;
	
	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public String execute() {
		
		// If both emailAddress and password are null, user just landed on page
		if (password == null && emailAddress == null) {
			return INPUT;
		}
		
		if (password == null || emailAddress == null) {
			errorMessage = "Please fill out all the fields";
			System.out.println("Please fill out all the fields");
			return ERROR;
		}
		
		Long userIdFromDb = userService.loginCheck(emailAddress, password);
		
		if (userIdFromDb == null) {
			errorMessage = "Incorrect password for input email.";
			System.out.println("Incorrect password for input email.");
			return ERROR;
		}
		
		user = userService.getUserById(userIdFromDb);
		if (user.getUserType() != 0) {
			return ERROR;
		}
		
		// Generate session token and store it in the user table.
		try {
			userService.updateSessionToken(userIdFromDb);
			sessionToken = userService.getUserTokenById(userIdFromDb);
		} catch(Exception e) {
			System.out.println("Could not update session");
		}
		
		// Redirect to search page.
		
		loggedIn = true;	
		
		return SUCCESS;
		
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

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Boolean getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}