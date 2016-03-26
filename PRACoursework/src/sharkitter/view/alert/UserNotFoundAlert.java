package sharkitter.view.alert;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserNotFoundAlert extends AlertFrame {

    /**
     * Constructor of UserNotFoundAlert
     */
    public UserNotFoundAlert() {
        super("User not found");

        addWidgets();
    }

    /**
     * Adds widget to the frame
     */
    private void addWidgets() {
        JLabel message1 = new JLabel("Sorry but your username already exists.");
        JLabel message2 = new JLabel("Please choose another username.");

        add(message1);
        add(message2);

        addButton();

        pack();
    }
}
