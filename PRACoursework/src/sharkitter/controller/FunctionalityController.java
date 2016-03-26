package sharkitter.controller;

import api.jaws.Jaws;
import sharkitter.api.JawsApi;
import sharkitter.model.FavouriteSharks;
import sharkitter.model.Konami;
import sharkitter.model.PingCollection;
import sharkitter.view.*;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Class to control the different functionalities of the application
 * - Searching
 * - Displaying the favourites
 * - Displaying the statistics
 */
public class FunctionalityController implements ActionListener, KeyListener, WindowListener {

    private MenuFrame menuFrame;
    private SearchFrame searchFrame;
    private StatisticsFrame statisticsFrame;
    private EasterEggFrame easterEggFrame;

    private Jaws jawsApi;
    private FavouriteSharks favouriteSharks;
    private PingCollection pingCollection;

    private Set<String> listOfTagLocations;

    private Konami konami;

    private AudioPlayer player;
    private AudioStream stream;

    private static final String SONG = "resources/Never Give Up On Sharks.wav";

    /**
     * Constructor of FunctionalityController
     * @param menuFrame Instance of the menu frame
     * @param favouriteSharks   Instance of FavouriteSharks
     * @param pingCollection    Instance of PingCollection
     * @throws IOException  If the song for the Easter Egg was not properly found
     */
    public FunctionalityController(MenuFrame menuFrame, FavouriteSharks favouriteSharks, PingCollection pingCollection) throws IOException {
        jawsApi = JawsApi.getInstance();
        this.menuFrame = menuFrame;
        this.favouriteSharks = favouriteSharks;
        this.pingCollection = pingCollection;
        this.listOfTagLocations = new HashSet<String>();

        searchFrame = new SearchFrame(this, favouriteSharks, pingCollection);

        statisticsFrame = new StatisticsFrame(this, pingCollection);

        konami = new Konami();

        player = AudioPlayer.player;
        stream = new AudioStream(getClass().getClassLoader().getResourceAsStream(SONG));
    }

    @Override
    /**
     * Performs the appropriate action depending on the event
     * Deals with opening the different frames from the Menu frame
     * Deals with going back to the Menu frame from the different frames
     */
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
                    FavouritesFrame favouritesFrame = new FavouritesFrame(favouriteSharks);
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

    public Set<String> getListOfTagLocations(){
        listOfTagLocations = new HashSet<String>();
        for (String sharkName : pingCollection.getPastMonth().keySet()) {
            String tagLoc = jawsApi.getShark(sharkName).getTagLocation();
            listOfTagLocations.add(tagLoc);
        }
        return listOfTagLocations;

    }

    @Override
    /**
     * Reacts to the pressed keys to trigger an Easter Egg
     */
    public void keyPressed(KeyEvent e) {
        konami.registerPressedKey(e.getKeyCode());
        if(konami.checkKonamiCode()) {
            konami.reset();
            easterEggFrame = new EasterEggFrame(this);
            easterEggFrame.addWindowListener(this);
            easterEggFrame.setVisible(true);
            player.start(stream);
        }
    }

    @Override
    /**
     * Stops the music once the EasterEggFrame is closed
     */
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
