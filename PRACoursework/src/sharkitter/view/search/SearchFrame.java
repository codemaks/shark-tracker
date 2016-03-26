package sharkitter.view.search;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.List;

import api.jaws.Jaws;
import sharkitter.api.JawsApi;
import sharkitter.controller.FunctionalityController;
import sharkitter.controller.RandomSharkRetriever;
import sharkitter.controller.SearchButtonListener;
import sharkitter.model.FavouriteSharks;
import sharkitter.model.PingCollection;
import sharkitter.model.SharkData;

/**
 * Class representation of the Search frame
 */
public class SearchFrame extends JFrame {

	/**
	 * The Jaws API.
	 */
	private Jaws jawsApi;

	/**
	 * The drop-down boxes for searching in the search frame.
	 */
	private JComboBox<String> stageOfLife;
	private JComboBox<String> trackingRange;
	private JComboBox<String> gender;
	private JComboBox<String> tagLocation;

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

	/**
	 * A black line border.
	 */
	private Border blackLineBorder;

	/**
	 * Data about favourite sharks.
	 */
    private FavouriteSharks favouriteSharks;
    private FunctionalityController functionalityController;
    private PingCollection pingCollection;

	/**
	 * Creates a new search frame.
	 * @param functionalityController the functionality controller.
	 * @param favouriteSharks data about favourite sharks.
	 * @param pingCollection all pings from the Jaws API.
	 */
    public SearchFrame(FunctionalityController functionalityController, FavouriteSharks favouriteSharks, PingCollection pingCollection) {
        super("Search");

        jawsApi = JawsApi.getInstance();

		//create border for later use
		blackLineBorder = BorderFactory.createLineBorder(Color.BLACK);

        this.pingCollection = pingCollection;
        this.favouriteSharks = favouriteSharks;
        this.functionalityController = functionalityController;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, 700));
        createPanels();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

    /**
     * Creates the panel to be added to the north of the frame.
     */
	private void createNorthPanel() {
		JMenuBar menuBar = new JMenuBar();

		JMenu view = new JMenu("View");

		JMenuItem menu = new JMenuItem("Menu");
		menu.addActionListener(functionalityController);
		menu.setToolTipText("Go back to the main menu");
        menu.setName("SearchFrame");

		view.add(menu);
		menuBar.add(view);
		add(menuBar, BorderLayout.NORTH);
	}

    /**
     * Creates the combo boxes.
     */
    private void createComboBoxes() {
        stageOfLife = new JComboBox<>();
        stageOfLife.addItem("All");
        stageOfLife.addItem("Mature");
        stageOfLife.addItem("Immature");
        stageOfLife.addItem("Undetermined");

        trackingRange = new JComboBox<>();
        trackingRange.addItem("Last 24 Hours");
        trackingRange.addItem("Last Week");
        trackingRange.addItem("Last Month");

        gender = new JComboBox<>();
        gender.addItem("All");
        gender.addItem("Male");
        gender.addItem("Female");

        tagLocation = new JComboBox<>();
        tagLocation.addItem("All");
        for(String sharkTagLocation : functionalityController.getListOfTagLocations()){
            tagLocation.addItem(sharkTagLocation);
        }
    }

    /**
     * Updates the tag location combobox if the ping collection was updated
     */
    public void updateTagLocation() {
        if(pingCollection.update()) {
            tagLocation = new JComboBox<>();
            tagLocation.addItem("All");
            for (String sharkName : functionalityController.getListOfTagLocations()) {
                tagLocation.addItem(jawsApi.getShark(sharkName).getTagLocation());
            }
        }
    }

    /**
     * Returns the "Stage of life" combo box.
     * @return the "Stage of life" combo box.
     */
    public JComboBox<String> getStageOfLife() {
        return stageOfLife;
    }

    /**
     * Returns the "Tracking range" combo box.
     * @return the "Tracking range" combo box.
     */
    public JComboBox<String> getTrackingRange() {
        return trackingRange;
    }

    /**
     * Returns the "Gender" combo box.
     * @return the "Gender" combo box.
     */
    public JComboBox<String> getGender() {
        return gender;
    }

    /**
     * Returns the "Tag location" combo box.
     * @return the "Tag location" combo box.
     */
    public JComboBox<String> getTagLocation() {
        return tagLocation;
    }

    /**
     * Creates search button.
     */
    private void createSearchButton() {
        search = new JButton("Search");

        SearchButtonListener searchButtonListener = new SearchButtonListener(this, pingCollection);
        search.addActionListener(searchButtonListener);
    }

	/**
	 * Creates the panel for the search results.
	 */
	private void createCentralPanel() {
		centralPanel = new JPanel();
		centralPanel.setLayout(new BorderLayout());

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
	 * @param sharkDataList the data of the sharks to be added.
	 * @return the panel with the search results.
	 */
    public JPanel addSeveralSharkContainersToView (List<SharkData> sharkDataList) {
        superCentralPanel.removeAll();
        updateTagLocation();

        if (!sharkDataList.isEmpty()) {
            int counter = sharkDataList.size();

            for (SharkData sharkdata : sharkDataList) {
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
            superCentralPanel.add(new JLabel("Nothing to show here :)"));
            revalidate();
            repaint();
            pack();
        }
        return centralPanel;
    }

    /**
     * Creates the west panel within the main panel.
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
        mwNorthPanel.add(stageOfLife);
        mwNorthPanel.add(new JLabel("Tracking range:"));
        mwNorthPanel.add(trackingRange);
        mwNorthPanel.add(new JLabel("Gender:"));
        mwNorthPanel.add(gender);
        mwNorthPanel.add(new JLabel("Tag location:"));
        mwNorthPanel.add(tagLocation);
        mwNorthPanel.add(search);
        mwNorthPanel.add(new JLabel(jawsApi.getLastUpdated()));

        mWestPanel.add(mwNorthPanel, BorderLayout.NORTH);
    }

    /**
     * Creates the panel with the logo and adds it to the west panel.
     */
    private void createWCentralPanel() {
        JPanel mwCentralPanel = new JPanel(new GridLayout(1, 1));

        ImageIcon shark = new ImageIcon(getClass().getClassLoader().getResource("SharkTracker.png"));
        Image img = shark.getImage();
        Image newImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        shark = new ImageIcon(newImg);

        JLabel sharkIcon = new JLabel("", shark, SwingConstants.CENTER);
        mwCentralPanel.add(sharkIcon);
        mWestPanel.add(mwCentralPanel, BorderLayout.CENTER);
    }

    /**
     * Creates a panel to hold the "Shark of the day" feature, and adds it to the west panel.
     */
    private void createWSouthPanel() {
        JPanel mwSouthPanel = new JPanel(new GridLayout(3, 1));

        JLabel sharkOfTheDayLabel = new JLabel("<HTML><U>Shark of the day: </U></HTML>");
        JLabel sharkOfTheDayName = new JLabel();
        JTextField sharkOfTheDayVideo = new JTextField();
        sharkOfTheDayVideo.setEditable(false);

	    //get random shark
	    RandomSharkRetriever randomSharkRetriever = new RandomSharkRetriever(jawsApi);
	    randomSharkRetriever.showRandomShark();

	    //display name and video link
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
