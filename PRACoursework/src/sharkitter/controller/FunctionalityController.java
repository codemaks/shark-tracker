package sharkitter.controller;

import sharkitter.model.FavouriteSharks;
import sharkitter.view.MenuFrame;
import sharkitter.view.SearchFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FunctionalityController implements ActionListener {

    private MenuFrame menuFrame;
    private SearchFrame searchFrame;

    private FavouriteSharks favouriteSharks;

    public FunctionalityController(MenuFrame menuFrame, FavouriteSharks favouriteSharks) {
        this.menuFrame = menuFrame;
        this.favouriteSharks = favouriteSharks;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonName = ((JButton) e.getSource()).getText();

        switch(buttonName) {
            case "Search":
                menuFrame.setVisible(false);
                searchFrame = new SearchFrame(favouriteSharks);
                searchFrame.setVisible(true);
                break;
            case "Favourites":
                break;
        }
    }
}
