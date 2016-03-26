package sharkitter.view.alert;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AlertFrame extends JFrame implements ActionListener {

    /**
     * Constructor of a frame of type "Alert"
     * @param title Title given to the frame
     */
    protected AlertFrame(String title) {
        super(title);
        setLayout(new GridLayout(3,1));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * Adds an "Ok" button to the frame
     */
    protected void addButton() {
        JButton ok = new JButton("Ok");
        ok.addActionListener(this);
        add(ok);
    }

    @Override
    /**
     * Disposes the frame when "Ok" button is pressed
     */
    public void actionPerformed(ActionEvent e) {
        dispose();
    }
}
