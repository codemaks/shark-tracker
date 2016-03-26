package sharkitter;

import sharkitter.controller.FunctionalityController;
import sharkitter.controller.UserController;
import sharkitter.model.FavouriteSharks;
import sharkitter.model.PingCollection;
import sharkitter.view.MenuFrame;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {

        PingCollection pingCollection = new PingCollection();

        FavouriteSharks favouriteSharks = new FavouriteSharks();

        MenuFrame frame = new MenuFrame();

        new UserController(frame, favouriteSharks);

        FunctionalityController functionalityController = new FunctionalityController(frame, favouriteSharks, pingCollection);
        frame.addFunctionalityController(functionalityController);
        frame.setFocusable(true);
        frame.setVisible(true);
    }
}
