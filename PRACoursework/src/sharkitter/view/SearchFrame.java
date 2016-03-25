package sharkitter.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import api.jaws.Jaws;
import sharkitter.api.JawsApi;
import sharkitter.controller.SearchButtonListener;
import sharkitter.model.FavouriteSharks;
import sharkitter.model.PingCollection;
import sharkitter.model.SharkData;


public class SearchFrame extends JFrame {

	private Jaws jawsApi;

	private JComboBox<String> stageOfLife;
	private JComboBox<String> trackingRange;
	private JComboBox<String> gender;
	private JComboBox<String> tagLocation;

	private JPanel centralPanel;
    private JPanel mWestPanel;
    private JScrollPane centralPane;
    private JPanel superCentralPanel;

    private JButton search;

    private Border blackLineBorder;

    private FavouriteSharks favouriteSharks;
    private ActionListener functionalityController;
    private PingCollection pingCollection;

    public SearchFrame(ActionListener functionalityController, FavouriteSharks favouriteSharks, PingCollection pingCollection) {
        super("Search");

        jawsApi = JawsApi.getInstance();
        System.out.println(jawsApi.getLastUpdated());

        //create borders for later use
        blackLineBorder = BorderFactory.createLineBorder(Color.BLACK);

        this.pingCollection = pingCollection;
        this.favouriteSharks = favouriteSharks;
        this.functionalityController = functionalityController;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, 700));
        createPanels();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

	/**
	 * Create and display the widgets on the main Frame
	 */
	private void createPanels() {
        createNorthPanel();
        createCentralPanel();
        createWestPanel();
        createWSouthPanel();
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
        stageOfLife = new JComboBox();
        stageOfLife.addItem("All");
        stageOfLife.addItem("Mature");
        stageOfLife.addItem("Immature");
        stageOfLife.addItem("Undetermined");

        trackingRange = new JComboBox();
        trackingRange.addItem("Last 24 Hours");
        trackingRange.addItem("Last Week");
        trackingRange.addItem("Last Month");

        gender = new JComboBox();
        gender.addItem("All");
        gender.addItem("Male");
        gender.addItem("Female");

        tagLocation = new JComboBox();
        tagLocation.addItem("All");
        for(String tagLoc: jawsApi.getTagLocations()){
            tagLocation.addItem(tagLoc);
        }
    }

    public JComboBox<String> getStageOfLife(){
        return stageOfLife;
    }

    public JComboBox<String> getTrackingRange(){
        return trackingRange;
    }

    public JComboBox<String> getGender(){
        return gender;
    }

    public JComboBox<String> getTagLocation(){
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
	 * Create and display the central element of the SearchFrame i.e. the search results.
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

    public JPanel addSeveralSharkContainersToView (List<SharkData> sharkDataList) {
        int counter = sharkDataList.size();

        superCentralPanel.removeAll();

        if (!sharkDataList.isEmpty()) {
            for (SharkData sharkdata : sharkDataList) {

                superCentralPanel.setLayout(new GridLayout(counter,1));
                superCentralPanel.add(new SharkContainer(sharkdata,favouriteSharks));
                centralPane.setViewportView(superCentralPanel);
                superCentralPanel.paintComponents(superCentralPanel.getGraphics());

                revalidate();
                repaint();
                pack();
            }

        } else {
            centralPanel.add(new JLabel("Nothing to show here :)"));
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
     * Creates the north panel within the west panel and adds it to the west panel.
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

        mWestPanel.add(mwNorthPanel);
    }

    /**
     * Creates the south panel within the west panel and adds it to the west panel.
     */
    private void createWSouthPanel() {
        JPanel mwSouthPanel = new JPanel(new GridLayout(1, 1));

        ImageIcon shark = new ImageIcon(getClass().getClassLoader().getResource("resources/SharkTracker.png"));
        Image img = shark.getImage();
        Image newImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        shark = new ImageIcon(newImg);

        JLabel sharkIcon = new JLabel("", shark, SwingConstants.CENTER);
        mwSouthPanel.add(sharkIcon);
        mWestPanel.add(mwSouthPanel, BorderLayout.SOUTH);
    }

    private void createWCentralPanel() {
        JPanel mwCentralPanel = new JPanel();

        JLabel sharkOfTheDay = new JLabel("Shark of the day: ");

    }

    /**
     * Creates south panel with acknowledgement statement
     */
    private void createSouthPanel() {
        JPanel msPanel = new JPanel();
        msPanel.setPreferredSize(new Dimension(WIDTH,50));
        JLabel acknowledgement = new JLabel(jawsApi.getAcknowledgement());
        msPanel.add(acknowledgement);
        add(msPanel, BorderLayout.SOUTH);
    }
}
