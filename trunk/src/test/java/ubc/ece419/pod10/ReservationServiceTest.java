package ubc.ece419.pod10;

import java.util.ArrayList;
import java.util.List;

import ubc.ece419.pod10.*;
import ubc.ece419.pod10.domain.Billing;
import ubc.ece419.pod10.domain.Booking;
import ubc.ece419.pod10.domain.Flight;
import ubc.ece419.pod10.domain.Ticket;
import ubc.ece419.pod10.domain.TicketSet;
import ubc.ece419.pod10.service.FlightService;
import ubc.ece419.pod10.service.ReservationService;
import ubc.ece419.pod10.service.UserService;
import junit.framework.TestCase;
import com.opensymphony.xwork2.ActionSupport;

//Please run UserServiceTest at least once
public class ReservationServiceTest extends TestCase {
	
	public static Long testUserID;
	public static Long testBookingID;
	public static Long testFlightID;
	public static List<Flight> testFlightList = new ArrayList<Flight>(); 
	public static List<Flight> listOfFlights;
	//Boolean continueTest = false;
	private static boolean prep = false;
	private static boolean testAddBookings = false;
	private static boolean testAddTickets = false;
	
	String name = "tester";
	String nameComp = "tester_comp";
	String password = "test1234";
	String emailAddress  = "test@pod10.com";
	String creditCardNum = "4500123456785555";
	String seatType = "1";
	String seatTypeComp = "2";
	String seatPref = "No Preference";
	String seatPrefComp = "No Preference";
	String mealPref = "No Preference";
	String mealPrefComp = "Chicken";
	
	UserService userService = new UserService();
	FlightService flightService = new FlightService();
	ReservationService reservationService = new ReservationService();
	
	public void testPrep() throws Exception
	{
		Flight fl = new Flight();
		
		prep = true;
				
		try {
			testUserID = userService.loginCheck(emailAddress, password);
		} catch (Exception e) {
			//continueTest = false;
			System.out.println(e.getMessage());
			fail("==TEST ABORTED: Prep==");
			return;
		}
		
		if(testUserID == null)
		{
			//continueTest = false;
			System.out.println("==Cannot find User ID with given email and password==");
			fail("==Reservation Service TEST ABORTED==");
		}
		
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
		
	
		fl = listOfFlights.get(0);
		
		testFlightID = fl.getId();

	}
	
	public void testAddBookings() throws Exception {
		
		Long retUserID;
		
		if(!prep)
		{
			testPrep();
		}
		
		testAddBookings = true;
		
		try {
			testBookingID = reservationService.addBooking(testUserID);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail("==TEST ABORTED: Test Add Bookings==");
			return;
		}
		
		assertNotNull("testAddBooking returned NULL booking ID",testBookingID);
		
		//Test Get Bookings
		retUserID = reservationService.getCurrentBooking(testBookingID).getUserID();
		assertEquals("Get Booking returns incorrect user ID", testUserID,retUserID);
	
	}
	
	public void testBookingLists() throws Exception
	{
		if(!prep)
			testPrep();
		else if(!testAddBookings)
			testAddBookings();
		
		boolean bookingFound = false;
		Booking currBooking = new Booking();
		
		List<Booking> bookingList = new ArrayList<Booking>(); 
		
		bookingList = reservationService.getBookingList(testUserID);
		
		for(int i=0; i<bookingList.size(); i++)
		{
			currBooking = bookingList.get(i);
			
			if((currBooking.getId() == testBookingID) && (currBooking.getUserID() == testUserID))
			{
				bookingFound = true;
				break;
			}
		}
		
		assertTrue("Booking List function cannot find booking", bookingFound);
		
	}
	
	
	public void testAddTickets() throws Exception
	{
		
		if(!prep)
			testPrep();
		else if(!testAddBookings)
			testAddBookings();
		
		List<TicketSet> ticketList = new ArrayList<TicketSet>();

		Ticket ticket1 = new Ticket();
		Ticket ticket2 = new Ticket();
		
		testAddTickets= true;
		
		try {
			testFlightList = reservationService.addTickets(testUserID, testFlightID, testBookingID, name, seatType, seatPref, mealPref);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail("==TEST ABORTED: Test Add Tickets==");
			return;
		}
		
		if(testFlightList == null)
		{
			System.out.println("==Something wrong when adding tickets==");
			fail("==TEST ABORTED: Test Add Tickets==");
		}
		
		//Add ticket for companion
		try {
			testFlightList = reservationService.addTickets(testUserID, testFlightID, testBookingID, nameComp, seatTypeComp, seatPrefComp, mealPrefComp);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail("==TEST ABORTED: Test Add Tickets==");
			return;
		}
		
		//Test get tickets
		ticketList  = reservationService.getTicketSetList(testBookingID);
		
		assertEquals("getTicketSetList returned ticket incorrect", 2, ticketList.size());
		
		ticket1 = ticketList.get(0).getTicket();
		ticket2 = ticketList.get(1).getTicket();
		
		if(ticket1.getPassengerName().equals(name)){
			assertTrue("Seat Type Incorrect", ticket1.getSeatType().equals(seatType));
			assertTrue("Seat Pref Incorrect", ticket1.getSeatPref().equals(seatPref));
			assertTrue("Meal Pref Incorrect", ticket1.getMealPref().equals(mealPref));
		}
		else if(ticket1.getPassengerName().equals(nameComp)){
			assertTrue("Seat Type Incorrect", ticket1.getSeatType().equals(seatTypeComp));
			assertTrue("Seat Pref Incorrect", ticket1.getSeatPref().equals(seatPrefComp));
			assertTrue("Meal Pref Incorrect", ticket1.getMealPref().equals(mealPrefComp));
		}
		
		if(ticket2.getPassengerName().equals(name)){
			assertTrue("Seat Type Incorrect", ticket2.getSeatType().equals(seatType));
			assertTrue("Seat Pref Incorrect", ticket2.getSeatPref().equals(seatPref));
			assertTrue("Meal Pref Incorrect", ticket2.getMealPref().equals(mealPref));
		}
		else if(ticket2.getPassengerName().equals(nameComp)){
			assertTrue("Seat Type Incorrect", ticket2.getSeatType().equals(seatTypeComp));
			assertTrue("Seat Pref Incorrect", ticket2.getSeatPref().equals(seatPrefComp));
			assertTrue("Meal Pref Incorrect", ticket2.getMealPref().equals(mealPrefComp));
		}
		
	
		
	}
	
	public void testBilling() throws Exception
	{
		if(!prep)
			testPrep();
		else if(!testAddBookings)
			testAddBookings();
		if(!testAddTickets)
			testAddTickets();

		
		Billing bill_init = new Billing();
		Billing bill = new Billing();
		//int ret;
		int amount = 2500;
		
		try {
			bill_init = reservationService.addBilling(testUserID, testBookingID, amount, name, creditCardNum, false);
		} catch (Exception e) {

			System.out.println(e.getMessage());
			fail("==TEST ABORTED: Add Billing==");
			return;
		}
		
		//assertNotNull("Add billing returns 0 or negative", bill_init);
		
		try {
			bill = reservationService.getCurrentBilling(testBookingID);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail("==TEST ABORTED: Get Billing==");
			return;
		}
		
		assertEquals("Billing: Amount not equal", amount, bill.getAmount());
		//assertEquals("Billing: UserID not equal", testUserID, bill.getUserId());
		assertEquals("Billing: Name not equal", name, bill.getName());
		assertEquals("Billing: Credit card # not equal", creditCardNum, bill.getCreditCardNum());
		assertFalse("Billing: Loyalty point was not used!", bill.isLoyaltyPointsUsed());
		
	}
	
	// Get manifests is actually Flight Service layer, but test here since need to verify booking
	public void testGetManifests() throws Exception
	{
		if(!prep)
			testPrep();
		else if(!testAddBookings)
			testAddBookings();
		if(!testAddTickets)
			testAddTickets();
		
		boolean ticketFound = false;
		
		List<Ticket> ticketList = new ArrayList<Ticket>(); 
		Ticket retTicket = new Ticket();
		
		ticketList = flightService.getFlightManifest(testFlightID);
		
		for(int i=0; i<ticketList.size(); i++)
		{
			retTicket = ticketList.get(i);
			if(retTicket.getBookingId().equals(testBookingID) && retTicket.getPassengerName().equals(name))
			{
				assertTrue("Seat Type Incorrect", retTicket.getSeatType().equals(seatType));
				assertTrue("Seat Pref Incorrect", retTicket.getSeatPref().equals(seatPref));
				assertTrue("Meal Pref Incorrect", retTicket.getMealPref().equals(mealPref));
				
				ticketFound = true;
				break;
			}
		}
		
		assertTrue("Ticket not found", ticketFound);
	}
	
}
