package sharkitter.controller;

import api.jaws.Jaws;
import sharkitter.model.FavouriteSharks;
import sharkitter.view.*;
import sharkitter.view.alert.ExistingUserAlert;
import sharkitter.view.alert.UserNotFoundAlert;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class UserController implements ActionListener {

    private ProfileCreationFrame accountCreation;
    private FavouriteSharks favouriteSharks;
    private MenuFrame menuFrame;
    private FunctionalityController functionalityController;

    private Jaws api;

    private String username;

    private static final Path PATH_TO_PROFILES = Paths.get("data/list_of_profiles.txt");

    /**
     * Constructor of UserController
     * @param menuFrame   Frame used by user to connect
     * @param favouriteSharks   Model of favourite sharks
     */
    public UserController(MenuFrame menuFrame, FavouriteSharks favouriteSharks) throws IOException {
        this.menuFrame = menuFrame;
        this.menuFrame.addUserController(this);

        this.favouriteSharks = favouriteSharks;

        api = new Jaws("EkZ8ZqX11ozMamO9", "E7gdkwWePBYT75KE", true);

        readProfiles();
        loadDefaultProfile();

        functionalityController = new FunctionalityController(menuFrame, favouriteSharks);
        this.menuFrame.addFunctionalityController(functionalityController);
        this.menuFrame.setFocusable(true);
    }

    /**
     * Action performed if event is triggered
     * @param e ActionEvent, in this case, pressed buttons
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().getClass().equals(JMenuItem.class)) {

            if (((JMenuItem) e.getSource()).getText().equals("Create Profile")) {
                accountCreation = new ProfileCreationFrame(this);
                accountCreation.setVisible(true);
            } else {
                username = ((JMenuItem) e.getSource()).getText();
                connectUser(username);
            }

        } else if (e.getSource().getClass().equals(JButton.class)) {

            if (((JButton) e.getSource()).getText().equals("Register")) {

                username = accountCreation.getUsername();

                if(!username.equals("")) {

                    List<String> userList = Arrays.asList(username);
                    try {
                        File newProfile = new File("data/" + username + ".txt");

                        if (newProfile.createNewFile()) {
                            Files.write(PATH_TO_PROFILES, userList, StandardOpenOption.APPEND);
                            favouriteSharks.clearData();
                            favouriteSharks.setUser(username);
                            menuFrame.addProfile(username);
                            accountCreation.dispose();
                        } else {
                            ExistingUserAlert existingUser = new ExistingUserAlert();
                            existingUser.setVisible(true);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

            }
        }
    }

    private void readProfiles() throws IOException {
        Path pathToProfile = Paths.get("data/list_of_profiles.txt");
        Scanner reader = new Scanner(pathToProfile);
        reader.useDelimiter("\n");

        while (reader.hasNext()) {
            String profileName = reader.next();
            menuFrame.loadProfile(profileName);
        }
    }

    private void loadDefaultProfile() {
        try {
            favouriteSharks.setUser("default");
            favouriteSharks.clearData();

            Path pathToFile = Paths.get("data/default.txt");
            Scanner reader = new Scanner(pathToFile);
            reader.useDelimiter("\n");

            if(!reader.hasNext()) {
                menuFrame.disableFavourites();
            }

            while (reader.hasNext()) {
                String sharkName = reader.next();
                favouriteSharks.loadSharks(api.getShark(sharkName));
            }
        } catch (FileNotFoundException e1) {
            UserNotFoundAlert userNotFound = new UserNotFoundAlert();
            userNotFound.setVisible(true);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    private void connectUser(String user) {
        UserNotFoundAlert userNotFound = new UserNotFoundAlert();

        if(!username.equals("")) {
            try {
                favouriteSharks.clearData();
                favouriteSharks.setUser(user);

                Path pathToFile = Paths.get("data/" + user + ".txt");
                Scanner reader = new Scanner(pathToFile);
                reader.useDelimiter("\n");

                if(!reader.hasNext()) {
                    menuFrame.disableFavourites();
                }

                while (reader.hasNext()) {
                    String sharkName = reader.next();
                    favouriteSharks.loadSharks(api.getShark(sharkName));
                }
            } catch (FileNotFoundException e1) {
                userNotFound.setVisible(true);
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
