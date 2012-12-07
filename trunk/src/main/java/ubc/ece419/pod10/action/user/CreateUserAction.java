package ubc.ece419.pod10.action.user;

import ubc.ece419.pod10.service.UserService;
import ubc.ece419.pod10.exceptions.UnableToCreateUserException;

import com.opensymphony.xwork2.ActionSupport;

public class CreateUserAction extends ActionSupport {
	
	UserService userService = new UserService();
	// passwordCopy and submit are unused variables. Workaround so that Tomcat/Struts2 stop throwing errors for unset variables. 
	String name, password, passwordCopy, emailAddress,sessionToken;
	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	String errorMessage;
	Long userId;
	
	
	public String execute() {
		
		if (name == null || password == null || emailAddress == null) {
			errorMessage = "Please fill out all the fields";
			return ERROR;
		}
		
		if (userService.isEmailAddressAlreadyUsed(emailAddress)) {
			errorMessage = "This email address is already registered. Please use another one.";
			return ERROR;
		}
		
		try {
			userId = userService.createUser(name, password, emailAddress);
			sessionToken = userService.getUserTokenById(userId);
		} catch (UnableToCreateUserException e) {
			errorMessage = e.getMessage();
			return ERROR;
		}
		
		
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPasswordCopy() {
		return passwordCopy;
	}

	public void setPasswordCopy(String passwordCopy) {
		this.passwordCopy = passwordCopy;
	}

}
