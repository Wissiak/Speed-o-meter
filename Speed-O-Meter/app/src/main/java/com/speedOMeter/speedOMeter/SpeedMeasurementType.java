package com.speedOMeter.speedOMeter;

/**
 * Created by serge on 31-Aug-17.
 */

public enum SpeedMeasurementType {
    MS(1, "m/s"), KMH(3.6f, "km/h"), MPH(2.23694f, "mph"), FS(3.28084f, "f/s");

    private final float conversionFromMS;

    private final String abbreviation;

    private SpeedMeasurementType(float conversionFromMS, String abbreviation){
        this.conversionFromMS = conversionFromMS;
        this.abbreviation = abbreviation;
    }

    public float getConversionFromMS(){
        return conversionFromMS;
    }

    public String getAbbreviation(){
        return abbreviation;
    }
}
