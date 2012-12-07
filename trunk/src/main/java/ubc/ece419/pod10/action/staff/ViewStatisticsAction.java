package ubc.ece419.pod10.action.staff;

import java.util.List;

import ubc.ece419.pod10.action.BaseAction;
import ubc.ece419.pod10.domain.Statistics;
import ubc.ece419.pod10.service.FlightService;

import com.opensymphony.xwork2.ActionSupport;

public class ViewStatisticsAction extends BaseAction {
	
	String userId;
	
	FlightService flightService = new FlightService();
	
	Statistics Stat;
	
	public String execute() {
		// If userId is null, or if user cannot be authenticated, re-direct user to the login page.
		if (sessionToken == null || sessionToken.isEmpty()) {
			return LOGIN;
		}
		
		if (!authenticate(sessionToken)) {
			return LOGIN;
		}
		
		
		Stat = flightService.getStatistics();
		
		return SUCCESS;
	}
	
	public Statistics getStat() {
		return Stat;
	}
	
	public void setStat(Statistics Stat) {
		this.Stat = Stat;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
