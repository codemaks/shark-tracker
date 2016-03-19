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

    public SearchButtonListener(SearchFrame searchframe){
        jawsApi = new Jaws("EkZ8ZqX11ozMamO9","E7gdkwWePBYT75KE", true);
        this.searchframe = searchframe;
        }

    public void actionPerformed(ActionEvent e){

        updatefromTagLocation( updatefromStageOfLife( updatefromGender(updatefromTrackingRange())));

    }

    private ArrayList<Ping> updatefromTrackingRange(){
        //1. read selected constraint from combo box

        tracking_range = (String)searchframe.getTracking_range().getSelectedItem();

        //2. get all shark components by tracking range
        if (tracking_range.equals("Last 24 Hours")) {
            searchframe.updateCentralPanel(jawsApi.past24Hours());
            return jawsApi.past24Hours();

        } else if (tracking_range.equals("Last Week")) {

            searchframe.updateCentralPanel(jawsApi.pastWeek());
            return jawsApi.pastWeek();

        } else if (tracking_range.equals("Last Month")) {

            searchframe.updateCentralPanel(jawsApi.pastMonth());
            return jawsApi.pastMonth();

        } else {
            System.out.println("SearchButtonListener Error 1 : Invalid ComboBox input");
            return null;
        }
    }

    private ArrayList<Ping> updatefromStageOfLife(ArrayList<Ping> listOfPings){
        stage_of_life = (String)searchframe.getStage_of_life().getSelectedItem();

        if (stage_of_life.equals("Mature")) {
            searchframe.updateCentralPanel(selectSharksByStageofLife(listOfPings,"Mature"));
            return selectSharksByStageofLife(listOfPings,"Mature");

        } else if (stage_of_life.equals("Immature")) {

            searchframe.updateCentralPanel(selectSharksByStageofLife(listOfPings,"Immature"));
            return selectSharksByStageofLife(listOfPings,"Immature");

        } else if (stage_of_life.equals("Undetermined")) {

            searchframe.updateCentralPanel(selectSharksByStageofLife(listOfPings,"Undetermined"));
            return selectSharksByStageofLife(listOfPings,"Undetermined");

        } else {
            System.out.println("SearchButtonListener Error 1 : Invalid ComboBox input");
            return null;
        }
    }

    private ArrayList<Ping> selectSharksByStageofLife(ArrayList<Ping> listOfPings, String selectionElement){
        System.out.println(selectionElement);
        ArrayList<Ping> newlistofPings = new ArrayList<Ping>();
        for (Ping ping: listOfPings){
           if( jawsApi.getShark(ping.getName()).getStageOfLife()== selectionElement){
               newlistofPings.add(ping);
           }
        }
        System.out.println(newlistofPings);
        return newlistofPings;
    }


    private ArrayList<Ping> updatefromGender(ArrayList<Ping> listOfPings){
        gender = (String)searchframe.getGender().getSelectedItem();

        if (stage_of_life.equals("Male")) {
            searchframe.updateCentralPanel(selectSharksByGender(listOfPings,"Male"));
            return selectSharksByGender(listOfPings,"Male");

        } else if (stage_of_life.equals("Female")) {

            searchframe.updateCentralPanel(selectSharksByGender(listOfPings,"Female"));
            return selectSharksByGender(listOfPings,"Female");
        }

        return listOfPings;
    }

    private ArrayList<Ping> selectSharksByGender(ArrayList<Ping> listOfPings, String selectionElement){
        System.out.println(selectionElement);
        ArrayList<Ping> newlistofPings = new ArrayList<Ping>();
        for (Ping ping: listOfPings){
            if( jawsApi.getShark(ping.getName()).getGender()== selectionElement){
                newlistofPings.add(ping);
            }
        }
        System.out.println(newlistofPings);
        return newlistofPings;
    }


    private void updatefromTagLocation(ArrayList<Ping> listOfPings){
        tag_location = (String)searchframe.getTag_location().getSelectedItem();

        if (tag_location!="All"){
            searchframe.updateCentralPanel(selectSharksByTagLocation(listOfPings,tag_location));
        }

    }

    private ArrayList<Ping> selectSharksByTagLocation(ArrayList<Ping> listOfPings, String selectionElement){
        System.out.println(selectionElement);
        ArrayList<Ping> newlistofPings = new ArrayList<Ping>();
        for (Ping ping: listOfPings){
            if( jawsApi.getShark(ping.getName()).getTagLocation() == selectionElement){
                newlistofPings.add(ping);
            }
        }
        System.out.println(newlistofPings);
        return newlistofPings;
    }
}

