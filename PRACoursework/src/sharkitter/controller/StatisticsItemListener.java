package sharkitter.controller;

import api.jaws.Jaws;
import api.jaws.Ping;
import sharkitter.model.SharkData;
import sharkitter.view.StatisticsFrame;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Evou on 23/03/2016.
 */
public class StatisticsItemListener implements ItemListener
{
    private Jaws jawsApi;
    private StatisticsFrame statisticsFrame;

    public StatisticsItemListener(StatisticsFrame statisticsFrame){
        jawsApi = new Jaws("EkZ8ZqX11ozMamO9","E7gdkwWePBYT75KE", true);
        this.statisticsFrame = statisticsFrame;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
       updateChart(updatefromTrackingRange((JComboBox<String>)e.getSource()),(JComboBox) e.getSource());
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

    private ArrayList<SharkData> updatefromTrackingRange(JComboBox<String> trackingrange){
        //1. read selected constraint from combo box

        String tracking_range = (String)trackingrange.getSelectedItem();
        ArrayList<SharkData> listOfSharks = new ArrayList<>();

        //2. get all shark components by tracking range
        if (tracking_range.equals("Last 24 Hours")) {
            for(Ping ping: sortPings(jawsApi.past24Hours()).values()){

                listOfSharks.add(new SharkData(jawsApi.getShark(ping.getName()),ping));
            }

        } else if (tracking_range.equals("Last Week")) {
            for(Ping ping: sortPings(jawsApi.pastWeek()).values()){

                listOfSharks.add(new SharkData(jawsApi.getShark(ping.getName()),ping));
            }

        } else if (tracking_range.equals("Last Month")) {
            for(Ping ping: sortPings(jawsApi.pastMonth()).values()){

                listOfSharks.add(new SharkData(jawsApi.getShark(ping.getName()),ping));
            }

        } else {
            System.out.println("SearchButtonListener Error 1 : Invalid ComboBox input");
        }
        return listOfSharks;
    }

    private void updateChart(ArrayList<SharkData> listofsharks, JComboBox source){
        statisticsFrame.updateShark(listofsharks,source);
    }
}
