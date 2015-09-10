package com.ilupper.domain;

import java.time.LocalDate;

public class AdultFactory implements PassengerAbstractFactory {

	LocalDate bd;
	char rideLevel;
	
	public AdultFactory(LocalDate boardingDate) {
		this.bd = boardingDate;
		this.rideLevel = 'a';
	}
	
	@Override
	public Passenger getPassenger() {
		return new Adult(bd, rideLevel);
	}

}
