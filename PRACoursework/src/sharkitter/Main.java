package sharkitter;

import sharkitter.controller.Controller;
import sharkitter.model.FavouriteSharks;
import sharkitter.view.MenuFrame;
import sharkitter.view.SearchFrame;

import javax.swing.*;
import java.io.File;

public class Main {

    public static void main(String[] args) {


        FavouriteSharks favouriteSharks = new FavouriteSharks();
        Controller controller = new Controller();
        controller.addModel(favouriteSharks);

        JFrame frame = new MenuFrame();
        frame.setVisible(true);
    }
}
