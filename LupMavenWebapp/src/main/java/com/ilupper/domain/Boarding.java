package com.ilupper.domain;

import java.util.Queue;
import java.util.Random;

public class Boarding extends Thread {

	Boolean onOff;
	public Boarding(Train train, boolean onOff) {
		this.onOff = onOff;
		this.random = new Random();
		this.traveling = train.traveling;
		this.train = train;
	}
	
	Queue<Passenger> traveling;
	Random random;
	Station station;
	Train train;

	@Override
	public void run() {
		if (onOff == true) {
			// take Passengers on train
			station = train.getStationLock(3, onOff);
			Integer numIn = random.nextInt(9);
			for (int j = 0; j < numIn; j++) {
				traveling.add(station.notePassengersBoardingTrain());
			}
		} else {
			// take Passengers off train
			station = train.getStationLock(2, onOff);
			Integer numOut = random.nextInt(9);
			for (int k = 0; k < numOut; k++) {
				station.receivePassengersOffTrain(traveling.poll()); // doesn't
																		// throw
																		// exception
																		// if
																		// empty
			}
		}
	}
}
