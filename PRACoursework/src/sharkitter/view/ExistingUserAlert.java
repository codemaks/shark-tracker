package sharkitter.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExistingUserAlert extends AlertFrame {

    /**
     * Constructor of ExistingUserAlert
     */
    public ExistingUserAlert() {
        super();

        message1.setText("Sorry but your username already exists.");
        message2.setText("Please choose another username.");
    }
}
