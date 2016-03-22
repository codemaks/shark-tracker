package sharkitter;

import sharkitter.model.FavouriteSharks;
import sharkitter.view.ConnectionFrame;
import sharkitter.view.MenuFrame;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        FavouriteSharks favouriteSharks = new FavouriteSharks();

        JFrame frame = new ConnectionFrame(favouriteSharks);
        frame.setVisible(true);
    }
}
