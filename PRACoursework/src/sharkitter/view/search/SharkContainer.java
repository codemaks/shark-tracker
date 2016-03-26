package sharkitter.view.search;

import sharkitter.controller.FavouriteController;
import sharkitter.model.FavouriteSharks;
import sharkitter.model.SharkData;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class SharkContainer extends JPanel {

    private FavouriteController favouriteController;
    private JButton followButton;
    private SharkData shark;

    /**
     * Class constructor: provides a JPanel containing all the details of a Shark when the following parameters
     * are given:
     * Display all information concerning the Sharks that fit the chosen fields for the search.
     * @param sharkData	A SharkData containing the Shark matching the chosen criteria.
     * @param favouriteSharks	A list of sharks to which the corresponding shark could belong to.
     */
    public SharkContainer(SharkData sharkData, FavouriteSharks favouriteSharks){
        setLayout(new BorderLayout());
        this.shark = sharkData;
        setName(shark.getName());

        favouriteController = new FavouriteController(this, favouriteSharks);

        add(createSharkFeaturesTable(shark), BorderLayout.NORTH);

        add(createSharkDescriptionText(shark), BorderLayout.CENTER);

        add(createSharkTrackOptions(sharkData.getDate()), BorderLayout.SOUTH);

        setPreferredSize(new Dimension(800,300));

        Border blackLineBorder = BorderFactory.createLineBorder(Color.black);
        setBorder(blackLineBorder);

        setVisible(true);
    }

    /**
     * Create and display the description of a matching Shark.
     * @param foundShark A Shark matching the chosen criteria.
     * @return	A JPanel containing all relevant information (description).
     */
    private JPanel createSharkDescriptionText(SharkData foundShark) {
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BorderLayout());

        JLabel descriptionLabel = new JLabel("<html> Description: <br><br>" + foundShark.getDescription() + "</html?>");
        descriptionPanel.add(descriptionLabel,BorderLayout.CENTER);

        return descriptionPanel;
    }
    /**
     * Create and display the last ping of a given Shark and the option to follow it.
     * @param date String object containing information about the last point of contact with a given shark.
     * @return	A JPanel containing all relevant information and a follow button.
     */
    private JPanel createSharkTrackOptions(String date) {
        JPanel pingPanel = new JPanel(new BorderLayout());

        JLabel pingLabel = new JLabel("Last ping: " + date);

        followButton = new JButton("Follow");

        followButton.addActionListener(favouriteController);

        pingPanel.add(pingLabel, BorderLayout.CENTER);
        pingPanel.add(followButton, BorderLayout.EAST);
        setVisible(true);

        return pingPanel;
    }

    /**
     * Create a table to display specific information about a Shark (name, gender, stage of life, species, length and weight).
     * @param foundShark    A Shark matching the chosen criteria.
     * @return	a JPanel containing all relevant information (name, gender, stage of life, species, length and weight).
     */
    private JPanel createSharkFeaturesTable(SharkData foundShark) {
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
        setVisible(true);

        return sharkFeatures;
    }

    /**
     * Getter of the corresponding shark
     * @return  The Shark object displayed by this container
     */
    public SharkData getShark() {
        return shark;
    }

    /**
     * Updates a button with the corresponding text
     * @param text  New text for the JButton
     */
    public void updateFollowButton(String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                followButton.setText(text);
            }
        });
    }
}