package sharkitter.view.map;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A JPanel with a background, which you can add 'mapPoints' either directly or from model
 * @author Maks Gajowniczek
 *
 */
@SuppressWarnings("serial")
public class EarthMap extends JPanel{
	private Image img;
	private EarthMapModel earthMapModel;
	
	private JLabel infoLabel;
	
	
	public EarthMap(Image img, EarthMapModel earthMapModel)
	{
		this.img = img; 
		this.earthMapModel = earthMapModel;
		infoLabel = null;
		//setPreferredSize(getSize());
		setLayout(null);
		
		MouseListener mp = new EarthMap.ClickHandler(); //Can have it listen to itself?
		addMouseListener(mp) ;
		addMouseMotionListener((MouseMotionListener)mp);
		populateMapPoints(earthMapModel);
	}
	
	public void setInfoLabel(JLabel infoLabel)
	{
		this.infoLabel = infoLabel;
	}
	
	public void populateMapPoints(EarthMapModel model)
	{
		List<Point> points = model.getPoints();
		for (Point p : points)
		{
			addMapPoint(p.x, p.y, "info: use getters, from shark obj");
		}
		
	}
	
	// Adds a singular, benign info, long lat to map and model
//	public void addLongLat(double longitude, double latitude)
//	{
//		Point p = earthMapModel.addMapCoord(longitude, latitude);
//		addMapPoint(p.x, p.y, "Random info");
//	}
	
	private void addMapPoint(int x, int y, String info)
	{
		MapPoint p1 = new MapPoint(x, y, info);
		p1.setLocation();
		add(p1);
		p1.addMouseListener(p1);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this); //Sets the background to the map image
	}
	
	public class ClickHandler extends MouseAdapter implements
    MouseMotionListener {
		@Override
		public void mouseClicked(MouseEvent e) { // probably not needed for earth map
			System.out.println(e.getX() + " ," + e.getY());
			double[] longlat = earthMapModel.getLongLatCoord(e.getX(), e.getY());
			System.out.printf("map clicked at long:%5.2f lat:%5.2f\n" , longlat[0], longlat[1] );
		}
		@Override
		public void mouseDragged(MouseEvent e) {}
		@Override
		public void mouseMoved(MouseEvent e) {
			if(infoLabel != null)
			{
				double[] longlat = earthMapModel.getLongLatCoord(e.getX(), e.getY());
				infoLabel.setText(String.format(
						"longitude:%5.2f latitude:%5.2f\n" , longlat[0], longlat[1]));
			}
		}
	}

}
