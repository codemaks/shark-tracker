package sharkitter.view;

import api.jaws.Jaws;
import sharkitter.controller.FunctionalityController;
import sharkitter.controller.UserController;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class MenuFrame extends JFrame {

	private JButton searchButton;
	private JButton favouritesButton;
	private JButton statisticsButton;

	private JMenu loadProfiles;
	private JMenuItem createProfile;

	private ActionListener userController, functionalityController;

	private Jaws jaws;

	public MenuFrame() throws IOException {
		super("Amnity Police");

		centreWindow(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		addWidgets();
	}

	public static void centreWindow(Window frame) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) /3);
		int y = (int) ((dimension.getHeight() - frame.getHeight())/4 );
		frame.setLocation(x, y);
	}

	public void addWidgets() {
		JMenuBar menuBar = createjMenuBar();

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(5, 1));

		searchButton = new JButton("Search");
		searchButton.setHorizontalAlignment(JButton.CENTER);

		favouritesButton = new JButton("Favourites");

		statisticsButton = new JButton("Statistics");
		statisticsButton.setHorizontalAlignment(JButton.CENTER);

		JLabel blank = new JLabel("");

		southPanel.add(searchButton);
		southPanel.add(favouritesButton);
		southPanel.add(statisticsButton);
		southPanel.add(blank);

		ImageIcon shark = new ImageIcon(getClass().getClassLoader().getResource("resources/SharkTracker.png"));
		Image img = shark.getImage();
		Image newImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		shark = new ImageIcon(newImg);

		JLabel sharkTrackerLabel = new JLabel("", shark, 0);
		sharkTrackerLabel.setVerticalTextPosition(JLabel.BOTTOM);
		sharkTrackerLabel.setHorizontalTextPosition(JLabel.CENTER);

		add(southPanel, BorderLayout.SOUTH);
		add(sharkTrackerLabel, BorderLayout.CENTER);
		add(menuBar, BorderLayout.NORTH);

		pack();
	}

	/**
	 * Create a JMenuBar for choosing between different profiles
	 * @return	Created JMenuBar
     */
	private JMenuBar createjMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu profiles = new JMenu("Profiles");
		JMenu tip = new JMenu("?");
		tip.setToolTipText("Try a Konami Code =p");

		menuBar.add(profiles);
		menuBar.add(tip);

		loadProfiles = new JMenu("Load Profiles");
		createProfile = new JMenuItem("Create Profile");

		profiles.add(loadProfiles);
		profiles.add(createProfile);

		return menuBar;
	}

	/**
	 * Add the functionality Controller to this frame
	 * @param functionalityController	Controller responsible for the different functionalities of this programme
     */
	public void addFunctionalityController(FunctionalityController functionalityController) {
		this.functionalityController = functionalityController;
		addKeyListener(functionalityController);
		searchButton.addActionListener(functionalityController);
		favouritesButton.addActionListener(functionalityController);
		statisticsButton.addActionListener(functionalityController);
	}

	/**
	 * Add the user Controller to this frame
	 * @param userController	Controller responsible to switching between users
     */
	public void addUserController(UserController userController) {
		this.userController = userController;
		createProfile.addActionListener(this.userController);
	}

	/**
	 * Disable the favourite button
	 */
	public void toggleFavourites(boolean b) {
		favouritesButton.setEnabled(false);
	}

	/**
	 * Load the given profile to the MenuBar
	 * @param profile	String representation of a certain username
	 */
	public void addProfile(String profile) {
		JMenuItem profileItem = new JMenuItem(profile);
		loadProfiles.add(profileItem);
		profileItem.addActionListener(userController);
		revalidate();
		repaint();
	}
	public void setJaws(Jaws jaws){this.jaws = jaws;}
	public Jaws getJaws(){return jaws;}


}
