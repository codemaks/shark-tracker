package sharkitter.model;

import api.jaws.Shark;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FavouriteSharks {

    private Set<String> favouriteSharks;
    private String user;
    private Path filePath;

    /**
     * Constructor of FavouriteSharks
     */
    public FavouriteSharks() {
        favouriteSharks = new HashSet<String>();
    }

    /**
     * Add a shark to the list of favourites
     * @param shark Shark to be added to the list
     */
    public void addShark(Shark shark) {
        favouriteSharks.add(shark.getName());
        List<String> sharkName = Arrays.asList(shark.getName());
        try {
            Files.write(filePath, sharkName, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove a shark from the list of favourites
     * @param shark Shark to be removed from the list
     * @throws FileNotFoundException
     */
    public void removeShark(Shark shark) throws IOException {
        favouriteSharks.remove(shark.getName());

        Files.write(filePath, favouriteSharks);
    }

    /**
     * Load the saved favourite sharks into the model
     * @param shark Saved shark
     */
    public void loadSharks(Shark shark) {
        favouriteSharks.add(shark.getName());
    }

    /**
     * Set current user
     * @param user  Current user
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public void setUser(String user) throws FileNotFoundException, UnsupportedEncodingException {
        this.user = user;
        filePath = Paths.get("data/" + user + ".txt");
    }

    /**
     * Getter of favourite sharks
     * @return  A List of favourite Sharks
     */
    public Set<String> getFavouriteSharks() {
        return favouriteSharks;
    }

    /**
     * Get of current user
     * @return  String representation of current user's username
     */
    public String getUser() {
        return user;
    }
}
