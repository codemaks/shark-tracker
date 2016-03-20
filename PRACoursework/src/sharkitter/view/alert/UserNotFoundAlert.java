package sharkitter.view.alert;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserNotFoundAlert extends JFrame implements ActionListener {

    /**
     * Constructor of UserNotFoundAlert
     */
    public UserNotFoundAlert() {
        super("User not found");
        setLayout(new GridLayout(3, 1));
        addWidgets();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private void addWidgets() {
        JLabel message1 = new JLabel("Sorry but your username already exists.");
        JLabel message2 = new JLabel("Please choose another username.");

        JButton ok = new JButton("Ok");
        ok.addActionListener(this);

        add(message1);
        add(message2);
        add(ok);

        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }
}
