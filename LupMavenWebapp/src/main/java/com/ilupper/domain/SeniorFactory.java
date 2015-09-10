package com.ilupper.domain;

import java.time.LocalDate;

public class SeniorFactory implements PassengerAbstractFactory {

	LocalDate bd;
	char rideLevel;
	
	public SeniorFactory(LocalDate boardingDate) {
		this.bd = boardingDate;
		this.rideLevel = 's';
	}
	
	@Override
	public Passenger getPassenger() {
		return new Senior(bd, rideLevel);
	}

}
