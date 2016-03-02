package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import api.jaws.Jaws;
import api.jaws.Ping;
import controller.SearchButtonListener;


public class SearchFrame extends JFrame {

	private Jaws jawsApi;

	private JComboBox<String> stage_of_life;
	private JComboBox<String> tracking_range;
	private JComboBox<String> gender;
	private JComboBox<String> tag_location;

	private JPanel centralpanel;
	private JScrollPane centralPane;
	private JPanel supercentralpanel;
	private int counter;
	 
	 public SearchFrame() {
		 super("Search");
		 jawsApi = new Jaws("EkZ8ZqX11ozMamO9","E7gdkwWePBYT75KE", true);
		 createWidgets();
		 setResizable(false);
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

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


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
		tag_location.addItem("Any");
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
		counter=1;


		search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				//1. read selected constraint from combo box
				String soflife = (String)stage_of_life.getSelectedItem();
				String trange = (String)tracking_range.getSelectedItem();
				String gen = (String)gender.getSelectedItem();
				String tagloc = (String)tag_location.getSelectedItem();
				//2. get all shark components by tracking range

				if (trange.equals("Last 24 Hours")) {
					updateCentralPanel(jawsApi.past24Hours());
					System.out.println(trange);

				} else if (trange.equals("Last Week")) {
					updateCentralPanel(jawsApi.pastWeek());
					System.out.println(trange);


				} else if (trange.equals("Last Month")) {
					updateCentralPanel(jawsApi.pastMonth());
					System.out.println(trange);


				} else {
					System.out.println("Search ButtonListener Error 1 : Invalid ComboBox input");
					System.out.println(trange);

				}
				//3. apply constraint on West panel filled with Shark Component objects

			}
		});

		revalidate();
		repaint();
		pack();
	}

	/**
	 * Create and display the central element of the SearchFrame i.e. the search results.
	 * @return JPanel representing the search results.
     */
	private JPanel createCentralPanel() {
		centralpanel = new JPanel();
		centralpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		supercentralpanel=new JPanel();
		centralPane = new JScrollPane(supercentralpanel);
		centralpanel.add(centralPane);

		return centralpanel;
	}

	private JPanel updateCentralPanel(ArrayList<Ping> listofpings){
		counter=(listofpings.size())-1;

		if(!listofpings.isEmpty()){

		for(Ping ping :listofpings) {
			System.out.println("Added SharkContainer for shark "+ping.getName());
			centralpanel.setLayout(new BorderLayout());
			supercentralpanel.setLayout(new GridLayout(0,1));
			supercentralpanel.add(new SharkContainer(jawsApi.getShark(ping.getName()),ping));
			supercentralpanel.add(new JSeparator(SwingConstants.HORIZONTAL));
			supercentralpanel.paintComponents(supercentralpanel.getGraphics());

			centralpanel.remove(centralPane);
			centralPane.setViewportView(supercentralpanel);
			centralpanel.add(centralPane);

			revalidate();
			repaint();
			pack();
			}
			System.out.println("Central panel updated.");
		}else{
			centralpanel.add(new JLabel("Nothing to show here :)"));
		}
		System.out.println("Central panel returned.");
		return centralpanel;
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

}
