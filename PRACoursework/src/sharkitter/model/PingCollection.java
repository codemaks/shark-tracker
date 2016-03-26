package sharkitter.model;

import api.jaws.Jaws;
import api.jaws.Ping;
import sharkitter.api.JawsApi;

import java.util.*;

public class PingCollection {
    private Map<String,Ping> past24hours;
    private Map<String,Ping> pastWeek;
    private Map<String,Ping> pastMonth;
    private Jaws jawsApi;
    private String lastUpdated;

    public PingCollection() {
        jawsApi = JawsApi.getInstance();
        past24hours = sortPings(jawsApi.past24Hours());
        pastWeek = sortPings(jawsApi.pastWeek());
        pastMonth = sortPings(jawsApi.pastMonth());
        lastUpdated = jawsApi.getLastUpdated();
    }

    private Map<String,Ping> sortPings(List<Ping> listOfPings){

        Map<String,Ping> MapOfPings = new HashMap<>();

        for(Ping ping: listOfPings){

            if(MapOfPings.containsKey(ping.getName())){

                if(MapOfPings.get(ping.getName()).getTime().compareTo(ping.getTime()) == -1){
                    MapOfPings.put(ping.getName(), ping);
                }
                continue;
            }
            MapOfPings.putIfAbsent(ping.getName(), ping);
        }
        return MapOfPings;
    }

    public boolean update(){
        if(!jawsApi.getLastUpdated().equals(lastUpdated)) {
            lastUpdated = jawsApi.getLastUpdated();
            past24hours = sortPings(jawsApi.past24Hours());
            past24hours = sortPings(jawsApi.pastWeek());
            past24hours = sortPings(jawsApi.pastMonth());
            return true;
        }else{
            return false;
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
