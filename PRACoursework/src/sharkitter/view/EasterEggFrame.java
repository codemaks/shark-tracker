package sharkitter.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EasterEggFrame extends JFrame implements ActionListener {

    public EasterEggFrame() {
        super("Easter Egg");
        setLayout(new GridLayout(2,1));

        addWidget();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void addWidget() {
        JLabel message = new JLabel("Easter Egg!!!");

        JButton ok = new JButton("Ok");
        ok.addActionListener(this);

        add(message);
        add(ok);

        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }
}
