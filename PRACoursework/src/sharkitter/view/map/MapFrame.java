package sharkitter.view.map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

import api.jaws.Location;
/**
 * 
 * @author Maks Gajowniczek
 *
 */
@SuppressWarnings("serial")
public class MapFrame extends JFrame {
	private final String MAP_IMAGE = "resources/Equirectangular_projection_SW.jpg";
	private final int MAP_IMAGE_BORDER_PIXLES = 6; 
	//specific to map "Equirectangular_projection_SW.jpg"
	public static final int SCALE_DOWN = 2;
	
	
	@SuppressWarnings("unused") // change SCALE_DOWN, in code to 1,2,3,4 to see
	public MapFrame(List<Location> locations) // TODO: make a constructor that takes a list of map points to add.
	{
		final int IMG_HEIGHT = 1036;
		final int IMG_WIDTH = 2058;
		
		
		// Making an earth map, based on image
		int height = IMG_HEIGHT / SCALE_DOWN;
		int width = IMG_WIDTH / SCALE_DOWN;


		ImageIcon mapIcon = new ImageIcon(getClass().getClassLoader().getResource(MAP_IMAGE));//"resources/SharkTracker.png"));
		Image oldimg = mapIcon.getImage();
		Image img = oldimg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		
		
		EarthMapModel model = new EarthMapModel(
				IMG_WIDTH, IMG_HEIGHT, SCALE_DOWN, MAP_IMAGE_BORDER_PIXLES);
		
		
		ArrayList<double[]> list2 = new ArrayList<double[]>();
		for(Location l :locations)
		{
			list2.add(new double[]{ l.getLongitude(), l.getLatitude()});
		}
		
		// get longitude, latidude coords : to set the model
		//model.addMapCoords(testCoords()); //here just a test
		
		model.addMapCoords(list2);
		
		EarthMap map = new EarthMap(img, model);
		JLabel info = new JLabel();
		info.setForeground(Color.GRAY);
		info.setFont(new Font("Arial", Font.BOLD, 20));
		map.setInfoLabel(info);
		add(map, BorderLayout.CENTER);
		add(info, BorderLayout.SOUTH);
		
		// setting dimensions based on image
		if (SCALE_DOWN > 1)setMinimumSize(new Dimension( width+20 , height +40 ));
		else setMinimumSize(new Dimension( width/2, height/2));
	
		setTitle("Sharkitter Map (Test Version)");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}
	
	/*
	// Just to test the model of the map
	public static ArrayList<double[]> testCoords()
	{
		ArrayList<double[]> list = new ArrayList<double[]>();
		Random r = new Random();
		for(int i = 0; i < 10 ; i ++){
			list.add( new double[]{r.nextDouble()*360 - 180, r.nextDouble()*180 - 90} );
		}
		return list;
	}
	
	public static void main(String args[]){
		JFrame mapFrame = new MapFrame();
		mapFrame.setVisible(true);

	}*/
}
