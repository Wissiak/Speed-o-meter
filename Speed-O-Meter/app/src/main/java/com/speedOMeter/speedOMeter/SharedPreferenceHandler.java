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
    private static final String AMOUNT_OF_MEASUREMENTS = "amount-of-measurements";
    private static final String CURRENT_SPEED = "current-speed";
    private static final String AVERAGE_SPEED = "average-speed";
    private static final String MAX_SPEED = "max-speed";
    private static final String TOTAL_SPEED = "total-speed";
    private final SharedPreferences sharedPreferences;

    public SharedPreferenceHandler(Context context) {
        // All objects are from android.context.Context
        this.sharedPreferences = context.getSharedPreferences(PREFS, 0);
        this.setSpeed(CURRENT_SPEED, 0);
        this.setSpeed(AVERAGE_SPEED, 0);
        this.setSpeed(MAX_SPEED, 0);
        this.setSpeed(TOTAL_SPEED, 0);
        this.setAmountOfMeasurements(0);
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

    public void setSpeed(double currentSpeed) {
        this.increaseAmountOfMeasurements();
        this.setTotalSpeed(currentSpeed);
        this.setAverage();
        this.setMaxSpeed(currentSpeed);
        this.setSpeed(CURRENT_SPEED, currentSpeed);
    }

    public float getTotalSpeed() {
        return sharedPreferences.getFloat(TOTAL_SPEED, 0);
    }

    public void setTotalSpeed(double currentSpeed) {
        this.setSpeed(TOTAL_SPEED, getTotalSpeed() + currentSpeed);
    }

    private int getAmountOfMeasurements() {
        return sharedPreferences.getInt(AMOUNT_OF_MEASUREMENTS, 0);
    }

    private void increaseAmountOfMeasurements() {
        int amountOfMeasurements = this.getAmountOfMeasurements() + 1;
        this.setAmountOfMeasurements(amountOfMeasurements);
    }

    public void setAmountOfMeasurements(int amountOfMeasurements) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(AMOUNT_OF_MEASUREMENTS, amountOfMeasurements);

        editor.apply();
    }

    public float getAverageSpeed() {
        return sharedPreferences.getFloat(AVERAGE_SPEED, 0);
    }

    private void setAverage() {
        float average = this.getTotalSpeed() / getAmountOfMeasurements();
        this.setSpeed(AVERAGE_SPEED, average);
    }

    public float getMaxSpeed() {
        return sharedPreferences.getFloat(MAX_SPEED, 0);
    }

    private void setMaxSpeed(double currentSpeed) {
        if(currentSpeed > this.getMaxSpeed()) {
            this.setSpeed(MAX_SPEED, currentSpeed);
        }
    }

    private void setSpeed(String type, double value) {
        // We need an Editor object to make preference changes.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(type, ((float) value));

        editor.apply();
    }
}
