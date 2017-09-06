package com.speedOMeter.speedOMeter;

import android.location.Location;

/**
 * Created by serge on 31-Aug-17.
 */

public class Measurement {
    private double speed;
    private double seconds;

    public Measurement(Location firstLocation, Location secondLocation, double seconds) {
        speed = getDistance(firstLocation.getLatitude(), firstLocation.getLongitude(), secondLocation.getLatitude(), secondLocation.getLongitude());
        this.seconds = seconds;
    }

    /**
     * Gets distance in meters, coordinates in RADIAN
     */
    private static double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371000; // for haversine use R = 6372.8 km instead of 6371 km
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        //double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return 2 * R * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        // simplify haversine:
        //return 2 * R * 1000 * Math.asin(Math.sqrt(a));
    }

    public double getSpeed(SpeedMeasurementType measurementTypes){
        speed *= measurementTypes.getConversionFromMS();
        speed /= seconds;
        return speed;
    }


    public double getTime() {
        return seconds;
    }
}
