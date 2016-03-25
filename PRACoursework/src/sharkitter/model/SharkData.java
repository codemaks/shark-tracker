package sharkitter.model;

import api.jaws.Ping;
import api.jaws.Shark;

public class SharkData implements Comparable<SharkData>{

    private String name;
    private String gender;
    private String stageOfLife;
    private String tagLocation;
    private String description;
    private String weight;
    private String length;
    private String species;
    private String date;


    public SharkData(Shark foundShark, Ping lastPing){
        populateSharkDetails(foundShark, lastPing);
    }

    private void populateSharkDetails(Shark foundShark, Ping ping){
        name = foundShark.getName();
        gender = foundShark.getGender();
        stageOfLife = foundShark.getStageOfLife();
        tagLocation = foundShark.getTagLocation();
        description = foundShark.getDescription();
        weight = foundShark.getWeight();
        length = foundShark.getLength();
        species = foundShark.getSpecies();
        date = ping.getTime();
    }

    public String getName(){
        return name;
    }

    public String getDate(){
        return date;
    }

    public String getTagLocation(){
        return tagLocation;
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
        return stageOfLife;
    }

    public String getDescription(){
        return description;
    }

    @Override
    public int compareTo(SharkData anotherSharkData) {
        return getDate().compareTo(anotherSharkData.getDate());
    }
}
