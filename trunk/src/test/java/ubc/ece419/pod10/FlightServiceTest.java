package ubc.ece419.pod10;

import java.util.List;

import junit.framework.TestCase;
import ubc.ece419.pod10.action.user.UserSearchFlightsAction;
import ubc.ece419.pod10.domain.Flight;
import ubc.ece419.pod10.domain.Statistics;
import ubc.ece419.pod10.service.FlightService;

import com.opensymphony.xwork2.ActionSupport;

public class FlightServiceTest extends TestCase {

	public static List<Flight> listOfFlights;
	
	private static boolean testGetFlights = false;
	
	FlightService flightService = new FlightService();
	
	
	public void testGetFlights() throws Exception
	{
		
		try {
			listOfFlights = flightService.getFlightSelection();
		} catch (Exception e) {
			//continueTest = false;
			System.out.println(e.getMessage());
			fail("==TEST ABORTED: Get Flight Lists==");
			return;
		}
		
		if(listOfFlights == null)
		{
			fail("Null Flight Lists returned");
		}
		
		testGetFlights = true;
	}
	
	public void testSearchFlights() throws Exception
	{
		
		if(!testGetFlights)
		{
			testGetFlights();
		}
			
		
		List<Flight> searchResult;
		boolean found = false;
		String msg;
		
		for(int i=0; i<listOfFlights.size(); i++)
		{
			Flight fl = new Flight();
			
			fl = listOfFlights.get(i);
			try{
				searchResult = flightService.getFlights(fl.getOrigin(), fl.getDestination(), fl.getDepartureTime(), 1, false);
			
				found = false;
				for(int j=0; j<searchResult.size();j++)
				{
					Flight flret = new Flight();
					flret = searchResult.get(j);
					if(fl.getId() == flret.getId())
					{
						found = true;
						break;
					}
					msg = "Cannot find ID " + fl.getId() + " from " + fl.getOrigin() + " to " + fl.getDestination();
					assertTrue("msg", found);
				}
		
			} catch (Exception e) {
				System.out.println("flightService.getFlights failed");
				System.out.println(e.getMessage());
			}
					
		}
		
	}
	
	
	public void testGetStatistics() throws Exception
	{
		Statistics stat = new Statistics();
		
		try{
			stat = flightService.getStatistics();
		} catch (Exception e) {
			System.out.println("flightService.getStatistics failed");
			System.out.println(e.getMessage());
		}
		
		assertNotNull("Null Statistics returned", stat);
	}
	
	
	
}
