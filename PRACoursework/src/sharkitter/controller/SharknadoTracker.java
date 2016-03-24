package sharkitter.controller;

import api.jaws.Jaws;
import api.jaws.Location;
import api.jaws.Shark;
import com.google.maps.ElevationApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.ElevationResult;
import com.google.maps.model.LatLng;

public class SharknadoTracker {

	private Jaws jawsApi;

	public SharknadoTracker(Jaws jawsApi) {
		this.jawsApi = jawsApi;
	}

	public boolean isOverLand(Shark s) {
		//get shark's last location
		Location location = jawsApi.getLastLocation(s.getName());

		//query Google Elevation API
		LatLng latLngLocation = new LatLng(location.getLatitude(), location.getLongitude());
		GeoApiContext context = new GeoApiContext();
		context.setApiKey("AIzaSyADbDrIJDhI302UNgqCPb-hozMcjDI1rL0");

		try {
			ElevationResult result = ElevationApi.getByPoint(context, latLngLocation).await();
			//for debugging purposes
			System.out.println(result.toString());

			//if the elevation is bigger than 0, ie if shark location is over land
			if(result.elevation > 0) {
				System.out.println("Sharknado!");
				return true;
			}
			else {
				System.out.println("Not a Sharknado.");
				return false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		/*
		TODO use this method in a new method in FavouriteSharks - check whether any of the sharks stored in the
		list of favourite sharks are involved in a Sharknado event (= isOverLand returns true). if there are any
		sharks fitting this criteria, return the details of those sharks as a Sharknado alert.
		 */
		return false;
	}
}
