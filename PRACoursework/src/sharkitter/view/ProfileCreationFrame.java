package sharkitter.view;

import sharkitter.controller.UserController;

import javax.swing.*;
import java.awt.*;

public class ProfileCreationFrame extends JFrame {

    private UserController userController;
    private JTextField usernameField;

    /**
     * Constructor for ProfileCreationFrame
     * @param userController    Controller of the user interface log/sign in
     */
    public ProfileCreationFrame(UserController userController) {
        super("Create new profile");
        setLayout(new GridLayout(3,1));
        setPreferredSize(new Dimension(400,100));

        this.userController = userController;

        addWidgets();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * Create widgets to add to the frame
     */
    private void addWidgets() {
        JLabel usernameLabel = new JLabel("Username: ");

        usernameField = new JTextField();
        usernameField.setFocusable(true);
        usernameField.addKeyListener(userController);

        JButton enter = new JButton("Register");
        enter.addActionListener(userController);

        add(usernameLabel);
        add(usernameField);
        add(enter);

        pack();
    }

    /**
     * Getter of the username
     * @return  Username entered
     */
    public String getUsername() {
        return usernameField.getText();
    }
}
