package com.ilupper.domain;

import java.time.LocalDate;

public class PassengerFactory {

	//entry point for Abstract Factory pattern
	public static Passenger getPassenger(PassengerAbstractFactory factory) {
		return factory.getPassenger();
	}
	
	/**
	 * Actually make inherited objects for each rideLevel. Another way is indication via field
	 * 
	 * @param determinant
	 * @return
	 */
	public static Passenger getPassenger(LocalDate boardingDate, char determinant) {
		if ('s' == determinant) {
			return new Senior(boardingDate, 's');
		}
		else if ('a' == determinant) {
			return new Adult(boardingDate, 'a');
		}
		return null;	
	}
	
	/**
	 * use Builder Pattern to recover Passenger object. And state is maintained via field, not type
	 * 
	 * @param determinant
	 * @return
	 */
	public static Passenger getPassenger2(LocalDate boardingDate, char determinant) {
		if ('s' == determinant) {
			return new Passenger.PassengerBuilder(boardingDate, 's').build();
		}
		else if ('a' == determinant) {
			return new Passenger.PassengerBuilder(boardingDate, 'a').build();
		}
		return null;
	}
	
}
