package sharkitter.controller;

import api.jaws.Jaws;
import api.jaws.Ping;
import sharkitter.model.ListOfSharkData;
import sharkitter.model.SharkData;
import sharkitter.view.SearchFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class SearchButtonListener implements ActionListener{
    private SearchFrame searchframe;
    private ListOfSharkData listOfSharkData;
    private String tracking_range;
    private String gender;
    private String tag_location;
    private String stage_of_life;
    private Jaws jawsApi;

    public SearchButtonListener(SearchFrame searchframe){
        jawsApi = new Jaws("EkZ8ZqX11ozMamO9","E7gdkwWePBYT75KE", true);
        this.searchframe = searchframe;
        listOfSharkData = new ListOfSharkData();
        }

    public void actionPerformed(ActionEvent e){
        sortByDate(updatefromTagLocation(updatefromStageOfLife(updatefromGender(updatefromTrackingRange()))));
    }

    private void sortByDate(ListOfSharkData listOfSharkData) {
        //clear the central panel
        searchframe.clear();
        //pass in all SharkData
        //create hashmap of Shark names to SharkData
        HashMap<String, ListOfSharkData> sortBySharkName = new HashMap<>();
        //loop through SharkData and add them to hashmap
        for (SharkData sharkdata : listOfSharkData) {

            String sharkName;
            sharkName = sharkdata.getName();

            //find an existing arrayList or create one
            ListOfSharkData currentSharks = sortBySharkName.getOrDefault(sharkName, new ListOfSharkData());
            //add SharkData to that arrayList
            currentSharks.add(sharkdata);
            //put that arrayList back if it was created
            sortBySharkName.putIfAbsent(sharkName, currentSharks);
        }

        ArrayList<SharkData> reverseArray = new ArrayList<>();

        for(String currentName: sortBySharkName.keySet()){
            //pull all the SharkDatas with the same name into a temporary arrayList
            ListOfSharkData currentList = sortBySharkName.get(currentName);
            //sort the SharkDatas by their date
                    Collections.sort(currentList);

            sortBySharkName.put(currentName, currentList);

            if(!currentList.isEmpty()){
                reverseArray.add(currentList.get(currentList.size()-1));
            }
        }
            //sort the SharkData by their date again
            Collections.sort(reverseArray);
            //reverse the Array to get the latest SharkData on top of the list
            for(int i=reverseArray.size()-1; i>-1 ;i--){
                listOfSharkData.add(reverseArray.get(i));
            }

        searchframe.addSeveralSharkContainersToView(listOfSharkData);
    }

    private ListOfSharkData updatefromTrackingRange(){
        //1. read selected constraint from combo box

        tracking_range = (String)searchframe.getTracking_range().getSelectedItem();

        //2. get all shark components by tracking range
        if (tracking_range.equals("Last 24 Hours")) {
            for(Ping ping: jawsApi.past24Hours()){
               listOfSharkData.add(new SharkData(jawsApi.getShark(ping.getName()),ping));
            }

        } else if (tracking_range.equals("Last Week")) {
            for(Ping ping: jawsApi.pastWeek()){
                listOfSharkData.add(new SharkData(jawsApi.getShark(ping.getName()),ping));
            }

        } else if (tracking_range.equals("Last Month")) {
            for(Ping ping: jawsApi.pastMonth()){
                listOfSharkData.add(new SharkData(jawsApi.getShark(ping.getName()),ping));
            }

        } else {
            System.out.println("SearchButtonListener Error 1 : Invalid ComboBox input");
        }
        return listOfSharkData;
    }

    private ListOfSharkData updatefromStageOfLife(ListOfSharkData listOfSharkData){
        stage_of_life = (String)searchframe.getStage_of_life().getSelectedItem();

        if (stage_of_life!="All"){
            listOfSharkData=selectSharksByStageofLife(listOfSharkData,stage_of_life);
        }
        return(listOfSharkData);
    }

    private ListOfSharkData selectSharksByStageofLife(ListOfSharkData listOfSD, String selectionElement){
        System.out.println("selectionElement: "+selectionElement);
        ListOfSharkData newlistofSD = new ListOfSharkData ();
        for (SharkData sharkData: listOfSD){
           if( jawsApi.getShark(sharkData.getName()).getStageOfLife().equals(selectionElement)){
               System.out.println("got there! selectionelement: " + selectionElement + " :");
               newlistofSD.add(sharkData);
           }
        }
        System.out.println("stageoflife_newlistofPings: "+newlistofSD);
        return newlistofSD;
    }


    private ListOfSharkData updatefromGender(ListOfSharkData listOfSD){
        gender = (String)searchframe.getGender().getSelectedItem();

        if (gender!="All"){
            listOfSD = selectSharksByGender(listOfSD,gender);
        }
        return listOfSD;
    }

    private ListOfSharkData  selectSharksByGender(ListOfSharkData  listOfSD, String selectionElement){
        System.out.println("gender: " +selectionElement);
        ListOfSharkData newlistOfSD = new ListOfSharkData ();
        for (SharkData SharkData: listOfSD){
            if( jawsApi.getShark(SharkData.getName()).getGender().equals(selectionElement)){
                System.out.println("got there! selectionelement: " + selectionElement + " :");
                newlistOfSD.add(SharkData);
            }
        }
        System.out.println("gender_newlistofPings: "+newlistOfSD);
        return newlistOfSD;
    }


    private ListOfSharkData  updatefromTagLocation(ListOfSharkData  listOfSD){
        tag_location = (String)searchframe.getTag_location().getSelectedItem();

        if (tag_location!="All"){
            listOfSD=selectSharksByTagLocation(listOfSD,tag_location);
        }
        System.out.println(listOfSD);
        return listOfSD;

    }

    private ListOfSharkData  selectSharksByTagLocation(ListOfSharkData  listOfPings, String selectionElement){
        System.out.println("tag location: "+ selectionElement);
        ListOfSharkData  newlistofPings = new ListOfSharkData ();
        for (SharkData SharkData: listOfPings){
            if( jawsApi.getShark(SharkData.getName()).getTagLocation().equals(selectionElement)){
                System.out.println("got there! selectionelement: " + selectionElement + " :");
                newlistofPings.add(SharkData);
            }
        }
        System.out.println("tagloc_newlistofPings: "+newlistofPings);
        return newlistofPings;
    }
}

