package sharkitter.controller;

import api.jaws.Jaws;
import api.jaws.Ping;
import api.jaws.Shark;
import sharkitter.view.SearchFrame;
import sharkitter.view.SharkContainer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
        stage_of_life = (String)searchframe.getStage_of_life().getSelectedItem();
        tracking_range = (String)searchframe.getTracking_range().getSelectedItem();
        gender = (String)searchframe.getGender().getSelectedItem();
        tag_location = (String)searchframe.getTag_location().getSelectedItem();
        //2. get all shark components by tracking range

        if (tracking_range.equals("Last 24 Hours")) {
                System.out.println(tracking_range);
                searchframe.updateCentralPanel(jawsApi.past24Hours());


            System.out.println("SearchButtonListener Error 1 : Invalid ComboBox input");
        } else if (tracking_range.equals("Last Week")) {

                System.out.println(tracking_range);
                searchframe.updateCentralPanel(jawsApi.pastWeek());

        } else if (tracking_range.equals("Last Month")) {

                System.out.println(tracking_range);
                searchframe.updateCentralPanel(jawsApi.pastMonth());

        } else {
            System.out.println("SearchButtonListener Error 1 : Invalid ComboBox input");
        }
        //3. apply constraint on West panel filled with Shark Component objects

    }

}

