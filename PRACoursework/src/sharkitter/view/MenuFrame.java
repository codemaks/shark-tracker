package sharkitter.view;

import sharkitter.controller.FunctionalityController;
import sharkitter.model.FavouriteSharks;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MenuFrame extends JFrame {
	//private JTextField searchField;
	private JButton searchButton;
	private JButton favouritesButton;
	private JButton statisticsButton;
	private JButton disconnectButton;
	private ActionListener userController, functionalityController;

	public MenuFrame(ActionListener userController) {
		super("Amnity Police");
		this.userController = userController;

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		addWidgets();
	}

	public void addWidgets() {
		JPanel northPanel = new JPanel(new BorderLayout());

		JPanel tipPanel = new JPanel(new BorderLayout());

		JButton tipButton = new JButton("?");
		tipButton.setToolTipText("Try a Konami Code =p");

		tipPanel.add(tipButton, BorderLayout.EAST);

		northPanel.add(tipPanel, BorderLayout.EAST);

		JMenuBar menuBar = new JMenuBar();
		JMenu profiles = new JMenu("Profiles");
		JMenu tip = new JMenu("?");
		tip.setToolTipText("Try a Konami Code =p");

		menuBar.add(profiles);
		menuBar.add(tip);

		JMenu loadProfiles = new JMenu("Load Profiles");
		JMenuItem createProfile = new JMenuItem("Create Profile");

		profiles.add(loadProfiles);
		profiles.add(createProfile);

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(5, 1));

		searchButton = new JButton("Search");
		searchButton.setHorizontalAlignment(JButton.CENTER);

		favouritesButton = new JButton("Favourites");

		statisticsButton = new JButton("Statistics");
		statisticsButton.setHorizontalAlignment(JButton.CENTER);

		JLabel blank = new JLabel("");
		disconnectButton = new JButton("Disconnect");
		disconnectButton.addActionListener(userController);

		southPanel.add(searchButton);
		southPanel.add(favouritesButton);
		southPanel.add(statisticsButton);
		southPanel.add(blank);
		southPanel.add(disconnectButton);

		ImageIcon shark = new ImageIcon(getClass().getClassLoader().getResource("resources/SharkTracker.png"));
		Image img = shark.getImage();
		Image newImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		shark = new ImageIcon(newImg);

		JLabel sharkTrackerLabel = new JLabel("", shark, 0);
		sharkTrackerLabel.setVerticalTextPosition(JLabel.BOTTOM);
		sharkTrackerLabel.setHorizontalTextPosition(JLabel.CENTER);
		//Font font = new Font("Monospace", Font.ITALIC, 30);
		//sharkTrackerLabel.setFont(font);

		add(southPanel, BorderLayout.SOUTH);
		add(sharkTrackerLabel, BorderLayout.CENTER);
		add(menuBar, BorderLayout.NORTH);

		pack();
	}

	public void addFunctionalityController(FunctionalityController functionalityController) {
		this.functionalityController = functionalityController;
		addKeyListener(functionalityController);
		searchButton.addActionListener(functionalityController);
		favouritesButton.addActionListener(functionalityController);
		statisticsButton.addActionListener(functionalityController);
	}
}
