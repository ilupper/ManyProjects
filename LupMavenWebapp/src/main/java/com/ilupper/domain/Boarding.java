package com.ilupper.domain;

import java.util.Queue;
import java.util.Random;

public class Boarding extends Thread {

	Boolean onOff;
	public Boarding(Train train, boolean onOff) {
		this.onOff = onOff;
		this.random = new Random();
		this.train = train;
	}
	
	Random random;
	Station station;
	Train train;

	@Override
	public void run() {
		if (onOff == true) {
			// take Passengers on train
			station = train.getDoor().getDoorLock(train.stationStay/2, onOff);
			Integer numIn = random.nextInt(9);
			for (int j = 0; j < numIn; j++) {
				Passenger passenger = station.notePassengersBoardingTrain();
				if (null != passenger)
					train.traveling.add(passenger);
			}
		} else {
			// take Passengers off train
			station = train.getDoor().getDoorLock(train.stationStay/2, onOff);
			Integer numOut = random.nextInt(9);
			for (int k = 0; k < numOut; k++) {
				station.receivePassengersOffTrain(train.traveling.poll()); // doesn't
																		// throw
																		// exception
																		// if
																		// empty
			}
		}
	}
}
