package com.speedOMeter.speedOMeter;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsActivity extends DrawerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_activity);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        Spinner spinner = (Spinner) findViewById(R.id.measurements_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.measurements_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("HIo");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        this.createDrawer();
    }
}
