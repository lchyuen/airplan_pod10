package ubc.ece419.pod10.service;

import com.opensymphony.xwork2.ActionSupport;
import java.sql.*;
import java.util.*;

import org.omg.CORBA.Request;

import ubc.ece419.pod10.domain.Flight;
import ubc.ece419.pod10.domain.Ticket;
import ubc.ece419.pod10.domain.User;
import ubc.ece419.pod10.domain.Statistics;

public class FlightService extends ActionSupport {
	  private static final long serialVersionUID = 1L;
	  private String message;

	  String destination, destination2;
	  String flightId, flightId2, flightManifest_flightId;
	  // the following fields are used for search for flights use case
	  String origin, departureTime, availableSeats; 

	  List<Flight> listOfFlights;
	  List<Ticket> listOfTickets;
	  
	  User user;

	  private static final String URL = "jdbc:mysql://localhost:3306/pod10_db?zeroDateTimeBehavior=convertToNull";
	  private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	  private static final String DB_USER_NAME = "root";
	  private static final String DB_PASSWORD = "ECE419_pod10";
	  
	  Connection con = null;
	  Statement stmt = null;
	  
	  public FlightService() {
			super();
			try { 
				Class.forName(DRIVER_NAME).newInstance();
				con = DriverManager.getConnection(URL, DB_USER_NAME, DB_PASSWORD);
			} catch(InstantiationException e) {
				e.printStackTrace();
			} catch(IllegalAccessException e) {
				e.printStackTrace();
			} catch(ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	  
	  public String getFlightManifest_flightId() {
		return flightManifest_flightId;
	}
	  
	  
	  public List<Ticket> getListOfTickets() {
			return listOfTickets;
		}

		public void setListOfTickets(List<Ticket> listOfTickets) {
			this.listOfTickets = listOfTickets;
		}

	public void setFlightManifest_flightId(String flightManifest_flightId) {
		this.flightManifest_flightId = flightManifest_flightId;
	}
	
	/**
	 * Use Case: Get Flight manifest.
	 * Query database for a list of tickets matching the flight ID given.  
	 * @param flightId The flight ID of the flight for which we want the manifest.
	 * @return A list of tickets for the flight ID. If no tickets are found, a null is returned. 
	 */
	public List<Ticket> getFlightManifest(Long flightManifest_flightId) {
		
		List<Ticket> ticketList = new ArrayList<Ticket>(); 
		
		try{
			
			PreparedStatement ps = null;
			ResultSet rs = null;
				
			ps = con.prepareStatement("SELECT * FROM Ticket WHERE flight_id = ?");
			ps.setLong(1, flightManifest_flightId);

			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {

					Ticket tk = new Ticket();
					tk.setId(rs.getLong("id"));
					tk.setFlightId(rs.getLong("flight_id"));
					tk.setBookingId(rs.getLong("booking_id"));
					tk.setSeatType(rs.getString("seat_type"));
					tk.setSeatPref(rs.getString("seat_pref"));
					tk.setMealPref(rs.getString("meal_pref"));
					tk.setPassengerName(rs.getString("passenger_name"));

					ticketList.add(tk);
				}
				return ticketList;
			}

		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		
		return null;
	}
	
	/**
	 * Use Case: search.
	 * Query database to map place name to code.  
	 * @param code of a place.
	 * @return name of the place. 
	 */
	public String getPlaceByCode(String code) {
		
		try{
			
			PreparedStatement ps = null;
			ResultSet rs = null;

			// get origin code
			ps = con.prepareStatement("SELECT name FROM place WHERE code = ?");
			ps.setString(1, code);
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {
					String name = rs.getString("name");
					return name;
				}
			}
		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		
		return null;
	}
	
	/**
	 * Use Case: Search Flights.
	 * Query database for a list of flights matching the origin/destination/requiredSeats/departureTime.  
	 * @param origin The origin of the flight.
	 * @param destination The destination of the flight.
	 * @param departureTime The expected time of departure for that flight.  
	 * @param requiredSeats The minimum number of seats that must be available for that flight. 
	 * @param isFirstClass The seat type of the flight.
	 * @return A list of flights that satisfies above conditions. If no suitable flights are found, a null is returned. 
	 */
	public List<Flight> getFlights(String origin, String destination, Timestamp departureTime, Integer requiredSeats, boolean isFirstClass) {
		
		List<Flight> flightList = new ArrayList<Flight>(); 
		
		String origin_code = "";
		String destination_code = "";
		
		try{
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			// get origin code
			ps = con.prepareStatement("SELECT code FROM place WHERE name = ?");
			ps.setString(1, origin);
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {
					origin_code = rs.getString("code");
					break;
				}
			}
			
			// get destination code
			ps = con.prepareStatement("SELECT code FROM place WHERE name = ?");
			ps.setString(1, destination);
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {
					destination_code = rs.getString("code");
					break;
				}
			}
			
			// get time interval
			Calendar cal = Calendar.getInstance();
			int diff = 2;
			java.util.Date ts = new java.util.Date(departureTime.getTime());
			cal.setTime(ts);
			cal.add(Calendar.DATE ,diff);
			java.util.Date ts_new = cal.getTime();
			Timestamp departureTime_end = new Timestamp(ts_new.getTime());
			
			
			if (isFirstClass)
				ps = con.prepareStatement("SELECT * FROM Flight WHERE origin = ? AND destination = ? AND departure_time > ? AND departure_time < ? AND first_class_avail >= ?");
			else
				ps = con.prepareStatement("SELECT * FROM Flight WHERE origin = ? AND destination = ? AND departure_time > ? AND departure_time < ? AND economy_avail >= ?");
			ps.setString(1, origin_code);
			ps.setString(2, destination_code);
			ps.setTimestamp(3, departureTime);
			ps.setTimestamp(4, departureTime_end);
			ps.setInt(5, requiredSeats);
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {

					Flight fl = new Flight();
					fl.setId(rs.getLong("id"));
					fl.setOrigin(rs.getString("origin"));
					fl.setOriginName(origin);
					fl.setDestination(rs.getString("destination"));
					fl.setDestinationName(destination);
					fl.setDepartureTime(rs.getTimestamp("departure_time"));
					fl.setArrivalTime(rs.getTimestamp("arrival_time"));
					fl.setAirlineName(rs.getString("airline_name"));
					if (isFirstClass) {
						fl.setAvailableSeats(rs.getInt("first_class_avail"));
						fl.setCost(rs.getInt("base_cost")*5);
					}
					else {
						fl.setAvailableSeats(rs.getInt("economy_avail"));
						fl.setCost(rs.getInt("base_cost"));
					}
					
					flightList.add(fl);
				}
				return flightList;
			}

		} catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		}
		
		return null;
		
	}

	/**
	 * Use Case: View flights list.
	 * Query database for a list of flights in the flight table.  
	 * @return A list of flights. 
	 */
	public List<Flight> getFlightSelection() {
		
		List<Flight> selectionList = new ArrayList<Flight>();
		
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;

		    ps = con.prepareStatement("SELECT * FROM flight");
			rs = ps.executeQuery();
			while (rs.next()) {
				Flight selection = new Flight();
				selection.setId(rs.getLong("id"));
				selection.setOrigin(rs.getString("origin"));
				selection.setDestination(rs.getString("destination"));
				selection.setDepartureTime(rs.getTimestamp("departure_time"));
				selection.setArrivalTime(rs.getTimestamp("arrival_time"));
				selection.setAirlineName(rs.getString("airline_name"));

				selectionList.add(selection);
			}
			return selectionList;
				
		  } catch(Exception e) {
			  System.out.println("Exception caught: " + e);
		  }
		  return null;
	}
	
	/**
	 * Use Case: View flights statistics.
	 * Query database for statistics.  
	 * @return Statistics object. 
	 */
	public Statistics getStatistics() {
		
		Statistics stat = new Statistics();
		
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			//Count all flights that are delayed
			ps = con.prepareStatement("SELECT COUNT(*) FROM Flight WHERE arrival_time < actual_arrival_time");
			rs = ps.executeQuery();
			rs.next();
			stat.setFlightsDelayed(rs.getInt("count(*)"));
				
			//Count all flights that are early
			ps = con.prepareStatement("SELECT COUNT(*) FROM Flight WHERE arrival_time > actual_arrival_time");
			rs = ps.executeQuery();
			rs.next();
			stat.setFlightsEarly(rs.getInt("count(*)"));
			
			//Count all flights that are on time
			ps = con.prepareStatement("SELECT COUNT(*) FROM Flight WHERE arrival_time = actual_arrival_time");
			rs = ps.executeQuery();
			rs.next();
			stat.setFlightsOnTime(rs.getInt("count(*)"));

			//Count all flights that were full
			ps = con.prepareStatement("SELECT COUNT(*) FROM Flight WHERE first_class_avail = 0 AND economy_avail = 0");
			rs = ps.executeQuery();
			rs.next();
			stat.setFlightsFull(rs.getInt("count(*)"));
			
			//Count all flights that were less than half full
			ps = con.prepareStatement("SELECT COUNT(*) FROM Flight WHERE first_class_avail + economy_avail > total_first_class/2 + total_economy/2");
			rs = ps.executeQuery();
			rs.next();
			stat.setFlightsHalfEmpty(rs.getInt("count(*)"));
			
			//String ori;
			//Count all flights heading from origin
			ps = con.prepareStatement("SELECT origin, COUNT(*) FROM Flight GROUP BY origin");
			rs = ps.executeQuery();
			if(rs == null) {
				return null;
			}
			else {
				Map<String, Integer> originMap = new HashMap<String, Integer>();
				while (rs.next()) {
					originMap.put(rs.getString("origin"), rs.getInt("count(*)"));
					/*ori = rs.getString("origin");
					ps = con.prepareStatement("SELECT name FROM place WHERE code = ?");
					ps.setString(1, ori);
					rs = ps.executeQuery();
					rs.next();
					st.setflightOriginStr(rs.getString("name"));*/
					stat.setFlightOriginMap(originMap);
				}
				
			}
			
			//String des;
			//Count all flights heading to destination
			ps = con.prepareStatement("SELECT destination, COUNT(*) FROM Flight GROUP BY destination");
			rs = ps.executeQuery();
			if(rs == null) {
				return null;
			}
			else {
				Map<String, Integer> destinationMap = new HashMap<String, Integer>();
				while (rs.next()) {
					destinationMap.put(rs.getString("destination"), rs.getInt("count(*)"));
					/*des = rs.getString("destination");
					ps = con.prepareStatement("SELECT name FROM place WHERE code = ?");
					ps.setString(1, des);
					rs = ps.executeQuery();
					rs.next();
					st.setflightDestinationStr(rs.getString("name"));*/
					stat.setFlightDestinationMap(destinationMap);
				}
			}
			
			String popular;
			//Find most popular destination
			ps = con.prepareStatement("SELECT destination, COUNT(*) as cnt FROM Flight GROUP BY destination ORDER BY cnt desc limit 1"); //Most popular
		    rs = ps.executeQuery();
		    rs.next();
		    popular = rs.getString("destination");
		    ps = con.prepareStatement("SELECT name FROM place WHERE code = ?");
		    ps.setString(1,  popular);
		    rs = ps.executeQuery();
		    rs.next();
			stat.setMostPopular(rs.getString("name"));

		    return stat;
		} catch(Exception e) {
			System.out.println("Exception caught: " + e);
		}
		return null;
	}
	
	public String getMessage() {
	      return message;
	}
    
    public void setMessage(String message) {
	      this.message = message;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDestination2() {
		return destination2;
	}

	public void setDestination2(String destination2) {
		this.destination2 = destination2;
	}

	public String getFlightId() {
		return flightId;
	}

	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}

	public String getFlightId2() {
		return flightId2;
	}

	public void setFlightId2(String flightId2) {
		this.flightId2 = flightId2;
	}
	
	public String getOrigin() {
		return origin;
	}


	public void setOrigin(String origin) {
		this.origin = origin;
	}


	public String getDepartureTime() {
		return departureTime;
	}


	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}


	public String getAvailableSeats() {
		return availableSeats;
	}


	public void setAvailableSeats(String availableSeats) {
		this.availableSeats = availableSeats;
	}
	
	public List<Flight> getListOfFlights() {
		return listOfFlights;
	}


	public void setListOfFlights(List<Flight> listOfFlights) {
		this.listOfFlights = listOfFlights;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}


}