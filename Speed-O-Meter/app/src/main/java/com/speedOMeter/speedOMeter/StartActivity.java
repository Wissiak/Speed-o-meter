package com.speedOMeter.speedOMeter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_start);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, SpeedTrackingActivity.class);
                startActivity(intent);
            }
        });
    }
}
