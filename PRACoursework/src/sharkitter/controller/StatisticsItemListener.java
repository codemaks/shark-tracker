package sharkitter.controller;

import api.jaws.Jaws;
import api.jaws.Ping;
import sharkitter.model.PingCollection;
import sharkitter.model.SharkData;
import sharkitter.view.StatisticsFrame;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsItemListener implements ItemListener
{
    private Jaws jawsApi;
    private StatisticsFrame statisticsFrame;
    private PingCollection pingCollection;

    public StatisticsItemListener(StatisticsFrame statisticsFrame, PingCollection pingCollection){
        //TODO api called here
        jawsApi = new Jaws("EkZ8ZqX11ozMamO9","E7gdkwWePBYT75KE", true);
        this.statisticsFrame = statisticsFrame;
        this.pingCollection = pingCollection;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
       updateChart(updatefromTrackingRange((JComboBox<String>)e.getSource()),(JComboBox) e.getSource());
    }

    private List<SharkData> updatefromTrackingRange(JComboBox<String> trackingrange){
        pingCollection.update();
        //1. read selected constraint from combo box

        String tracking_range = (String)trackingrange.getSelectedItem();
        ArrayList<SharkData> listOfSharks = new ArrayList<>();

        //2. get all shark components by tracking range
        if (tracking_range.equals("Last 24 Hours")) {
            for(Ping ping: pingCollection.getPast24hours().values()){

                listOfSharks.add(new SharkData(jawsApi.getShark(ping.getName()),ping));
            }

        } else if (tracking_range.equals("Last Week")) {
            for(Ping ping: pingCollection.getPastWeek().values()){

                listOfSharks.add(new SharkData(jawsApi.getShark(ping.getName()),ping));
            }

        } else if (tracking_range.equals("Last Month")) {
            for(Ping ping: pingCollection.getPastMonth().values()){

                listOfSharks.add(new SharkData(jawsApi.getShark(ping.getName()),ping));
            }

        } else {
            //TODO remove println
            System.out.println("SearchButtonListener Error 1 : Invalid ComboBox input");
        }
        return listOfSharks;
    }

    private void updateChart(List<SharkData> listofsharks, JComboBox source){
        statisticsFrame.updateShark(listofsharks,source);
    }
}
