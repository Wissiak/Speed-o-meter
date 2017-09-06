package com.speedOMeter.speedOMeter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class ActivitySettingsDrawer extends ActivityDrawer {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.createDrawer(R.layout.content_settings_layout);

        Spinner spinner = (Spinner) findViewById(R.id.languages_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("hioooo1");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                System.out.println("hioooo2");
            }
        });

    }
}
