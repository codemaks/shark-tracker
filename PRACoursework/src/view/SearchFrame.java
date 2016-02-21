package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import api.jaws.Jaws;
import api.jaws.Ping;
import api.jaws.Shark;


public class SearchFrame extends JFrame implements ActionListener {

	private Jaws jawsApi;
	 
	 public SearchFrame() {
		 super("Search");
		 jawsApi = new Jaws("EkZ8ZqX11ozMamO9","E7gdkwWePBYT75KE", true);
		 createWidgets();
	 }

	/**
	 * Create and display the widgets on the main Frame
	 */
	private void createWidgets() {

		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(1200,700));

		JPanel mwestPanel = new JPanel(new BorderLayout());//west Panel in main panel
		JPanel mwnorthPanel = new JPanel(new GridLayout(10,1));//north Panel in west panel which is in the main panel

		Dimension westdim = new Dimension (300,this.getHeight());

		JButton search = new JButton("Search");

		search.addActionListener(new ActionListener(){
            //anonymous inner class...
           public void actionPerformed(ActionEvent e){
                       //1. read selected constraint from combo box

                       //2. get all shark components

                       //3. apply constraint on West panel filled with Shark Component objects

                }

        });
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		this.add(createSouthPanel(),BorderLayout.SOUTH);
		this.add(createCentralPanel(), BorderLayout.CENTER);


		mwestPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		mwestPanel.setPreferredSize(westdim);

		this.add(mwestPanel,BorderLayout.WEST);
		// The commented code below was used to test a very simplistic version of the west side of the frame.
		// You are welcome to uncomment it if necessary.
/*		JComboBox jcbstage_of_life = new JComboBox();
		JComboBox jcbtracking_range = new JComboBox();
		JComboBox jcbgender = new JComboBox();
		JComboBox jcbtag_location = new JComboBox();
		mwestPanel.add(mwnorthPanel);
		mwnorthPanel.add(new JLabel("stage of life"));
		mwnorthPanel.add(jcbstage_of_life);
		mwnorthPanel.add(new JLabel("tracking range:"));
		mwnorthPanel.add(jcbtracking_range);
		mwnorthPanel.add(new JLabel("gender:"));
		mwnorthPanel.add(jcbgender);
		mwnorthPanel.add(new JLabel("tag location:"));
		mwnorthPanel.add(jcbtag_location);
		mwnorthPanel.add(search);*/

		this.pack();
	}

	/**
	 * Create and display the central element of the SearchFrame i.e. the search results.
	 * @return	a JPanel representing the search results.
     */
	private JPanel createCentralPanel() {
		JPanel centralPanel = new JPanel();
		centralPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		centralPanel.add(new JScrollPane());
		centralPanel.add(detailsOfFoundShark());
		centralPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

		return centralPanel;
	}

	/**
	 * Display all information concerning the Sharks that fit the chosen fields for the search.
	 * @param foundShark	A Shark matching the chosen criteria.
	 * @param lastPing	The last Ping for the matching Shark.
     * @return	A JPanel containing all relevant information concerning a particular Shark.
     */
	private JPanel detailsOfFoundShark(Shark foundShark, Ping lastPing) {
		JPanel topPanel = new JPanel(new BorderLayout());

		topPanel.add(createSharkFeaturesTable(foundShark), BorderLayout.NORTH);

		topPanel.add(createSharkDescriptionText(foundShark), BorderLayout.CENTER);

		topPanel.add(createSharkTrackOptions(lastPing), BorderLayout.SOUTH);

		return topPanel;
	}

	/**
	 * Create a table to display specific information about a Shark (name, gender, stage of life, species, length and weight).
	 * @param foundShark	A Shark matching the chosen criteria.
	 * @return	a JPanel containing all relevant information (name, gender, satge of life, species, length and weight).
     */
	private JPanel createSharkFeaturesTable(Shark foundShark) {
		JPanel sharkFeatures = new JPanel(new GridLayout(6, 2));

		JLabel nameLabel = new JLabel("Name: ");
		JLabel genderLabel = new JLabel("Gender: ");
		JLabel stageOfLifeLabel = new JLabel("Stage of Life: ");
		JLabel specieLabel = new JLabel("Species: ");
		JLabel lengthLabel = new JLabel("Length: ");
		JLabel weightLabel = new JLabel("Weight: ");

		JLabel sharkName = new JLabel(foundShark.getName());
		JLabel sharkGender = new JLabel(foundShark.getGender());
		JLabel sharkStageOfLife = new JLabel(foundShark.getStageOfLife());
		JLabel sharkSpecie = new JLabel(foundShark.getSpecies());
		JLabel sharkLength = new JLabel(foundShark.getLength());
		JLabel sharkWeight = new JLabel(foundShark.getWeight());

		sharkFeatures.add(nameLabel);
		sharkFeatures.add(sharkName);
		sharkFeatures.add(genderLabel);
		sharkFeatures.add(sharkGender);
		sharkFeatures.add(stageOfLifeLabel);
		sharkFeatures.add(sharkStageOfLife);
		sharkFeatures.add(specieLabel);
		sharkFeatures.add(sharkSpecie);
		sharkFeatures.add(lengthLabel);
		sharkFeatures.add(sharkLength);
		sharkFeatures.add(weightLabel);
		sharkFeatures.add(sharkWeight);

		return sharkFeatures;
	}

	/**
	 * Create and display the description of a matching Shark.
	 * @param foundShark	A Shark matching the chosen criteria.
	 * @return	A JPanel containing all relevant information (description).
     */
	private JPanel createSharkDescriptionText(Shark foundShark) {
		JPanel descriptionPanel = new JPanel();

		descriptionPanel.add(new JLabel("Description: \n\n"));
		descriptionPanel.add(new JLabel(foundShark.getDescription()));

		return descriptionPanel;
	}

	/**
	 * Create and display the last ping of a given Shark and the option to follow it.
	 * @param lastPing	Object containing information about the last point of contact with a given shark.
	 * @return	A JPanel containing all revelant information and a follow button.
     */
	private JPanel createSharkTrackOptions(Ping lastPing) {
		JPanel pingPanel = new JPanel(new BorderLayout());

		JLabel pingLabel = new JLabel("Last ping: " + lastPing.getTime());
		JButton followButton = new JButton("Follow");

		pingPanel.add(pingLabel, BorderLayout.CENTER);
		pingPanel.add(followButton, BorderLayout.EAST);

		return pingPanel;
	}

	/**
	 * Create the south Panel with the acknowledgement statement.
	 * @return	A JPanel the acknowledgement statement.
     */
	private JPanel createSouthPanel() {
		JPanel msPanel = new JPanel();
		msPanel.setPreferredSize(new Dimension(WIDTH,50));

		JLabel acknowledgement = new JLabel(jawsApi.getAcknowledgement());
		msPanel.add(acknowledgement);

		return msPanel;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
