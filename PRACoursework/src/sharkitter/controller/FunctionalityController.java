package sharkitter.controller;

import sharkitter.model.FavouriteSharks;
import sharkitter.model.Konami;
import sharkitter.view.*;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.URISyntaxException;

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

    public FunctionalityController(MenuFrame menuFrame, FavouriteSharks favouriteSharks) throws IOException, URISyntaxException {
        this.menuFrame = menuFrame;
        this.favouriteSharks = favouriteSharks;

        searchFrame = new SearchFrame(this, favouriteSharks);
        favouritesFrame = new FavouritesFrame(favouriteSharks, menuFrame.getJaws());
        statisticsFrame = new StatisticsFrame(this);

        konami = new Konami();

        player = AudioPlayer.player;
        InputStream test = new FileInputStream(new File(getClass().getClassLoader().getResource("resources/Never Give Up On Sharks.wav").toURI()));
        stream = new AudioStream(test);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().getClass() == JButton.class) {
            String buttonName = ((JButton) e.getSource()).getText();

            switch (buttonName) {
                case "Search":
                    menuFrame.setVisible(false);
                    searchFrame.setVisible(true);
                    break;

                case "Favourites":
                    favouritesFrame.setVisible(true);
                    break;

                case "Statistics":
                    menuFrame.setVisible(false);
                    statisticsFrame.setVisible(true);
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

    @Override
    public void keyPressed(KeyEvent e) {
        konami.registerPressedKey(e.getKeyCode());
        if(konami.checkKonamiCode()) {
            konami.reset();
            easterEggFrame = new EasterEggFrame();
            easterEggFrame.addWindowListener(this);
            easterEggFrame.setVisible(true);
            player.start(stream);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
        player.stop(stream);
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
