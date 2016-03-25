package sharkitter.model;

import api.jaws.Jaws;
import api.jaws.Ping;

import java.util.*;

public class PingCollection {
    private String LastUpdated;
    private Map<String,Ping> past24hours;
    private Map<String,Ping> pastWeek;
    private Map<String,Ping> pastMonth;
    private Jaws jawsApi;


    /**
     * creates a new PingCollection object with a new Jaws and three maps of strings to pings, one for the pings
     * recieved 24 hours ago, another for the pings recieved up to a week ago and finally one for the pings recieved up
     * to one month ago.
     */
    public PingCollection() {
        jawsApi = new Jaws("EkZ8ZqX11ozMamO9","E7gdkwWePBYT75KE", true);
        LastUpdated = jawsApi.getLastUpdated();
        past24hours = sortPings(jawsApi.past24Hours());
        pastWeek = sortPings(jawsApi.pastWeek());
        pastMonth = sortPings(jawsApi.pastMonth());
    }

    /**
     * ping sorting method which returns a map of strings to pings
     * @param listOfPings
     * @return map of pings
     */
    private Map<String,Ping> sortPings(List<Ping> listOfPings){

        Map<String,Ping> MapOfPings = new HashMap<>();

        for(Ping ping: listOfPings){

            if(MapOfPings.containsKey(ping.getName())){

                if(MapOfPings.get(ping.getName()).getTime().compareTo(ping.getTime()) == -1){
                    MapOfPings.put(ping.getName(),ping);
                }
                continue;
            }
            MapOfPings.putIfAbsent(ping.getName(),ping);
        }
        return MapOfPings;
    }

    /**
     * void method which updates each Map with fresh data every day when if the application is left running.
     */
    public void update(){
        if(jawsApi.getLastUpdated().equals(LastUpdated)) {
            past24hours = sortPings(jawsApi.past24Hours());
            past24hours = sortPings(jawsApi.pastWeek());
            past24hours = sortPings(jawsApi.pastMonth());
        }
    }

    /**
     * getter method for the field past24hours, a map of strings to pings
     * @return past24hours
     */
    public Map<String,Ping> getPast24hours(){
        return past24hours;
    }

    /**
     * getter method for the field pastWeek, a map of strings to pings
     * @return pastWeek
     */
    public Map<String,Ping> getPastWeek(){
        return pastWeek;
    }

    /**
     * getter method for the field pastMonth, a map of strings to pings
     * @return pastMonth
     */
    public Map<String,Ping> getPastMonth(){
        return pastMonth;
    }

}
