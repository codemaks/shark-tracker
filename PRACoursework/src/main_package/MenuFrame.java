package main_package;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuFrame extends JFrame implements ActionListener {
	private JTextField searchField;
	private JButton favouritesButton;

	public MenuFrame() {
		super();
		setTitle("Amnity Police");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWidgets();

	}

	public void addWidgets() {
		JPanel sPanel = new JPanel();
		sPanel.setLayout(new BorderLayout());
		searchField = new JTextField("Search");
		searchField.setHorizontalAlignment(JTextField.CENTER);
		searchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField) e.getSource();
				System.out.println("You Pressed Enter");
				tf.setText("");
			}
		});
		favouritesButton = new JButton("Favourites");
		favouritesButton.setEnabled(false);
		sPanel.add(searchField, BorderLayout.NORTH);
		sPanel.add(favouritesButton, BorderLayout.SOUTH);

		ImageIcon shark = new ImageIcon("shark9.png");
		Image img = shark.getImage();
		Image newimg = img.getScaledInstance(230, 150, java.awt.Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newimg);

		JLabel sharkTrackerLabel = new JLabel("Shark Tracker", newIcon, 0);
		sharkTrackerLabel.setVerticalTextPosition(JLabel.BOTTOM);
		sharkTrackerLabel.setHorizontalTextPosition(JLabel.CENTER);
		Font font = new Font("Monospace", Font.ITALIC, 30);
		sharkTrackerLabel.setFont(font);

		add(sPanel, BorderLayout.SOUTH);
		add(sharkTrackerLabel, BorderLayout.CENTER);

		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String args[]) {
		JFrame frame = new MenuFrame();
		frame.setVisible(true);
	}

}
