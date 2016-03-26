package sharkitter.controller;

import sharkitter.model.FavouriteSharks;
import sharkitter.view.search.SharkContainer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * CLass controlling the addition and removal of sharks in the favourite lists from the search panel.
 */
public class FavouriteController implements ActionListener {

    private SharkContainer sharkContainer;
    private FavouriteSharks favouriteSharks;

    /**
     * Constructor for FavouriteController
     * @param sharkContainer    The tracked SharkContainer view
     */
    public FavouriteController(SharkContainer sharkContainer, FavouriteSharks favouriteSharks) {
        this.sharkContainer = sharkContainer;
        this.favouriteSharks = favouriteSharks;

        updateFavouriteButton();
    }

    /**
     * Update the favourite button to "Following" if the shark is already in the favourites
     */
    private void updateFavouriteButton() {
        if(favouriteSharks.getFavouriteSharks().isEmpty())
            return;
        for(String sharkName : favouriteSharks.getFavouriteSharks()) {
            if (sharkName.equals(sharkContainer.getName())) {
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
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                sharkContainer.updateFollowButton("Follow");
        }
    }
}
