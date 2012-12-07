package ubc.ece419.pod10.action.staff;

import java.util.List;

import ubc.ece419.pod10.action.BaseAction;
import ubc.ece419.pod10.domain.Ticket;
import ubc.ece419.pod10.service.FlightService;

import com.opensymphony.xwork2.ActionSupport;

public class ViewManifestAction extends BaseAction {

	String flightManifest_flightId;
	
	List<Ticket> ticketList;
	
	FlightService flightService = new FlightService();


	public String execute() {
		
		// If userId is null, or if user cannot be authenticated, re-direct user to the login page.

		if (sessionToken == null || sessionToken.isEmpty()) {
			return LOGIN;
		}
		
		if (!authenticate(sessionToken)) {
			return LOGIN;
		}
		
		if (flightManifest_flightId == null) {
			return ERROR;
		}
		Long flightManifest_flightIdInt = Long.parseLong(flightManifest_flightId);

		ticketList = flightService.getFlightManifest(flightManifest_flightIdInt);
		
		return SUCCESS;
	}

	
	public List<Ticket> getTicketList() {
		return ticketList;
	}

	public void setFlightList(List<Ticket> ticketList) {
		this.ticketList = ticketList;
	}

	public String getFlightManifest_flightId() {
		return flightManifest_flightId;
	}


	public void setFlightManifest_flightId(String flightManifest_flightId) {
		this.flightManifest_flightId = flightManifest_flightId;
	}
	
}
