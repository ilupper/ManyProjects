package com.ilupper.domain;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import com.ilupper.domain.util.RideLevelIterator;

public class Train extends Thread {

	@Override
	public void run() {

		while (true) {
			// run length
			tripLength = Duration.ofSeconds(1); //1 second
			
			LocalTime now = LocalTime.now();
			
			// indicate when arrive at station 
			do {
				//System.out.println("Traveling for 1 second.");
			} while (now.plus(tripLength).isAfter( LocalTime.now() ));
			
				// train arrives
				Boarding in = new Boarding(this, true);
				in.start();
				station.letStationKnow(this);
		}
	}
	
	public Stack<Passenger> findSeniorCitizens() {
			Iterator<Passenger> i = this.traveling.iterator();
			RideLevelIterator rli = new RideLevelIterator(i);
			
			Stack<Passenger> stack = new Stack<>();
			
			Passenger subject;
			while (rli.hasNext()) {
				subject = rli.next();
				stack.push(subject);
				rli.remove();
			}
			
			return stack;
			
	}

	/*
	 * 
	 * Really should be getDoorLock() 
	 * 
	 * seconds - duration of which to get hold of door
	 * onOff - for indication of which thread is calling for lock
	 */
	public synchronized Station getStationLock(long seconds, boolean onOff) {
		
		tripLength = Duration.ofSeconds(seconds); 
		
		LocalTime now = LocalTime.now();
	
		do {
			//System.out.println("Should be in wait for " + seconds + " seconds: " + onOff);
		} while (now.plus(tripLength).isAfter( LocalTime.now() ));
		
		return station;
	}
	
	Duration tripLength; // influenced by bottlenecks at station
						// (loading/unloading) - but let's say it's nothing for now
	
	Station station;
	Random random;
	Queue<Passenger> traveling;

	/**
	 * Assume a train of a) 1 track (in loop) b) 1 station c) 1 door (fits only
	 * 1 person at a time)
	 *
	 * OT: next generation train will have 2 doors (1 for exiting, another for
	 * entry)
	 */
	public Train() {
		this.random = new Random();
		this.traveling = new LinkedList<>();
		this.start();
		
		//for now, just 1 station and tightly coupled with this train
		this.station = new Station();
	}

	public Queue<Passenger> getTraveling() {
		return traveling;
	}

}
