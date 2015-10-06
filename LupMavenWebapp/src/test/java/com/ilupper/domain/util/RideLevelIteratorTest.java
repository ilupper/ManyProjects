package com.ilupper.domain.util;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ilupper.domain.AdultFactory;
import com.ilupper.domain.Passenger;
import com.ilupper.domain.PassengerFactory;
import com.ilupper.domain.SeniorFactory;

public class RideLevelIteratorTest {

	static Logger log = LoggerFactory.getLogger(RideLevelIteratorTest.class);
	
	static LinkedList<Passenger> traveling = new LinkedList<>();
	
	public static void main(String[] args) {

		//not a senior citizen
		Passenger passenger = PassengerFactory.getPassenger(new AdultFactory(LocalDate.now()));
		traveling.add(passenger);
		//yes, a senior citizen
		Passenger passenger2 = PassengerFactory.getPassenger(new SeniorFactory(LocalDate.now()));
		traveling.add(passenger2);
		
		Iterator<Passenger> i = traveling.iterator();
		RideLevelIterator rli = new RideLevelIterator(i);

		Passenger subject;
		while (rli.hasNext()) {
			subject = rli.next();
			log.info("Ride Level is " + subject.rideLevel);
			rli.remove();
		}

	}

}
