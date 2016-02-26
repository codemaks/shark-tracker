package main_package;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuFrame extends JFrame implements ActionListener, WindowListener {
	//private JTextField searchField;
	private JButton searchButton;
	private JButton favouritesButton;

	public MenuFrame() {
		super("Amnity Police");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWidgets();
	}

	public void addWidgets() {
		JPanel sPanel = new JPanel();
		sPanel.setLayout(new BorderLayout());

		//searchField = new JTextField("Search");
		//searchField.setHorizontalAlignment(JTextField.CENTER);
		//searchField.addActionListener(new ActionListener() {
		searchButton = new JButton("Search");
		searchButton.setHorizontalAlignment(JButton.CENTER);
		searchButton.addActionListener(this);
/*		searchButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField) e.getSource();
				System.out.println("You Pressed Enter");
				tf.setText("");
			}
		});*/

		favouritesButton = new JButton("Favourites");
		favouritesButton.setEnabled(false);

		//sPanel.add(searchField, BorderLayout.NORTH);
		sPanel.add(searchButton, BorderLayout.NORTH);
		sPanel.add(favouritesButton, BorderLayout.SOUTH);

		ImageIcon shark = new ImageIcon("..\\PRACoursework\\PRACoursework\\SharkTracker.png");
		Image img = shark.getImage();
		Image newimg = img.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newimg);

		JLabel sharkTrackerLabel = new JLabel("", newIcon, 0);
		sharkTrackerLabel.setVerticalTextPosition(JLabel.BOTTOM);
		sharkTrackerLabel.setHorizontalTextPosition(JLabel.CENTER);
		//Font font = new Font("Monospace", Font.ITALIC, 30);
		//sharkTrackerLabel.setFont(font);

		add(sPanel, BorderLayout.SOUTH);
		add(sharkTrackerLabel, BorderLayout.CENTER);

		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == searchButton)
			setVisible(false);
	}

	@Override
	public void windowOpened(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {

	}

	@Override
	public void windowClosed(WindowEvent e) {
		setVisible(true);
	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}

	public static void main(String args[]) {
		JFrame frame = new MenuFrame();
		frame.setVisible(true);
	}
}
