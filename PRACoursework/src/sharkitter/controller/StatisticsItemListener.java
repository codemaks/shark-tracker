package sharkitter.controller;

import sharkitter.model.PingCollection;
import sharkitter.model.SharkData;
import sharkitter.view.StatisticsFrame;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

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
}
