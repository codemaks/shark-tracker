package sharkitter.model;

import api.jaws.Ping;
import api.jaws.Shark;

import java.util.HashMap;

/**
 * Created by Evou on 22/03/2016.
 */
public class SharkData implements Comparable<SharkData>{

    private HashMap<String,String> sharkdetails;
    private Shark shark;

    public SharkData(Shark foundShark,Ping lastPing){
        shark = foundShark;
        sharkdetails = new HashMap<>();
        populateSharkDetails(foundShark,lastPing);
    }

    private void populateSharkDetails(Shark foundShark, Ping ping){
        sharkdetails.put("name",foundShark.getName());
        sharkdetails.put("gender",foundShark.getGender());
        sharkdetails.put("stageoflife",foundShark.getStageOfLife());
        sharkdetails.put("taglocation",foundShark.getTagLocation());
        sharkdetails.put("description",foundShark.getDescription());
        sharkdetails.put("weight", foundShark.getWeight());
        sharkdetails.put("length",foundShark.getLength());
        sharkdetails.put("species",foundShark.getSpecies());
        sharkdetails.put("date",ping.getTime());
    }

    public String getName(){
        return sharkdetails.get("name");
    }

    public String getDate(){
        return sharkdetails.get("date");
    }

    public String getTagloc(){
        return sharkdetails.get("taglocation");
    }

    public String getWeight(){
        return sharkdetails.get("weight");
    }

    public String getSpecies(){
        return sharkdetails.get("species");
    }

    public String getLength(){
        return sharkdetails.get("length");
    }

    public String getGender(){
        return sharkdetails.get("gender");
    }

    public String getStageOfLife(){
        return sharkdetails.get("stageoflife");
    }

    public String getDescription(){
        return sharkdetails.get("description");
    }

    public Shark getShark(){
        return shark;
    }

    @Override
    public int compareTo(SharkData anotherSharkData) {
        return getDate().compareTo(anotherSharkData.getDate());
    }
}
