package sharkitter.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import api.jaws.Jaws;
import sharkitter.controller.RandomSharkRetriever;
import sharkitter.controller.SearchButtonListener;
import sharkitter.model.FavouriteSharks;
import sharkitter.model.SharkData;

public class SearchFrame extends JFrame {

	private Jaws jawsApi;

	private JComboBox<String> stage_of_life;
	private JComboBox<String> tracking_range;
	private JComboBox<String> gender;
	private JComboBox<String> tag_location;

	private JPanel centralPanel;
	private JPanel mWestPanel;
	private JScrollPane centralPane;
	private JPanel superCentralPanel;

	private JButton search;

	private int counter;

	private SearchButtonListener sbl;

	//black line border
	private Border blackLineBorder;

	private FavouriteSharks favouriteSharks;
	private ActionListener functionalityController;

	public SearchFrame(ActionListener functionalityController, FavouriteSharks favouriteSharks) {
		super("Search");
		jawsApi = new Jaws("EkZ8ZqX11ozMamO9", "E7gdkwWePBYT75KE", true);
		System.out.println(jawsApi.getLastUpdated());

		//create borders for later use
		blackLineBorder = BorderFactory.createLineBorder(Color.BLACK);

		this.favouriteSharks = favouriteSharks;
		this.functionalityController = functionalityController;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(1200, 700));
		createPanels();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Create and display the widgets on the main Frame
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
		createWCentralPanel();
        createWSouthPanel();

		pack();
	}

	private void createNorthPanel() {
		JMenuBar menuBar = new JMenuBar();

		JMenu view = new JMenu("View");

		JMenuItem menu = new JMenuItem("Menu");
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

	public JComboBox<String> getStage_of_life(){
		return stage_of_life;
	}

	public JComboBox<String> getTracking_range(){
		return tracking_range;
	}

	public JComboBox<String> getGender(){
		return gender;
	}

	public JComboBox<String> getTag_location(){
		return tag_location;
	}

	/**
	 * Creates search button.
	 */
	private void createSearchButton() {
		search = new JButton("Search");

		sbl = new SearchButtonListener(this, favouriteSharks);
		search.addActionListener(sbl);
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

	public JPanel addSharkContainerToView(SharkContainer sharkcontainer){

        centralPanel.setLayout(new BorderLayout());
        superCentralPanel.add(sharkcontainer);
        superCentralPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        superCentralPanel.paintComponents(superCentralPanel.getGraphics());

        centralPanel.remove(centralPane);
        centralPane.setViewportView(superCentralPanel);
        centralPanel.add(centralPane);

        revalidate();
        repaint();
        pack();

		return centralPanel;
	}

	public JPanel addSeveralSharkContainersToView (java.util.List<SharkContainer> listofsharkcontainers) {

		superCentralPanel.removeAll();
		System.out.println(listofsharkcontainers);

		if (!listofsharkcontainers.isEmpty()) {
			for (SharkContainer sharkcontainer : listofsharkcontainers) {

				superCentralPanel.add(sharkcontainer);
				superCentralPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
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

	public void clear(){
		superCentralPanel.removeAll();
		revalidate();
		repaint();
		pack();
	}

   /* public JPanel updateCentralPanel(ArrayList<Ping> listofpings){

		counter = 0;
		superCentralPanel.removeAll();

		if(!listofpings.isEmpty()){
			counter=(listofpings.size())-1;
			for(Ping ping :listofpings) {

				centralPanel.setLayout(new BorderLayout());
				superCentralPanel.setLayout(new GridLayout(0,1));
				superCentralPanel.add(new SharkContainer(jawsApi.getShark(ping.getName()), ping, favouriteSharks));
				superCentralPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
				superCentralPanel.paintComponents(superCentralPanel.getGraphics());

				centralPanel.remove(centralPane);
				centralPane.setViewportView(superCentralPanel);
				centralPanel.add(centralPane);

				pack();
			}

		}else{
			centralPanel.add(new JLabel("Nothing to show here :)"));
		}

		revalidate();
		repaint();

		return centralPanel;
   }*/


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
     * Creates the south panel within the west panel and adds it to the west panel.
     */
    private void createWCentralPanel() {
        JPanel mwCentralPanel = new JPanel(new GridLayout(1, 1));

        ImageIcon shark = new ImageIcon(getClass().getClassLoader().getResource("resources/SharkTracker.png"));
        Image img = shark.getImage();
        Image newImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        shark = new ImageIcon(newImg);

        JLabel sharkIcon = new JLabel("", shark, 0);
        mwCentralPanel.add(sharkIcon);
        mWestPanel.add(mwCentralPanel, BorderLayout.CENTER);
    }

    /**
     * Schedules "Shark of the day" so that it changes every day at midnight.
     */
    private void createWSouthPanel() {
        JPanel mwSouthPanel = new JPanel(new GridLayout(3, 1));

        JLabel sharkOfTheDayLabel = new JLabel("<HTML><U>Shark of the day: </U></HTML>");
        JLabel sharkOfTheDayName = new JLabel();
        JLabel sharkOfTheDayVideo = new JLabel();

        File f = new File("timestamp.txt");

        //get current date
        Calendar timeNow = Calendar.getInstance();
        String currentDay = (new Integer(timeNow.get(Calendar.DAY_OF_MONTH))).toString();

        RandomSharkRetriever randomSharkRetriever = new RandomSharkRetriever(jawsApi);

        String[] infoToWrite = new String[3];
        infoToWrite[0] = currentDay;

        try {
            //if file doesn't exist
            if(f.createNewFile()) {
                //for debugging purposes
                System.out.println("File created!");
                randomSharkRetriever.retrieveNewShark();

                infoToWrite[1] = randomSharkRetriever.getSharkName();
                infoToWrite[2] = randomSharkRetriever.getSharkVideo();

                //write current day and new shark name/video to file
                BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));

                for(int i = 0; i < 3; i++) {
                    writer.write(infoToWrite[i]);
                    writer.newLine();
                }

                writer.close();

                sharkOfTheDayName.setText(infoToWrite[1]);
                sharkOfTheDayVideo.setText(infoToWrite[2]);
            }
            //if file exists
            else {
                //for debugging purposes
                System.out.println("File already exists!");

                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);

                String dayLastLoaded = br.readLine();

                String sharkName;
                String sharkVideo;

                //if the day has changed, retrieve new shark and write info to file
                if(!dayLastLoaded.equals(currentDay)) {
                    randomSharkRetriever.retrieveNewShark();
                    sharkName = randomSharkRetriever.getSharkName();
                    sharkVideo = randomSharkRetriever.getSharkVideo();

                    infoToWrite[1] = sharkName;
                    infoToWrite[2] = sharkVideo;

                    //write current day and new shark name/video to file
                    BufferedWriter writer = new BufferedWriter(new FileWriter(f, false));

                    for(int i = 0; i < 3; i++) {
                        writer.write(infoToWrite[i]);
                        writer.newLine();
                    }

                    writer.close();
                }
                //if the day has not changed, just retrieve shark and video from text file
                else {
                    sharkName = br.readLine();
                    sharkVideo = br.readLine();
                }

                br.close();

                sharkOfTheDayName.setText(sharkName);
                sharkOfTheDayVideo.setText(sharkVideo);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //add labels to panel
        mwSouthPanel.add(sharkOfTheDayLabel);
        mwSouthPanel.add(sharkOfTheDayName);
        mwSouthPanel.add(sharkOfTheDayVideo);

        //add to bottom of main west panel
        mWestPanel.add(mwSouthPanel, BorderLayout.SOUTH);
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
