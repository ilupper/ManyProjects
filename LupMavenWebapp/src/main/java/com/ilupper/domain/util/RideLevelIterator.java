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
			Passenger p = i.next();
			if (p.getRideLevel() == 's')
				return true;
		}
		return false;
	}

	Passenger p;
	@Override
	public Passenger next() {
		
		p = i.next();
		if (p.getRideLevel() == 's') {
			return p;
		}
		else 
			return null;
		
	}

	/**
	 * use is to remove the Passenger assigned by a call to next()
	 */
	public void remove() {
		
		i.remove();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
