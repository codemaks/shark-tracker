package main_package;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class SearchFrame extends JFrame implements ActionListener{
	
		 int k = 3;
		 JComboBox jcbstage_of_life = new JComboBox();
		 JComboBox jcbtracking_range = new JComboBox();
		 JComboBox jcbgender = new JComboBox();
		 JComboBox jcbtag_location = new JComboBox();
		 JButton search;
		 JPanel mPanel = new JPanel(new BorderLayout());//main panel
		 JPanel msPanel = new JPanel();//main south Panel (by opposition to the main panel)
		 JPanel mwestPanel = new JPanel(new BorderLayout());//west Panel in main panel
		 JPanel mwnorthPanel = new JPanel(new GridLayout(10,1));//north Panel in west panel which is in the main panel
		 private Dimension westdim = new Dimension (300,this.getHeight());
		 JPanel mcenterPanel = new JPanel(new GridLayout(k,1));//center Panel in main panel
	 
	 public SearchFrame() {
		 
		 super("Search");
		 search = new JButton("Search");
		 
		 search.addActionListener(new ActionListener(){
			 //anonymous inner class...
			@Override
			public void actionPerformed(ActionEvent e){
						//1. read selected constraint from combo box
					
						//2. get all shark components
						
						//3. apply constraint on West panel filled with Shark Component objects
						
				 }
			 
		 });
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		 this.setLayout(new BorderLayout());
		 this.add(msPanel,BorderLayout.SOUTH);
		 msPanel.setPreferredSize(new Dimension(WIDTH,50));
		 msPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		 this.setPreferredSize(new Dimension(1200,700));
		 this.add(mPanel);
		 mPanel.add(mcenterPanel);
		 mcenterPanel.add(new JScrollPane(new JTextArea()));
		  
		 mwestPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		 mwestPanel.setPreferredSize(westdim);
		 
		 mPanel.add(mwestPanel,BorderLayout.WEST);
		 // The commented code below was used to test a very simplistic version of the west side of the frame.
		 // You are welcome to uncomment it if necessary.
//		 mwestPanel.add(mwnorthPanel);
//		 mwnorthPanel.add(new JLabel("stage of life"));
//		 mwnorthPanel.add(jcbstage_of_life);
//		 mwnorthPanel.add(new JLabel("tracking range:"));
//		 mwnorthPanel.add(jcbtracking_range);
//		 mwnorthPanel.add(new JLabel("gender:"));
//		 mwnorthPanel.add(jcbgender);
//		 mwnorthPanel.add(new JLabel("tag location:"));
//		 mwnorthPanel.add(jcbtag_location);
//		 mwnorthPanel.add(search);
		
		 this.pack();
	 }
	 	
		 public static void main(String[] args) {
		JFrame frame = new SearchFrame();
		frame.setVisible(true);
	 }

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
}
