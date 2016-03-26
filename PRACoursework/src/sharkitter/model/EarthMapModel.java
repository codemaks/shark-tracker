package sharkitter.model;


import api.jaws.Location;
import sharkitter.view.map.MapPoint;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores map information (which pixle location points are at), 
 * and does calculations base on longitude and latitude
 * @author Maks Gajowniczek
 *
 */
public class EarthMapModel {
    private List<MapPoint> points;
	private int scaleDown;
	private int originalHeight;
	private int originalWidth;
	private int borderPixles;

	/**
	 * Creates the EarthMap model
	 * @param originalWidth The width of the map image
	 * @param originalHeight The height of the map image
	 * @param scaleDown The integer amount you are scaling down the image
	 * @param borderPixles The amount of border pixles around each edge of the map image
     */
	public EarthMapModel(int originalWidth, int originalHeight, int scaleDown, int borderPixles)
	{
		points = new ArrayList<MapPoint>();
		this.scaleDown = scaleDown;
		this.originalHeight = originalHeight;
		this.originalWidth = originalWidth;
		this.borderPixles = borderPixles;
	}


	/**
	 * Adds a map coordinate into the model
	 * @param infoLocation A pair of Location and String information
	 * @return Returns a point representing where the mapPoint is added
     */
	public Point addMapCoord(InfoLocation infoLocation)
	{//
		Location l = infoLocation.getLocation();
		Point p = convertToMapCoord(l.getLongitude(), -l.getLatitude());
		points.add(new MapPoint(p.x, p.y, infoLocation.getInformation()));
		return p;
	}

	/**
	 * Adds a list of map coordinates to the model
	 * @param infoLocations A list of infoLocations
     */
	public void addMapCoords(List<InfoLocation> infoLocations)
	{
		for (InfoLocation infoLoc: infoLocations)
		{
			addMapCoord(infoLoc);
		}
	}

	/**
	 * Gets the list of infoLocations
	 * @return thhe list of infoLocations
     */
	public List<MapPoint> getPoints()
	{
		return points;
	}

	// preconditions: -180< longitude <= 180 , -180< latitude <= 180
	/**
	 * Converts a longitude and latitude coordinate to a pixle location for the model as if it was using full sized version of map
	 * @param longitude
	 * @param latitude
     * @return A point representing the pixle location for the full sized version of the map
     */
	private Point convertToLargeMapCoord(double longitude, double latitude)
	{
		int mapWidth = originalWidth - 2 * borderPixles;
		int mapHeight = originalHeight - 2 * borderPixles;
		
		double ratioX = (longitude + 180 ) / 360.0; 
		double ratioY = (latitude + 90) / 180.0;
		
		int x, y;
		x = (int)Math.round((borderPixles + ratioX * mapWidth));
		y = (int)Math.round((borderPixles + ratioY * mapHeight));
		return new Point(x,y);
	}
	
	// preconditions: -180< longitude <= 180 , -180< latitude <= 180
	/**
	 * Converts a longitude and latitude coordinate to a pixle location for the model
	 * @param longitude
	 * @param latitude
     * @return A point representing the pixle location
     */
	public Point convertToMapCoord(double longitude, double latitude)
	{
		Point p = convertToLargeMapCoord(longitude, latitude);
		p.x /= scaleDown;
		p.y /= scaleDown;
		return p;
	}
	
	// preconditions: 0 =< x <= orginalWidth/scaleDown , 0 =< y <= originalHeight/scaleDown
	/**
	 * Gets what the longitude and latitude should be given pixle coordinates on the map
	 * @param x the x location of the pixle
	 * @param y the y location of the pixle
     * @return A pair representing the longitude and latitude coordinates
     */
	public double[] getLongLatCoord(int x, int y)
	{
		// working out from original map
		int pixX = x * scaleDown - borderPixles;
		int pixY = y * scaleDown - borderPixles;
		double mapWidth = originalWidth - 2 * borderPixles;
		double mapHeight = originalHeight - 2 * borderPixles;
		double longitude = (pixX / mapWidth * 360)- 180;
		double latitude = -(pixY / mapHeight * 180) + 90;
		
		return new double[]{longitude, latitude};
	}
}
