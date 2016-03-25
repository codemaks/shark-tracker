package sharkitter.controller;

import sharkitter.model.FavouriteSharks;
import sharkitter.model.Konami;
import sharkitter.model.PingCollection;
import sharkitter.view.*;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;


/**
 * A controller public class which implements ActionListener, Keylistener and WindowListener
 */
public class FunctionalityController implements ActionListener, KeyListener, WindowListener {

    private MenuFrame menuFrame;
    private SearchFrame searchFrame;
    private StatisticsFrame statisticsFrame;
    private FavouritesFrame favouritesFrame;
    private EasterEggFrame easterEggFrame;

    private FavouriteSharks favouriteSharks;

    private Konami konami;

    private AudioPlayer player;
    private AudioStream stream;

    private static final String SONG = "resources/Never Give Up On Sharks.wav";

    /**
     * Instantiates a FunctionalityController from a given MenuFrame, FavoriteSharks and PingCollection.
     * @param menuFrame
     * @param favouriteSharks
     * @param pingCollection
     * @throws IOException if the audio file isn't found
     */
    public FunctionalityController(MenuFrame menuFrame, FavouriteSharks favouriteSharks, PingCollection pingCollection) throws IOException {
        this.menuFrame = menuFrame;
        this.favouriteSharks = favouriteSharks;

        searchFrame = new SearchFrame(this, favouriteSharks,pingCollection);

        //TODO MenuFrame doesn't use api, put it in other class?
        favouritesFrame = new FavouritesFrame(favouriteSharks, menuFrame.getJaws());
        statisticsFrame = new StatisticsFrame(this,pingCollection);

        konami = new Konami();

        player = AudioPlayer.player;
        stream = new AudioStream(getClass().getClassLoader().getResourceAsStream(SONG));
    }

    /**
     * Void method from the ActionListener interface
     * @param e, an action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().getClass() == JButton.class) {
            String buttonName = ((JButton) e.getSource()).getText();

            switch (buttonName) {
                case "Search":
                    menuFrame.setVisible(false);
                    searchFrame.setVisible(true);
                    konami.reset();
                    break;

                case "Favourites":
                    favouritesFrame.setVisible(true);
                    break;

                case "Statistics":
                    menuFrame.setVisible(false);
                    statisticsFrame.setVisible(true);
                    break;

                case "Ok":
                    easterEggFrame.dispose();
                    break;
            }
        }

        if(e.getSource().getClass() == JMenuItem.class) {
            JMenuItem source = (JMenuItem) e.getSource();
            String menuName = source.getText();

            switch (source.getName()) {
                case "SearchFrame":
                    switch (menuName) {
                        case "Menu":
                            searchFrame.setVisible(false);
                            if (!favouriteSharks.getFavouriteSharks().isEmpty()) menuFrame.toggleFavourites(true);
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
                            break;
                    }
                    break;
            }
        }
    }

    /**
     * void method inherited from the KeyListener interface which instantiates a new EasterEggFrame
     * @param e, a key event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        konami.registerPressedKey(e.getKeyCode());
        if(konami.checkKonamiCode()) {
            System.out.println("Konami Code was typed... Unleashing shark power.");
            konami.reset();
            easterEggFrame = new EasterEggFrame(this);
            easterEggFrame.addWindowListener(this);
            easterEggFrame.setVisible(true);
            player.start(stream);
        }
    }

    /**
     * void method which stops the music and resets the list of keys pressed in the konami object when the window is*
     * closed. Inherited from the WindowListener interface.
     * @param e, a window event
     */
    @Override
    public void windowClosed(WindowEvent e) {
        player.stop(stream);
        konami.reset();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

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
