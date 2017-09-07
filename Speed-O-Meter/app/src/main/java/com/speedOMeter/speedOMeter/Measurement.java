package com.speedOMeter.speedOMeter;

import android.location.Location;

/**
 * Created by serge on 31-Aug-17.
 */

public class Measurement {
    private float metersTraveled;
    private float seconds;

    public Measurement(Location firstLocation, Location secondLocation, float seconds) {
        metersTraveled = firstLocation.distanceTo(secondLocation);
        metersTraveled -= firstLocation.getAccuracy() / 4;
        metersTraveled -= secondLocation.getAccuracy() / 4;
        metersTraveled = metersTraveled < 0 ? 0 : metersTraveled;
        this.seconds = seconds;
    }

    /**
     * Gets distance in meters, coordinates in RADIAN
     */
    private static double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
                        * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 6371 * c;
    }

    public double getSpeed(SpeedMeasurementType measurementTypes){
        return (seconds == 0) ? (metersTraveled * measurementTypes.getConversionFromMS()) :
                ((metersTraveled * measurementTypes.getConversionFromMS()) / seconds);
    }

    public double getTime() {
        return seconds;
    }

    public double getDistance() {
        return metersTraveled;
    }
}
