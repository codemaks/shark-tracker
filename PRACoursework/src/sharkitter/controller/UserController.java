package sharkitter.controller;

import api.jaws.Jaws;
import sharkitter.model.FavouriteSharks;
import sharkitter.view.AccountCreationFrame;
import sharkitter.view.AlertWindow;
import sharkitter.view.ConnectionFrame;
import sharkitter.view.MenuFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class UserController implements ActionListener {

    private ConnectionFrame connectionFrame;
    private AccountCreationFrame accountCreation;
    private FavouriteSharks favouriteSharks;
    private MenuFrame startApp;

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

        startApp = new MenuFrame(favouriteSharks);
    }

    /**
     * Action performed if event is trigerred
     * @param e ActionEvent, in this case, pressed buttons
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonName = ((JButton) e.getSource()).getText();
        if(buttonName.equals("Enter")) {
            String username = connectionFrame.getUsername();
            try {
                favouriteSharks.setUser(username);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            try {
                 Scanner reader = new Scanner(new File("data/" + username + ".txt"));
                while(reader.hasNext()) {
                    favouriteSharks.loadSharks(api.getShark(reader.next()));
                }
            } catch (FileNotFoundException e1) {
                AlertWindow userNotFound = new AlertWindow("User not found");
                userNotFound.setVisible(true);
            }

            connectionFrame.dispose();

            startApp.setVisible(true);
        }
        if(buttonName.equals("Create account")) {
            connectionFrame.dispose();
            accountCreation = new AccountCreationFrame(this);
            accountCreation.setVisible(true);
        }
        if(buttonName.equals("Register")) {
            String username = accountCreation.getUsername();

            // Create new file
            File newUserFile = new File("data/" + username + ".txt");

            //Give user to model
            try {
                favouriteSharks.setUser(username);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            accountCreation.dispose();
            startApp.setVisible(true);
        }
    }
}
