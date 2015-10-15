package com.ilupper.domain;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ilupper.util.grid.GenericColumn;

/**
 * No 2 trains can use the same station at the same time
 * 
 * @author Yeslup
 *
 */
public enum Transit {
	
	transit;
	
	@SuppressWarnings("rawtypes")
	HashSet possiblePassengers = null;
	
	Transit() {
		//just comment this for a random system
	    possiblePassengers = new GenericColumn("importFile.txt", "com.ilupper.domain.Passenger").getUniqueItems();
	}
		
	HashSet<Train> trains;
	HashMap<String, Station> stations;
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	public boolean setInitialTransit(Integer numTrains, Integer numStations) {
		if (transit.getStations() == null) {

	    	trains = new HashSet<>(numTrains);
			stations = new HashMap<>(numStations);
		
			for (int i = 0; i < numTrains; i++) {
				trains.add(new Train());
			}
			for (int j = 0; j < numStations; j++) {
				stations.put("Station " + String.valueOf(j), new Station(this.possiblePassengers));
			}
			
			return true; //successful setting
		}
		
		return false; //already existent
	}
	
	public static Transit getInstance() {	
		return transit;
	}
	
	public boolean setInitialTransit(Integer numTrains, Collection<String> stationList) {
		if (transit.getStations() == null) {
			trains = new HashSet<>(numTrains);
			Integer numStations = stationList.size();
			stations = new HashMap<>(numStations);
		
			for (int i = 0; i < numTrains; i++) {
				trains.add(new Train());
			}
			for (String stationName: stationList) {
				stations.put(stationName, new Station(this.possiblePassengers));
			}
			
			return true; //successful setting
		}
		
		return false; //already existent
	}
	
	/*
	 * seconds - duration of which to get hold of station 
	 */

	public synchronized Station getStationLock(String stationName, long seconds) {

		Duration tripLength = Duration.ofSeconds(seconds);

		LocalTime now = LocalTime.now();

		do {
			//log.debug("Should be in wait for " + seconds	+ " seconds. ");
		} while (now.plus(tripLength).isAfter(LocalTime.now()));

		return stations.get(stationName);
	}
	
	public HashSet<Train> getTrains() {
		return trains;
	}
	
	public HashMap<String, Station> getStations() {
		return stations;
	}
	
	public void addTrain() {
		trains.add(new Train());
	}
	
	public void addStation() {
		Integer nextMark = stations.size(); //index starts at 0
		stations.put("Station " + nextMark, new Station(this.possiblePassengers));
	}
	
	public void addStation(String stationName) {
		stations.put(stationName, new Station(this.possiblePassengers));
	}
	
}
