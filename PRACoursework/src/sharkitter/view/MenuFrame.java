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
	private JButton disconnectButton;
	private ActionListener userController, functionalityController;

	public MenuFrame(ActionListener userController) {
		super("Amnity Police");
		this.userController = userController;

	//	centreWindow(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWidgets();
	}

	/*public static void centreWindow(Window frame) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) /3);
		int y = (int) ((dimension.getHeight() - frame.getHeight())/4 );
		frame.setLocation(x, y);
	}*/

	public void addWidgets() {
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(4, 1));

		searchButton = new JButton("Search");
		searchButton.setHorizontalAlignment(JButton.CENTER);

		favouritesButton = new JButton("Favourites");
		favouritesButton.setEnabled(false);

		JLabel blank = new JLabel("");
		disconnectButton = new JButton("Disconnect");
		disconnectButton.addActionListener(userController);

		southPanel.add(searchButton);
		southPanel.add(favouritesButton);
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
	}
}
