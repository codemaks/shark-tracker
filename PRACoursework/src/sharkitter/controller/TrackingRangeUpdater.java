package sharkitter.controller;

import api.jaws.Jaws;
import api.jaws.Ping;
import sharkitter.api.JawsApi;
import sharkitter.model.PingCollection;
import sharkitter.model.SharkData;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class updates the tracking range.
 */
public class TrackingRangeUpdater {

    private Jaws jawsApi;
    private PingCollection pingCollection;

	/**
     * Constructor for tracking range updater.
     * @param pingCollection a collection of pings.
     */
    public TrackingRangeUpdater(PingCollection pingCollection){
        jawsApi = JawsApi.getInstance();
        this.pingCollection = pingCollection;
    }

	/**
	 * Updates the list of SharkData from the tracking range.
	 * @param trackingRange the "tracking range" combo box.
	 * @return the list of SharkData.
	 */
    public List<SharkData> updateFromTrackingRange(JComboBox<String> trackingRange){
        pingCollection.update();
        List<SharkData> listOfSharks = new ArrayList<>();

        //1. read selected constraint from combo box
        String trackingRangeSelectedItem = (String)trackingRange.getSelectedItem();

        //2. get all shark components by tracking range
        switch (trackingRangeSelectedItem) {
            case "Last 24 Hours":
                for (Ping ping : pingCollection.getPast24hours().values()) {
                    listOfSharks.add(new SharkData(jawsApi.getShark(ping.getName()), ping));
                }
                break;

            case "Last Week":
                for (Ping ping : pingCollection.getPastWeek().values()) {
                    listOfSharks.add(new SharkData(jawsApi.getShark(ping.getName()), ping));
                }
                break;

            case "Last Month":
                for (Ping ping : pingCollection.getPastMonth().values()) {
                    listOfSharks.add(new SharkData(jawsApi.getShark(ping.getName()), ping));
                }
                break;

            default:
                throw new IllegalArgumentException("SearchButtonListener Error 1 : Invalid ComboBox input");
        }
        return listOfSharks;
    }
}
