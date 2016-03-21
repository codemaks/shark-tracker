package sharkitter.controller;

import api.jaws.Jaws;
import api.jaws.Ping;
import api.jaws.Shark;
import sharkitter.view.SearchFrame;
import sharkitter.view.SharkContainer;
import sun.security.provider.SHA;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchButtonListener implements ActionListener{
    private SearchFrame searchframe;
    private ArrayList<SharkContainer> allSharkContainers;
    private String tracking_range;
    private String gender;
    private String tag_location;
    private String stage_of_life;
    private Jaws jawsApi;

    public SearchButtonListener(SearchFrame searchframe){
        jawsApi = new Jaws("EkZ8ZqX11ozMamO9","E7gdkwWePBYT75KE", true);
        this.searchframe = searchframe;
        allSharkContainers = new ArrayList<>();
        }

    public void actionPerformed(ActionEvent e){
        sortByDate(updatefromTagLocation(updatefromStageOfLife(updatefromGender(updatefromTrackingRange()))));
    }

    private void sortByDate(ArrayList<SharkContainer> listOfSharkContainers) {
        //clear the central panel
        searchframe.clear();
        //pass in all SharkContainers
        allSharkContainers = new ArrayList<>();
        //create hashmap of Shark names to SharkContainers
        HashMap<String, ArrayList<SharkContainer>> sortBySharkName = new HashMap<>();
        //loop through SharkContainers and add them to hashmap
        for (SharkContainer container : listOfSharkContainers) {

            String sharkName;
            sharkName = container.getSharkName();

            //find an existing arrayList or create one
            ArrayList<SharkContainer> currentSharks = sortBySharkName.getOrDefault(sharkName, new ArrayList<>());
            //add SharkContainer to that arrayList
            currentSharks.add(container);
            //put that arrayList back if it was created
            sortBySharkName.putIfAbsent(sharkName, currentSharks);
        }

        ArrayList<SharkContainer> reverseArray = new ArrayList<>();

        for(String currentName: sortBySharkName.keySet()){
            //pull all the SharkContainers with the same name into a temporary arrayList
            ArrayList<SharkContainer> currentList = sortBySharkName.get(currentName);
            //sort the sharkContainers by their date
                    Collections.sort(currentList);

            sortBySharkName.put(currentName, currentList);

            if(!currentList.isEmpty()){
                reverseArray.add(currentList.get(currentList.size()-1));
            }
        }
            //sort the sharkContainers by their date again
            Collections.sort(reverseArray);
            for(int i=reverseArray.size()-1; i>-1 ;i--){
                allSharkContainers.add(reverseArray.get(i));
            }

        searchframe.addSeveralSharkContainersToView(allSharkContainers);
    }

    private ArrayList<SharkContainer> updatefromTrackingRange(){
        //1. read selected constraint from combo box

        tracking_range = (String)searchframe.getTracking_range().getSelectedItem();

        //2. get all shark components by tracking range
        if (tracking_range.equals("Last 24 Hours")) {
            for(Ping ping: jawsApi.past24Hours()){
                allSharkContainers.add(new SharkContainer(jawsApi.getShark(ping.getName()),ping,null));
            }

        } else if (tracking_range.equals("Last Week")) {
            for(Ping ping: jawsApi.pastWeek()){
                allSharkContainers.add(new SharkContainer(jawsApi.getShark(ping.getName()),ping,null));
            }

        } else if (tracking_range.equals("Last Month")) {
            for(Ping ping: jawsApi.pastMonth()){
                allSharkContainers.add(new SharkContainer(jawsApi.getShark(ping.getName()),ping,null));
            }

        } else {
            System.out.println("SearchButtonListener Error 1 : Invalid ComboBox input");
        }
        return allSharkContainers;
    }

    private ArrayList<SharkContainer> updatefromStageOfLife(ArrayList<SharkContainer>  listOfPings){
        stage_of_life = (String)searchframe.getStage_of_life().getSelectedItem();

        if (stage_of_life!="All"){
            listOfPings=selectSharksByStageofLife(listOfPings,stage_of_life);
        }
        return(listOfPings);
    }

    private ArrayList<SharkContainer>  selectSharksByStageofLife(ArrayList<SharkContainer>  listOfSCs, String selectionElement){
        System.out.println("selectionElement: "+selectionElement);
        ArrayList<SharkContainer>  newlistofSCs = new ArrayList<SharkContainer> ();
        for (SharkContainer sharkContainer: listOfSCs){
           if( jawsApi.getShark(sharkContainer.getName()).getStageOfLife().equals(selectionElement)){
               System.out.println("got there! selectionelement: " + selectionElement + " :");
               newlistofSCs.add(sharkContainer);
           }
        }
        System.out.println("stageoflife_newlistofPings: "+newlistofSCs);
        return newlistofSCs;
    }


    private ArrayList<SharkContainer>  updatefromGender(ArrayList<SharkContainer>  listOfSCs){
        gender = (String)searchframe.getGender().getSelectedItem();

        if (gender!="All"){
            listOfSCs = selectSharksByGender(listOfSCs,gender);
        }
        return listOfSCs;
    }

    private ArrayList<SharkContainer>  selectSharksByGender(ArrayList<SharkContainer>  listOfSCs, String selectionElement){
        System.out.println("gender: " +selectionElement);
        ArrayList<SharkContainer> newlistofSCs = new ArrayList<SharkContainer> ();
        for (SharkContainer sharkContainer: listOfSCs){
            if( jawsApi.getShark(sharkContainer.getName()).getGender().equals(selectionElement)){
                System.out.println("got there! selectionelement: " + selectionElement + " :");
                newlistofSCs.add(sharkContainer);
            }
        }
        System.out.println("gender_newlistofPings: "+newlistofSCs);
        return newlistofSCs;
    }


    private ArrayList<SharkContainer>  updatefromTagLocation(ArrayList<SharkContainer>  listOfSCs){
        tag_location = (String)searchframe.getTag_location().getSelectedItem();

        if (tag_location!="All"){
            listOfSCs=selectSharksByTagLocation(listOfSCs,tag_location);
        }
        System.out.println(listOfSCs);
        return listOfSCs;

    }

    private ArrayList<SharkContainer>  selectSharksByTagLocation(ArrayList<SharkContainer>  listOfPings, String selectionElement){
        System.out.println("tag location: "+ selectionElement);
        ArrayList<SharkContainer>  newlistofPings = new ArrayList<SharkContainer> ();
        for (SharkContainer sharkContainer: listOfPings){
            if( jawsApi.getShark(sharkContainer.getName()).getTagLocation().equals(selectionElement)){
                System.out.println("got there! selectionelement: " + selectionElement + " :");
                newlistofPings.add(sharkContainer);
            }
        }
        System.out.println("tagloc_newlistofPings: "+newlistofPings);
        return newlistofPings;
    }
}

