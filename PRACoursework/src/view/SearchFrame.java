package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import model.API_explorer;


public class SearchFrame extends JFrame {

	private API_explorer apiexplorer;

	private JComboBox<String> stage_of_life;
	private JComboBox<String> tracking_range;
	private JComboBox<String> gender;
	private JComboBox<String> tag_location;
	 
	 public SearchFrame() {
		 super("Search");
		 apiexplorer = new API_explorer();
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
						stage_of_life.getSelectedItem();
			   			tracking_range.getSelectedItem();
			   			gender.getSelectedItem();
			   			tag_location.getSelectedItem();
                       //2. get all shark components
						apiexplorer.getAllSharks();
                       //3. apply constraint on West panel filled with Shark Component objects

                }

        });
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		this.add(createSouthPanel(),BorderLayout.SOUTH);
		this.add(createCentralPanel(), BorderLayout.CENTER);


		mwestPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		mwestPanel.setPreferredSize(westdim);

		this.add(mwestPanel,BorderLayout.WEST);
		//The commented code below was used to test a very simplistic version of the west side of the frame.
		// You are welcome to uncomment it if necessary.
		JComboBox jcbstage_of_life = new JComboBox();
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
		mwnorthPanel.add(search);

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
		//centralPanel.add(detailsOfFoundShark());
		centralPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

		return centralPanel;
	}


	/**
	 * Create the south Panel with the acknowledgement statement.
	 * @return	A JPanel the acknowledgement statement.
     */
	private JPanel createSouthPanel() {
		JPanel msPanel = new JPanel();
		msPanel.setPreferredSize(new Dimension(WIDTH,50));

		JLabel acknowledgement = new JLabel(apiexplorer.getAcknowledgement());
		msPanel.add(acknowledgement);

		return msPanel;
	}

}
