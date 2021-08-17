package assignment2019.codeprovided;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WineSampleCellar extends AbstractWineSampleCellar {


    public WineSampleCellar(String redWineFile, String whiteWineFile, String queriesFile) {
        super(redWineFile, whiteWineFile, queriesFile);
    }


    @Override
    /**
     * @params queryList: List of queries read from file and stored in a list
     *
     */
    public List<Query> readQueries(List<String> queryList) {
        List<String> query = queryList;
        List<Integer> idx = new ArrayList();
        List<List> querys = new ArrayList();
        List<Query> queries = new ArrayList();

        // in order to split the list on select, get the indexes of "select"
        for (int i = 0; i < query.size(); i++) {
            //System.out.println(query.get(i));
            //returns index of "select"
            if (query.get(i).contains("select")) {
                idx.add(i);
            }
        }

        // split the list on the word "select"
        for (int i = 0; i < idx.size() - 1; i++) {

            querys.add(query.subList(idx.get(i), idx.get(i + 1)));
        }


        List<QueryCondition> qq = new ArrayList();
        List<List> qvc = new ArrayList();
        Query q = new Query(this.getWineSampleList(WineType.WHITE), qq, WineType.WHITE);
        for (List str : querys) {

            List<List> qc = new ArrayList();
            //store the indexes of "where" "select" "and"
            List<Integer> idex = new ArrayList();

            for (int i = 0; i < str.size(); i++) {

                if (str.get(i).equals("select")) {

                    idex.add(i);

                } else if (str.get(i).equals("where")) {

                    idex.add(i);
                } else if (str.get(i).equals("and")) {

                    idex.add(i);

                }
            }

            //create sublists  excluding "select", "where", "and"
            //[[color],[condition],[condition]]
            for (int j = 0; j <= idex.size() - 1; j++) {

                while (j != idex.size() - 1) {

                    qc.add(str.subList(idex.get(j) + 1, idex.get(j + 1)));
                    j++;
                }
                qc.add(str.subList(idex.get(idex.size() - 1) + 1, str.size()));


            }


            qvc.add(qc);


        }

        //Insert the list of conditions into query list

        for (List qu : qvc) {

            List<QueryCondition> qyc = new ArrayList();

            WineType t;
            List type = (List) qu.get(0);
            //System.out.println(qu.get(0));


            if (type.get(0).equals("red") && type.size() == 1) {

                t = WineType.RED;
            } else if (type.get(0).equals("white") && type.size() == 1) {

                t = WineType.WHITE;

            } else {

                t = WineType.ALL;


            }

            Query qr = new Query(this.getWineSampleList(t), qyc, t);

            for(int i = 1; i<=qu.size()-1;i++){

                List quant = (List) qu.get(i);

                //System.out.println(qu.get(i));
                WineProperty p = WineProperty.fromFileIdentifier((String)quant.get(0));
                QueryCondition n = new QueryCondition(p,(String) quant.get(1), Double.valueOf((String)(quant.get(2))));
                qyc.add(n);



            }

            queries.add(qr);




        }

        for(int i = 0; i<=queries.size()-1;i++) {
            System.out.println(queries.get(i).getQueryConditionList());

        }
     return queries;


    }

    @Override
    public void updateCellar() {

        List<WineSample> all = new ArrayList();
        System.out.println(wineSampleRacks.get(WineType.WHITE).size());
        for(WineSample w:wineSampleRacks.get(WineType.WHITE)){

           // System.out.println(w.getType());
            all.add(w);

        }

        System.out.println(all.size());
        for(WineSample w:wineSampleRacks.get(WineType.RED)){

            //System.out.println(w.getType());
            all.add(w);
        }

        System.out.println(all.size());

        wineSampleRacks.put(WineType.ALL,all);


    }

    @Override
    public void displayQueryResults(Query query) {

    }

    @Override
    //takes in wineType and returns bestQualityWIne
    public List<WineSample> bestQualityWine(WineType wineType) {


        List<WineSample> Bestquality = this.getWineSampleList(wineType);


        Collections.sort(Bestquality, new Comparator<WineSample>() {
            @Override
            public int compare(WineSample u1, WineSample u2) {
                return Double.compare(u2.getQuality(),u1.getQuality());
            }
        });

        for(WineSample sample : this.getWineSampleList(wineType)) {

            System.out.println(sample.getQuality()+" "+ sample.getId() );

        }
        // returns a list of wines sorted by quality in desc order
        return Bestquality;


    }

    @Override
    public List<WineSample> worstQualityWine(WineType wineType) {
        List<WineSample> worstQuality = this.getWineSampleList(wineType);


        Collections.sort(worstQuality, new Comparator<WineSample>() {
            @Override
            public int compare(WineSample u1, WineSample u2) {
                return Double.compare(u1.getQuality(), u2.getQuality()); }});


        for(WineSample sample : this.getWineSampleList(wineType)) {

            System.out.println(sample.getQuality()+" "+ sample.getId() );

        }

        return worstQuality;

    }


    @Override
    public List<WineSample> highestPH(WineType wineType) {

        List<WineSample> highestPH = this.getWineSampleList(wineType);


        Collections.sort(highestPH, new Comparator<WineSample>() {
            @Override
            public int compare(WineSample u1, WineSample u2) {
                return Double.compare(u2.getpH(), u1.getpH()); }});


        for(WineSample sample : this.getWineSampleList(wineType)) {

            System.out.println(sample.getpH()+" "+ sample.getpH() );

        }
        return highestPH;
    }

    @Override
    public List<WineSample> lowestPH(WineType wineType) {
        List<WineSample> lowestPH = this.getWineSampleList(wineType);


        Collections.sort(lowestPH, new Comparator<WineSample>() {
            @Override
            public int compare(WineSample u1, WineSample u2) {
                return Double.compare(u1.getpH(), u2.getpH()); }});


        for(WineSample sample : this.getWineSampleList(wineType)) {

            System.out.println(sample.getpH()+" "+ sample.getpH() );

        }
        return lowestPH;
    }

    @Override
    public double highestAlcoholContent(WineType wineType) {

        List<WineSample> hac = this.getWineSampleList(wineType);


        Collections.sort(hac, new Comparator<WineSample>() {
            @Override
            public int compare(WineSample u1, WineSample u2) {
                return Double.compare(u1.getAlcohol(), u2.getAlcohol()); }});


        return hac.get(hac.size()-1).getAlcohol();
    }

    @Override
    public double lowestCitricAcid(WineType wineType) {
        List<WineSample> lca = this.getWineSampleList(wineType);


        Collections.sort(lca, new Comparator<WineSample>() {
            @Override
            public int compare(WineSample u1, WineSample u2) {
                return Double.compare(u1.getCitricAcid(), u2.getCitricAcid()); }});



        return lca.get(lca.size()-1).getCitricAcid();
    }

    @Override
    public double averageAlcoholContent(WineType wineType) {

        List<WineSample> hac = this.getWineSampleList(wineType);
        double total=0;

        for(WineSample alcohol : hac){

            total += alcohol.getAlcohol();


        }


        return total/hac.size();
    }
}
