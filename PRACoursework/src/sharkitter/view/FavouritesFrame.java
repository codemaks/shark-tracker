package sharkitter.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import api.jaws.Location;
import sharkitter.api.JawsApi;
import sharkitter.controller.SharknadoTracker;
import sharkitter.model.FavouriteSharks;
import sharkitter.model.InfoLocation;
import sharkitter.view.map.MapFrame;

/**
 * The favourites frame, that shows all the favourite sharks from the currently loaded user profile.
 * Also provides access to the MapFrame
 */
public class FavouritesFrame extends JFrame {

	// Kings College London's longitude, and latitude coordinates.
	private static final double KINGS_LONGITUDE = 51.510;
	private static final double KINGS_LATITUDE = -0.117;

    /**
     * Constructs the FavouritesFrame
     * @param favs A list of sharkFavourites, from the currently loaded user profile
     */
	public FavouritesFrame(FavouriteSharks favs) {
		super();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(400,300));

        //An inital message, telling you what the information displayed in the frame is about
		add(new JLabel("Your favourite sharks are this far away from you right now:") , BorderLayout.NORTH);

		// Object that checks if a shark is over land
		SharknadoTracker sharknadoTracker = new SharknadoTracker();
		String distanceToKingsInfo = "";

		// Uses the kclLocation to compare shark locations to KCL University location
		Location kclLocation = new Location(KINGS_LONGITUDE, KINGS_LATITUDE);

        // A list to add all the shark locations from our favourites
		List<InfoLocation> sharkLocations = new ArrayList<InfoLocation>();

		// Loads up the favourite shark names from the model.
		Set<String> favouriteNames = favs.getFavouriteSharks();
		for(String shark: favouriteNames)
		{
			distanceToKingsInfo += shark; //adds the shark name to list
			//System.out.println("**" + shark + "**"); //For debugging purposes

			//Uses the shark name with the 'Jaws' api to find a shark's last location
			Location l = JawsApi.getInstance().getLastLocation(shark);
            sharkLocations.add(new InfoLocation(shark, l));
			double distance = findDistanceBetween(kclLocation, l); // the distance from kings
			distanceToKingsInfo += " : " + String.format("%5.2fkm ", distance);

			//checks whether a Sharknado is occurring for this shark
			if(sharknadoTracker.isOverLand(shark)) {
				distanceToKingsInfo += "[Sharknado - this shark is over land right now!]";
			}

			distanceToKingsInfo += "\n"; //so the next shark distance is on the next line
		}

        // Adds a non-editable text area showing information about distances & Sharknando events
		JTextArea ta = new JTextArea(distanceToKingsInfo);
		ta.setEditable(false);
		add(ta, BorderLayout.CENTER);
		
		JButton mapButton = new JButton("Map");
		mapButton.addActionListener(new ActionListener() { //Makes the map button make a new mapFrame
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame mapFrame = new MapFrame(sharkLocations);
				mapFrame.setVisible(true);
			}
		});
		add(mapButton,BorderLayout.SOUTH);
		pack();
		
	}

	/**
	 * Calculates the distances in kilometers between two Locations
	 * @param loc1 First location in longitude and latitude
	 * @param loc2 Second location in longitude and latitude
     * @return The distance between locations in kilometers
     */
	//TODO move out of frame?
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

		return R * c; // Multiplied by R, which scales up unit sphere distance to kilometers on Earth
	}
}
