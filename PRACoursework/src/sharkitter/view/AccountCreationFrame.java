package sharkitter.view;

import sharkitter.controller.UserController;

import javax.swing.*;
import java.awt.*;

public class AccountCreationFrame extends JFrame{

    private UserController userController;
    private JTextField usernameField;

    public AccountCreationFrame(UserController userController) {
        super("Sign in");

        addWidgets();

        this.userController = userController;
    }

    private void addWidgets() {
        JPanel sPanel = new JPanel(new GridLayout(5,1));

        JLabel usernameLabel = new JLabel("Username: ");

        usernameField = new JTextField();
        JPasswordField pwdField = new JPasswordField();
        JButton enter = new JButton("Register");
        enter.addActionListener(userController);

        sPanel.add(usernameLabel);
        sPanel.add(usernameField);

        sPanel.add(pwdField);
        sPanel.add(enter);

        ImageIcon shark = new ImageIcon(getClass().getClassLoader().getResource("resources/SharkTracker.png"));
        Image img = shark.getImage();
        Image newImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        shark = new ImageIcon(newImg);

        JLabel sharkTrackerLabel = new JLabel("", shark, 0);
        sharkTrackerLabel.setVerticalTextPosition(JLabel.BOTTOM);
        sharkTrackerLabel.setHorizontalTextPosition(JLabel.CENTER);

        add(sPanel, BorderLayout.SOUTH);
        add(sharkTrackerLabel, BorderLayout.CENTER);

        pack();
    }

    public String getUsername() {
        return usernameField.getText();
    }
}
