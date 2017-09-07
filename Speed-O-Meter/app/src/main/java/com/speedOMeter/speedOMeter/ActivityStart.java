package com.speedOMeter.speedOMeter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ActivityStart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferenceHandler preferenceHandler = new SharedPreferenceHandler(getApplicationContext());
        preferenceHandler.reset();

        setContentView(R.layout.activity_start_layout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_start);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityStart.this, ActivityTracking.class);
                startActivity(intent);
            }
        });
    }
}
