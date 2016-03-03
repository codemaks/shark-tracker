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
import sharkitter.controller.SearchButtonListener;


public class SearchFrame extends JFrame implements Observer {

	private Jaws jawsApi;

	private JComboBox<String> stage_of_life;
	private JComboBox<String> tracking_range;
	private JComboBox<String> gender;
	private JComboBox<String> tag_location;

	private JPanel centralpanel;
    private JPanel mWestPanel;
    private JScrollPane centralPane;
    private JPanel supercentralpanel;

    private JButton search;

	private int counter;

    private SearchButtonListener sbl;

    public SearchFrame() {
        super("Search");
        jawsApi = new Jaws("EkZ8ZqX11ozMamO9", "E7gdkwWePBYT75KE", true);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, 700));
        createPanels();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

	/**
	 * Create and display the widgets on the main Frame
	 */
	private void createPanels() {
        createCentralPanel();
        createWestPanel();
        createWSouthPanel();

        createSearchButton();
        createComboBoxes();

        createWNorthPanel();
        createWSouthPanel();

		pack();
	}

    /**
     * Creates the combo boxes.
     */
    private void createComboBoxes() {
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
        for(String tagLoc: jawsApi.getTagLocations()){
            tag_location.addItem(tagLoc);
        }
    }

    /**
     * Creates search button.
     */
    private void createSearchButton() {
        search = new JButton("Search");

        sbl = new SearchButtonListener(this);
        search.addActionListener(sbl);
                /*new ActionListener() {
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
        });*/
    }

	/**
	 * Create and display the central element of the SearchFrame i.e. the search results.
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
     * Creates the west panel within the main panel.
     */
    private void createWestPanel() {
        mWestPanel = new JPanel(new BorderLayout());//west Panel in main panel

        mWestPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        mWestPanel.setPreferredSize(new Dimension(300, this.getHeight()));

        add(mWestPanel,BorderLayout.WEST);
    }

    /**
     * Creates the north panel within the west panel and adds it to the west panel.
     */
    private void createWNorthPanel() {
        JPanel mwNorthPanel = new JPanel(new GridLayout(10, 1));

        mwNorthPanel.add(new JLabel("Stage of life"));
        mwNorthPanel.add(stage_of_life);
        mwNorthPanel.add(new JLabel("Tracking range:"));
        mwNorthPanel.add(tracking_range);
        mwNorthPanel.add(new JLabel("Gender:"));
        mwNorthPanel.add(gender);
        mwNorthPanel.add(new JLabel("Tag location:"));
        mwNorthPanel.add(tag_location);
        mwNorthPanel.add(search);

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

        JLabel sharkIcon = new JLabel("", shark, 0);
        mwSouthPanel.add(sharkIcon);
        mWestPanel.add(mwSouthPanel, BorderLayout.SOUTH);
    }

	@Override
	public void update(Observable o, Object arg) {

	}
}
