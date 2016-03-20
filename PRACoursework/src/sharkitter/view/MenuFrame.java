package sharkitter.view;

import sharkitter.controller.FunctionalityController;
import sharkitter.model.FavouriteSharks;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(5, 1));

		searchButton = new JButton("Search");
		searchButton.setHorizontalAlignment(JButton.CENTER);

		favouritesButton = new JButton("Favourites");
		favouritesButton.setEnabled(false);

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

		pack();
	}

	public void addFunctionalityController(FunctionalityController functionalityController) {
		this.functionalityController = functionalityController;
		searchButton.addActionListener(functionalityController);
		favouritesButton.addActionListener(functionalityController);
		statisticsButton.addActionListener(functionalityController);
	}
}
