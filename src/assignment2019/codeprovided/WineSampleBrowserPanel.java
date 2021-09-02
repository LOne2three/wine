package assignment2019.codeprovided;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class WineSampleBrowserPanel extends AbstractWineSampleBrowserPanel {


    public WineSampleBrowserPanel(AbstractWineSampleCellar cellar) {
        super(cellar);
    }

    @Override
    public List<QueryCondition> getQueryConditionList() {
        return super.getQueryConditionList();
    }

    @Override
    public List<WineSample> getFilteredWineSampleList() {
        return super.getFilteredWineSampleList();
    }

    @Override
    public void addListeners() {

        buttonClearFilters.addActionListener( new ActionListener() {
    @Override
     public void actionPerformed(ActionEvent e) {

        clearFilters(); }});

        buttonAddFilter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                queryConditionsTextArea.setText("");
                addFilter();


                System.out.println("click");
                sample.getVerticalScrollBar().setValue(0);


            }


        }

        );




    }

    /**
     * private class ButtonHandler implements ActionListener {
     *
     * @Override public void actionPerformed(ActionEvent e) {
     * <p>
     * }
     * }
     **/


    @Override
    public void addFilter() {
        //gets value from text box and parses values into a double
        double val =Double.parseDouble(value.getText());

        //converts the sign operator recieved from the dropdown menu into a string
        String sign = (String) comboOperators.getSelectedItem();
        //converts the wine pt received from the dropdown menu into a string
        String pt = (String) comboProperties.getSelectedItem();
        //converts the wine type received from the dropdown menu into string
        String type = (String) comboWineTypes.getSelectedItem();
        //uses the lookup method to return the corresponding WineProperty object
        WineProperty property = WineProperty.lookup(pt);
        //create a new queryCondition object with the values received from the combo  and text boxes
        QueryCondition query = new QueryCondition(property,sign,val);
        //add the object to a list
        queryConditionList.add(query);
        //displays the query in the text area
        for(QueryCondition q: queryConditionList) {

            queryConditionsTextArea.append(q.toString() + " ; ");

        }

        //execute the query and returns the queried values
        this.executeQuery();

        //column names
        filteredWineSamplesTextArea.setText("Winetype" + "\t" + "ID" + "\t" + "Fixed acidity" + "\t" + "Citric acid" +
                "\t" + " Alcohol" + "\t" + "Quality" + "\t" + "Density" + "\t" + "Chlorides" + "\t" + "Residual sugar" + "\t" +
                "Free sulfur dioxide" + "\t" + "Total sulfur dioxide" + "\n");

        this.updateWineList();
        System.out.println(sample.getVerticalScrollBar().getMaximum());

        //column names in the statistics box
        statisticsTextArea.setText("\t"+ "Fixed acidity"+"\t"+"pH"+ "\t"+"Citric acid"+
                "\t"+"Sulphate"+"\t"+" Alcohol"+"\t"+"Quality"+"\t"+"Density"+"\t"+"Chlorides"+"\t"+"Residual sugar"+"\t"+
                "Free sulfur dioxide"+"\t"+"Total sulfur dioxide"+"\n");
        this.updateStatistics();
    }

    //calculates the average returned values
    private double calculateAverage(List<Double> ss) {

        long sum = 0;
        for (Double s : ss) {
            sum += s;
        }
        return Math.floor(ss.isEmpty()? 0: 1.0*sum/ss.size()*100)/100;
    }

    @Override
    //clears the filter when button is clicked
    public void clearFilters() {
        //clears filters
        queryConditionList.clear();
        filteredWineSamplesTextArea.setText("");
        queryConditionsTextArea.setText("");
        statisticsTextArea.setText("");
    }

    @Override
    public void updateStatistics() {
        //create a list for each property in order to use the maximum and minimum function
        List<Double> citric = new ArrayList<Double>();
        List<Double> fa = new ArrayList<Double>();
        List<Double> al = new ArrayList<Double>();
        List<Double> den = new ArrayList<Double>();
        List<Double> chl = new ArrayList<Double>();
        List<Double> rs = new ArrayList<Double>();
        List<Double> fsd = new ArrayList<Double>();
        List<Double> td = new ArrayList<Double>();
        List<Double> q = new ArrayList<Double>();
        List<Double> ph = new ArrayList<Double>();
        List<Double> su = new ArrayList<Double>();
        //adds the various characteristics for a specific wine sample into their respective arraylist
        for (WineSample w : filteredWineSampleList) {



            citric.add(w.getCitricAcid());
            fa.add(w.getFixedAcidity());
            fsd.add(w.getFreeSulfurDioxide());
            td.add(w.getTotalSulfurDioxide());
            al.add(w.getAlcohol());
            rs.add(w.getResidualSugar());
            q.add(w.getQuality());
            ph.add(w.getpH());
            su.add(w.getSulphates());
            chl.add(w.getChlorides());
            den.add(w.getDensity());
        }


        statisticsTextArea.append("MAX" + "\t" + Collections.max(fa) + "\t" + Collections.max(ph)+"\t"+ Collections.max(citric) +
                "\t" +Collections.max(su)+"\t"+ Collections.max(al) + "\t" + Collections.max(q) + "\t"+
                + Collections.max(den) + "\t" + Collections.max(chl) + "\t"
                + Collections.max(rs) + "\t" +
                Collections.max(fsd) + "\t\t" + Collections.max(td) + "\n");

        statisticsTextArea.append("MIN" + "\t" + Collections.min(fa) + "\t" + Collections.min(ph)+"\t"+ Collections.min(citric) +
                "\t" + Collections.min(su)+"\t"+  Collections.min(al) + "\t" + Collections.min(q) + "\t"+
                + Collections.min(den) + "\t" + Collections.min(chl) + "\t"
                + Collections.min(rs) + "\t" +
                Collections.min(fsd) + "\t\t" + Collections.min(td) + "\n");

        statisticsTextArea.append("Average" + "\t" + this.calculateAverage(fa) + "\t" +this.calculateAverage(ph)+"\t"+ this.calculateAverage(citric) +
                "\t" + this.calculateAverage(su)+"\t"+this.calculateAverage(al) + "\t" + this.calculateAverage(q) + "\t"+
                + this.calculateAverage(den) + "\t" + this.calculateAverage(chl) + "\t"
                + this.calculateAverage(rs) + "\t" +
                this.calculateAverage(fsd) + "\t\t" + this.calculateAverage(td) + "\n");
    }

    @Override
    public void updateWineList() {

        for (WineSample w : filteredWineSampleList) {

            filteredWineSamplesTextArea.append(w.getType().name() + "\t" + w.getId() + "\t" + w.getProperty(WineProperty.FixedAcidity)
                    + "\t" + w.getCitricAcid() + "\t" + ((Math.round(w.getAlcohol() * 100) / 100)) + "\t" + w.getQuality() + "\t" +
                    w.getDensity() + "\t" + w.getChlorides() + "\t" + w.getResidualSugar() + "\t" +
                    w.getFreeSulfurDioxide() + "\t\t" + w.getTotalSulfurDioxide() + "\n");

        }

    }

    @Override
    public void executeQuery(){

        String type = (String) comboWineTypes.getSelectedItem();
        Query queries = new Query (cellar.getWineSampleList(WineType.valueOf(type)),queryConditionList,WineType.valueOf(type));
        //System.out.println(cellar.getWineSampleList(wineType.RED));

        queries.solveQuery();
        filteredWineSampleList =  queries.solveQuery();

    }
}
