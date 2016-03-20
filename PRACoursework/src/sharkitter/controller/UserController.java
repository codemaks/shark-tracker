package sharkitter.controller;

import api.jaws.Jaws;
import sharkitter.model.FavouriteSharks;
import sharkitter.view.*;

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

        if(buttonName.equals("Enter")) {
            String username = connectionFrame.getUsername();
            UserNotFoundAlert userNotFound = new UserNotFoundAlert();

            if(!username.equals("")) {
                try {
                    favouriteSharks.setUser(username);

                    Path pathToFile = Paths.get("data/" + username + ".txt");
                    Scanner reader = new Scanner(pathToFile);

                    //TODO Check if load sharks
                    while (reader.hasNext()) {
                        favouriteSharks.loadSharks(api.getShark(reader.next()));
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
        }

        if(buttonName.equals("Create new account")) {
            accountCreation = new AccountCreationFrame(this);
            connectionFrame.setVisible(false);
            accountCreation.setVisible(true);
        }

        if(buttonName.equals("Register")) {
            String username = accountCreation.getUsername();

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
        }

        if(buttonName.equals("<-")) {
            accountCreation.dispose();
            connectionFrame.setVisible(true);
        }

        if(buttonName.equals("Disconnect")) {
            menuFrame.dispose();
            connectionFrame.setVisible(true);
        }
    }
}
