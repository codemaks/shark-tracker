package sharkitter.controller;

import api.jaws.Jaws;
import sharkitter.model.FavouriteSharks;
import sharkitter.view.AccountCreationFrame;
import sharkitter.view.AlertWindow;
import sharkitter.view.ConnectionFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class UserController implements ActionListener, KeyListener {

    private ConnectionFrame connectionFrame;
    private AccountCreationFrame accountCreation;
    private FavouriteSharks favouriteSharks;

    private Jaws api;

    public UserController(ConnectionFrame connectionFrame, FavouriteSharks favouriteSharks) {
        this.connectionFrame = connectionFrame;
        this.favouriteSharks = favouriteSharks;


        api = new Jaws("EkZ8ZqX11ozMamO9", "E7gdkwWePBYT75KE", true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonName = ((JButton) e.getSource()).getText();
        if(buttonName.equals("Enter")) {
            String username = connectionFrame.getUsername();
            favouriteSharks.setUser();
            try {
                 Scanner reader = new Scanner(new File(username + ".txt"));
                while(reader.hasNext()) {
                    favouriteSharks.loadSharks(api.getShark(reader.next()));
                }
            } catch (FileNotFoundException e1) {
                AlertWindow userNotFound = new AlertWindow("User not found");
                userNotFound.setVisible(true);
            }

        }
        if(buttonName.equals("Create account")) {
            accountCreation = new AccountCreationFrame(this);
            accountCreation.setVisible(true);
            connectionFrame.dispose();
        }
        if(buttonName.equals("Register")) {
            String username = accountCreation.getUsername();
            favouriteSharks.setUser(username);
            try {
                PrintWriter writer = new PrintWriter(username + ".txt", "UTF-8");
                accountCreation.dispose();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
