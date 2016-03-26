package sharkitter.controller;

import api.jaws.Jaws;
import api.jaws.Ping;
import sharkitter.api.JawsApi;
import sharkitter.model.PingCollection;
import sharkitter.model.SharkData;
import sharkitter.view.SearchFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SearchButtonListener implements ActionListener{
    private SearchFrame searchframe;
    private List<SharkData> listOfSharks;
    private PingCollection pingCollection;
    private Jaws jawsApi;

    public SearchButtonListener(SearchFrame searchframe, PingCollection pingCollection){
        jawsApi = JawsApi.getInstance();
        this.searchframe = searchframe;
        this.pingCollection = pingCollection;
        listOfSharks = new ArrayList<>();
    }

    public void actionPerformed(ActionEvent e){
        pingCollection.update();
        listOfSharks = updateFromTagLocation(updateFromStageOfLife(updateFromGender(updateFromTrackingRange())));
        searchframe.addSeveralSharkContainersToView(listOfSharks);
    }

    private List<SharkData> updateFromTrackingRange(){
        //1. read selected constraint from combo box

        String trackingRange = (String) searchframe.getTrackingRange().getSelectedItem();

        //TODO Duplicated code inside StatisticsItemListener
        //2. get all shark components by tracking range
        if (trackingRange.equals("Last 24 Hours")) {
            for(Ping ping: pingCollection.getPast24hours().values()){

               listOfSharks.add(new SharkData(jawsApi.getShark(ping.getName()), ping));
            }

        } else if (trackingRange.equals("Last Week")) {
            for(Ping ping: pingCollection.getPastWeek().values()){

                listOfSharks.add(new SharkData(jawsApi.getShark(ping.getName()),ping));
            }

        } else if (trackingRange.equals("Last Month")) {
            for(Ping ping: pingCollection.getPastMonth().values()){

                listOfSharks.add(new SharkData(jawsApi.getShark(ping.getName()),ping));
            }

        } else {
           throw new IllegalArgumentException("SearchButtonListener Error 1 : Invalid ComboBox input");
        }
        return listOfSharks;
    }

    private List<SharkData> updateFromStageOfLife(List<SharkData> listOfSharks){
        String stageOfLife = (String) searchframe.getStageOfLife().getSelectedItem();

        if (!stageOfLife.equals("All")){
            listOfSharks = selectSharksByStageOfLife(listOfSharks, stageOfLife);
        }
        return(listOfSharks);
    }

    private List<SharkData> selectSharksByStageOfLife(List<SharkData> sharkDataList, String selectionElement){

        List<SharkData> newSharkDataList = new ArrayList<> ();
        for (SharkData sharkData: sharkDataList){
           if( jawsApi.getShark(sharkData.getName()).getStageOfLife().equals(selectionElement)){

               newSharkDataList.add(sharkData);
           }
        }

        return newSharkDataList;
    }


    private List<SharkData> updateFromGender(List<SharkData> sharkDataList){
        String gender = (String) searchframe.getGender().getSelectedItem();

        if (!gender.equals("All")){
            sharkDataList = selectSharksByGender(sharkDataList, gender);
        }
        return sharkDataList;
    }

    private List<SharkData> selectSharksByGender(List<SharkData> sharkDataList, String selectionElement){

        List<SharkData> newSharkDataList = new ArrayList<> ();
        for (SharkData SharkData: sharkDataList){
            if( jawsApi.getShark(SharkData.getName()).getGender().equals(selectionElement)){
                newSharkDataList.add(SharkData);
            }
        }
        return newSharkDataList;
    }


    private List<SharkData> updateFromTagLocation(List<SharkData> sharkDataList){
        String tagLocation = (String) searchframe.getTagLocation().getSelectedItem();

        if (!tagLocation.equals("All")){
            sharkDataList = selectSharksByTagLocation(sharkDataList, tagLocation);
        }
        return sharkDataList;

    }

    private List<SharkData>  selectSharksByTagLocation(List<SharkData>  listOfPings, String selectionElement){
        List<SharkData>  newListOfPings = new ArrayList<> ();
        for (SharkData SharkData: listOfPings){
            if( jawsApi.getShark(SharkData.getName()).getTagLocation().equals(selectionElement)){
                newListOfPings.add(SharkData);
            }
        }
        return newListOfPings;
    }
}

