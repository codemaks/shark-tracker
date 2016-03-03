package controller;

import api.jaws.Jaws;
import api.jaws.Ping;
import api.jaws.Shark;
import view.SearchFrame;
import view.SharkContainer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Evou on 25/02/2016.
 */
public class SearchButtonListener implements ActionListener{
    private SearchFrame searchframe;
    private String tracking_range;
    private String gender;
    private String tag_location;
    private String stage_of_life;
    private Jaws jawsApi;
    private ArrayList<Shark> sharkstoprint;

    public SearchButtonListener(SearchFrame searchframe){
        jawsApi = new Jaws("EkZ8ZqX11ozMamO9","E7gdkwWePBYT75KE", true);
        this.searchframe = searchframe;
        }

    public void actionPerformed(ActionEvent e){
        //1. read selected constraint from combo box
        //stage_of_life.getSelectedItem();
        //searchframe.tracking_range.getSelectedItem();
        //searchframe.gender.getSelectedItem();
        //searchframe.tag_location.getSelectedItem();
        //2. get all shark components by tracking range
        int counter = 0;
        if (tracking_range.equals("Last 24 hours")) {
            for (Ping ping : jawsApi.past24Hours()) {
                new SharkContainer(jawsApi.getShark(ping.getName()), ping);
                counter++;
            }

            for (Ping ping : jawsApi.pastWeek()) {
                new SharkContainer(jawsApi.getShark(ping.getName()), ping);
                counter++;
            }

            for (Ping ping : jawsApi.pastMonth()) {
                new SharkContainer(jawsApi.getShark(ping.getName()), ping);
                counter++;
            }

        } else if (tracking_range.equals("Last Week")) {
            for (Ping ping : jawsApi.pastWeek()) {
                new SharkContainer(jawsApi.getShark(ping.getName()), ping);
                counter++;
            }

        } else if (tracking_range.equals("Last Month")) {
            for (Ping ping : jawsApi.pastMonth()) {
                new SharkContainer(jawsApi.getShark(ping.getName()), ping);
                counter++;
            }

        } else {
            System.out.println("SearchButtonListener Error 1 : Invalid ComboBox input");
        }
        //3. apply constraint on West panel filled with Shark Component objects

    }

}

