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

public class StatisticsFrame extends JFrame {

    private JComboBox<String> tagLocation;
    private JComboBox<String> gender;
    private JComboBox<String> stageOfLife;
    private FunctionalityController functionalityController;
    private StatisticsItemListener statisticsItemListener;
    private JPanel stageOfLifePanel;
    private JPanel genderPanel;
    private JPanel tagLocPanel;

    public StatisticsFrame(FunctionalityController functionalityController, PingCollection pingCollection){

        super("Shark Statistics");
        this.functionalityController = functionalityController;
        this.statisticsItemListener = new StatisticsItemListener(this, pingCollection);
        setPreferredSize(new Dimension(400, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createUI(this.functionalityController);
    }

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

    private JPanel createTagLocationSubPanel(List<SharkData> sharkData, String titleOfChart)
    {
        JFreeChart chart = createChart(createSharkDatasetByTagLocation(sharkData),titleOfChart);
        return new ChartPanel( chart );
    }

    private JPanel createGenderSubPanel(List<SharkData> sharkData, String titleOfChart)
    {
        JFreeChart chart = createChart(createSharkDatasetByGender(sharkData), titleOfChart);
        return new ChartPanel( chart );
    }

    private JPanel createStageOfLifeSubPanel(List<SharkData> sharkData, String titleOfChart)
    {
        JFreeChart chart = createChart(createSharkDatasetByStageOfLife(sharkData), titleOfChart);
        return new ChartPanel( chart );
    }

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

    private PieDataset createSharkDatasetByGender(List<SharkData> listOfSharks)
    {
        DefaultPieDataset dataset = new DefaultPieDataset();

        //TODO This part (separate gender in different lists) should by done by the controller
        List<SharkData> maleSharks = new ArrayList<>();
        List<SharkData> femaleSharks = new ArrayList<>();

        for(SharkData sharkdata: listOfSharks){
            if(sharkdata.getGender().equals("Male")){
                maleSharks.add(sharkdata);
            }else{
                femaleSharks.add(sharkdata);
            }
        }
        dataset.setValue("Male", maleSharks.size());
        dataset.setValue("Female", femaleSharks.size());

        return dataset;
    }

    private PieDataset createSharkDatasetByStageOfLife(List<SharkData> listOfSharks)
    {
        DefaultPieDataset dataset = new DefaultPieDataset();

        //TODO This part (separate stage of life in different lists) should by done by the controller
        List<SharkData> matureSharks = new ArrayList<>();
        List<SharkData> immatureSharks = new ArrayList<>();
        List<SharkData> undeterminedSharks = new ArrayList<>();

        for(SharkData sharkData:listOfSharks){

            if(sharkData.getStageOfLife().equals("Mature")) {
                matureSharks.add(sharkData);

            } else if(sharkData.getStageOfLife().equals("Immature")) {
                immatureSharks.add(sharkData);

            } else {
                undeterminedSharks.add(sharkData);
            }
        }
        if(!matureSharks.isEmpty()){
            dataset.setValue("Mature", matureSharks.size());
        }
        if(!immatureSharks.isEmpty()){
            dataset.setValue("Immature", immatureSharks.size());
        }
        if(!undeterminedSharks.isEmpty()){
           dataset.setValue("Undetermined", undeterminedSharks.size());
        }

        return dataset;
    }

    private PieDataset createSharkDatasetByTagLocation(List<SharkData> listOfSharks)
    {
        DefaultPieDataset dataset = new DefaultPieDataset();

        //TODO This part (whatever that is, with the map) should by done by the controller
        Map<String,Integer> mapOfSharks = new HashMap<>();

        for(SharkData sharkData: listOfSharks){
            if(mapOfSharks.containsKey(sharkData.getTagLocation())){
                mapOfSharks.put(sharkData.getTagLocation(),mapOfSharks.get(sharkData.getTagLocation()) + 1);
            }
            mapOfSharks.putIfAbsent(sharkData.getTagLocation(), 1);
        }
        for(String key : mapOfSharks.keySet()){
           dataset.setValue(key,mapOfSharks.get(key));
        }
        return dataset;
    }

    private JFreeChart createChart(PieDataset dataset, String titleOfChart)
    {
        JFreeChart chart = ChartFactory.createPieChart(
                titleOfChart, dataset,true, true, false);
            // chart title, then data then legend
        return chart;
    }

    public void updateShark(List<SharkData> sharkData, JComboBox source){
        if(source.equals(gender)) {
            genderPanel.removeAll();
            genderPanel.add(gender, BorderLayout.NORTH);
            genderPanel.add(createGenderSubPanel(sharkData, "Gender:"));
            revalidate();
            repaint();
        } else if(source.equals(tagLocation)) {
            tagLocPanel.removeAll();
            tagLocPanel.add(tagLocation, BorderLayout.NORTH);
            tagLocPanel.add(createTagLocationSubPanel(sharkData, "Tag Location:"));
            revalidate();
            repaint();
        } else if(source.equals(stageOfLife)) {
            stageOfLifePanel.removeAll();
            stageOfLifePanel.add(stageOfLife, BorderLayout.NORTH);
            stageOfLifePanel.add(createStageOfLifeSubPanel(sharkData, "Stage of Life"));
            revalidate();
            repaint();
        } else {
            //TODO remove println
            System.out.println("StatisticsFrameError 1: Could not find original combobox");
        }
    }
}
