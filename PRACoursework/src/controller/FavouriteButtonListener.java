package controller;

import api.jaws.Jaws;
import model.FavouriteSharks;
import view.SharkContainer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FavouriteButtonListener implements ActionListener {
    private SharkContainer sharkContainer;
    private FavouriteSharks favouriteSharks;
    private Jaws jawsApi;

    public FavouriteButtonListener(SharkContainer sharkContainer, FavouriteSharks favouriteSharks) {
        this.sharkContainer = sharkContainer;
        this.favouriteSharks = favouriteSharks;
        jawsApi = new Jaws("EkZ8ZqX11ozMamO9","E7gdkwWePBYT75KE", true);
    }


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
