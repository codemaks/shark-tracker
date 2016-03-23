package sharkitter.model;

import api.jaws.Ping;
import api.jaws.Shark;

import java.util.HashMap;

/**
 * Created by Evou on 22/03/2016.
 */
public class SharkData implements Comparable<SharkData>{

    private String name;
    private String gender;
    private String stageoflife;
    private String taglocation;
    private String description;
    private String weight;
    private String length;
    private String species;
    private String date;
    private Shark  shark;


    public SharkData(Shark foundShark,Ping lastPing){
        populateSharkDetails(foundShark,lastPing);
    }

    private void populateSharkDetails(Shark foundShark, Ping ping){
        name=foundShark.getName();
        gender=foundShark.getGender();
        stageoflife=foundShark.getStageOfLife();
        taglocation=foundShark.getTagLocation();
        description=foundShark.getDescription();
        weight= foundShark.getWeight();
        length=foundShark.getLength();
        species=foundShark.getSpecies();
        date=ping.getTime();
        shark=foundShark;

    }

    public Shark getShark(){
        return shark;
    }

    public String getName(){
        return name;
    }

    public String getDate(){
        return date;
    }

    public String getTaglocation(){
        return taglocation;
    }

    public String getWeight(){
        return weight;
    }

    public String getSpecies(){
        return species;
    }

    public String getLength(){
        return length;
    }

    public String getGender(){
        return gender;
    }

    public String getStageOfLife(){
        return stageoflife;
    }

    public String getDescription(){
        return description;
    }

    @Override
    public int compareTo(SharkData anotherSharkData) {
        return getDate().compareTo(anotherSharkData.getDate());
    }
}
