package sharkitter.view;

import sharkitter.model.FavouriteSharks;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuFrame extends JFrame implements ActionListener, WindowListener {
	//private JTextField searchField;
	private JButton searchButton;
	private JButton favouritesButton;
	private JButton statisticsButton;
	private SearchFrame searchframe;
	private StatisticsFrame statisticsFrame;
	private FavouriteSharks favouriteSharks;

	public MenuFrame(FavouriteSharks favouriteSharks) {
		super("Amnity Police");
	//	centreWindow(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWidgets();
	}

	/*public static void centreWindow(Window frame) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) /3);
		int y = (int) ((dimension.getHeight() - frame.getHeight())/4 );
		frame.setLocation(x, y);
	}*/

	public void addWidgets() {
		JPanel sPanel = new JPanel();
		sPanel.setLayout(new GridLayout(0,1));

		//searchField = new JTextField("Search");
		//searchField.setHorizontalAlignment(JTextField.CENTER);
		//searchField.addActionListener(new ActionListener() {

		searchButton = new JButton("Search");
		searchButton.setHorizontalAlignment(JButton.CENTER);
		searchButton.addActionListener(this);

		statisticsButton = new JButton("Statistics");
		statisticsButton.setHorizontalAlignment(JButton.CENTER);
		statisticsButton.addActionListener(this);

/*		searchButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField) e.getSource();
				System.out.println("You Pressed Enter");
				tf.setText("");
			}
		});*/

		favouritesButton = new JButton("Favourites");
		favouritesButton.setEnabled(false);

		//sPanel.add(searchField, BorderLayout.NORTH);
		sPanel.add(searchButton);
		sPanel.add(favouritesButton);
		sPanel.add(statisticsButton);

		ImageIcon shark = new ImageIcon(getClass().getClassLoader().getResource("resources/SharkTracker.png"));
		Image img = shark.getImage();
		Image newImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		shark = new ImageIcon(newImg);

		JLabel sharkTrackerLabel = new JLabel("", shark, 0);
		sharkTrackerLabel.setVerticalTextPosition(JLabel.BOTTOM);
		sharkTrackerLabel.setHorizontalTextPosition(JLabel.CENTER);
		//Font font = new Font("Monospace", Font.ITALIC, 30);
		//sharkTrackerLabel.setFont(font);

		add(sPanel, BorderLayout.SOUTH);
		add(sharkTrackerLabel, BorderLayout.CENTER);

		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == searchButton) {
			setVisible(false);
			searchframe = new SearchFrame(favouriteSharks);
			searchframe.addWindowListener(this);
			searchframe.setVisible(true);
		}else if(e.getSource() == statisticsButton){
			setVisible(false);
			statisticsFrame = new StatisticsFrame();
			statisticsFrame.addWindowListener(this);
			statisticsFrame.setVisible(true);
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {

	}

	@Override
	public void windowClosed(WindowEvent e) {
		setVisible(true);
	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}
}
