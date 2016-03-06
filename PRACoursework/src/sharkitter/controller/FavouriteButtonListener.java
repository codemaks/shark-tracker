package sharkitter.controller;

import api.jaws.Jaws;

import sharkitter.view.SharkContainer;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class FavouriteButtonListener extends Controller {

    private SharkContainer sharkContainer;
    private Jaws jawsApi;

    /**
     * Constructor for FavouriteButtonListener
     * @param sharkContainer    The tracked SharkContainer view
     */
    public FavouriteButtonListener(SharkContainer sharkContainer) {
        super();
        this.sharkContainer = sharkContainer;
        jawsApi = new Jaws("EkZ8ZqX11ozMamO9","E7gdkwWePBYT75KE", true);
    }


    /**
     * Actions to perform to handle the event when "Follow" button is pressed
     * @param e The action triggered by pressing the button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton followButton = (JButton) e.getSource();
        if(followButton.getText().equals("Follow")) {
            favouriteSharks.addShark(jawsApi.getShark(sharkContainer.getName()));
            followButton.setText("Unfollow");
        }
        if(followButton.getText().equals("Unfollow")) {
            favouriteSharks.removeShark(jawsApi.getShark(sharkContainer.getName()));
            followButton.setText("Follow");
        }
    }
}
