package sharkitter.view.map;

import java.awt.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 * Custom JComponent, that acts like a small button
 * To be positioned with setPosition() method, then added to a container
 * with no LayoutManager.
 * @author Maks
 *
 */
@SuppressWarnings("serial")
public class MapPoint extends JComponent implements MouseListener{
	public final static int SIZE = 5;
	public final static int OFFSET = - SIZE/2; // to center the button
	
	private int x, y;
	private String information;
	private String title;

	/**
	 * Creates a map point to be placed on a EarthMap Panel
	 * @param x The x pixle coordinate of the map point
	 * @param y The y pixle coordinate of the map point
	 * @param information The information to be displayed when
     */
	public MapPoint(int x, int y, String information)
	{
		super();
		this.x = x;
		this.y = y;
		title = "Favourite Shark:";
		enableInputMethods(true);
		setToolTipText("shark");
		this.information = information;
	}

	/**
	 * Sets the 'properties' of the location of a mapPoint, so that when it's added to EarthMap it shows up in right place
	 */
	public void setLocation()
	{
		setBounds(x + MapPoint.OFFSET, y + MapPoint.OFFSET , MapPoint.SIZE, MapPoint.SIZE);
	}

	/**
	 * Makes sure the components is the right size
	 * @return
	 */
	@Override 
	public Dimension getPreferredSize(){
		return new Dimension(SIZE, SIZE);
	}

	/**
	 * Makes sure the components is the right size
	 * @return
	 */
	@Override
	public Dimension getMinimumSize(){
		return new Dimension(SIZE, SIZE);
	}

	/**
	 * Makes sure the components is the right size
	 * @return
     */
	@Override
	public Dimension getMaximumSize(){
		return new Dimension(SIZE, SIZE);
	}

	/**
	 * Draws a red square, the same size as the size of the MapPoint
	 * @param g
     */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.fillRect(0, 0, SIZE, SIZE);
	}

	/**
	 * Creates a pop-up to display which shark information
	 * @param e
     */
	@Override
	public void mouseClicked(MouseEvent e) {
		ImageIcon shark = new ImageIcon(getClass().getClassLoader().getResource("resources/SharkTracker.png"));
		Image img = shark.getImage();
		Image newImg = img.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
		shark = new ImageIcon(newImg);
		EarthMap parent = (EarthMap)getParent();
		parent.revalidate();
		parent.repaint();
		JOptionPane.showMessageDialog(this, information, title, 0, shark);

	}

	/**
	 * Not used in this application
	 * @param e
     */
	@Override
	public void mouseEntered(MouseEvent e) {}
	/**
	 * Not used in this application
	 * @param e
	 */
	@Override
	public void mouseExited(MouseEvent e) {}
	/**
	 * Not used in this application
	 * @param e
	 */
	@Override
	public void mousePressed(MouseEvent e) {}
	/**
	 * Not used in this application
	 * @param e
	 */
	@Override
	public void mouseReleased(MouseEvent e) {}

}
