package sharkitter.controller;

import sharkitter.model.FavouriteSharks;
import sharkitter.view.SharkContainer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FavouriteButtonListener implements ActionListener {

    private SharkContainer sharkContainer;
    private FavouriteSharks favouriteSharks;

    /**
     * Constructor for FavouriteButtonListener
     * @param sharkContainer    The tracked SharkContainer view
     */
    public FavouriteButtonListener(SharkContainer sharkContainer, FavouriteSharks favouriteSharks) {
        this.sharkContainer = sharkContainer;
        this.favouriteSharks = favouriteSharks;
    }


    /**
     * Actions to perform to handle the event when "Follow" button is pressed
     * @param e The action triggered by pressing the button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton followButton = (JButton) e.getSource();
        if(followButton.getText().equals("Follow")) {
            favouriteSharks.addShark(sharkContainer.getShark());
            updateContainerButton(followButton, "Following");
        }
        if(followButton.getText().equals("Following")) {
            favouriteSharks.removeShark(sharkContainer.getShark());
            updateContainerButton(followButton, "Follow");
        }

//        For debugging purposes:
//        System.out.println("Pressed");
    }

    /**
     * Updates a button with the corresponding text
     * @param button    JButton to update
     * @param text  New text for the JButton
     */
    private void updateContainerButton(JButton button, String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                button.setText(text);
            }
        });
    }
}
