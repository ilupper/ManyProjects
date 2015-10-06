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

	Integer stationStay;
	
	@Override
	public void run() {

		while (true) {
			// run length
			tripLength = Duration.ofSeconds(1); // 1 second

			LocalTime now = LocalTime.now();

			// indicate when arrive at station
			do {
				// System.out.println("Traveling for 1 second.");
			} while (now.plus(tripLength).isAfter(LocalTime.now()));

			// train arrives
			stationStay = 2;
			station = Transit.getInstance().getStationLock("Station " + 0, stationStay);
			door.commenceDisEmbark(this);
		}
	}

	public Station getStation() {
		return station;
	}
	
	public Door getDoor() {
		return door;
	}

	Duration tripLength; // influenced by bottlenecks at station
							// (loading/unloading) - but let's say it's nothing
							// for now

	Door door;
	Station station;
	Random random;
	Queue<Passenger> traveling;

	/**
	 * Assume a train of a) 1 door (fits only 1 person at a time)
	 *
	 * OT: next generation train will have 2 doors (1 for exiting, another for
	 * entry)
	 */
	public Train() {
		this.door = new Door();
		this.random = new Random();
		this.traveling = new LinkedList<>();
		this.start();
	}

	public Queue<Passenger> getTraveling() {
		return traveling;
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

}
