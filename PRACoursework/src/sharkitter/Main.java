package sharkitter;

import sharkitter.model.FavouriteSharks;
import sharkitter.view.MenuFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        FavouriteSharks favouriteSharks = new FavouriteSharks();

        JFrame frame = new MenuFrame(favouriteSharks);
        frame.setVisible(true);
    }
}
