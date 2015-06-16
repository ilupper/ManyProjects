package com.ilupper.util;

public class NumberDeterminant {
	
	public boolean isInteger(String possibleNum) {
		try {
			new Integer(possibleNum);
			return true;
		}
		catch (NumberFormatException nfe) {
			return false;
		}
	}
}
