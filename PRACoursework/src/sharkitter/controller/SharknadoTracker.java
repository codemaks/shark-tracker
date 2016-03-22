package sharkitter.controller;

import api.jaws.Jaws;
import api.jaws.Location;
import api.jaws.Shark;

public class SharknadoTracker {

	private Jaws jawsApi;

	public SharknadoTracker(Jaws jawsApi) {
		this.jawsApi = jawsApi;
	}

	public boolean isOverLand(Shark s) {
		//for given shark, retrieve location
		//determine if that location is over land or not
		//if it is over land and thus is involved in a Sharknado event, return true

		Location location = jawsApi.getLastLocation(s.getName());

		//random code for now to enable compilation.
		if(location == null) {
			return true;
		}
		else {
			return false;
		}
		/*
		notes: use this method in a new method in FavouriteSharks - check whether any of the sharks stored in the
		list of favourite sharks are involved in a Sharknado event (= isOverLand returns true). if there are any
		sharks fitting this criteria, return the details of those sharks as a Sharknado alert.
		 */
	}
}
