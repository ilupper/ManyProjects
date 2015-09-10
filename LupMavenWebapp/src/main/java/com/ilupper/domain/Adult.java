package com.ilupper.domain;

import java.time.LocalDate;

public class Adult extends Passenger {

	public Adult(LocalDate boardingDate, char rideLevel) {
		this.boardingDate = boardingDate;
    	this.rideLevel = rideLevel;
	}
	
}
