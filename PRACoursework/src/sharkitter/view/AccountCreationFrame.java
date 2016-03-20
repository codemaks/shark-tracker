package sharkitter.view;

import sharkitter.controller.UserController;

import javax.swing.*;
import java.awt.*;

public class AccountCreationFrame extends JFrame {

    private UserController userController;
    private JTextField usernameField;

    /**
     * Constructor for AccountCreationFrame
     * @param userController    Controller of the user interface log/sign in
     */
    public AccountCreationFrame(UserController userController) {
        super("Sign in");

        this.userController = userController;

        addWidgets();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * Create widgets to add to the frame
     */
    private void addWidgets() {
        JPanel northPanel = new JPanel(new BorderLayout());
        JPanel sPanel = new JPanel(new GridLayout(5,1));

        JPanel backPanel = new JPanel(new BorderLayout());

        JButton backButton = new JButton("<-");
        backButton.addActionListener(userController);
        JLabel backLabel = new JLabel(" Back");

        backPanel.add(backButton, BorderLayout.WEST);
        backPanel.add(backLabel, BorderLayout.CENTER);

        northPanel.add(backPanel, BorderLayout.WEST);

        JLabel usernameLabel = new JLabel("Username: ");

        usernameField = new JTextField();
        JPasswordField pwdField = new JPasswordField();
        JButton enter = new JButton("Register");
        enter.addActionListener(userController);

        sPanel.add(usernameLabel);
        sPanel.add(usernameField);
        
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
        add(northPanel, BorderLayout.NORTH);

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
