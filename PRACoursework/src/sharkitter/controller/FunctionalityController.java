package sharkitter.controller;

import sharkitter.model.FavouriteSharks;
import sharkitter.model.Konami;
import sharkitter.model.PingCollection;
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
    private FavouritesFrame favouritesFrame;
    private EasterEggFrame easterEggFrame;
    private PingCollection pingCollection;

    private FavouriteSharks favouriteSharks;

    private Konami konami;

    public FunctionalityController(MenuFrame menuFrame, FavouriteSharks favouriteSharks, PingCollection pingCollection) {
        this.menuFrame = menuFrame;
        this.favouriteSharks = favouriteSharks;
        this.pingCollection = pingCollection;

        searchFrame = new SearchFrame(this, favouriteSharks,pingCollection);
        favouritesFrame = new FavouritesFrame(favouriteSharks, menuFrame.getJaws());
        statisticsFrame = new StatisticsFrame(this,pingCollection);

        konami = new Konami();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().getClass() == JButton.class) {
            String buttonName = ((JButton) e.getSource()).getText();

            switch (buttonName) {
                case "Search":
                    menuFrame.setVisible(false);
                    searchFrame = new SearchFrame(this, favouriteSharks, pingCollection);
                    searchFrame.setVisible(true);
                    break;

                case "Favourites":
                    favouritesFrame.setVisible(true);
                    break;

                case "Statistics":
                    menuFrame.setVisible(false);
                    statisticsFrame = new StatisticsFrame(this, pingCollection);
                    statisticsFrame.setVisible(true);
                    break;
            }
        }

        if (e.getSource().getClass() == JMenuItem.class) {
            JMenuItem source = (JMenuItem) e.getSource();
            String menuName = source.getText();

            switch (menuName) {
                case "Menu":
                    searchFrame.setVisible(false);
                    if (!favouriteSharks.getFavouriteSharks().isEmpty())
                        menuFrame.toggleFavourites(true);
                    menuFrame.setVisible(true);
                case "Back":
                    if (statisticsFrame != null) {
                        statisticsFrame.setVisible(false);
                    }
                    menuFrame.setVisible(true);
                    switch (source.getName()) {
                        case "SearchFrame":
                            switch (menuName) {
                                case "Menu":
                                    searchFrame.setVisible(false);
                                    if (!favouriteSharks.getFavouriteSharks().isEmpty())
                                        menuFrame.toggleFavourites(true);
                                    menuFrame.setVisible(true);
                                    break;
                            }
                            break;
                        case "StatisticsFrame":
                            switch (menuName) {
                                case "Menu":
                                    statisticsFrame.setVisible(false);
                                    menuFrame.setVisible(true);
                                    break;
                                case "Search":
                                    statisticsFrame.setVisible(false);
                                    searchFrame.setVisible(true);
                            }
                    }
                }
            }
        }

        @Override
        public void keyTyped (KeyEvent e){

        }

        @Override
        public void keyPressed (KeyEvent e){
            konami.registerPressedKey(e.getKeyCode());
            if (konami.checkKonamiCode()) {
                konami.reset();
                easterEggFrame = new EasterEggFrame();
                easterEggFrame.setVisible(true);
            }
        }

        @Override
        public void keyReleased (KeyEvent e){

        }

}
