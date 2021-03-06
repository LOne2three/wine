package assignment2019.codeprovided;

/*
 * WineProperty.java  	1.0  06/04/2019
 *
 * Copyright (c) University of Sheffield 2019
 */

import java.util.HashMap;
import java.util.Map;

/**
 * WineProperty.java
 *
 * Provides a helper enum with constants representing the wine properties of a wine sample.
 *
 * @version 1.0  06/04/2019
 *
 * @author Maria-Cruz Villa-Uriol (m.villa-uriol@sheffield.ac.uk)
 */

public enum WineProperty {
    FixedAcidity("Fixed Acidity"),
    VolatileAcidity("Volatile Acidity"),
    CitricAcid("Citric Acidity"),
    ResidualSugar("Residual Sugar"),
    Chlorides("Chlorides"),
    FreeSulfurDioxide("Free Sulfur Dioxide"),
    TotalSulfurDioxide("Total Sulfur Dioxide"),
    Density("Density"),
    PH("pH"),
    Sulphates("Sulphates"),
    Alcohol("Alcohol"),
    Quality("Quality");

    private String propertyName;
    static Map<String,WineProperty> l = new HashMap<String,WineProperty>();


    WineProperty(String pName) { propertyName = pName; }

    public String getName() { return this.propertyName; }


    public static  WineProperty lookup( String property){



        for(WineProperty p : WineProperty.values()){

            l.put(p.getName(), p );

        }

            return l.get(property);

        }











    public static WineProperty fromFileIdentifier(String queryFileIdentifier)
    {
        switch (queryFileIdentifier)
        {
            case "qual":
                return WineProperty.Quality;
            case "f_acid":
                return WineProperty.FixedAcidity;
            case "v_acid":
                return WineProperty.VolatileAcidity;
            case "c_acid":
                return WineProperty.CitricAcid;
            case "r_sugar":
                return WineProperty.ResidualSugar;
            case "chlorid":
                return WineProperty.Chlorides;
            case "f_sulf":
                return WineProperty.FreeSulfurDioxide;
            case "t_sulf":
                return WineProperty.TotalSulfurDioxide;
            case "dens":
                return WineProperty.Density;
            case "ph":
                return WineProperty.PH;
            case "sulph":
                return WineProperty.Sulphates;
            case "alc":
                return WineProperty.Alcohol;
        }
        return null;
    }
}