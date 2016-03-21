package sharkitter.view;

import sharkitter.controller.UserController;
import sharkitter.model.FavouriteSharks;

import javax.swing.*;
import java.awt.*;

public class ConnectionFrame extends JFrame {

    private UserController userController;
    private JTextField usernameField;
    private FavouriteSharks favouriteSharks;

    /**
     * Constructor of ConnectionFrame
     * @param favouriteSharks   Model of favourite sharks
     */
    public ConnectionFrame(FavouriteSharks favouriteSharks) {
        super("Login");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        userController = new UserController(this, favouriteSharks);
        this.favouriteSharks = favouriteSharks;

        addWidgets();
    }

    /**
     * Create widgets to add to the frame
     */
    private void addWidgets() {
        JPanel sPanel = new JPanel(new GridLayout(6,1));

        JLabel usernameLabel = new JLabel("Username: ");

        usernameField = new JTextField();
        usernameField.addKeyListener(userController);
        usernameField.setFocusable(true);

        JButton enter = new JButton("Enter");
        enter.addActionListener(userController);

        sPanel.add(usernameLabel);
        sPanel.add(usernameField);

        sPanel.add(enter);

        JLabel optionLabel = new JLabel("Or: ");
        sPanel.add(optionLabel);

        JButton newUser = new JButton("Create new account");
        newUser.addActionListener(userController);

        sPanel.add(newUser);

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

    /**
     * Getter of username
     * @return Username entered
     */
    public String getUsername() {
        return usernameField.getText();
    }
}
