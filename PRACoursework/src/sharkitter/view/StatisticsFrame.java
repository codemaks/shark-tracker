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

    private JComboBox<String> tag_location;
    private JComboBox<String> gender;
    private JComboBox<String> stage_of_life;
    private FunctionalityController functionalityController;
    private StatisticsItemListener statisticsItemListener;
    private PingCollection pingCollection;
    private JPanel stage_of_lifepanel;
    private JPanel genderpanel;
    private JPanel taglocpanel;

    public StatisticsFrame(FunctionalityController functionalityController, PingCollection pingCollection){

        super("Shark Statistics");
        this.functionalityController = functionalityController;
        this.statisticsItemListener = new StatisticsItemListener(this, pingCollection);
        this.pingCollection = pingCollection;
        setPreferredSize(new Dimension(400, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createUI(this.functionalityController);
    }

    private void createUI(FunctionalityController functionalityController){
        setLayout(new BorderLayout());

        add(createjMenuBar(functionalityController),BorderLayout.NORTH);

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
        stage_of_lifepanel = new JPanel();
        stage_of_lifepanel.setLayout(new BorderLayout());
        stage_of_life = new JComboBox<>();
        stage_of_life.addItem("-------");
        stage_of_life.addItem("Last 24 Hours");
        stage_of_life.addItem("Last Week");
        stage_of_life.addItem("Last Month");
        stage_of_life.addItemListener(statisticsItemListener);
        stage_of_lifepanel.add(stage_of_life,BorderLayout.NORTH);
        stage_of_lifepanel.add(createStageOfLifeSubPanel(new ArrayList<>(),"Stage of Life:"));

        return stage_of_lifepanel;
    }

    private JPanel createGenderPanel(){
        genderpanel = new JPanel();
        genderpanel.setLayout(new BorderLayout());
        gender = new JComboBox<>();
        gender.addItem("-------");
        gender.addItem("Last 24 Hours");
        gender.addItem("Last Week");
        gender.addItem("Last Month");
        gender.addItemListener(statisticsItemListener);
        genderpanel.add(gender,BorderLayout.NORTH);
        genderpanel.add(createGenderSubPanel(new ArrayList<>(),"Gender:"));

        return genderpanel;
    }

    private JPanel createTagLocationPanel(){
        taglocpanel = new JPanel();
        taglocpanel.setLayout(new BorderLayout());
        taglocpanel.add(new JLabel("Tracking Range"));
        tag_location = new JComboBox<>();
        tag_location.addItem("-------");
        tag_location.addItem("Last 24 Hours");
        tag_location.addItem("Last Week");
        tag_location.addItem("Last Month");
        tag_location.addItemListener(statisticsItemListener);
        taglocpanel.add(tag_location,BorderLayout.NORTH);
        taglocpanel.add(createTagLocationSubPanel(new ArrayList<>(), "Tag Location:"));

        return taglocpanel;
    }

    private JPanel createTagLocationSubPanel(List<SharkData> sharkData, String titleofchart)
    {
        JFreeChart chart = createChart(createSharkDatasetByTagLocation(sharkData),titleofchart);
        return new ChartPanel( chart );
    }

    private JPanel createGenderSubPanel(List<SharkData> sharkData, String titleofchart)
    {
        JFreeChart chart = createChart(createSharkDatasetByGender(sharkData), titleofchart);
        return new ChartPanel( chart );
    }

    private JPanel createStageOfLifeSubPanel(List<SharkData> sharkData, String titleofchart)
    {
        JFreeChart chart = createChart(createSharkDatasetByStageOfLife(sharkData), titleofchart);
        return new ChartPanel( chart );
    }

    private JMenuBar createjMenuBar(FunctionalityController functionalityController) {
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

    private PieDataset createSharkDatasetByGender(List<SharkData> listofsharks)
    {
        DefaultPieDataset dataset = new DefaultPieDataset();

        //TODO This part (separate gender in different lists) should by done by the controller
        List<SharkData> maleSharks = new ArrayList<>();
        List<SharkData> femaleSharks = new ArrayList<>();

        for(SharkData sharkdata: listofsharks){
            if(sharkdata.getGender().equals("Male")){
                maleSharks.add(sharkdata);
            }else{
                femaleSharks.add(sharkdata);
            }
        }
        dataset.setValue("Male",maleSharks.size());
        dataset.setValue("Female",femaleSharks.size());

        return dataset;
    }

    private PieDataset createSharkDatasetByStageOfLife(List<SharkData> listofsharks)
    {
        DefaultPieDataset dataset = new DefaultPieDataset();

        //TODO This part (separate stage of life in different lists) should by done by the controller
        List<SharkData> matureSharks = new ArrayList<>();
        List<SharkData> immatureSharks = new ArrayList<>();
        List<SharkData> undeterminedSharks = new ArrayList<>();

        for(SharkData sharkdata:listofsharks){

            if(sharkdata.getStageOfLife().equals("Mature")){
                matureSharks.add(sharkdata);

            }else if(sharkdata.getStageOfLife().equals("Immature")){
                immatureSharks.add(sharkdata);

            }else{
                undeterminedSharks.add(sharkdata);
            }
        }
        if(!matureSharks.isEmpty()){
            dataset.setValue("Mature",matureSharks.size());
        }
        if(!immatureSharks.isEmpty()){
            dataset.setValue("Immature",immatureSharks.size());
        }
        if(!undeterminedSharks.isEmpty()){
           dataset.setValue("Undetermined",undeterminedSharks.size());
        }

        return dataset;
    }

    private PieDataset createSharkDatasetByTagLocation(List<SharkData> listofsharks)
    {
        DefaultPieDataset dataset = new DefaultPieDataset();

        //TODO This part (whatever that is, with the map) should by done by the controller
        Map<String,Integer> mapofsharks = new HashMap<>();

        for(SharkData sharkdata: listofsharks){
            if(mapofsharks.containsKey(sharkdata.getTagLocation())){
                mapofsharks.put(sharkdata.getTagLocation(),mapofsharks.get(sharkdata.getTagLocation())+1);
            }
            mapofsharks.putIfAbsent(sharkdata.getTagLocation(),1);
        }
        for(String key : mapofsharks.keySet()){
           dataset.setValue(key,mapofsharks.get(key));
        }
        return dataset;
    }

    private JFreeChart createChart(PieDataset dataset, String titleofchart )
    {
        JFreeChart chart = ChartFactory.createPieChart(
                titleofchart, dataset,true, true, false);
            // chart title, then data then legend
        return chart;
    }

    public void updateShark(List<SharkData> sharkData, JComboBox source){
        if(source.equals(gender)){
            genderpanel.removeAll();
            genderpanel.add(gender,BorderLayout.NORTH);
            genderpanel.add(createGenderSubPanel(sharkData,"Gender:"));
            revalidate();
            repaint();
        }else if(source.equals(tag_location)){
            taglocpanel.removeAll();
            taglocpanel.add(tag_location,BorderLayout.NORTH);
            taglocpanel.add(createTagLocationSubPanel(sharkData,"Tag Location:"));
            revalidate();
            repaint();
        }else if(source.equals(stage_of_life)){
            stage_of_lifepanel.removeAll();
            stage_of_lifepanel.add(stage_of_life,BorderLayout.NORTH);
            stage_of_lifepanel.add(createStageOfLifeSubPanel(sharkData,"Stage of Life"));
            revalidate();
            repaint();
        }else{
            //TODO remove println
            System.out.println("StatisticsFrameError 1: Could not find original combobox");
        }
    }

}
