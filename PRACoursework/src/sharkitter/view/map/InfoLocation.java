package sharkitter.view.map;

import api.jaws.Location;

/**
 * Class that holds a Location, with it's corresponding information
 */
public class InfoLocation {
    private String information;
    private Location location;
    public InfoLocation(String information, Location location)
    {
        this.location = location;
        this.information = information;
    }
    public String getInformation(){ return information;}
    public Location getLocation(){return location;}
}
