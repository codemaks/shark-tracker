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

	public static final int SCALE_DOWN = 2; // How much to scale down the image

	public MapFrame(List<InfoLocation> locations) // TODO: make a constructor that takes a list of map points to add.
	{
		final int IMG_HEIGHT = 1036; //specific to map "Equirectangular_projection_SW.jpg"
		final int IMG_WIDTH = 2058; //specific to map "Equirectangular_projection_SW.jpg"
		
		
		// Making an earth map, based on image
		int height = IMG_HEIGHT / SCALE_DOWN;
		int width = IMG_WIDTH / SCALE_DOWN;


		ImageIcon mapIcon = new ImageIcon(getClass().getClassLoader().getResource(MAP_IMAGE));//"resources/SharkTracker.png"));
		Image oldimg = mapIcon.getImage(); //get's the image from the map icon
		Image img = oldimg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		
		//Creates a new earthMapModel based on image properites, and how much to scale down
		EarthMapModel model = new EarthMapModel(
				IMG_WIDTH, IMG_HEIGHT, SCALE_DOWN, MAP_IMAGE_BORDER_PIXLES);

		
		model.addMapCoords(locations);
		
		EarthMap map = new EarthMap(img, model);
		JLabel info = new JLabel();
		info.setForeground(Color.GRAY);
		info.setFont(new Font("Arial", Font.BOLD, 20));
		map.setInfoLabel(info);
		add(map, BorderLayout.CENTER);
		add(info, BorderLayout.SOUTH);
		
		// setting dimensions based on image
		if (SCALE_DOWN > 1)setMinimumSize(new Dimension( width+20 , height +50 ));
		else setMinimumSize(new Dimension( width/2, height/2));
	
		setTitle("Sharkitter Map (Test Version)");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}

}
