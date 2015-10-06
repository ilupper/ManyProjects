package com.ilupper.domain.util;

import java.util.Iterator;

import com.ilupper.domain.Passenger;

public class RideLevelIterator implements Iterator<Passenger> {

	Iterator<Passenger> i;
	public RideLevelIterator(Iterator<Passenger> i) {
		this.i = i;
	}
	
	@Override
	public boolean hasNext() {

		while (i.hasNext()) {
			p = i.next();
			if (p.getRideLevel() == 's')
				return true;
		}
		return false;
	}

	Passenger p;
	@Override
	public Passenger next() {
		
		if (p != null) 
			return swapPassenger(p);
		else {
			p = i.next();
			return swapPassenger(p);
		}
	}
	
	private Passenger swapPassenger(Passenger p) {
		if (p.getRideLevel() == 's') {
			Passenger returnedPassenger = p;
			p = null;
			return returnedPassenger;	
		}
		else return null;
	}

	/**
	 * use is to remove the Passenger assigned by a call to next()
	 */
	public void remove() {
		
		i.remove();
	}
	
}
