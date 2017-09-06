package com.speedOMeter.speedOMeter;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Patrick Wissiak on 02.09.2017.
 */

public class SharedPreferenceHandler {
    private static final String PREFS = "speed-o-meter-preferences-file";
    private static final String SETTINGS_LANGUAGE = "settings-language";
    private static final String SETTINGS_MEASUREMENT = "settings-measurement";
    private static final String MEASUREMENT = "measurement";
    private static final String CURRENT_SPEED = "current-speed";
    private static final String AVERAGE_SPEED = "average-speed";
    private static final String METERS_TRAVLED = "meters-traveled";
    private static final String SECONDS_TRAVLED = "seconds-traveled";
    private static final String MAX_SPEED = "max-speed";
    private final SharedPreferences sharedPreferences;

    public SharedPreferenceHandler(Context context) {
        // All objects are from android.context.Context
        this.sharedPreferences = context.getSharedPreferences(PREFS, 0);
        this.setSpeed(CURRENT_SPEED, 0);
        this.setSpeed(AVERAGE_SPEED, 0);
        this.setSpeed(SECONDS_TRAVLED, 0);
        this.setSpeed(METERS_TRAVLED, 0);
        this.setSpeed(MAX_SPEED, 0);
    }

    public void setMeasurement(SpeedMeasurementType measurement) {
        // We need an Editor object to make preference changes.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MEASUREMENT, measurement.name());

        // Apply the edits!
        editor.apply();
    }

    public SpeedMeasurementType getMeasurement() {
        return  SpeedMeasurementType.valueOf(sharedPreferences.getString(MEASUREMENT, "KMH"));
    }

    public float getCurrentSpeed() {
        return sharedPreferences.getFloat(CURRENT_SPEED, 0);
    }

    public void setSpeed(Measurement measurement) {
        this.setAverage(measurement);
        this.setMaxSpeed((float) measurement.getSpeed(SpeedMeasurementType.MS));
        this.setSpeed(CURRENT_SPEED, (float) measurement.getSpeed(SpeedMeasurementType.MS));
    }


    public float getAverageSpeed() {
        return getSpeed(SECONDS_TRAVLED) < 1 ? 0 : (getSpeed(METERS_TRAVLED) / sharedPreferences.getFloat(SECONDS_TRAVLED, 1));
    }

    private void setAverage(Measurement measurement) {
        this.setSpeed(METERS_TRAVLED, (float) (this.getSpeed(METERS_TRAVLED) + measurement.getDistance()));
        this.setSpeed(SECONDS_TRAVLED, (float) (this.getSpeed(SECONDS_TRAVLED) + measurement.getTime()));
    }

    private float getSpeed(String type){
        return sharedPreferences.getFloat(type, 0);
    }

    public float getMaxSpeed() {
        return sharedPreferences.getFloat(MAX_SPEED, 0);
    }

    private void setMaxSpeed(float currentSpeed) {
        if(currentSpeed > this.getMaxSpeed()) {
            this.setSpeed(MAX_SPEED, currentSpeed);
        }
    }

    private void setSpeed(String type, float value) {
        // We need an Editor object to make preference changes.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(type, value);

        editor.apply();
    }
}
