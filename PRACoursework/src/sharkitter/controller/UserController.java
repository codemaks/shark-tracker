package sharkitter.controller;

import api.jaws.Jaws;
import sharkitter.model.FavouriteSharks;
import sharkitter.view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class UserController implements ActionListener, WindowListener {

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
            if(!username.equals("")) {
                try {
                    favouriteSharks.setUser(username);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                try {
                    Scanner reader = new Scanner(new File("data/" + username + ".txt"));
                    while (reader.hasNext()) {
                        favouriteSharks.loadSharks(api.getShark(reader.next()));
                    }
                } catch (FileNotFoundException e1) {
                    AlertFrame userNotFound = new UserNotFoundAlert();
                    userNotFound.setVisible(true);
                }

                connectionFrame.dispose();

                startApp.setVisible(true);
            }
        }

        if(buttonName.equals("Create new account")) {
            accountCreation = new AccountCreationFrame(this);
            connectionFrame.setVisible(false);
            accountCreation.setVisible(true);
        }

        if(buttonName.equals("Register")) {
            String username = accountCreation.getUsername();

            //TODO check if file exists
            File newUserFile = new File("data/" + username + ".txt");
            if(newUserFile.exists()) {
                AlertFrame existingUser = new ExistingUserAlert();
                existingUser.setVisible(true);
            }

            //Give user to model
            try {
                favouriteSharks.setUser(username);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            connectionFrame.dispose();
            accountCreation.dispose();
            startApp.setVisible(true);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {
        connectionFrame.setVisible(true);
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
