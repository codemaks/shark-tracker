package sharkitter.model;

import api.jaws.Location;

/**
 * A class that holds a location, with its corresponding information.
 */
public class InfoLocation {
    private String information;
    private Location location;

    /**
     * Creates a new InfoLocation.
     * @param information the information to store.
     * @param location the location to store.
     */
    public InfoLocation(String information, Location location) {
        this.location = location;
        this.information = information;
    }

    /**
     * Gets the stored infomation String.
     * @return the information stored.
     */
    public String getInformation() {
        return information;
    }

    /**
     * Gets the stored location.
     * @return the location stored.
     */
    public Location getLocation() {
        return location;
    }
}
