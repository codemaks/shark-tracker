package sharkitter;

import api.jaws.Jaws;
import api.jaws.Location;
import api.jaws.Shark;
import sharkitter.controller.FunctionalityController;
import sharkitter.controller.UserController;
import sharkitter.model.FavouriteSharks;
import sharkitter.view.MenuFrame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws IOException {

        FavouriteSharks favouriteSharks = new FavouriteSharks();

        MenuFrame frame = new MenuFrame();

        Jaws api = new Jaws("EkZ8ZqX11ozMamO9", "E7gdkwWePBYT75KE", true);

        frame.setJaws(api);

        UserController controller = new UserController(frame, favouriteSharks, api);

        FunctionalityController functionalityController = new FunctionalityController(frame, favouriteSharks, api);
        frame.addFunctionalityController(functionalityController);
        frame.setFocusable(true);
        frame.setVisible(true);
    }
}
