package sharkitter.controller;

import api.jaws.Jaws;
import api.jaws.Ping;
import sharkitter.api.JawsApi;
import sharkitter.model.PingCollection;
import sharkitter.model.SharkData;

import javax.swing.*;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;


public class TrackingRangeUpdater {

    private Jaws jawsApi;
    private PingCollection pingCollection;
    private List<SharkData> listOfSharks;

    public TrackingRangeUpdater(PingCollection pingCollection){
        jawsApi = JawsApi.getInstance();
        this.pingCollection = pingCollection;
    }
    public List<SharkData> updateFromTrackingRange(JComboBox<String> trackingRange){
        pingCollection.update();
        listOfSharks = new ArrayList<>();
        //1. read selected constraint from combo box

        String trackingRangeSelectedItem = (String)trackingRange.getSelectedItem();

        //2. get all shark components by tracking range
        if (trackingRangeSelectedItem.equals("Last 24 Hours")) {
            for(Ping ping: pingCollection.getPast24hours().values()){

                listOfSharks.add(new SharkData(jawsApi.getShark(ping.getName()),ping));
            }

        } else if (trackingRangeSelectedItem.equals("Last Week")) {
            for(Ping ping: pingCollection.getPastWeek().values()){

                listOfSharks.add(new SharkData(jawsApi.getShark(ping.getName()),ping));
            }

        } else if (trackingRangeSelectedItem.equals("Last Month")) {
            for(Ping ping: pingCollection.getPastMonth().values()){

                listOfSharks.add(new SharkData(jawsApi.getShark(ping.getName()),ping));
            }

        } else {
            throw new IllegalArgumentException("SearchButtonListener Error 1 : Invalid ComboBox input");
        }
        return listOfSharks;
    }

}
