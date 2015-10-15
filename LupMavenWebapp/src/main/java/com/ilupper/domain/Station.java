package com.ilupper.domain;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Station extends Thread {

	Queue<Passenger> prefill = null;
	
	Duration stationTurnover;
	
	@Override
	public void run() {
		
		while (true) {
			
			stationTurnover = Duration.ofSeconds(1); // 1 second

			LocalTime now = LocalTime.now();

			// indicate when new transit users come into and out of station (via the street)
			do {
				// System.out.println("1 second elapsed");
			} while (now.plus(stationTurnover).isAfter(LocalTime.now()));

			log.debug("Number of Waiting before Entering: " + this.waiting.toString());
			
			if (prefill != null) {
				log.debug("Finite system");
				this.simulateFiniteLeakDetection();
				//this.simulateFiniteSystem();
			}
			else {
				log.debug("Random system");
				this.simulateRandomSystem();
			}
			
			log.debug("Number of Waiting after Entering: " + this.waiting.toString());
		}
	}

	
	@SuppressWarnings("unused")
	private void simulateFiniteLeakDetection() {

		//generate Passenger coming into station
		Integer numIn = prefill.size();
		for (int i = 0; i < numIn; i++) {
			Passenger passenger = prefill.poll();
			if (null != passenger)
				waiting.add(passenger);
		}
				
	}
	
	//all passengers in system are managed by the Transit domain
	private void simulateFiniteSystem() {

		//generate Passenger coming into station
		Integer numIn = random.nextInt(1);
		if (numIn > prefill.size())
			numIn = prefill.size();
		for (int i = 0; i < numIn; i++) {
			Passenger passenger = prefill.poll();
			if (null != passenger)
				waiting.add(passenger);
		}
		
		//generate Passenger leaving station
		Integer numOut = random.nextInt(1);
		if (numOut > waiting.size())
			numOut = waiting.size();
		for (int i = 0; i < numOut; i++) {
			Passenger passenger = waiting.poll(); //doesn't throw exception if empty
			if (null != passenger)
				prefill.add(passenger);
		}
			
	}
	
	private void simulateRandomSystem() {
	
		//generate Passenger coming into station
		Integer numIn = random.nextInt(1), proportion = 0;
		for (int i = 0; i < numIn; i++) {
			//set up system where every 1/7th passenger is a senior citizen, every 1/6 is a child
			//Passenger passenger = new Passenger.PassengerBuilder(LocalDate.now(), 's').build();
			//Passenger passenger = PassengerFactory.getPassenger2(LocalDate.now(), 's');
			//Passenger passenger = PassengerFactory.getPassenger(LocalDate.now(), 's');
			proportion++;
			if ( (proportion % 7) == 1 ) {
				Passenger passenger = PassengerFactory.getPassenger(new SeniorFactory(LocalDate.now()));
				waiting.add(passenger);
			}
			else {
				Passenger passenger2 = PassengerFactory.getPassenger(new AdultFactory(LocalDate.now()));
				waiting.add(passenger2);
			}
		}
		
		//generate Passenger leaving station
		Integer numOut = random.nextInt(1);
		for (int i = 0; i < numOut; i++) {
			waiting.poll(); //doesn't throw exception if empty
		}
		
	}
	
	public void receivePassengersOffTrain(Passenger e) {
		if (null != e)
			waiting.add(e);
	}
	
	public Passenger notePassengersBoardingTrain() {
		return waiting.poll();
	}
	
	Random random;
	Queue<Passenger> waiting;
	
	/*
	 * Random number generation is by single-digit only (otherwise, randomness too random - *not too rigid too
	 */
	public Station() {
		this.random = new Random();
		this.waiting = new LinkedList<>();
		this.start();			
	}

	Logger log;
	
	public Station(@SuppressWarnings("rawtypes") Collection prefill) {
		
		log = LoggerFactory.getLogger(this.getClass());

			if (prefill != null) {
				this.prefill = new LinkedList<> ();
				@SuppressWarnings("rawtypes")
				Iterator iterator = prefill.iterator();
				while (iterator.hasNext()) {
					this.prefill.add((Passenger)iterator.next());
				}	
			}
			
			this.random = new Random();
			this.waiting = new LinkedList<>();
			this.start();			
	}

	public Queue<Passenger> getWaiting() {
		return waiting;
	}

}
