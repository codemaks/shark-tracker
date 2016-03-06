package sharkitter.controller;

import sharkitter.model.FavouriteSharks;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener{

    protected FavouriteSharks favouriteSharks;

    public Controller() {

    }

    public void addModel(FavouriteSharks favouriteSharks) {
        this.favouriteSharks = favouriteSharks;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
