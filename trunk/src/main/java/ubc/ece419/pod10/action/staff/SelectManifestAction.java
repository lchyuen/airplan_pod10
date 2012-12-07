package ubc.ece419.pod10.action.staff;

import java.util.ArrayList;
import java.util.List;
import ubc.ece419.pod10.action.BaseAction;
import ubc.ece419.pod10.domain.Flight;
import ubc.ece419.pod10.domain.Staff;
import ubc.ece419.pod10.domain.User;
import ubc.ece419.pod10.domain.User;
import ubc.ece419.pod10.service.FlightService;
import ubc.ece419.pod10.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

public class SelectManifestAction extends BaseAction{
	List<Flight> selectionList = new ArrayList<Flight>();
	String userId, staffPlace, message = "";
	Long userType;

	FlightService flightService = new FlightService();
	UserService userService = new UserService();
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getUserType() {
		return userType;
	}

	public void setUserType(Long userType) {
		this.userType = userType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String execute() {
		if (sessionToken == null || sessionToken.isEmpty())
			return ERROR;
		//Long userId_l = Long.parseLong(userId);
		//If userId is null, or if user cannot be authenticated, re-direct user to the login page.
		if (!authenticate(sessionToken)) {
			return LOGIN;
		}
		
		//check whether the user is a customer, staff, or manager
		User user = userService.getUserByToken(sessionToken);
		if(user == null) return ERROR;
		List<Flight> flights = flightService.getFlightSelection();
		
		userType = user.getUserType();
		selectionList.clear();
		
		//decide what to show the user based on what user_type is
		if(userType == 0){
			message = "Customers: you may not view any flights.";
			//don't allow customers to see the flight manifests
			return ERROR;
		}
		else if(userType == 1){		//user is a staff
			//only show flights that have the staff's place as the origin/destination
			String flightOrigin, flightDestination;
			Staff staff = userService.getStaffByUserToken(sessionToken);
			staffPlace = staff.getPlace();
			for(int i = 0; i < flights.size(); i++){
				flightDestination = flights.get(i).getDestination();
				flightOrigin = flights.get(i).getOrigin();
				//message += staffPlace + " " + flightDestination + " " + flightOrigin + "<br>";
				message = "Staff: You may view flights where the origin or destination is your location.";
				
				if( flightOrigin.equals(staffPlace)  || flightDestination.equals(staffPlace)){
					selectionList.add(flights.get(i));
				}
			}
		}
		else if(userType == 2){ 	//user is a manager
			message = "Managers: you may view all flights.";
			selectionList = flights;	//managers see all flights
		}	
		
		if (selectionList == null) {
			return ERROR;
		}		
		
		return SUCCESS;
	}

	public List<Flight> getSelectionList() {
		return selectionList;
	}

	public void setSelectionList(List<Flight> selectionList) {
		this.selectionList = selectionList;
	}

	public FlightService getFlightService() {
		return flightService;
	}

	public void setFlightService(FlightService flightService) {
		this.flightService = flightService;
	}
}
