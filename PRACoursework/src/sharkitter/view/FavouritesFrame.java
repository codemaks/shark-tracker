package sharkitter.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import api.jaws.Jaws;
import api.jaws.Location;
import api.jaws.Shark;
import sharkitter.model.FavouriteSharks;
import sharkitter.view.map.MapFrame;

@SuppressWarnings("serial")
public class FavouritesFrame extends JFrame{
	
	private ArrayList<Location> locations;
	// Kings longitude, and latitude
	private static final double KINGS_LONGITUDE = 51.510;
	private static final double KINGS_LATITUDE = -0.117;
	public FavouritesFrame(FavouriteSharks favs, Jaws jawsApi) {
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(300,300));
		add(new JLabel("Your favourite sharks are this far away from you right now:") , BorderLayout.NORTH);
		String distanceToKingsInfo = "";

		Location kclLocation = new Location(KINGS_LONGITUDE, KINGS_LATITUDE);
		locations = new ArrayList<Location>();
		for(/*Shark*/ String shark: favs.getFavouriteSharks())
		{
			distanceToKingsInfo += shark/*.getName()*/;

			Location l = jawsApi.getLastLocation(shark/*.getName()*/);
			locations.add(l);
			distanceToKingsInfo += " : " + findDistanceBetween(kclLocation, l); // haven't tested yet
			distanceToKingsInfo += "\n";
		}
		
		
		
		JTextArea ta = new JTextArea(distanceToKingsInfo);
		add(ta, BorderLayout.CENTER);
		
		JButton button = new JButton("Map");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// call the map
				System.out.println("test map button");
				for(Location loc: locations)
				{
					System.out.println("long " + loc.getLongitude() + " lat " + loc.getLatitude());
				}
				JFrame mapFrame = new MapFrame(locations);
				mapFrame.setVisible(true);
			}
		});
		add(button,BorderLayout.SOUTH);
		pack();
		
	}

	/**
	 * Calculates the distances in kilometers between two Locations
	 * @param loc1 First location in longitude and latitude
	 * @param loc2 Second location in longitude and latitude
     * @return The distance between locations in kilometers
     */
	private static double findDistanceBetween(Location loc1, Location loc2)
	{
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

		return R * c;
	}
}
