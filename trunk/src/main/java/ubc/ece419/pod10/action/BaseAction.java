package ubc.ece419.pod10.action;

import ubc.ece419.pod10.domain.User;
import ubc.ece419.pod10.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class BaseAction extends ActionSupport {
	public User user;
	public String userId;
	public String sessionToken;
	

	UserService userService = new UserService();
	public BaseAction() {
		super();
	}
	public String execute() {
		//Long userIdLong = Long.parseLong(userId);
		authenticate(sessionToken);
		return SUCCESS;
	}
	
	/**
	* Authenticates a userId. Also sets user.
	* @param userId Identifies a user.
	* @return True is user is authenticated, false otherwise.
	*/
	public Boolean authenticate(String token) {
		user = userService.getUserByToken(token);
		return userService.hasValidSession(token);
	}
	
	public String input() {
		return SUCCESS;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSessionToken() {
		return sessionToken;
	}
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
}