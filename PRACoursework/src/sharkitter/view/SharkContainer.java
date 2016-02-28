//placed in 'view' for now; feel free to move if necessary.
package sharkitter.view;

import api.jaws.Ping;
import api.jaws.Shark;

import java.awt.*;
import javax.swing.*;

public class SharkContainer extends JPanel{
    /**
     * Class constructor: provides a JPanel containing all the details of a Shark when the following parameters
     * are given:
     * Display all information concerning the Sharks that fit the chosen fields for the search.
     * @param foundShark	A Shark matching the chosen criteria.
     * @param lastPing	The last Ping for the matching Shark.
     */

    public SharkContainer(Shark foundShark, Ping lastPing){
        JPanel topPanel = new JPanel(new BorderLayout());

        topPanel.add(createSharkFeaturesTable(foundShark), BorderLayout.NORTH);

        topPanel.add(createSharkDescriptionText(foundShark), BorderLayout.CENTER);

        topPanel.add(createSharkTrackOptions(lastPing), BorderLayout.SOUTH);
    }

    /**
     * Create and display the description of a matching Shark.
     * @param foundShark	A Shark matching the chosen criteria.
     * @return	A JPanel containing all relevant information (description).
     */
    private JPanel createSharkDescriptionText(Shark foundShark) {
        JPanel descriptionPanel = new JPanel();

        descriptionPanel.add(new JLabel("Description: \n\n"));
        descriptionPanel.add(new JLabel(foundShark.getDescription()));

        return descriptionPanel;
    }
    /**
     * Create and display the last ping of a given Shark and the option to follow it.
     * @param lastPing	Object containing information about the last point of contact with a given shark.
     * @return	A JPanel containing all revelant information and a follow button.
     */
    private JPanel createSharkTrackOptions(Ping lastPing) {
        JPanel pingPanel = new JPanel(new BorderLayout());

        JLabel pingLabel = new JLabel("Last ping: " + lastPing.getTime());
        JButton followButton = new JButton("Follow");

        pingPanel.add(pingLabel, BorderLayout.CENTER);
        pingPanel.add(followButton, BorderLayout.EAST);

        return pingPanel;
    }

    /**
     * Create a table to display specific information about a Shark (name, gender, stage of life, species, length and weight).
     * @param foundShark	A Shark matching the chosen criteria.
     * @return	a JPanel containing all relevant information (name, gender, satge of life, species, length and weight).
     */
    private JPanel createSharkFeaturesTable(Shark foundShark) {
        JPanel sharkFeatures = new JPanel(new GridLayout(6, 2));

        JLabel nameLabel = new JLabel("Name: ");
        JLabel genderLabel = new JLabel("Gender: ");
        JLabel stageOfLifeLabel = new JLabel("Stage of Life: ");
        JLabel specieLabel = new JLabel("Species: ");
        JLabel lengthLabel = new JLabel("Length: ");
        JLabel weightLabel = new JLabel("Weight: ");

        JLabel sharkName = new JLabel(foundShark.getName());
        JLabel sharkGender = new JLabel(foundShark.getGender());
        JLabel sharkStageOfLife = new JLabel(foundShark.getStageOfLife());
        JLabel sharkSpecie = new JLabel(foundShark.getSpecies());
        JLabel sharkLength = new JLabel(foundShark.getLength());
        JLabel sharkWeight = new JLabel(foundShark.getWeight());

        sharkFeatures.add(nameLabel);
        sharkFeatures.add(sharkName);
        sharkFeatures.add(genderLabel);
        sharkFeatures.add(sharkGender);
        sharkFeatures.add(stageOfLifeLabel);
        sharkFeatures.add(sharkStageOfLife);
        sharkFeatures.add(specieLabel);
        sharkFeatures.add(sharkSpecie);
        sharkFeatures.add(lengthLabel);
        sharkFeatures.add(sharkLength);
        sharkFeatures.add(weightLabel);
        sharkFeatures.add(sharkWeight);

        return sharkFeatures;
    }
}
