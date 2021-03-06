package com.speedOMeter.speedOMeter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Patrick Wissiak on 02.09.2017.
 */

public class SharedPreferenceHandler {
    private static final String PREFS = "speed-o-meter-preferences-file";
    private static final String SETTINGS_LANGUAGE = "settings-language";
    private static final String SETTINGS_MEASUREMENT = "settings-measurement";
    private static final String CURRENT_SPEED = "current-speed";
    private static final String AVERAGE_SPEED = "average-speed";
    private static final String METERS_TRAVELED = "meters-traveled";
    private static final String SECONDS_TRAVELED = "seconds-traveled";
    private static final String MAX_SPEED = "max-speed";
    private static final String POINTS = "points";
    private static final String COUNTER = "points";
    private final SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferenceHandler(Context context) {
        this.context = context;
        // All objects are from android.context.Context
        this.sharedPreferences = context.getSharedPreferences(PREFS, 0);
    }

    public void reset(){
        this.setSpeed(CURRENT_SPEED, 0);
        this.setSpeed(AVERAGE_SPEED, 0);
        this.setSpeed(SECONDS_TRAVELED, 0);
        this.setSpeed(METERS_TRAVELED, 0);
        this.setSpeed(MAX_SPEED, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(COUNTER, 0);
        editor.apply();
    }

    public void setLocale(String lang) {
        //TODO: doesn't work - fix it
        //lang == en || lang == de
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        context.getResources().getConfiguration().updateFrom(config);
    }

    public List<Float> getMeasurements(){
        int counter = sharedPreferences.getInt(COUNTER, 0);
        List<Float> list = new LinkedList();
        for (int i = 0; i < counter; i++) {
            list.add(sharedPreferences.getFloat(POINTS + Integer.toString(i), 0));
        }
        return list;
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

    public void addFloatToList(float measurement){
        int counter = sharedPreferences.getInt(COUNTER, 0);
        setSpeed(POINTS + Integer.toString(counter), measurement);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(COUNTER, counter+1);
        editor.apply();
    }

    public float getCurrentSpeed() {
        return sharedPreferences.getFloat(CURRENT_SPEED, 0);
    }

    public void setSpeed(Measurement measurement) {
        this.setAverage(measurement);
        this.setMaxSpeed((float) measurement.getSpeed(SpeedMeasurementType.MS));
        this.setSpeed(CURRENT_SPEED, (float) measurement.getSpeed(SpeedMeasurementType.MS));
        this.addFloatToList(getAverageSpeed());
    }

    public float getAverageSpeed() {
        return getSpeed(SECONDS_TRAVELED) < 1 ? 0 : (getSpeed(METERS_TRAVELED) / sharedPreferences.getFloat(SECONDS_TRAVELED, 1));
    }

    private void setAverage(Measurement measurement) {
        this.setSpeed(METERS_TRAVELED, (float) (this.getSpeed(METERS_TRAVELED) + measurement.getDistance()));
        this.setSpeed(SECONDS_TRAVELED, (float) (this.getSpeed(SECONDS_TRAVELED) + measurement.getTime()));
    }

    private float getSpeed(String type){
        return sharedPreferences.getFloat(type, 0);
    }

    public float getMaxSpeed() {
        return sharedPreferences.getFloat(MAX_SPEED, 0);
    }

    private void setMaxSpeed(float currentSpeed) {
        if((this.getMaxSpeed() != 0 && currentSpeed > 2 && (currentSpeed / this.getMaxSpeed() > 20))) {
            currentSpeed = this.getMaxSpeed();
        }
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
