package sharkitter.model;

import api.jaws.Jaws;
import api.jaws.Ping;
import sharkitter.api.JawsApi;

import java.util.*;

/**
 * A class containing a collection of pings.
 */
public class PingCollection {
    private Map<String, Ping> past24hours;
    private Map<String, Ping> pastWeek;
    private Map<String, Ping> pastMonth;
    private Jaws jawsApi;
    private String lastUpdated;

	/**
	 * Constructs a PingCollection.
	 */
    public PingCollection() {
        jawsApi = JawsApi.getInstance();
        past24hours = sortPings(jawsApi.past24Hours());
        pastWeek = sortPings(jawsApi.pastWeek());
        pastMonth = sortPings(jawsApi.pastMonth());
        lastUpdated = jawsApi.getLastUpdated();
    }

	/**
	 * Sorts pings by shark name.
	 * @param listOfPings a list of pings to be sorted.
	 * @return a map mapping the name of the shark to the corresponding ping.
	 */
    private Map<String, Ping> sortPings(List<Ping> listOfPings) {

        Map<String, Ping> mapOfPings = new HashMap<>();

        for(Ping ping : listOfPings){

            if(mapOfPings.containsKey(ping.getName())){

                if(mapOfPings.get(ping.getName()).getTime().compareTo(ping.getTime()) == -1){
                    mapOfPings.put(ping.getName(), ping);
                }
                continue;
            }
            mapOfPings.putIfAbsent(ping.getName(), ping);
        }
        return mapOfPings;
    }

	/**
	 * Updates the list of pings if the last updated time is not equal to the time the Jaws API was last updated.
	 * @return true if the list has been updated, false otherwise.
	 */
    public boolean update() {
        if(!jawsApi.getLastUpdated().equals(lastUpdated)) {
            lastUpdated = jawsApi.getLastUpdated();
            past24hours = sortPings(jawsApi.past24Hours());
            past24hours = sortPings(jawsApi.pastWeek());
            past24hours = sortPings(jawsApi.pastMonth());

            return true;
        }
        else {
            return false;
        }
    }

	/**
	 * Gets the map for pings within the last 24 hours.
	 * @return the map mapping shark names to pings.
	 */
    public Map<String, Ping> getPast24hours(){
        return past24hours;
    }

	/**
	 * Gets the map for pings within the last week.
	 * @return the map mapping shark names to pings.
	 */
    public Map<String, Ping> getPastWeek(){
        return pastWeek;
    }

	/**
	 * Gets the map for pings within the past month.
	 * @return the map mapping shark names to pings.
	 */
    public Map<String, Ping> getPastMonth(){
        return pastMonth;
    }

}
