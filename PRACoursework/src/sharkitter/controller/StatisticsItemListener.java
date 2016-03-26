package sharkitter.controller;

import sharkitter.model.PingCollection;
import sharkitter.model.SharkData;
import sharkitter.view.StatisticsFrame;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;

public class StatisticsItemListener implements ItemListener
{
    private StatisticsFrame statisticsFrame;
    private TrackingRangeUpdater comboBoxListener;

    public StatisticsItemListener(StatisticsFrame statisticsFrame, PingCollection pingCollection){
        this.statisticsFrame = statisticsFrame;
        comboBoxListener = new TrackingRangeUpdater(pingCollection);
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
       updateChart(comboBoxListener.updateFromTrackingRange((JComboBox<String>)e.getSource()),(JComboBox) e.getSource());
    }

    private void updateChart(List<SharkData> sharkDataList, JComboBox source){
        statisticsFrame.updateShark(sharkDataList, source);
    }

    public Map<String,List<SharkData>> separateSharkDataByGender(List<SharkData> listOfSharks){
        Map<String,List<SharkData>> mapofsharkdata = new HashMap<String,List<SharkData>>();
        List<SharkData> maleSharks = new ArrayList<>();
        List<SharkData> femaleSharks = new ArrayList<>();

        for(SharkData sharkdata: listOfSharks){
            if(sharkdata.getGender().equals("Male")){
                maleSharks.add(sharkdata);
            }else{
                femaleSharks.add(sharkdata);
            }
        }
        mapofsharkdata.put("Male",maleSharks);
        mapofsharkdata.put("Female",femaleSharks);
        return mapofsharkdata;
    }

    public Map<String,List<SharkData>> separateSharkDataByStageOfLife(List<SharkData> listOfSharks){
        Map<String,List<SharkData>> mapofsharkdata = new HashMap<String,List<SharkData>>();
        List<SharkData> matureSharks = new ArrayList<>();
        List<SharkData> immatureSharks = new ArrayList<>();
        List<SharkData> undeterminedSharks = new ArrayList<>();

        for(SharkData sharkData:listOfSharks){

            if(sharkData.getStageOfLife().equals("Mature")) {
                matureSharks.add(sharkData);

            } else if(sharkData.getStageOfLife().equals("Immature")) {
                immatureSharks.add(sharkData);

            } else {
                undeterminedSharks.add(sharkData);
            }
        }
        mapofsharkdata.put("Mature",matureSharks);
        mapofsharkdata.put("Immature",immatureSharks);
        mapofsharkdata.put("Undetermined",undeterminedSharks);
        return mapofsharkdata;
    }

    public Map<String,Integer> separateSharkDataByTagLocation(List<SharkData> listOfSharks){
        Map<String,Integer> mapofsharkdata = new HashMap<>();

        for(SharkData sharkData: listOfSharks){
            if(mapofsharkdata.containsKey(sharkData.getTagLocation())){
                mapofsharkdata.put(sharkData.getTagLocation(),mapofsharkdata.get(sharkData.getTagLocation()) + 1);
            }
            mapofsharkdata.putIfAbsent(sharkData.getTagLocation(), 1);
        }
        return mapofsharkdata;
    }

}
