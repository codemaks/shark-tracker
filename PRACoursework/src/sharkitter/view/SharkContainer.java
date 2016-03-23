package sharkitter.view;

import api.jaws.Ping;
import api.jaws.Shark;
import sharkitter.controller.FavouriteController;
import sharkitter.model.FavouriteSharks;
import sharkitter.model.SharkData;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class SharkContainer extends JPanel {

    private FavouriteController favouriteController;
    private JButton followButton;
    private FavouriteSharks favouriteSharks;
    private SharkData shark;
    private JLabel descriptionlabel;

    /**
     * Class constructor: provides a JPanel containing all the details of a Shark when the following parameters
     * are given:
     * Display all information concerning the Sharks that fit the chosen fields for the search.
     * @param shark	A SharkData containing the Shark matching the chosen criteria.
     * @param favouriteSharks	A list of sharks to which the corresponding shark could belong to.
     */
    public SharkContainer(SharkData shark, FavouriteSharks favouriteSharks){
        setLayout(new BorderLayout());
        this.shark = shark;
        setName(shark.getName());

        favouriteController = new FavouriteController(this, favouriteSharks);

        add(createSharkFeaturesTable(shark), BorderLayout.NORTH);

        add(createSharkDescriptionText(shark), BorderLayout.WEST);

        add(createSharkTrackOptions(shark.getDate()), BorderLayout.SOUTH);
        setPreferredSize(new Dimension(800,200));

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
        TitledBorder title;
        title = BorderFactory.createTitledBorder("Description");
        descriptionPanel.setBorder(title);
        descriptionlabel=new JLabel("<html><br><dr>"+foundShark.getDescription()+"</html?>");
        descriptionlabel.setPreferredSize(new Dimension(getWidth(),150));
        descriptionPanel.add(descriptionlabel,BorderLayout.CENTER);

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
        try{
            if(!favouriteSharks.getFavouriteSharks().contains(shark))
                followButton.setText("Unfollow");
        }catch (Exception e){
            //e.printStackTrace();
        }

        followButton.addActionListener(favouriteController);

        pingPanel.add(pingLabel, BorderLayout.CENTER);
        pingPanel.add(followButton, BorderLayout.EAST);
        setVisible(true);

        return pingPanel;
    }

    /**
     * Create a table to display specific information about a Shark (name, gender, stage of life, species, length and weight).
     * @param foundShark	A Shark matching the chosen criteria.
     * @return	a JPanel containing all relevant information (name, gender, satge of life, species, length and weight).
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

    public Shark getShark(){
        return shark.getShark();
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