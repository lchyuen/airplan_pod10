package ubc.ece419.pod10.action.user;

import java.util.List;

import ubc.ece419.pod10.action.BaseAction;
import ubc.ece419.pod10.domain.Transactions;
import ubc.ece419.pod10.domain.User;

import ubc.ece419.pod10.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

public class ViewTransactionHistoryAction extends BaseAction {
	
	UserService userService = new UserService();
	List<Transactions> transactionList;
	int points;
	
	
	public String execute() {
		// If userId is null, or if user cannot be authenticated, re-direct user to the login page.
		if (sessionToken == null || sessionToken.isEmpty()) {
			return LOGIN;
		}
		
		if (!authenticate(sessionToken)) {
			return LOGIN;
		}
		
		Long userIdInt = userService.getUserIdByToken(sessionToken);
		transactionList = userService.getTransactions(userIdInt);
		points = userService.getPoints(userIdInt);
		
		return SUCCESS;
		}


	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<Transactions> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<Transactions> transactionList) {
		this.transactionList = transactionList;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
}
