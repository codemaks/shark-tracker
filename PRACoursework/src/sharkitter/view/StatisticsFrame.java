package sharkitter.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import sharkitter.controller.FunctionalityController;
import sharkitter.controller.StatisticsItemListener;
import sharkitter.model.PingCollection;
import sharkitter.model.SharkData;
import javax.swing.JPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representation of the Statistics Frame
 */
public class StatisticsFrame extends JFrame {

    private JComboBox<String> tagLocation;
    private JComboBox<String> gender;
    private JComboBox<String> stageOfLife;
    private FunctionalityController functionalityController;
    private StatisticsItemListener statisticsItemListener;
    private JPanel stageOfLifePanel;
    private JPanel genderPanel;
    private JPanel tagLocPanel;

    /**
     * Constructor for the StatisticsFrame
     * @param functionalityController   Controller for the StatisticsFrame
     * @param pingCollection    Collection of pings
     */
    public StatisticsFrame(FunctionalityController functionalityController, PingCollection pingCollection){

        super("Shark Statistics");
        this.functionalityController = functionalityController;
        this.statisticsItemListener = new StatisticsItemListener(this, pingCollection);
        setPreferredSize(new Dimension(400, 600));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createUI(this.functionalityController);
    }

    /**
     * Creates the interface for the StatisticsFrame
     * @param functionalityController   Controller of the MenuBar
     */
    private void createUI(FunctionalityController functionalityController){
        setLayout(new BorderLayout());

        add(createJMenuBar(functionalityController),BorderLayout.NORTH);

        JPanel underPanel = new JPanel();
        underPanel.setLayout(new GridLayout(3,1));

        underPanel.add(createStageOfLifePanel());

        underPanel.add(createGenderPanel());

        underPanel.add(createTagLocationPanel());
        add(underPanel);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Creates the panel for the "Stage of life" part
     * @return  The created panel
     */
    private JPanel createStageOfLifePanel(){
        stageOfLifePanel = new JPanel();
        stageOfLifePanel.setLayout(new BorderLayout());
        stageOfLife = new JComboBox<>();
        stageOfLife.addItem("-------");
        stageOfLife.addItem("Last 24 Hours");
        stageOfLife.addItem("Last Week");
        stageOfLife.addItem("Last Month");
        stageOfLife.addItemListener(statisticsItemListener);
        stageOfLifePanel.add(stageOfLife,BorderLayout.NORTH);
        stageOfLifePanel.add(createStageOfLifeSubPanel(new ArrayList<>(),"Stage of Life:"));

        return stageOfLifePanel;
    }

    /**
     * Creates the panel for the "Gender" part
     * @return   Created panel
     */
    private JPanel createGenderPanel(){
        genderPanel = new JPanel();
        genderPanel.setLayout(new BorderLayout());
        gender = new JComboBox<>();
        gender.addItem("-------");
        gender.addItem("Last 24 Hours");
        gender.addItem("Last Week");
        gender.addItem("Last Month");
        gender.addItemListener(statisticsItemListener);
        genderPanel.add(gender,BorderLayout.NORTH);
        genderPanel.add(createGenderSubPanel(new ArrayList<>(),"Gender:"));

        return genderPanel;
    }

    /**
     * Craetes the panel for the "Tag location" part
     * @return  Created panel
     */
    private JPanel createTagLocationPanel(){
        tagLocPanel = new JPanel();
        tagLocPanel.setLayout(new BorderLayout());
        tagLocPanel.add(new JLabel("Tracking Range"));
        tagLocation = new JComboBox<>();
        tagLocation.addItem("-------");
        tagLocation.addItem("Last 24 Hours");
        tagLocation.addItem("Last Week");
        tagLocation.addItem("Last Month");
        tagLocation.addItemListener(statisticsItemListener);
        tagLocPanel.add(tagLocation,BorderLayout.NORTH);
        tagLocPanel.add(createTagLocationSubPanel(new ArrayList<>(), "Tag Location:"));

        return tagLocPanel;
    }

    /**
     * Creates the subpanel of the Tag location panel
     * @param sharkData List of SharkData
     * @param titleOfChart  Title given to the chart
     * @return  Created panel
     */
    private JPanel createTagLocationSubPanel(List<SharkData> sharkData, String titleOfChart)
    {
        JFreeChart chart = createChart(createSharkDatasetByTagLocation(sharkData),titleOfChart);
        return new ChartPanel( chart );
    }

    /**
     * Creates subpanel of the Gender panel
     * @param sharkData List of SharkData
     * @param titleOfChart  Title given to the chart
     * @return  Created panel
     */
    private JPanel createGenderSubPanel(List<SharkData> sharkData, String titleOfChart)
    {
        JFreeChart chart = createChart(createSharkDatasetByGender(sharkData), titleOfChart);
        return new ChartPanel( chart );
    }

    /**
     * Creates subpanel of the Stage of Life panel
     * @param sharkData List of SharkData
     * @param titleOfChart  Title given to the chart
     * @return  Created panel
     */
    private JPanel createStageOfLifeSubPanel(List<SharkData> sharkData, String titleOfChart)
    {
        JFreeChart chart = createChart(createSharkDatasetByStageOfLife(sharkData), titleOfChart);
        return new ChartPanel( chart );
    }

    /**
     * Creates a JMenuBar
     * @param functionalityController   Controller of the JMenuBar
     * @return  Created JMenuBar
     */
    private JMenuBar createJMenuBar(FunctionalityController functionalityController) {
        JMenuBar menuBar = new JMenuBar();

        JMenu view = new JMenu("View");

        JMenuItem menu = new JMenuItem("Menu");
        menu.setName("StatisticsFrame");
        menu.addActionListener(functionalityController);
        menu.setToolTipText("Go back to the main menu");

        JMenuItem search = new JMenuItem("Search");
        search.setName("StatisticsFrame");
        search.addActionListener(functionalityController);
        search.setToolTipText("Search for sharks!");

        view.add(menu);
        view.add(search);
        menuBar.add(view);
        add(menuBar, BorderLayout.NORTH);

        return menuBar;
    }

    /**
     * Creates a PieDataset by gender
     * @param listOfSharks  List of SharkData
     * @return  Cerated PieDataset
     */
    private PieDataset createSharkDatasetByGender(List<SharkData> listOfSharks)
    {
        DefaultPieDataset dataset = new DefaultPieDataset();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                Map<String, List<SharkData>> mapofsharks = statisticsItemListener.separateSharkDataByGender(listOfSharks);
                dataset.setValue("Male",mapofsharks.get("Male").size());
                dataset.setValue("Female", mapofsharks.get("Female").size());
                }
    });
          return dataset;
    }

    /**
     * Creates a PieDataset by stage of life
     * @param listOfSharks  List of SharkData
     * @return  Cerated PieDataset
     */
    private PieDataset createSharkDatasetByStageOfLife(List<SharkData> listOfSharks)
    {
        DefaultPieDataset dataset = new DefaultPieDataset();
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    Map<String, List<SharkData>> mapofsharks = statisticsItemListener.separateSharkDataByStageOfLife(listOfSharks);
                    if(!mapofsharks.get("Mature").isEmpty()){
                        dataset.setValue("Mature", mapofsharks.get("Mature").size());
                    }
                    if(!mapofsharks.get("Immature").isEmpty()){
                        dataset.setValue("Immature", mapofsharks.get("Immature").size());
                    }
                    if(!mapofsharks.get("Undetermined").isEmpty()){
                        dataset.setValue("Undetermined", mapofsharks.get("Undetermined").size());
                    }
                }
            });
        return dataset;
    }

    /**
     * Creates a PieDataset by tag location
     * @param listOfSharks  List of SharkData
     * @return  Cerated PieDataset
     */
    private PieDataset createSharkDatasetByTagLocation(List<SharkData> listOfSharks)
    {
        DefaultPieDataset dataset = new DefaultPieDataset();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                Map<String, Integer> mapOfSharks = statisticsItemListener.separateSharkDataByTagLocation(listOfSharks);

                for (String key : mapOfSharks.keySet()) {
                    dataset.setValue(key, mapOfSharks.get(key));
                }
            }
        });
        return dataset;
    }

    /**
     * Creates a JFreeChart of the given PieDataset
     * @param dataset   PieDataset used to create the JFreeChart
     * @param titleOfChart Title given to the chart
     * @return  Created JFreeChart
     */
    private JFreeChart createChart(PieDataset dataset, String titleOfChart)
    {
        JFreeChart chart = ChartFactory.createPieChart(
                titleOfChart, dataset,true, true, false);
            // chart title, then data then legend
        return chart;
    }

    /**
     * Updates the different panels given the data
     * @param sharkData List of SharkData
     * @param source    Source needed to know which panel should be updated
     */
    public void updateShark(List<SharkData> sharkData, JComboBox source){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (source.equals(gender)) {
                    genderPanel.removeAll();
                    genderPanel.add(gender, BorderLayout.NORTH);
                    genderPanel.add(createGenderSubPanel(sharkData, "Gender:"));
                    revalidate();
                    repaint();
                } else if (source.equals(tagLocation)) {
                    tagLocPanel.removeAll();
                    tagLocPanel.add(tagLocation, BorderLayout.NORTH);
                    tagLocPanel.add(createTagLocationSubPanel(sharkData, "Tag Location:"));
                    revalidate();
                    repaint();
                } else if (source.equals(stageOfLife)) {
                    stageOfLifePanel.removeAll();
                    stageOfLifePanel.add(stageOfLife, BorderLayout.NORTH);
                    stageOfLifePanel.add(createStageOfLifeSubPanel(sharkData, "Stage of Life"));
                    revalidate();
                    repaint();
                } else {
                    throw new NullPointerException("StatisticsFrameError 1: Could not find original combobox");
                }
            }
        });
    }
}
