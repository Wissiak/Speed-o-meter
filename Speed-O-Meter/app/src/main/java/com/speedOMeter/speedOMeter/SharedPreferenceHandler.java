package com.speedOMeter.speedOMeter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by Patrick Wissiak on 02.09.2017.
 */

public class SharedPreferenceHandler {
    private static final String PREFS = "speed-o-meter-preferences-file";
    private static final String SETTINGS_LANGUAGE = "settings-language";
    private static final String SETTINGS_MEASUREMENT = "settings-measurement";
    private static final String AMOUNT_OF_MEASUREMENTS = "amount-of-measurements";
    private static final String CURRENT_SPEED = "current-speed";
    private static final String AVERAGE_SPEED = "average-speed";
    private static final String MAX_SPEED = "max-speed";
    private static final String TOTAL_SPEED = "total-speed";
    private final SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferenceHandler(Context context) {
        this.context = context;
        // All objects are from android.context.Context
        this.sharedPreferences = context.getSharedPreferences(PREFS, 0);
        this.setSpeed(CURRENT_SPEED, 0);
        this.setSpeed(AVERAGE_SPEED, 0);
        this.setSpeed(MAX_SPEED, 0);
        this.setSpeed(TOTAL_SPEED, 0);
        this.setAmountOfMeasurements(0);
    }

    public void setLocale(String lang) {
        //lang == en || lang == de
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        //context.getResources().getConfiguration().updateFrom(config);
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    public void setLanguage(Language language) {
        this.setLocale(language.toString());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SETTINGS_LANGUAGE, language.toString());

        editor.apply();
    }

    public Language getLanguage() {
        return Language.valueOf(sharedPreferences.getString(SETTINGS_LANGUAGE, "EN"));
    }

    public void setMeasurement(SpeedMeasurementType measurement) {
        // We need an Editor object to make preference changes.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SETTINGS_MEASUREMENT, measurement.name());

        // Apply the edits!
        editor.apply();
    }

    public SpeedMeasurementType getMeasurement() {
        return SpeedMeasurementType.valueOf(sharedPreferences.getString(SETTINGS_MEASUREMENT, "KMH"));
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
        if (currentSpeed > this.getMaxSpeed()) {
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
