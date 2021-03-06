package sharkitter.model;

import api.jaws.Location;

import sharkitter.view.map.MapPoint;

import java.awt.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores map information (where pixel location points are at),
 * and does calculations based on longitude and latitude.
 */
public class EarthMapModel {
    private List<MapPoint> points;
	private int scaleDown;
	private int originalHeight;
	private int originalWidth;
	private int borderPixels;

	/**
	 * Creates the EarthMap model.
	 * @param originalWidth the width of the map image.
	 * @param originalHeight the height of the map image.
	 * @param scaleDown the integer amount you are scaling down the image.
	 * @param borderPixels the amount of border pixels around each edge of the map image.
     */
	public EarthMapModel(int originalWidth, int originalHeight, int scaleDown, int borderPixels) {
		points = new ArrayList<>();
		this.scaleDown = scaleDown;
		this.originalHeight = originalHeight;
		this.originalWidth = originalWidth;
		this.borderPixels = borderPixels;
	}

    /**
     * Calculates the distances in kilometers between two locations.
     * @param loc1 first location in longitude and latitude.
     * @param loc2 second location in longitude and latitude.
    * @return the distance between locations in kilometers.
    */
    public static double findDistanceBetween(Location loc1, Location loc2) {
        double R = 6371; // kilometers
        double latRadians1 = Math.toRadians(loc1.getLatitude());
        double latRadians2 = Math.toRadians(loc2.getLatitude());
        double latRadiansDifference = Math.toRadians(loc2.getLatitude()-loc1.getLatitude());
        double longRadiansDifference = Math.toRadians(loc2.getLongitude()-loc2.getLongitude());

        // uses the ‘haversine’ formula to calculate the great-circle distance between two points, over a sphere
        Double a = Math.sin(latRadiansDifference) * Math.sin(latRadiansDifference) +
                Math.cos(latRadians1) * Math.cos(latRadians2) *
                        Math.sin(longRadiansDifference/2) * Math.sin(longRadiansDifference/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return R * c; // Multiplied by R, which scales up unit sphere distance to kilometers on Earth
    }


    /**
	 * Adds a map coordinate into the model.
	 * @param infoLocation a pair of Location and String information.
	 * @return Returns a point representing where the mapPoint is added.
     */
	public Point addMapCoord(InfoLocation infoLocation) {
		Location l = infoLocation.getLocation();
		Point p = convertToMapCoord(l.getLongitude(), -l.getLatitude());
		points.add(new MapPoint(p.x, p.y, infoLocation.getInformation()));
		return p;
	}

	/**
	 * Adds a list of map coordinates to the model.
	 * @param infoLocations a list of infoLocations.
     */
	public void addMapCoords(List<InfoLocation> infoLocations) {
		for (InfoLocation infoLoc: infoLocations) {
			addMapCoord(infoLoc);
		}
	}

	/**
	 * Gets the list of infoLocations.
	 * @return the list of infoLocations.
     */
	public List<MapPoint> getPoints()
	{
		return points;
	}

	// preconditions: -180< longitude <= 180 , -180< latitude <= 180
	/**
	 * Converts a longitude and latitude coordinate to a pixel location for the model as if it was using
	 * the full sized version of the map.
	 * @param longitude the longitude coordinate to convert.
	 * @param latitude the latitude coordinate to convert.
     * @return a point representing the pixel location for the full sized version of the map.
     */
	private Point convertToLargeMapCoord(double longitude, double latitude) {
		int mapWidth = originalWidth - 2 * borderPixels;
		int mapHeight = originalHeight - 2 * borderPixels;
		
		double ratioX = (longitude + 180 ) / 360.0; 
		double ratioY = (latitude + 90) / 180.0;
		
		int x, y;
		x = (int)Math.round((borderPixels + ratioX * mapWidth));
		y = (int)Math.round((borderPixels + ratioY * mapHeight));
		return new Point(x,y);
	}
	
	// preconditions: -180< longitude <= 180 , -180< latitude <= 180
	/**
	 * Converts a longitude and latitude coordinate to a pixel location for the model
	 * @param longitude the longitude coordinate to convert.
	 * @param latitude the latitude coordinate to convert.
     * @return a point representing the pixel location.
     */
	public Point convertToMapCoord(double longitude, double latitude) {
		Point p = convertToLargeMapCoord(longitude, latitude);
		p.x /= scaleDown;
		p.y /= scaleDown;
		return p;
	}
	
	// preconditions: 0 =< x <= orginalWidth/scaleDown , 0 =< y <= originalHeight/scaleDown
	/**
	 * Gets what the longitude and latitude should be given pixel coordinates on the map.
	 * @param x the x location of the pixel.
	 * @param y the y location of the pixel.
     * @return a pair representing the longitude and latitude coordinates.
     */
	public double[] getLongLatCoord(int x, int y) {
		// working out from original map
		int pixX = x * scaleDown - borderPixels;
		int pixY = y * scaleDown - borderPixels;
		double mapWidth = originalWidth - 2 * borderPixels;
		double mapHeight = originalHeight - 2 * borderPixels;
		double longitude = (pixX / mapWidth * 360)- 180;
		double latitude = -(pixY / mapHeight * 180) + 90;
		
		return new double[]{longitude, latitude};
	}
}
