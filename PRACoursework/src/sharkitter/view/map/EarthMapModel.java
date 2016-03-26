package sharkitter.view.map;


import api.jaws.Location;

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
	//private List<Point> points;
    private List<MapPoint> points;
	private int scaleDown;
	private int originalHeight;
	private int originalWidth;
	private int borderPixles;
	
	public EarthMapModel(int originalWidth, int originalHeight, int scaleDown, int borderPixles)
	{
		points = new ArrayList<MapPoint>();//new ArrayList<>();
		this.scaleDown = scaleDown;
		this.originalHeight = originalHeight;
		this.originalWidth = originalWidth;
		this.borderPixles = borderPixles;
	}


	public Point addMapCoord(InfoLocation infoLocation)
	{//
		Location l = infoLocation.getLocation();
		Point p = convertToMapCoord(l.getLongitude(), -l.getLatitude());
		points.add(new MapPoint(p.x, p.y, infoLocation.getInformation()));
		return p;
	}

	public void addMapCoords(List<InfoLocation> infoLocations)
	{
		for (InfoLocation infoLoc: infoLocations)
		{
			addMapCoord(infoLoc);
		}
	}

	public List<MapPoint> getPoints()
	{
		return points;
	}

	// preconditions: -180< longitude <= 180 , -180< latitude <= 180
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
	public Point convertToMapCoord(double longitude, double latitude)
	{
		Point p = convertToLargeMapCoord(longitude, latitude);
		p.x /= scaleDown;
		p.y /= scaleDown;
		return p;
	}
	
	// preconditions: 0 =< x <= orginalWidth/scaleDown , 0 =< y <= originalHeight/scaleDown
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
