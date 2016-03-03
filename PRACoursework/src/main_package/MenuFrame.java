package main_package;

import view.SearchFrame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

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
	private SearchFrame searchframe;
	private String filepath;
	private static String OS = System.getProperty("os.name").toLowerCase();

	public MenuFrame() {
		super("Amnity Police");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWidgets();
		System.out.println("Welcome to the shark Tracker app!");
	}

	public static boolean isWindows() {

		return (OS.indexOf("win") >= 0);

	}

	public static boolean isMac() {

		return (OS.indexOf("mac") >= 0);

	}

	public static boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );

	}

	public static boolean isSolaris() {

		return (OS.indexOf("sunos") >= 0);
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


		if (isWindows()) {
			System.out.println("This is Windows");
			filepath ="..\\PRACoursework\\PRACoursework\\SharkTracker.png";
			System.out.println(filepath);
		} else if (isMac()) {
			System.out.println("This is Mac");
			filepath = "../PRACoursework/PRACoursework/SharkTracker.png";
			System.out.println(filepath);
		} else if (isUnix()) {
			System.out.println("This is Unix or Linux");
			filepath = "../PRACoursework/PRACoursework/SharkTracker.png";
			System.out.println(filepath);
		} else if (isSolaris()) {
			System.out.println("This is Solaris");
			filepath = "../PRACoursework/PRACoursework/SharkTracker.png";
			System.out.println(filepath);
		} else {
			System.out.println("Menu Frame error 1: Operating System not supported for this app");
			System.out.println(filepath);
		}

		System.out.println("before printing :" +filepath);

		ImageIcon shark = new ImageIcon(filepath);
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
			searchframe = new SearchFrame();
			searchframe.setVisible(true);
			searchframe.addWindowListener(this);
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
}
