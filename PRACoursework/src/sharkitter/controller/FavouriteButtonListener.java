package sharkitter.controller;

import api.jaws.Shark;
import sharkitter.model.FavouriteSharks;
import sharkitter.view.SharkContainer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

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

        updateFavouriteButton();
    }

    private void updateFavouriteButton() {
        for(Shark shark : favouriteSharks.getFavouriteSharks()) {
            if (shark.getName().equals(sharkContainer.getName())) {
                sharkContainer.updateFollowButton("Following");
            }
        }
    }


    /**
     * Actions to perform to handle the event when "Follow" button is pressed
     * @param e The action triggered by pressing the button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton followButton = (JButton) e.getSource();
        String buttonText = followButton.getText();

        switch (buttonText) {
            case "Follow":
                favouriteSharks.addShark(sharkContainer.getShark());
                sharkContainer.updateFollowButton("Following");
                break;

            case "Following":
                try {
                    favouriteSharks.removeShark(sharkContainer.getShark());
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                sharkContainer.updateFollowButton("Follow");
        }

//        For debugging purposes:
//        System.out.println("Pressed");
    }
}
