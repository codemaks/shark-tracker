package sharkitter.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EasterEggFrame extends JFrame {

    /**
     * Instianties an EasterEgg frame from an action listener
     * @param functionalityListener an action listener
     */
    public EasterEggFrame(ActionListener functionalityListener) {
        super("Easter Egg");
        setLayout(new BorderLayout());

        addWidget(functionalityListener);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * void method which sets all visual components of the Easter Egg inside the EasterEggFrame
     * @param functionalityListener, an action listener fr the ok button
     */
    private void addWidget(ActionListener functionalityListener) {
        JLabel sharkLabel = new JLabel();

        JLabel confettiLabel = new JLabel();

        sharkLabel.setIcon(new ImageIcon(imagePanel()));
        confettiLabel.setIcon(new ImageIcon(imagePanel()));

        JLabel message = new JLabel("<html><center>Easter Egg!!!<br>You've been rick rolled ;)</center></html>");
        message.setHorizontalAlignment(SwingConstants.CENTER);

        JButton ok = new JButton("Ok");
        ok.addActionListener(functionalityListener);

        add(sharkLabel, BorderLayout.CENTER);
        add(confettiLabel,BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new GridLayout(2,1));
        southPanel.add(message);
        southPanel.add(ok);

        add(southPanel, BorderLayout.SOUTH);

        pack();
    }

    /**
     * method which returns the animated gif of the left shark
     * @return left shark animated gif image
     */
    private Image imagePanel() {
        return Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("resources/Left Shark.gif"));
    }
}
