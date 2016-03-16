package sharkitter.model;

import api.jaws.Shark;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.prefs.Preferences;

public class FavouriteSharks {

    private List<Shark> favouriteSharks;
    private String user;
    private PrintWriter writer;

    /**
     * Constructor of FavouriteSharks
     */
    public FavouriteSharks() {
        favouriteSharks = new ArrayList<Shark>();
    }

    /**
     * Add a shark to the list of favourites
     * @param shark Shark to be added to the list
     */
    public void addShark(Shark shark) {
        favouriteSharks.add(shark);
        writer.println(shark.getName());
    }

    /**
     * Remove a shark from the list of favourites
     * @param shark Shark to be removed from the list
     * @throws FileNotFoundException
     */
    public void removeShark(Shark shark) throws FileNotFoundException {
        favouriteSharks.remove(shark);

        Scanner reader = new Scanner(new File(user + ".txt"));

        while(reader.hasNext()) {
            String registeredShark = reader.next();
            if(registeredShark.equals(shark.getName())) {
                //TODO remove registered shark
                reader.remove();
            }
        }
        reader.close();
    }

    /**
     * Load the saved favourite sharks into the model
     * @param shark Saved shark
     */
    public void loadSharks(Shark shark) {
        favouriteSharks.add(shark);
    }

    /**
     * Set current user
     * @param user  Current user
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public void setUser(String user) throws FileNotFoundException, UnsupportedEncodingException {
        this.user = user;
        writer = new PrintWriter(user + ".txt", "UTF-8");
    }

    /**
     * Getter of favourite sharks
     * @return  A List of favourite Sharks
     */
    public List<Shark> getFavouriteSharks() {
        return favouriteSharks;
    }
}
