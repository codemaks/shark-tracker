package sharkitter.model;

import api.jaws.Ping;
import api.jaws.Shark;

/**
 * An object representation of a shark and all associated information, including the date of the last ping.
 */
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

	/**
	 * Constructs a new SharkData object for the given shark and ping.
	 * @param foundShark the shark to be included.
	 * @param lastPing the ping to be included.
	 */
    public SharkData(Shark foundShark, Ping lastPing){
        populateSharkDetails(foundShark, lastPing);
    }

	/**
	 * Enters the details of the shark into the SharkData object.
	 * @param foundShark the shark to be included.
	 * @param ping the ping to be included.
	 */
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

	/**
	 * Returns the shark's name.
	 * @return the shark's name.
	 */
    public String getName(){
        return name;
    }

	/**
	 * Returns the date of the ping.
	 * @return the date of the ping.
	 */
    public String getDate(){
        return date;
    }

	/**
	 * Returns the location the shark was tagged at.
	 * @return the location the shark was tagged at.
	 */
    public String getTagLocation(){
        return tagLocation;
    }

	/**
	 * Returns the weight of the shark.
	 * @return the weight of the shark.
	 */
    public String getWeight(){
        return weight;
    }

	/**
	 * Returns the species of the shark.
	 * @return the species of the shark.
	 */
    public String getSpecies(){
        return species;
    }

	/**
	 * Returns the length of the shark.
	 * @return the length of the shark.
	 */
    public String getLength(){
        return length;
    }

	/**
	 * Returns the gender of the shark.
	 * @return the gender of the shark.
	 */
    public String getGender(){
        return gender;
    }

	/**
	 * Returns the stage of life the shark is currently in.
	 * @return the stage of life the shark is currently in.
	 */
    public String getStageOfLife(){
        return stageOfLife;
    }

	/**
	 * Returns the description of the shark.
	 * @return the description of the shark.
	 */
    public String getDescription(){
        return description;
    }

	/**
	 * Compares the date of the shark's ping to another ping.
	 * @param anotherSharkData the SharkData object to compare the ping date for.
	 * @return 0 if they are the same, a negative value if the other date is after the shark's ping, and a positive value if
	 * the shark's ping is after the other ping.
	 */
    @Override
    public int compareTo(SharkData anotherSharkData) {
        return getDate().compareTo(anotherSharkData.getDate());
    }
}
