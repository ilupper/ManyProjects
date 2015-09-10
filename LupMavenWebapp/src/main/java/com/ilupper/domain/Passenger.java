package com.ilupper.domain;

import java.time.LocalDate;;

public class Passenger extends Person {

	public LocalDate boardingDate;public double width;public char rideLevel;
	
	byte findSomeUse; 
	
	enum poses {standing, seated};
	
	poses pose;
	
    public void printPassengerStatus() {
        System.out.println("Passenger " + this.toString() + " is currently  "+ pose.name() );
    }
    
    public Passenger() {
    	
    }

    private Passenger(PassengerBuilder builder) {
    	
    	this.boardingDate = builder.boardingDate;
    	this.rideLevel = builder.rideLevel;
    	this.width = builder.width;
    	
    }
    
    public static class PassengerBuilder {
    	
    	public LocalDate boardingDate; public char rideLevel; //required
    	public double width; //optional
    	
    	public PassengerBuilder(LocalDate boardingDate, char rideLevel) {
    		//defaults
    		this.boardingDate = boardingDate;
    		this.rideLevel = rideLevel;
    	}
    	
    	public PassengerBuilder setWidth(Double width) {
    		this.width = width;
    		return this;
    	}
    	
    	public Passenger build() {
    		return new Passenger(this);
    	}
    }
    
	public poses getPose() {
		return pose;
	}

	public LocalDate getBoardingDate() {
		return boardingDate;
	}

	public void setBoardingDate(LocalDate boardingDate) {
		this.boardingDate = boardingDate;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public char getRideLevel() {
		return rideLevel;
	}

	public void setRideLevel(char rideLevel) {
		this.rideLevel = rideLevel;
	}

}
