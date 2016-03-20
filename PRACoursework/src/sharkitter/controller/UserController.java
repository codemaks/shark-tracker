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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class UserController implements ActionListener {

    private ConnectionFrame connectionFrame;
    private AccountCreationFrame accountCreation;
    private FavouriteSharks favouriteSharks;
    private MenuFrame menuFrame;
    private FunctionalityController functionalityController;

    private Jaws api;

    /**
     * Constructor of UserController
     * @param connectionFrame   Frame used by user to connect
     * @param favouriteSharks   Model of favourite sharks
     */
    public UserController(ConnectionFrame connectionFrame, FavouriteSharks favouriteSharks) {
        this.connectionFrame = connectionFrame;
        this.favouriteSharks = favouriteSharks;


        api = new Jaws("EkZ8ZqX11ozMamO9", "E7gdkwWePBYT75KE", true);

        menuFrame = new MenuFrame(this);
        functionalityController = new FunctionalityController(menuFrame, favouriteSharks);
        menuFrame.addFunctionalityController(functionalityController);
    }

    /**
     * Action performed if event is triggered
     * @param e ActionEvent, in this case, pressed buttons
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonName = ((JButton) e.getSource()).getText();
        String username;

        switch (buttonName) {

            case "Enter":
                username = connectionFrame.getUsername();
                UserNotFoundAlert userNotFound = new UserNotFoundAlert();

                if(!username.equals("")) {
                    try {
                        favouriteSharks.setUser(username);

                        Path pathToFile = Paths.get("data/" + username + ".txt");
                        Scanner reader = new Scanner(pathToFile);
                        reader.useDelimiter("\n");

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

                    connectionFrame.dispose();

                    menuFrame.setVisible(true);
                }
                break;

            case "Create new account":
                accountCreation = new AccountCreationFrame(this);
                connectionFrame.setVisible(false);
                accountCreation.setVisible(true);
                break;

            case "Register":
                username = accountCreation.getUsername();

                File newUser = new File("data/" + username + ".txt");

                if(newUser.exists()) {
                    ExistingUserAlert existingUser = new ExistingUserAlert();
                    existingUser.setVisible(true);
                } else {
//                File newUser = new File("data/" + username + ".txt");

                    //Give user to model
                    try {
                        favouriteSharks.setUser(username);
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    }

                    accountCreation.dispose();
                    connectionFrame.setVisible(false);
                    menuFrame.setVisible(true);
                }
                break;

            case "<-":
                accountCreation.dispose();
                connectionFrame.setVisible(true);
                break;

            case "Disconnect":
                menuFrame.dispose();
                connectionFrame.setVisible(true);
                break;
        }
    }
}
