package sharkitter.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EasterEggFrame extends JFrame {

    /**
     * Constructor of EasterEggFrame
     * @param functionalityListener ActionListener and WindowListener observing the frame
     */
    public EasterEggFrame(ActionListener functionalityListener) {
        super("Easter Egg");
        setLayout(new BorderLayout());

        addWidget(functionalityListener);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * Adds widget to the frame
     * @param functionalityListener ActionListener for the "Ok" button
     */
    private void addWidget(ActionListener functionalityListener) {
        JLabel sharkTrackerLabel = new JLabel();
        sharkTrackerLabel.setVerticalTextPosition(JLabel.BOTTOM);
        sharkTrackerLabel.setHorizontalTextPosition(JLabel.CENTER);
        sharkTrackerLabel.setIcon(new ImageIcon(imagePanel()));

        JLabel message = new JLabel("<html><center>Easter Egg!!!<br>You've been rick rolled ;)</center></html>");
        message.setHorizontalAlignment(SwingConstants.CENTER);

        JButton ok = new JButton("Ok");
        ok.addActionListener(functionalityListener);

        add(sharkTrackerLabel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new GridLayout(2,1));
        southPanel.add(message);
        southPanel.add(ok);

        add(southPanel, BorderLayout.SOUTH);

        pack();
    }

    /**
     * Creates the image to be added to the frame
     * @return  IMage to be added to the frame
     */
    private Image imagePanel() {
        return Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("resources/Left Shark.gif"));
    }
}
