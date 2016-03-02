package model;

import api.jaws.Shark;

import java.util.ArrayList;
import java.util.List;

public class FavouriteSharks {

    private List<Shark> favouriteSharks;
    
    public FavouriteSharks() {
        favouriteSharks = new ArrayList<Shark>();
    }

    public void addShark(Shark shark) {
        favouriteSharks.add(shark);
    }

    public void removeShark(Shark shark) {
        favouriteSharks.remove(shark);
    }
}
