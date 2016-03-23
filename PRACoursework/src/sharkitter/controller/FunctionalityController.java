package sharkitter.controller;

import api.jaws.Jaws;
import sharkitter.model.FavouriteSharks;
import sharkitter.model.Konami;
import sharkitter.view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FunctionalityController implements ActionListener, KeyListener {

    private MenuFrame menuFrame;
    private SearchFrame searchFrame;
    private StatisticsFrame statisticsFrame;
    private EasterEggFrame easterEggFrame;

    private FavouriteSharks favouriteSharks;
    private Jaws jawsApi;

    private Konami konami;

    public FunctionalityController(MenuFrame menuFrame, FavouriteSharks favouriteSharks, Jaws jawsApi) {
        this.menuFrame = menuFrame;
        this.favouriteSharks = favouriteSharks;
        this.jawsApi = jawsApi;

        konami = new Konami();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().getClass() == JButton.class) {
            String buttonName = ((JButton) e.getSource()).getText();

            switch (buttonName) {
                case "Search":
                    menuFrame.setVisible(false);
                    searchFrame = new SearchFrame(this, favouriteSharks, jawsApi);
                    searchFrame.setVisible(true);
                    break;

                case "Favourites":
                    FavouritesFrame favouritesFrame = new FavouritesFrame(favouriteSharks, menuFrame.getJaws());
                    favouritesFrame.setVisible(true);
                    break;

                case "Statistics":
                    menuFrame.setVisible(false);
                    statisticsFrame = new StatisticsFrame();
                    statisticsFrame.setVisible(true);
                    break;
            }
        }

        if(e.getSource().getClass() == JMenuItem.class) {
            String menuName = ((JMenuItem) e.getSource()).getText();

            switch (menuName) {
                case "Menu":
                    searchFrame.setVisible(false);
                    if (!favouriteSharks.getFavouriteSharks().isEmpty()) menuFrame.toggleFavourites(true);
                    menuFrame.setVisible(true);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        konami.registerPressedKey(e.getKeyCode());
        if(konami.checkKonamiCode()) {
            konami.reset();
            easterEggFrame = new EasterEggFrame();
            easterEggFrame.setVisible(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
