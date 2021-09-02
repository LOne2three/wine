package assignment2019.codeprovided;

import javax.swing.*;
import java.util.List;


public class WineSampleBrowser {

    public static void main(String[] args){



        if(args.length ==0){


            args = new String[]{

                    "resources/winequality-red.csv",
                    "resources/winequality-white.csv",
                    "resources/queries.txt"


            };


        }

        // placing each file into a variable
        String redWineFile = args[0];
        String whiteWineFile = args[1];
        String queriesFile = args[2];

        //create a new cellar object
        WineSampleCellar cellar = new WineSampleCellar(redWineFile, whiteWineFile, queriesFile );
        cellar.readQueries(cellar.fileQuery);
        System.out.println(cellar.fileQuery);


        //Query q = cellar.readQueries(cellar.fileQuery).get(0);
      //  System.out.println(q.getQueryConditionList());



        //create a new jframe
        JFrame frame = new JFrame("Wine Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        WineSampleBrowserPanel p  = new WineSampleBrowserPanel(cellar);
        p.setBounds(400,75,300,300);
        frame.add(p);
        frame.setVisible(true);









    }
}
