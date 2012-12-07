package ubc.ece419.pod10.domain;

import java.util.Map;

public class Statistics {
	
	public Statistics() {
		super();
	}
	public Statistics(Integer flightsDelayed, Integer flightsEarly, Integer flightsOnTime, Integer flightsFull, Integer flightsHalfEmpty,
			Map<String, Integer> flightOriginMap, Map<String, Integer> flightDestinationMap, String mostPopular) {
		super();
		this.flightsDelayed = flightsDelayed;
		this.flightsEarly = flightsEarly;
		this.flightsOnTime = flightsOnTime;
		this.flightsFull = flightsFull;
		this.flightsHalfEmpty = flightsHalfEmpty;
		this.flightOriginMap = flightOriginMap;
		this.flightDestinationMap = flightDestinationMap;
		this.mostPopular = mostPopular;
	}
	private Integer flightsDelayed;
	private Integer flightsEarly;
	private Integer flightsOnTime;
	private Integer flightsFull;
	private Integer flightsHalfEmpty;
	private Map<String, Integer> flightOriginMap;
	private Map<String, Integer> flightDestinationMap;
	private String  mostPopular;
	

	public Integer getFlightsDelayed() {
		return flightsDelayed;
	}
	public void setFlightsDelayed(Integer flightsDelayed) {
		this.flightsDelayed = flightsDelayed;
	}
	public Integer getFlightsEarly() {
		return flightsEarly;
	}
	public void setFlightsEarly(Integer flightsEarly) {
		this.flightsEarly = flightsEarly;
	}
	public Integer getFlightsOnTime() {
		return flightsOnTime;
	}
	public void setFlightsOnTime(Integer flightsOnTime) {
		this.flightsOnTime = flightsOnTime;
	}
	public Integer getFlightsFull() {
		return flightsFull;
	}
	public void setFlightsFull(Integer flightsFull) {
		this.flightsFull = flightsFull;
	}
	public Integer getFlightsHalfEmpty() {
		return flightsHalfEmpty;
	}
	public void setFlightsHalfEmpty(Integer flightsHalfEmpty) {
		this.flightsHalfEmpty = flightsHalfEmpty;
	}
	public String getMostPopular() {
		return mostPopular;
	}
	public void setMostPopular(String mostPopular) {
		this.mostPopular = mostPopular;
	}
	public Map<String, Integer> getFlightOriginMap() {
		return flightOriginMap;
	}
	public void setFlightOriginMap(Map<String, Integer> flightOriginMap) {
		this.flightOriginMap = flightOriginMap;
	}
	public Map<String, Integer> getFlightDestinationMap() {
		return flightDestinationMap;
	}
	public void setFlightDestinationMap(Map<String, Integer> flightDestinationMap) {
		this.flightDestinationMap = flightDestinationMap;
	}
	
}
