package sharkitter.view.map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

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
	
	public MapPoint(int x, int y)
	{
		super();
		this.x = x;
		this.y = y;
		enableInputMethods(true);
		setToolTipText("undefined");
		information = "No info";
	}
	
	public MapPoint(int x, int y, String information)
	{
		super();
		this.x = x;
		this.y = y;
		enableInputMethods(true);
		setToolTipText("shark");
		this.information = information;
	}
	
	public void setLocation()
	{
		setBounds(x + MapPoint.OFFSET, y + MapPoint.OFFSET , MapPoint.SIZE, MapPoint.SIZE);
	}
	
	@Override 
	public Dimension getPreferredSize(){
		return new Dimension(SIZE, SIZE);
	}
	@Override
	public Dimension getMinimumSize(){
		return new Dimension(SIZE, SIZE);
	}
	@Override
	public Dimension getMaximumSize(){
		return new Dimension(SIZE, SIZE);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.fillRect(0, 0, SIZE, SIZE);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(x + ", " + y); //not needed here
		JOptionPane.showMessageDialog(this, information, "Shark Details:", 0, new ImageIcon("shark-icon.png"));
		EarthMap parent = (EarthMap)getParent();
		//parent.remove(this);
		parent.revalidate();
		parent.repaint(); //perhaps, notifiy observers here?
	}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

}
