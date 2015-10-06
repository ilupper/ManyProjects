package com.ilupper.domain;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * No 2 trains can use the same station at the same time
 * 
 * @author Yeslup
 *
 */
public class TransitClass {

	private static class TransitHolder {
		private static final TransitClass transit = new TransitClass();		
	}
	
	HashSet<Train> trains;
	HashMap<String, Station> stations;
	
	Logger log;
	private TransitClass() {
	     log = LoggerFactory.getLogger(this.getClass());
	}

	public static TransitClass getInstance() {	
		return TransitHolder.transit;
	}
	
	public boolean setInitialTransit(Integer numTrains, Integer numStations) {
		if (TransitHolder.transit.getStations() == null) {
			trains = new HashSet<>(numTrains);
			stations = new HashMap<>(numStations);
		
			for (int i = 0; i < numTrains; i++) {
				trains.add(new Train());
			}
			for (int j = 0; j < numStations; j++) {
				stations.put("Station " + String.valueOf(j), new Station());
			}
			
			return true; //successful setting
		}
		
		return false; //already existent
	}
	
	public boolean setInitialTransit(Integer numTrains, Collection<String> stationList) {
		if (TransitHolder.transit.getStations() == null) {
			trains = new HashSet<>(numTrains);
			Integer numStations = stationList.size();
			stations = new HashMap<>(numStations);
		
			for (int i = 0; i < numTrains; i++) {
				trains.add(new Train());
			}
			for (String stationName: stationList) {
				stations.put(stationName, new Station());
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
		stations.put("Station " + nextMark, new Station());
	}
	
	public void addStation(String stationName) {
		stations.put(stationName, new Station());
	}
	
}
