package sharkitter.controller;

import api.jaws.Jaws;
import sharkitter.api.JawsApi;
import sharkitter.model.PingCollection;
import sharkitter.model.SharkData;
import sharkitter.view.search.SearchFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Class controlling the search:
 * - listens to the "Search" button
 * - updates the view according to the result of the search
 */
public class SearchButtonListener implements ActionListener{
    private SearchFrame searchframe;
    private List<SharkData> listOfSharks;
    private PingCollection pingCollection;
    private Jaws jawsApi;
    private TrackingRangeUpdater comboBoxListener;

    /**
     * Instantiates a new SearchButtonListener (controller of type ActionListener) from a search frame and a ping
     * collection
     * @param searchframe   Object representation of the Search frame
     * @param pingCollection    Collection of pings
     */
    public SearchButtonListener(SearchFrame searchframe, PingCollection pingCollection){
        jawsApi = JawsApi.getInstance();
        this.searchframe = searchframe;
        this.pingCollection = pingCollection;
        listOfSharks = new ArrayList<>();
        comboBoxListener = new TrackingRangeUpdater(pingCollection);
    }

    /**
     * void method inherited from the ActionListener interface. Updates the Pingcollection and the list of shark data
     * by the combobox selection as soon as an action is performed
     * @param e, an action event
     */
    public void actionPerformed(ActionEvent e){
        pingCollection.update();
        listOfSharks = updateFromTagLocation(updateFromStageOfLife(updateFromGender(comboBoxListener.updateFromTrackingRange(searchframe.getTrackingRange()))));
        searchframe.addSeveralSharkContainersToView(listOfSharks);
    }

    /**
     * Retrieves sharks according to their stage of life
     * @param listOfSharks  List of shark to be analysed
     * @return  Sorted list of shark by stage of life
     */
    private List<SharkData> updateFromStageOfLife(List<SharkData> listOfSharks){
        String stageOfLife = (String) searchframe.getStageOfLife().getSelectedItem();

        if (!stageOfLife.equals("All")){
            listOfSharks = selectSharksByStageOfLife(listOfSharks, stageOfLife);
        }
        return(listOfSharks);
    }

    /**
     * Selects sharks by stage of life
     * @param sharkDataList List of sharks to be analysed
     * @param selectionElement  String representation of the wanted stage of life
     * @return  A list of SharkData selected by the given stage of life
     */
    private List<SharkData> selectSharksByStageOfLife(List<SharkData> sharkDataList, String selectionElement){

        List<SharkData> newSharkDataList = new ArrayList<> ();
        for (SharkData sharkData: sharkDataList){
           if( jawsApi.getShark(sharkData.getName()).getStageOfLife().equals(selectionElement)){

               newSharkDataList.add(sharkData);
           }
        }

        return newSharkDataList;
    }

    /**
     * Retrieves sharks according to their gender
     * @param sharkDataList List of shark to be analysed
     * @return  Sorted list of shark by gender
     */
    private List<SharkData> updateFromGender(List<SharkData> sharkDataList){
        String gender = (String) searchframe.getGender().getSelectedItem();

        if (!gender.equals("All")){
            sharkDataList = selectSharksByGender(sharkDataList, gender);
        }
        return sharkDataList;
    }

    /**
     * Selects sharks by gender
     * @param sharkDataList List of sharks to be analysed
     * @param selectionElement  String representation of the wanted gender
     * @return  A list of SharkData selected by gender
     */
    private List<SharkData> selectSharksByGender(List<SharkData> sharkDataList, String selectionElement){

        List<SharkData> newSharkDataList = new ArrayList<> ();
        for (SharkData SharkData: sharkDataList){
            if( jawsApi.getShark(SharkData.getName()).getGender().equals(selectionElement)){
                newSharkDataList.add(SharkData);
            }
        }
        return newSharkDataList;
    }

    /**
     * Retrieves sharks according to their tag location
     * @param sharkDataList List of shark to be analysed
     * @return  Sorted list of shark by tag location
     */
    private List<SharkData> updateFromTagLocation(List<SharkData> sharkDataList){
        String tagLocation = (String) searchframe.getTagLocation().getSelectedItem();

        if (!tagLocation.equals("All")){
            sharkDataList = selectSharksByTagLocation(sharkDataList, tagLocation);
        }
        return sharkDataList;

    }

    /**
     * Selects sharks by tag location
     * @param listOfPings List of sharks to be analysed
     * @param selectionElement  String representation of the wanted tag location
     * @return  A list of SharkData selected by tag location
     */
    private List<SharkData> selectSharksByTagLocation(List<SharkData> listOfPings, String selectionElement){
        List<SharkData>  newListOfPings = new ArrayList<> ();
        for (SharkData SharkData: listOfPings){
            if( jawsApi.getShark(SharkData.getName()).getTagLocation().equals(selectionElement)){
                newListOfPings.add(SharkData);
            }
        }
        return newListOfPings;
    }
}

