package sharkitter.controller;

import api.jaws.Jaws;
import api.jaws.Ping;
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
    private String tracking_range;
    private String gender;
    private String tag_location;
    private String stage_of_life;
    private PingCollection pingCollection;
    private Jaws jawsApi;

    public SearchButtonListener(SearchFrame searchframe, PingCollection pingCollection){
        jawsApi = new Jaws("EkZ8ZqX11ozMamO9","E7gdkwWePBYT75KE", true);
        this.searchframe = searchframe;
        this.pingCollection = pingCollection;
        listOfSharks = new ArrayList<>();
    }

    public void actionPerformed(ActionEvent e){
        pingCollection.update();
        listOfSharks= updatefromTagLocation(updatefromStageOfLife(updatefromGender(updatefromTrackingRange())));
        searchframe.addSeveralSharkContainersToView(listOfSharks);
    }

    private List<SharkData> updatefromTrackingRange(){
        //1. read selected constraint from combo box

        tracking_range = (String)searchframe.getTracking_range().getSelectedItem();

        //2. get all shark components by tracking range
        if (tracking_range.equals("Last 24 Hours")) {
            for(Ping ping: pingCollection.getPast24hours().values()){

               listOfSharks.add(new SharkData(jawsApi.getShark(ping.getName()),ping));
            }

        } else if (tracking_range.equals("Last Week")) {
            for(Ping ping: pingCollection.getPastWeek().values()){

                listOfSharks.add(new SharkData(jawsApi.getShark(ping.getName()),ping));
            }

        } else if (tracking_range.equals("Last Month")) {
            for(Ping ping: pingCollection.getPastMonth().values()){

                listOfSharks.add(new SharkData(jawsApi.getShark(ping.getName()),ping));
            }

        } else {
            //TODO removed Println
            System.out.println("SearchButtonListener Error 1 : Invalid ComboBox input");
        }
        return listOfSharks;
    }

    private List<SharkData> updatefromStageOfLife(List<SharkData> listofsharks){
        stage_of_life = (String)searchframe.getStage_of_life().getSelectedItem();

        if (stage_of_life!="All"){
            listofsharks=selectSharksByStageofLife(listofsharks,stage_of_life);
        }
        return(listofsharks);
    }

    private List<SharkData> selectSharksByStageofLife(List<SharkData> listOfSD, String selectionElement){
        //TODO remove println
        System.out.println("selectionElement: "+selectionElement);
        List<SharkData> newlistofSD = new ArrayList<SharkData> ();
        for (SharkData sharkData: listOfSD){
           if( jawsApi.getShark(sharkData.getName()).getStageOfLife().equals(selectionElement)){
               //TODO remove println
               System.out.println("got there! selectionelement: " + selectionElement + " :");
               newlistofSD.add(sharkData);
           }
        }
        //TODO remove println
        System.out.println("stageoflife_newlistofPings: "+newlistofSD);
        return newlistofSD;
    }


    private List<SharkData> updatefromGender(List<SharkData> listOfSD){
        gender = (String)searchframe.getGender().getSelectedItem();

        if (gender!="All"){
            listOfSD = selectSharksByGender(listOfSD,gender);
        }
        return listOfSD;
    }

    private List<SharkData> selectSharksByGender(List<SharkData> listOfSD, String selectionElement){
        //TODO remove println
        System.out.println("gender: " +selectionElement);
        List<SharkData> newlistOfSD = new ArrayList<SharkData> ();
        for (SharkData SharkData: listOfSD){
            if( jawsApi.getShark(SharkData.getName()).getGender().equals(selectionElement)){
                //TODO remove println
                System.out.println("got there! selectionelement: " + selectionElement + " :");
                newlistOfSD.add(SharkData);
            }
        }
        //TODO remove println
        System.out.println("gender_newlistofPings: "+newlistOfSD);
        return newlistOfSD;
    }


    private List<SharkData> updatefromTagLocation(List<SharkData> listOfSD){
        tag_location = (String)searchframe.getTag_location().getSelectedItem();

        if (tag_location!="All"){
            listOfSD=selectSharksByTagLocation(listOfSD,tag_location);
        }
        //TODO remove println
        System.out.println(listOfSD);
        return listOfSD;

    }

    private List<SharkData>  selectSharksByTagLocation(List<SharkData>  listOfPings, String selectionElement){
        //TODO remove println
        System.out.println("tag location: "+ selectionElement);
        List<SharkData>  newlistofPings = new ArrayList<SharkData> ();
        for (SharkData SharkData: listOfPings){
            if( jawsApi.getShark(SharkData.getName()).getTagLocation().equals(selectionElement)){
                //TODO remove println
                System.out.println("got there! selectionelement: " + selectionElement + " :");
                newlistofPings.add(SharkData);
            }
        }
        //TODO remove println
        System.out.println("tagloc_newlistofPings: "+newlistofPings);
        return newlistofPings;
    }
}

