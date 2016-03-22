package sharkitter.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import sharkitter.model.ListOfSharkData;
import sharkitter.model.SharkData;
import javax.swing.JPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Evou on 11/03/2016.
 */
public class StatisticsFrame extends JFrame {

    private JComboBox<String> tag_location;
    private JComboBox<String> gender;
    private JComboBox<String> stage_of_life;

    public StatisticsFrame(){

        super("Shark Statistics");
        setMinimumSize(new Dimension(400, 600));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        createUI();
    }

    private void createUI(){
        setLayout(new GridLayout(11, 1));

        add(new JLabel("Shark Tracker Statistics"));
        add(new JSeparator(JSeparator.HORIZONTAL));

        add(new JLabel("Stage of life:"));
        createStageOfLifeComboBox();
        createStageOfLifePieChart();

        add(new JLabel("Gender:"));
        createGenderComboBox();
        createGenderPieChart();

        add(new JLabel("Tag location:"));
        createTagLocationComboBox();
        createTagLocationPieChart();

        pack();
    }
    private void createGenderComboBox(){
        add(new JLabel("Tracking Range"));
        gender = new JComboBox<>();
        gender.addItem("Last 24 Hours");
        gender.addItem("Last Week");
        gender.addItem("Last Month");
        add(gender);
    }

    private void createStageOfLifeComboBox(){
        add(new JLabel("Tracking Range"));
        stage_of_life = new JComboBox<>();
        gender.addItem("Last 24 Hours");
        gender.addItem("Last Week");
        gender.addItem("Last Month");
        add(gender);
    }
    private void createTagLocationComboBox(){
        add(new JLabel("Tracking Range"));
        tag_location = new JComboBox<>();
        gender.addItem("Last 24 Hours");
        gender.addItem("Last Week");
        gender.addItem("Last Month");
        add(gender);
    }

    public void createGenderPieChart(){}

    public void createStageOfLifePieChart(){
        add(new JLabel("This will be a chart in some future"));
    }

    public void createTagLocationPieChart(){
        add(new JLabel("This will be a chart in some future"));
    }

    private PieDataset createSharkDataset()
    {
        DefaultPieDataset dataset = new DefaultPieDataset( );
        dataset.setValue( "IPhone 5s" , new Double( 20 ) );
        dataset.setValue( "SamSung Grand" , new Double( 20 ) );
        dataset.setValue( "MotoG" , new Double( 40 ) );
        dataset.setValue( "Nokia Lumia" , new Double( 10 ) );
        return dataset;
    }

    private PieDataset createSharkDatasetByGender(ListOfSharkData listOfSharkData)
    {
        DefaultPieDataset dataset = new DefaultPieDataset();

        ArrayList<SharkData> maleSharks = new ArrayList<>();
        ArrayList<SharkData> femaleSharks = new ArrayList<>();

        for(SharkData sharkdata: listOfSharkData){
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

    private PieDataset createSharkDatasetByStageOfLife(ListOfSharkData listOfSharkData)
    {
        DefaultPieDataset dataset = new DefaultPieDataset();

        ArrayList<SharkData> matureSharks = new ArrayList<>();
        ArrayList<SharkData> immatureSharks = new ArrayList<>();
        ArrayList<SharkData> undeterminedSharks = new ArrayList<>();

        for(SharkData sharkdata: listOfSharkData){

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

    private PieDataset createSharkDatasetByTagLocation(ListOfSharkData listOfSharkData)
    {
        DefaultPieDataset dataset = new DefaultPieDataset();

        ArrayList<SharkData> maleSharks = new ArrayList<>();
        ArrayList<SharkData> femaleSharks = new ArrayList<>();

        for(SharkData sharkdata: listOfSharkData){
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

    private JFreeChart createChart(PieDataset dataset, String titleofchart )
    {
        JFreeChart chart = ChartFactory.createPieChart(
                titleofchart, dataset,true, true, false);
            // chart title, then data then legend
        return chart;
    }
    public JPanel createDemoPanel(String titleofchart )
    {
        JFreeChart chart = createChart(createSharkDataset( ),titleofchart);
        return new ChartPanel( chart );
    }


}
