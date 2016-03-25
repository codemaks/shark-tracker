package sharkitter.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EasterEggFrame extends JFrame {

    public EasterEggFrame(ActionListener functionalityListener) {
        super("Easter Egg");
        setLayout(new BorderLayout());

        addWidget(functionalityListener);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

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

    private Image imagePanel() {
        return Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("resources/Left Shark.gif"));
    }
}
