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
    
    public FavouriteSharks() {
        favouriteSharks = new ArrayList<Shark>();
    }

    public void addShark(Shark shark) {
        favouriteSharks.add(shark);
        writer.println(shark.getName());
    }

    public void removeShark(Shark shark) throws FileNotFoundException {
        favouriteSharks.remove(shark);

        Scanner reader = new Scanner(new File(user + ".txt"));

        while(reader.hasNext()) {
            String registeredShark = reader.next();
            if(registeredShark.equals(shark.getName())) {
                //TODO remove registered shark
            }
        }
    }

    public void loadSharks(Shark shark) {
        favouriteSharks.add(shark);
    }

    public void setUser(String user) throws FileNotFoundException, UnsupportedEncodingException {
        this.user = user;
        writer = new PrintWriter(user + ".txt", "UTF-8");
    }
}
