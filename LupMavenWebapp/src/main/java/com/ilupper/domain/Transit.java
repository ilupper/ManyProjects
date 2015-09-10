package com.ilupper.domain;

public class Transit {

	static Transit transit;
	Train train;
	Station station;
		
	private Transit() {
		train = new Train();
		station = new Station(); //no effect for now, will have to redo when redevelop into full system
	}

	public static Transit getTransit() {
		if (null == transit) {
			synchronized (Transit.class) {
				if (null == transit) {
					transit = new Transit();
				}
			}
		}
			
		return transit;
	}

	public Train getTrain() {
		return train;
	}
	
	public Station getStation() {
		return station;
	}
	
}
