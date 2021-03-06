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

        clearFilters();

                                                  }
                                              });

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
        String v =  value.getText();
        double val =Double.parseDouble(v);
        String p = (String) comboOperators.getSelectedItem();
        String pt = (String) comboProperties.getSelectedItem();
        String type = (String) comboWineTypes.getSelectedItem();
        WineProperty property = WineProperty.lookup(pt);
        QueryCondition query = new QueryCondition(property,p,val);
        queryConditionList.add(query);
        System.out.println(queryConditionList);
        System.out.println(query.toString());
        for(QueryCondition q: queryConditionList) {
            queryConditionsTextArea.append(q.toString() + " ; ");

        }

        this.executeQuery();

        //column names
        filteredWineSamplesTextArea.setText("Winetype" + "\t" + "ID" + "\t" + "Fixed acidity" + "\t" + "Citric acid" +
                "\t" + " Alcohol" + "\t" + "Quality" + "\t" + "Density" + "\t" + "Chlorides" + "\t" + "Residual sugar" + "\t" +
                "Free sulfur dioxide" + "\t" + "Total sulfur dioxide" + "\n");

        this.updateWineList();
        System.out.println(sample.getVerticalScrollBar().getMaximum());
        //column names
        statisticsTextArea.setText("\t"+ "Fixed acidity"+"\t"+"pH"+ "\t"+"Citric acid"+
                "\t"+"Sulphate"+"\t"+" Alcohol"+"\t"+"Quality"+"\t"+"Density"+"\t"+"Chlorides"+"\t"+"Residual sugar"+"\t"+
                "Free sulfur dioxide"+"\t"+"Total sulfur dioxide"+"\n");
        this.updateStatistics();
    }


    private double calculateAverage(List<Double> ss) {
        long sum = 0;
        for (Double s : ss) {
            sum += s;
        }
        return Math.floor(ss.isEmpty()? 0: 1.0*sum/ss.size()*100)/100;
    }

    @Override
    public void clearFilters() {
        queryConditionList.clear();
        filteredWineSamplesTextArea.setText("");
        queryConditionsTextArea.setText("");
        statisticsTextArea.setText("");
    }

    @Override
    public void updateStatistics() {
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
