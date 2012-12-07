package ubc.ece419.pod10.action.staff;

import ubc.ece419.pod10.action.BaseAction;
import ubc.ece419.pod10.service.UserService;

@SuppressWarnings("serial")
public class StaffGetCustomerAction extends BaseAction{

	String userId, email;
	Long customerId;
	String customerToken;
	
	public String getCustomerToken() {
		return customerToken;
	}

	public void setCustomerToken(String customerToken) {
		this.customerToken = customerToken;
	}
	
	UserService userService = new UserService();

	public String execute() {
		
		// If userId is null, or if user cannot be authenticated, re-direct user to the login page.
		if (sessionToken == null || sessionToken.isEmpty()) {
			return LOGIN;
		}
		
		if (!authenticate(sessionToken)) {
			return LOGIN;
		}
		
		customerId = userService.getUserIdByEmail(email);
		customerToken = userService.getUserTokenById(customerId);
		return SUCCESS;
	}

	public String getEmailInput() {
		// If userId is null, or if user cannot be authenticated, re-direct user to the login page.
		if (sessionToken == null || sessionToken.isEmpty()) {
			return LOGIN;
		}
		
		if (!authenticate(sessionToken)) {
			return LOGIN;
		}
		
		return SUCCESS;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
}
