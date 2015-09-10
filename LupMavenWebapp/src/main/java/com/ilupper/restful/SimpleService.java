package com.ilupper.restful;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Queue;

import javax.ws.rs.*;

import com.ilupper.domain.Passenger;
import com.ilupper.domain.Station;
import com.ilupper.domain.Train;
import com.ilupper.domain.Transit;

@Path("rs/simple")
public class SimpleService
{
 
    @GET
    @Path("simpleGET")
    public String getStringAndDate() {
        return "a string. Date = " + LocalDate.now();
    }
    
    @GET
    @Path("transit")
    public String getTrainOnboard() {
    	HashSet<Train> trains = Transit.getTransit().getTrains();
    	HashSet<Station> stations = Transit.getTransit().getStations();
    	
    	Queue<Passenger> onTrain = train.getTraveling();
    	Queue<Passenger> onPlatform = station.getWaiting();
    	return "Number onboard train is " + onTrain.size() + "\n" + 
    			"Number waiting on platform is " + onPlatform.size() ;
    }
    
}
