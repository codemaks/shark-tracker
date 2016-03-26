package sharkitter.model;

import api.jaws.Shark;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Object representation of user's favourite sharks.
 */
public class FavouriteSharks {

    private Set<String> favouriteSharks;
    private Path filePath;

    /**
     * Constructor of FavouriteSharks.
     */
    public FavouriteSharks() {
        favouriteSharks = new HashSet<String>();
    }

    /**
     * Adds a shark to the list of favourites.
     * @param shark the shark to be added to the list.
     */
    public void addShark(SharkData shark) {
        favouriteSharks.add(shark.getName());
        List<String> sharkName = Arrays.asList(shark.getName());
        try {
            Files.write(filePath, sharkName, StandardOpenOption.APPEND);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a shark from the list of favourites.
     * @param shark the shark to be removed from the list.
     * @throws IOException
     */
    public void removeShark(SharkData shark) throws IOException {
        favouriteSharks.remove(shark.getName());

        Files.write(filePath, favouriteSharks);
    }

    /**
     * Loads the given shark into the model.
     * @param shark the shark to load.
     */
    public void loadSharks(Shark shark) {
        favouriteSharks.add(shark.getName());
    }

    /**
     * Sets current user.
     * @param user the current user.
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public void setUser(String user) throws FileNotFoundException, UnsupportedEncodingException {
        filePath = Paths.get("data/" + user + ".txt");
    }

    /**
     * Getter of favourite sharks.
     * @return a list of favourite sharks.
     */
    public Set<String> getFavouriteSharks() {
        return favouriteSharks;
    }

    /**
     * Clears all favourite sharks.
     */
    public void clearData() {
        favouriteSharks.clear();
    }
}
