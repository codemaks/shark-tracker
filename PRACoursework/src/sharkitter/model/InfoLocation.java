package sharkitter.model;

import api.jaws.Location;

/**
 * Class that holds a Location, with it's corresponding information
 * @author Maks
 */
public class InfoLocation {
    private String information;
    private Location location;

    /**
     * Creates a new InfoLocation
     * @param information
     * @param location
     */
    public InfoLocation(String information, Location location)
    {
        this.location = location;
        this.information = information;
    }

    /**
     * Gets the infomation String store in infolocation
     * @return The information stored
     */
    public String getInformation(){ return information;}

    /**
     * Get the location stored in infolocation
     * @return The location stored
     */
    public Location getLocation(){return location;}
}
