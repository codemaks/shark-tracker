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


public class SearchFrame extends JFrame implements Observer{

	private Jaws jawsApi;

	private JComboBox<String> stage_of_life;
	private JComboBox<String> tracking_range;
	private JComboBox<String> gender;
	private JComboBox<String> tag_location;

	private JPanel centralpanel;
	private int counter;

	 public SearchFrame() {
		 super("Search");
		 jawsApi = new Jaws("EkZ8ZqX11ozMamO9","E7gdkwWePBYT75KE", true);
		 createWidgets();
		 setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	 }

	/**
	 * Create and display the widgets on the main Frame
	 */
	private void createWidgets() {

		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(1200,700));

		JPanel mwestPanel = new JPanel(new BorderLayout());//west Panel in main panel
		JPanel mwnorthPanel = new JPanel(new GridLayout(10,1));//north Panel in west panel which is in the main panel

		Dimension westdim = new Dimension (300,this.getHeight());

		JButton search = new JButton("Search");

		search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				//1. read selected constraint from combo box
				String soflife = (String)stage_of_life.getSelectedItem();
				String trange = (String)tracking_range.getSelectedItem();
				String gen = (String)gender.getSelectedItem();
				String tagloc = (String)tag_location.getSelectedItem();
				//2. get all shark components by tracking range

				if (soflife=="Last 24 hours") {
					System.out.println(soflife);
					updateCentralPanel(jawsApi.past24Hours());


				} else if (soflife.equals("Last Week")) {
					updateCentralPanel(jawsApi.pastWeek());


				} else if (soflife.equals("Last Month")) {
					updateCentralPanel(jawsApi.pastMonth());

				} else {
					System.out.println("Search ButtonListener Error 1 : Invalid ComboBox input");
				}
				//3. apply constraint on West panel filled with Shark Component objects

                }

        });

		add(createSouthPanel(),BorderLayout.SOUTH);
		centralpanel = createCentralPanel();
		add((centralpanel), BorderLayout.CENTER);


		mwestPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		mwestPanel.setPreferredSize(westdim);

		add(mwestPanel,BorderLayout.WEST);

		stage_of_life = new JComboBox();
		stage_of_life.addItem(" Any");
		stage_of_life.addItem(" Mature");
		stage_of_life.addItem(" Immature");
		stage_of_life.addItem(" Undetermined");

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

		mwestPanel.add(mwnorthPanel);
		mwnorthPanel.add(new JLabel("stage of life"));
		mwnorthPanel.add(stage_of_life);
		mwnorthPanel.add(new JLabel("tracking range:"));
		mwnorthPanel.add(tracking_range);
		mwnorthPanel.add(new JLabel("gender:"));
		mwnorthPanel.add(gender);
		mwnorthPanel.add(new JLabel("tag location:"));
		mwnorthPanel.add(tag_location);
		mwnorthPanel.add(search);

		pack();
	}

	/**
	 * Create and display the central element of the SearchFrame i.e. the search results.
	 * @return JPanel representing the search results.
     */
	private JPanel createCentralPanel() {
		centralpanel = new JPanel();
		centralpanel.setBorder(BorderFactory.createLineBorder(Color.black));

		centralpanel.add(new JScrollPane());
		//centralPanel.add(detailsOfFoundShark());
		centralpanel.add(new JSeparator(SwingConstants.HORIZONTAL));

		return centralpanel;
	}

	private JPanel updateCentralPanel(ArrayList<Ping> listofpings){
		for(Ping ping :listofpings) {
			centralpanel = new JPanel();
			setLayout(new GridLayout(counter, 1));
			centralpanel.add(new SharkContainer(jawsApi.getShark(ping.getName()), ping), counter);
			counter++;
			return centralpanel;
		}
	return null;
	}


	/**
	 * Create the south Panel with the acknowledgement statement.
	 * @return	JPanel the acknowledgement statement.
     */
	private JPanel createSouthPanel() {
		JPanel msPanel = new JPanel();
		msPanel.setPreferredSize(new Dimension(WIDTH,50));

		JLabel acknowledgement = new JLabel(jawsApi.getAcknowledgement());
		msPanel.add(acknowledgement);

		return msPanel;
	}

	@Override
	public void update(Observable o, Object arg) {

	}
}
