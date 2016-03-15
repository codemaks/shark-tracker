package sharkitter.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlertWindow extends JFrame implements ActionListener {

    public AlertWindow(String title) {
        super(title);

        JLabel message1 = new JLabel("Sorry but your username wasn't recognised.");
        JLabel message2 = new JLabel("Please check your spelling or create a new account.");

        JButton ok = new JButton("Ok");
        ok.addActionListener(this);

        add(message1);
        add(message2);
        add(ok);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }
}
