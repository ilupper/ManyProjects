package com.ilupper.domain;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransitTest {

	/**
	 * Train starts us off because legacy-wise, trains can pick ppl up off the
	 * tracks as necessary w.o Stations
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		new TransitTest();
	}

	Transit transit;
	Integer numTrains = 1, numStations = 1;

	Logger log;

	private TransitTest() {

		log = LoggerFactory.getLogger(this.getClass());

		transit = Transit.transit;
		transit.setInitialTransit(numTrains, numStations);

		HashSet<Train> trains = Transit.valueOf("transit").getTrains();
		HashMap<String, Station> stations = Transit.getInstance().getStations();

		while (true) {
			
			int seconds = 5;
			Duration tripLength = Duration.ofSeconds(seconds); 
			
			LocalTime now = LocalTime.now();
		
			//pause for 5 seconds
			do {}
			while (now.plus(tripLength).isAfter( LocalTime.now() ));
			
				Integer totalTrain = 0, totalStation = 0;
				
				for (Train train: trains) 
					totalTrain += train.getTraveling().size();
				Collection<Station> collStations = stations.values();
				for (Station station: collStations) { 
					totalStation += station.getWaiting().size();
				}
				
				log.warn("Number onboard trains is " + totalTrain + " in a set of " + trains.size() + " train(s) \n"
						+ "Number waiting on platform is " + totalStation + " in a set of " + stations.size() + " station(s)"); 
		}
	}

}
