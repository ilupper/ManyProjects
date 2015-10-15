package com.ilupper.restful;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import javax.ws.rs.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ilupper.domain.Station;
import com.ilupper.domain.Train;
import com.ilupper.domain.Transit;

@Path("rs/simple")
public class SimpleService {
	
	Logger log;
	public SimpleService() {
		log = LoggerFactory.getLogger(this.getClass());
	}

	@GET
	@Path("simpleGET")
	public String getStringAndDate() {
		return "a string. Date = " + LocalDate.now();
	}

	@GET
	@Path("transit")
	public String getTrainOnboard() {
		Transit transit = Transit.getInstance();
		HashSet<Train> trains = null;
		HashMap<String, Station> stations = null;

		transit.setInitialTransit(1, 1);

		trains = Transit.getInstance().getTrains();
		stations = Transit.getInstance().getStations();

		Integer totalTrain = 0, totalStation = 0;
		
		for (Train train: trains) 
			totalTrain += train.getTraveling().size();
		Collection<Station> collStations = stations.values();
		for (Station station: collStations) { 
			totalStation += station.getWaiting().size();
		}

		log.info("Number onboard trains is " + totalTrain + " in a set of " + trains.size() + " train(s) \n"
				+ "Number waiting on platform is " + totalStation + " in a set of " + stations.size() + " station(s)");
		return "Number onboard trains is " + totalTrain + " in a set of " + trains.size() + " train(s) \n"
				+ "Number waiting on platform is " + totalStation + " in a set of " + stations.size() + " station(s)";

	}

}
