package sharkitter.controller;

import api.jaws.Jaws;

import sharkitter.api.JawsApi;
import sharkitter.model.FavouriteSharks;
import sharkitter.view.*;
import sharkitter.view.alert.ExistingUserAlert;
import sharkitter.view.alert.UserNotFoundAlert;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Controller of user profiles:
 * - Loads default profile
 * - Creates and loads specific profiles
 */
public class UserController implements ActionListener, KeyListener {

    private ProfileCreationFrame accountCreation;
    private FavouriteSharks favouriteSharks;
    private MenuFrame menuFrame;

    private Jaws api;

    private String username;

    private static final Path PATH_TO_PROFILES = Paths.get("data/list_of_profiles.txt");
    private static final Pattern DELIMITER = Pattern.compile("\\r\\n|\\n");

    /**
     * Constructor of UserController.
     * @param menuFrame the menu frame.
     * @param favouriteSharks model of favourite sharks.
     */
    public UserController(MenuFrame menuFrame, FavouriteSharks favouriteSharks) throws IOException {
        this.menuFrame = menuFrame;
        this.menuFrame.addUserController(this);

        this.favouriteSharks = favouriteSharks;

        api = JawsApi.getInstance();

        createDataFolder();

        readProfiles();
        loadDefaultProfile();
    }

    /**
     * Actions performed if event is triggered
     * @param e an ActionEvent, in this case, pressed buttons or menu selection.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().getClass().equals(JMenuItem.class)) {
            String text = ((JMenuItem) e.getSource()).getText();

            if (text.equals("Create Profile")) {
                accountCreation = new ProfileCreationFrame(this);
                accountCreation.setVisible(true);
            } else {
                username = text;
                loadUser(username);
            }

        } else if (e.getSource().getClass().equals(JButton.class)) {
            String text = ((JButton) e.getSource()).getText();

            if (text.equals("Register")) {
                registerUser();
            }
        }
    }

    /**
     * Creates the "data/" folder if it does not exist.
     * Also creates the list of profiles if it does not exist.
     * @throws IOException if the file or folder is not found.
     */
    private void createDataFolder() throws IOException {
        File dataFolder = Paths.get("data/").toFile();
        if(!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        if(!PATH_TO_PROFILES.toFile().createNewFile()) {
            new File(PATH_TO_PROFILES.toString());
        }
    }

    /**
     * Reads existing profiles and updates the view accordingly.
     * @throws IOException if profiles are not found.
     */
    private void readProfiles() throws IOException {
        Scanner reader = createScanner(PATH_TO_PROFILES);

        while (reader.hasNext()) {
            String profileName = reader.next();
            menuFrame.addProfile(profileName);
        }
    }

    /**
     * Loads the default profile and any data associated with it.
     */
    private void loadDefaultProfile() {
        try {
            favouriteSharks.setUser("default");
            favouriteSharks.clearData();

            Path pathToFile = Paths.get("data/default.txt");

            if(!pathToFile.toFile().createNewFile()) {
                new File(pathToFile.toString());
            }

            Scanner reader = createScanner(pathToFile);

            if(!reader.hasNext()) {
                menuFrame.toggleFavourites(false);
            }

            while (reader.hasNext()) {
                String sharkName = reader.next();
                favouriteSharks.loadSharks(api.getShark(sharkName));
            }
        } catch (FileNotFoundException e1) {
            UserNotFoundAlert userNotFound = new UserNotFoundAlert();
            userNotFound.setVisible(true);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    /**
     * Loads specific user.
     * @param user a String representation of a user.
     */
    private void loadUser(String user) {
        UserNotFoundAlert userNotFound = new UserNotFoundAlert();

        if(!username.equals("")) {
            try {
                favouriteSharks.clearData();
                favouriteSharks.setUser(user);

                Path pathToFile = Paths.get("data/" + user + ".txt");
                Scanner reader = createScanner(pathToFile);

                if(!reader.hasNext()) {
                    menuFrame.toggleFavourites(false);
                }

                while (reader.hasNext()) {
                    String sharkName = reader.next();
                    favouriteSharks.loadSharks(api.getShark(sharkName));
                }
            } catch (FileNotFoundException e1) {
                userNotFound.setVisible(true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Registers entered user and updates the model and the view accordingly.
     */
    private void registerUser() {
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

    /**
     * Creates a Scanner to read the specified path.
     * @param pathToFile the path to the file to be read.
     * @return  the created Scanner.
     * @throws IOException if the file was not found.
     */
    private Scanner createScanner(Path pathToFile) throws IOException {
        Scanner reader = new Scanner(pathToFile);
        reader.useDelimiter(DELIMITER);
        return reader;
    }

    /**
     * Reacts to the "Enter" key being pressed.
     * @param e the "Enter" key being pressed.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 10)
            registerUser();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
