package com.ilupper.domain;

import java.time.LocalDate;

public class Senior extends Passenger {

	public Senior(LocalDate boardingDate, char rideLevel) {
		this.boardingDate = boardingDate;
    	this.rideLevel = rideLevel;
	}
}
