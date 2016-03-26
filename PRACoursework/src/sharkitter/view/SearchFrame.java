package sharkitter.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

import api.jaws.Jaws;
import sharkitter.controller.RandomSharkRetriever;
import sharkitter.controller.SearchButtonListener;
import sharkitter.model.FavouriteSharks;
import sharkitter.model.PingCollection;
import sharkitter.model.SharkData;

public class SearchFrame extends JFrame {

	/**
	 * The Jaws API.
	 */
	private Jaws jawsApi;

	/**
	 * The drop-down boxes for searching in the search frame.
	 */
	private JComboBox<String> stage_of_life;
	private JComboBox<String> tracking_range;
	private JComboBox<String> gender;
	private JComboBox<String> tag_location;

	/**
	 * The panels in the search frame.
	 */
	private JPanel centralPanel;
	private JPanel mWestPanel;
	private JPanel superCentralPanel;

	/**
	 * The scroll pane in the search frame.
	 */
	private JScrollPane centralPane;

	/**
	 * The search button.
	 */
	private JButton search;

	private int counter;

	/**
	 * The action listener for the search button.
	 */
	private SearchButtonListener sbl;

	/**
	 * A black line border.
	 */
	private Border blackLineBorder;

	/**
	 * Data about favourite sharks.
	 */
    private FavouriteSharks favouriteSharks;
    private ActionListener functionalityController;
    private PingCollection pingCollection;

	/**
	 * Creates a new search frame.
	 * @param functionalityController the functionality controller.
	 * @param favouriteSharks data about favourite sharks.
	 * @param pingCollection all pings from the Jaws API.
	 */
    public SearchFrame(ActionListener functionalityController, FavouriteSharks favouriteSharks, PingCollection pingCollection) {
        super("Search");
        jawsApi = new Jaws("EkZ8ZqX11ozMamO9", "E7gdkwWePBYT75KE", true);
        System.out.println(jawsApi.getLastUpdated());

		//create border for later use
		blackLineBorder = BorderFactory.createLineBorder(Color.BLACK);

        this.pingCollection = pingCollection;
        this.favouriteSharks = favouriteSharks;
        this.functionalityController = functionalityController;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, 700));
        createPanels();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

	/**
	 * Creates and displays the widgets on the search frame.
	 */
	private void createPanels() {
		createNorthPanel();
		createCentralPanel();
		createWestPanel();
		createWCentralPanel();
		createSouthPanel();

		createSearchButton();
		createComboBoxes();

        createWNorthPanel();
        createWSouthPanel();

		pack();
	}

	private void createNorthPanel() {
		JMenuBar menuBar = new JMenuBar();

		JMenu view = new JMenu("View");

		JMenuItem menu = new JMenuItem("Menu");
		menu.addActionListener(functionalityController);
		menu.setToolTipText("Go back to the main menu");
        menu.setName("SearchFrame");
        menu.addActionListener(functionalityController);
        menu.setToolTipText("Go back to the main menu");

		view.add(menu);
		menuBar.add(view);
		add(menuBar, BorderLayout.NORTH);
	}

	/**
	 * Creates the combo boxes.
	 */
	private void createComboBoxes() {
		stage_of_life = new JComboBox();
		stage_of_life.addItem("All");
		stage_of_life.addItem("Mature");
		stage_of_life.addItem("Immature");
		stage_of_life.addItem("Undetermined");

		tracking_range = new JComboBox();
		tracking_range.addItem("Last 24 Hours");
		tracking_range.addItem("Last Week");
		tracking_range.addItem("Last Month");

		gender = new JComboBox();
		gender.addItem("All");
		gender.addItem("Male");
		gender.addItem("Female");

		tag_location = new JComboBox();
		tag_location.addItem("All");
		for(String tagLoc: jawsApi.getTagLocations()){
			tag_location.addItem(tagLoc);
		}
	}

	/**
	 * Returns the "Stage of life" combo box.
	 * @return the "Stage of life" combo box.
	 */
	public JComboBox<String> getStage_of_life(){
		return stage_of_life;
	}

	/**
	 * Returns the "Tracking range" combo box.
	 * @return the "Tracking range" combo box.
	 */
	public JComboBox<String> getTracking_range(){
		return tracking_range;
	}

	/**
	 * Returns the "Gender" combo box.
	 * @return the "Gender" combo box.
	 */
	public JComboBox<String> getGender(){
		return gender;
	}

	/**
	 * Returns the "Tag location" combo box.
	 * @return the "Tag location" combo box.
	 */
	public JComboBox<String> getTag_location(){
		return tag_location;
	}

	/**
	 * Creates search button.
	 */
	private void createSearchButton() {
		search = new JButton("Search");

        sbl = new SearchButtonListener(this,pingCollection);
        search.addActionListener(sbl);
    }

	/**
	 * Creates the panel for the search results.
	 */
	private void createCentralPanel() {
		centralPanel = new JPanel();
		centralPanel.setLayout(new BorderLayout());;

		Border emptyBorder = BorderFactory.createEmptyBorder(5, 0, 5, 5);
		centralPanel.setBorder(BorderFactory.createCompoundBorder(emptyBorder, blackLineBorder));
		superCentralPanel =new JPanel();
		superCentralPanel.setLayout(new GridLayout(0,1));
		centralPane = new JScrollPane(superCentralPanel);
		centralPanel.add(centralPane);

		add(centralPanel, BorderLayout.CENTER);
	}

	/**
	 * Adds search results to the frame.
	 * @param listOfSharks the data of the sharks to be added.
	 * @return the panel with the search results.
	 */
    public JPanel addSeveralSharkContainersToView (List<SharkData> listOfSharks) {
        int counter = listOfSharks.size();

        superCentralPanel.removeAll();

        if (!listOfSharks.isEmpty()) {
            for (SharkData sharkdata : listOfSharks) {
                superCentralPanel.setLayout(new GridLayout(counter,1));
                superCentralPanel.add(new SharkContainer(sharkdata,favouriteSharks));
                centralPane.setViewportView(superCentralPanel);
                superCentralPanel.paintComponents(superCentralPanel.getGraphics());

				revalidate();
				repaint();
				pack();
			}

		}
        else {
			centralPanel.add(new JLabel("Nothing to show here :)"));
		}
		return centralPanel;
	}

	/**
    * Creates the west panel which holds the combo boxes, search button, logo, and "Shark of the day".
    */
    private void createWestPanel() {
        mWestPanel = new JPanel(new BorderLayout(10, 10));//west Panel in main panel

        Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 0);
        mWestPanel.setBorder(BorderFactory.createCompoundBorder(emptyBorder, blackLineBorder));
        mWestPanel.setPreferredSize(new Dimension(300, this.getHeight()));

        add(mWestPanel,BorderLayout.WEST);
    }

    /**
     * Creates the panel with the combo boxes and search button and adds it to the west panel.
     */
    private void createWNorthPanel() {
        JPanel mwNorthPanel = new JPanel(new GridLayout(12, 1));

        Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        mwNorthPanel.setBorder(emptyBorder);

        mwNorthPanel.add(new JLabel("Shark Tracker"));
        mwNorthPanel.add(new JSeparator(JSeparator.HORIZONTAL));
        mwNorthPanel.add(new JLabel("Stage of life"));
        mwNorthPanel.add(stage_of_life);
        mwNorthPanel.add(new JLabel("Tracking range:"));
        mwNorthPanel.add(tracking_range);
        mwNorthPanel.add(new JLabel("Gender:"));
        mwNorthPanel.add(gender);
        mwNorthPanel.add(new JLabel("Tag location:"));
        mwNorthPanel.add(tag_location);
        mwNorthPanel.add(search);
        mwNorthPanel.add(new JLabel(jawsApi.getLastUpdated()));

        mWestPanel.add(mwNorthPanel, BorderLayout.NORTH);
    }

    /**
     * Creates the panel with the logo and adds it to the west panel.
     */
    private void createWCentralPanel() {
        JPanel mwCentralPanel = new JPanel(new GridLayout(1, 1));

        ImageIcon shark = new ImageIcon(getClass().getClassLoader().getResource("resources/SharkTracker.png"));
        Image img = shark.getImage();
        Image newImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        shark = new ImageIcon(newImg);

        JLabel sharkIcon = new JLabel("", shark, 0);
        mwCentralPanel.add(sharkIcon);
        mWestPanel.add(mwCentralPanel, BorderLayout.CENTER);
    }

    /**
     * Schedules "Shark of the day" so that it changes every day at midnight, creates a panel to hold it,
     * and adds it to the west panel.
     */
    private void createWSouthPanel() {
        JPanel mwSouthPanel = new JPanel(new GridLayout(3, 1));

        JLabel sharkOfTheDayLabel = new JLabel("<HTML><U>Shark of the day: </U></HTML>");
        JLabel sharkOfTheDayName = new JLabel();
        JLabel sharkOfTheDayVideo = new JLabel();

	    RandomSharkRetriever randomSharkRetriever = new RandomSharkRetriever(jawsApi);
	    randomSharkRetriever.showRandomShark();

	    sharkOfTheDayName.setText(randomSharkRetriever.getSharkName());
	    sharkOfTheDayVideo.setText(randomSharkRetriever.getSharkVideo());

        //add labels to panel
        mwSouthPanel.add(sharkOfTheDayLabel);
        mwSouthPanel.add(sharkOfTheDayName);
        mwSouthPanel.add(sharkOfTheDayVideo);

        //add to bottom of main west panel
        mWestPanel.add(mwSouthPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates panel holding acknowledgement statement.
     */
    private void createSouthPanel() {
        JPanel msPanel = new JPanel();
        msPanel.setPreferredSize(new Dimension(WIDTH,50));
        JLabel acknowledgement = new JLabel(jawsApi.getAcknowledgement());
        msPanel.add(acknowledgement);
        add(msPanel, BorderLayout.SOUTH);
    }
}
