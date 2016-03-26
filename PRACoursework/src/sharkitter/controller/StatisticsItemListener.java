package sharkitter.controller;

import api.jaws.Jaws;
import api.jaws.Ping;
import sharkitter.api.JawsApi;
import sharkitter.model.PingCollection;
import sharkitter.model.SharkData;
import sharkitter.view.StatisticsFrame;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class StatisticsItemListener implements ItemListener
{
    private Jaws jawsApi;
    private StatisticsFrame statisticsFrame;
    private PingCollection pingCollection;

    public StatisticsItemListener(StatisticsFrame statisticsFrame, PingCollection pingCollection){
        jawsApi = JawsApi.getInstance();
        this.statisticsFrame = statisticsFrame;
        this.pingCollection = pingCollection;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
       updateChart(updateFromTrackingRange((JComboBox<String>)e.getSource()),(JComboBox) e.getSource());
    }

    private List<SharkData> updateFromTrackingRange(JComboBox<String> trackingRange){
        pingCollection.update();
        //1. read selected constraint from combo box

        String trackingRangeSelectedItem = (String)trackingRange.getSelectedItem();
        ArrayList<SharkData> listOfSharks = new ArrayList<>();

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
            //TODO remove println
            System.out.println("SearchButtonListener Error 1 : Invalid ComboBox input");
        }
        return listOfSharks;
    }

    private void updateChart(List<SharkData> sharkDataList, JComboBox source){
        statisticsFrame.updateShark(sharkDataList, source);
    }
}
