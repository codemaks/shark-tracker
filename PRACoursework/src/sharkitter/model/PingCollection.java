package sharkitter.model;

import api.jaws.Jaws;
import api.jaws.Ping;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Evou on 24/03/2016.
 */
public class PingCollection {
    private Map<String,Ping> past24hours;
    private Map<String,Ping> pastWeek;
    private Map<String,Ping> pastMonth;
    private Jaws jawsApi;


    public PingCollection(){
        jawsApi = new Jaws("EkZ8ZqX11ozMamO9","E7gdkwWePBYT75KE", true);
        past24hours = sortPings(jawsApi.past24Hours());
        pastWeek = sortPings(jawsApi.pastWeek());
        pastMonth = sortPings(jawsApi.pastMonth());
    }

    private Map<String,Ping> sortPings(ArrayList<Ping> listOfPings){

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

    public void update(){
        if(Calendar.HOUR_OF_DAY == 7) {
            //When taking a look at GetLastUpdated, we realised that the API was updated once a day at regular intervals,
            // around 6am.
            past24hours = sortPings(jawsApi.past24Hours());
            past24hours = sortPings(jawsApi.pastWeek());
            past24hours = sortPings(jawsApi.pastMonth());
        }
    }

    public Map<String,Ping> getPast24hours(){
        return past24hours;
    }

    public Map<String,Ping> getPastWeek(){
        return pastWeek;
    }

    public Map<String,Ping> getPastMonth(){
        return pastMonth;
    }

}
