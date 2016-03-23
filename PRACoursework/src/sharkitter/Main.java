package sharkitter;

import sharkitter.controller.UserController;
import sharkitter.model.FavouriteSharks;
import sharkitter.view.MenuFrame;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        FavouriteSharks favouriteSharks = new FavouriteSharks();

        MenuFrame frame = new MenuFrame();

        UserController controller = new UserController(frame, favouriteSharks);
        frame.setVisible(true);
    }
}
