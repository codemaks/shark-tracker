package sharkitter.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlertFrame extends JFrame implements ActionListener {

    protected JLabel message1;
    protected JLabel message2;

    public AlertFrame () {
        super();

        addWidgets();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void addWidgets() {
        message1 = new JLabel();
        message2 = new JLabel();

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
