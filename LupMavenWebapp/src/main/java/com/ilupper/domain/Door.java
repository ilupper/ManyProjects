package com.ilupper.domain;

import java.time.Duration;
import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Door {

	Logger log;
	Door() {
	     log = LoggerFactory.getLogger(this.getClass());
	}
	
	/*
	 * seconds - duration of which to get hold of door
	 * onOff - for indication of which thread is calling for lock
	 */
	public synchronized Station getDoorLock(long seconds, boolean onOff) {
		
		Duration tripLength = Duration.ofSeconds(seconds); 
		
		LocalTime now = LocalTime.now();
	
		do {
			//log.debug("Should be in wait for " + seconds + " seconds: " + onOff);
		} while (now.plus(tripLength).isAfter( LocalTime.now() ));
		
		return train.getStation();
	}
	
	Train train;
	public void commenceDisEmbark(Train train) {
		this.train = train;
		
		Boarding in = new Boarding(train, true);
		in.start();
		Boarding out = new Boarding(train, false);
		out.start();
	}
}
