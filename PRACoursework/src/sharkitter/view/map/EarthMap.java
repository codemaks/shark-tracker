package sharkitter.view.map;

import java.awt.Graphics;
import java.awt.Image;
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

	/**
	 * Creates an earthMap using a model and populates itself with mapPoints
	 * @param img The image using as a map
	 * @param earthMapModel The model we are using
     */
	public EarthMap(Image img, EarthMapModel earthMapModel)
	{
		this.img = img; 
		this.earthMapModel = earthMapModel;
		infoLabel = null;
		setLayout(null);
		
		MouseListener mp = new EarthMap.ClickHandler();
		addMouseListener(mp) ;
		addMouseMotionListener((MouseMotionListener)mp);
		populateMapPoints(earthMapModel);
	}

	/**
	 * Sets the information label with some text
	 * @param infoLabel
     */
	public void setInfoLabel(JLabel infoLabel)
	{
		this.infoLabel = infoLabel;
	}

	/**
	 * Populates the earthMap Panel with the list of mapPoints from the model.
	 * @param model
     */
	private void populateMapPoints(EarthMapModel model)
	{
		List<MapPoint> points = model.getPoints();
		for (MapPoint p : points)
		{
			addMapPoint(p);
		}
		
	}

	/**
	 * Adds a map point to the EarthMap Panel
	 * @param p The mapPoint to be added
     */
	private void addMapPoint(MapPoint p)
	{
		p.setLocation();
		add(p);
		p.addMouseListener(p);
	}

	/**
	 * Paints the Panel with the image of a map
	 * @param g
     */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this); //Sets the background to the map image
	}

	/**
	 * A private class that listens to where the mouse is moved
	 */
	private class ClickHandler extends MouseAdapter implements
    MouseMotionListener {
		@Override
		public void mouseClicked(MouseEvent e) {
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
