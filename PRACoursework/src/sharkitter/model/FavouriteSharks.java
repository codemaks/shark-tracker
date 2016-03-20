package sharkitter.model;

import api.jaws.Shark;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FavouriteSharks {

    private List<Shark> favouriteSharks;
    private String user;
    private PrintWriter writer;
    private Path filePath;

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
    public void removeShark(Shark shark) throws FileNotFoundException {
        favouriteSharks.remove(shark);

        Scanner reader = new Scanner("data/" + user + ".txt");
        reader.useDelimiter("\n");

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
        filePath = Paths.get("data/" + user + ".txt");
    }

    /**
     * Getter of favourite sharks
     * @return  A List of favourite Sharks
     */
    public List<Shark> getFavouriteSharks() {
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
