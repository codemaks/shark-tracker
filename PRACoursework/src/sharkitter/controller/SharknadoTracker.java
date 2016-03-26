package sharkitter.controller;

import api.jaws.Jaws;
import api.jaws.Location;
import com.google.maps.ElevationApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.ElevationResult;
import com.google.maps.model.LatLng;
import sharkitter.api.JawsApi;

public class SharknadoTracker {

	/**
	 * The Jaws API.
	 */
	private Jaws jawsApi;

	/**
	 * Creates a new Sharknado tracker.
	 */
	public SharknadoTracker() {
		jawsApi = JawsApi.getInstance();
	}

	/**
	 * Checks whether the given shark's last location is over land, using the Google Elevation API to check elevation.
	 * @param sName the name of the shark to check location for.
	 * @return true if a Sharknado event has occurred, false if not.
	 */
	public boolean isOverLand(String sName) {
		//get shark's last location
		Location location = jawsApi.getLastLocation(sName);

		//query Google Elevation API
		LatLng latLngLocation = new LatLng(location.getLatitude(), location.getLongitude());
		GeoApiContext context = new GeoApiContext();
		context.setApiKey("AIzaSyADbDrIJDhI302UNgqCPb-hozMcjDI1rL0");

		try {
			ElevationResult result = ElevationApi.getByPoint(context, latLngLocation).await();

			//if the elevation is bigger than 0 (if shark location is over land)
			if(result.elevation > 0) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
