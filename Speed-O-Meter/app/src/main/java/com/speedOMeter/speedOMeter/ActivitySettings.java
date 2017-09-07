package com.speedOMeter.speedOMeter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Arrays;

public class ActivitySettings extends ActivityDrawer {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.createDrawer(R.layout.content_settings_layout);

        final SharedPreferenceHandler sharedPreferenceHandler = new SharedPreferenceHandler(getApplicationContext());

        setMeasurementSpinner(sharedPreferenceHandler);
        Button resetButton = (Button) findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferenceHandler.reset();
            }
        });
        setLanguagesSpinner(sharedPreferenceHandler);
    }

    private void setLanguagesSpinner(final SharedPreferenceHandler sharedPreferenceHandler) {
        Spinner languagesSpinner = (Spinner) findViewById(R.id.languages_spinner);
        languagesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String item = (String) adapterView.getItemAtPosition(position);
                sharedPreferenceHandler.setLanguage(Language.valueOf(item));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                System.out.println("nothing selected");
            }
        });

        String[] languagesArray = Arrays.toString(Language.values()).replaceAll("^.|.$", "").split(", ");
        ArrayAdapter<String> languagesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languagesArray);
        languagesSpinner.setAdapter(languagesAdapter);

        int index = Arrays.asList(Language.values()).indexOf(sharedPreferenceHandler.getLanguage());
        languagesSpinner.setSelection(index);
    }

    private void setMeasurementSpinner(final SharedPreferenceHandler sharedPreferenceHandler) {
        Spinner measurementsSpinner = (Spinner) findViewById(R.id.measurements_spinner);

        measurementsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String item = (String) adapterView.getItemAtPosition(position);
                sharedPreferenceHandler.setMeasurement(SpeedMeasurementType.valueOf(item));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                System.out.println("nothing selected");
            }
        });

        String[] measurementsValues = Arrays.toString(SpeedMeasurementType.values()).replaceAll("^.|.$", "").split(", ");
        ArrayAdapter<String> measurementAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, measurementsValues);
        measurementsSpinner.setAdapter(measurementAdapter);

        int index = Arrays.asList(SpeedMeasurementType.values()).indexOf(sharedPreferenceHandler.getMeasurement());
        measurementsSpinner.setSelection(index);
    }
}
