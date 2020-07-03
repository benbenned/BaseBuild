package de.ben.BaseBuild.utils;

public class LocationNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public LocationNotFoundException() {
		super("Error occured while getting Locations from file. Are they set in the right way?");
	}

}
