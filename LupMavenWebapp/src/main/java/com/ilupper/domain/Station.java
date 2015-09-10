package com.ilupper.domain;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Station extends Thread {

	@Override
	public void run() {
		
		//generate Passenger coming into station
		Integer numIn = random.nextInt(1);
		for (int i = 0; i < numIn; i++) {
			//set up system where every 1/7th passenger is a senior citizen, every 1/6 is a child
			//Passenger passenger = new Passenger.PassengerBuilder(LocalDate.now(), 's').build();
			//Passenger passenger = PassengerFactory.getPassenger2(LocalDate.now(), 's');
			//Passenger passenger = PassengerFactory.getPassenger(LocalDate.now(), 's');
			Passenger passenger = PassengerFactory.getPassenger(new SeniorFactory(LocalDate.now()));
			waiting.add(passenger);
		}
		
		//generate Passenger leaving station
		Integer numOut = random.nextInt(1);
		for (int i = 0; i < numOut; i++) {
			waiting.poll(); //doesn't throw exception if empty
		}
		
	}
	
	public void letStationKnow(Train train) {
		Boarding out = new Boarding(train, false);
		out.start();
	}
	
	public void receivePassengersOffTrain(Passenger e) {
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

	public Queue<Passenger> getWaiting() {
		return waiting;
	}

}
