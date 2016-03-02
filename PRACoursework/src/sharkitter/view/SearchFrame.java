package sharkitter.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import api.jaws.Jaws;
import api.jaws.Ping;


public class SearchFrame extends JFrame implements Observer {

	private Jaws jawsApi;

	private JComboBox<String> stage_of_life;
	private JComboBox<String> tracking_range;
	private JComboBox<String> gender;
	private JComboBox<String> tag_location;

	private JPanel centralPanel;
    private JPanel mWestPanel;
    private JPanel mwNorthPanel;
    private JPanel mwSouthPanel;

	private int counter;

    public SearchFrame() {
        super("Search");
        jawsApi = new Jaws("EkZ8ZqX11ozMamO9", "E7gdkwWePBYT75KE", true);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, 700));
        createWidgets();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

	/**
	 * Create and display the widgets on the main Frame
	 */
	private void createWidgets() {
		mWestPanel = new JPanel(new BorderLayout());//west Panel in main panel
		mwNorthPanel = new JPanel(new GridLayout(10, 1));//north Panel in west panel which is in the main panel
        mwSouthPanel = new JPanel(new GridLayout(1, 1));//south panel in main west panel

		//Dimension westDim = new Dimension(300, this.getHeight());

		JButton search = new JButton("Search");

		search.addActionListener(new ActionListener() {
            //assuming we don't need this bit as SearchButtonListener is now a separate class?? <Nina>
			@Override
			public void actionPerformed(ActionEvent e){
				//1. read selected constraint from combo box
				String sOfLife = (String)stage_of_life.getSelectedItem();
				String tRange = (String)tracking_range.getSelectedItem();
				String gen = (String)gender.getSelectedItem();
				String tagLoc = (String)tag_location.getSelectedItem();

				//2. get all shark components by tracking range
				if (sOfLife.equals("Last 24 hours")) {
					System.out.println(sOfLife);
					updateCentralPanel(jawsApi.past24Hours());
				}
                else if (sOfLife.equals("Last Week")) {
					updateCentralPanel(jawsApi.pastWeek());
				}
                else if (sOfLife.equals("Last Month")) {
					updateCentralPanel(jawsApi.pastMonth());
				}
                else {
					System.out.println("Search ButtonListener Error 1 : Invalid ComboBox input");
				}
				//3. apply constraint on West panel filled with Shark Component objects

            }
        });

        createSouthPanel();
		createCentralPanel();

		mWestPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		//mWestPanel.setPreferredSize(westDim);
        mWestPanel.setPreferredSize(new Dimension(300, this.getHeight()));

		add(mWestPanel,BorderLayout.WEST);

		stage_of_life = new JComboBox();
		stage_of_life.addItem("Any");
		stage_of_life.addItem("Mature");
		stage_of_life.addItem("Immature");
		stage_of_life.addItem("Undetermined");

		tracking_range = new JComboBox();
		tracking_range.addItem("Last 24 Hours");
		tracking_range.addItem("Last Week");
		tracking_range.addItem("Last Month");

		gender = new JComboBox();
		gender.addItem("Any");
		gender.addItem("Male");
		gender.addItem("Female");

		tag_location = new JComboBox();
		for(String tagloc: jawsApi.getTagLocations()){
			tag_location.addItem(tagloc);
		}

		mWestPanel.add(mwNorthPanel);
		mwNorthPanel.add(new JLabel("Stage of life"));
		mwNorthPanel.add(stage_of_life);
		mwNorthPanel.add(new JLabel("Tracking range:"));
		mwNorthPanel.add(tracking_range);
		mwNorthPanel.add(new JLabel("Gender:"));
		mwNorthPanel.add(gender);
		mwNorthPanel.add(new JLabel("Tag location:"));
		mwNorthPanel.add(tag_location);
		mwNorthPanel.add(search);

		pack();
	}

	/**
	 * Create and display the central element of the SearchFrame i.e. the search results.
     */
	private void createCentralPanel() {
		centralPanel = new JPanel();
		centralPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		centralPanel.add(new JScrollPane());
		//centralPanel.add(detailsOfFoundShark());
		centralPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

        add(centralPanel, BorderLayout.CENTER);
    }

	private JPanel updateCentralPanel(ArrayList<Ping> listOfPings){
		for(Ping ping :listOfPings) {
			centralPanel = new JPanel();
			setLayout(new GridLayout(counter, 1));
			centralPanel.add(new SharkContainer(jawsApi.getShark(ping.getName()), ping), counter);
			counter++;
			return centralPanel;
		}
	    return null;
	}


	/**
	 * Create the south Panel with the acknowledgement statement.
	 * @return	JPanel the acknowledgement statement.
     */
	private void createSouthPanel() {
		JPanel msPanel = new JPanel();
		msPanel.setPreferredSize(new Dimension(WIDTH,50));

		JLabel acknowledgement = new JLabel(jawsApi.getAcknowledgement());
		msPanel.add(acknowledgement);

        add(msPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates the west panel within the main panel.
     */
    private void createWestPanel() {

    }

    /**
     * Creates the south panel within the west panel and adds it to the west panel.
     */
    private void createWestSouthPanel() {
        ImageIcon shark = new ImageIcon(getClass().getClassLoader().getResource("resources/shark9.png"));
        Image img = shark.getImage();
        Image newImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        shark = new ImageIcon(newImg);

        JLabel sharkIcon = new JLabel("", shark, 0);
        mwSouthPanel.add(sharkIcon);
        mWestPanel.add(mwSouthPanel, BorderLayout.SOUTH);
    }

	@Override
	public void update(Observable o, Object arg) {

	}
}
