package com.speedOMeter.speedOMeter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class SpeedTrackingActivity extends AppCompatActivity {
    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setHeight(350);
        return textView;
    }

    private TextView createTitleTextView(String text, int colorId) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(34);
        textView.setTextColor(ResourcesCompat.getColor(getResources(), colorId, null));
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setHeight(350);
        return textView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int view = R.layout.speed_tracking_activity;
        setContentView(view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<View> content = new ArrayList<>();
        content.add(this.createTitleTextView(getString(R.string.current_speed), R.color.colorThirdAccent));
        content.add(this.createTextView(getString(R.string.formatted_speed, 0, getString(R.string.current_measurement))));

        content.add(this.createTitleTextView(getString(R.string.average_speed), R.color.colorFourthAccent));
        content.add(this.createTextView(getString(R.string.formatted_speed, 0, getString(R.string.current_measurement))));

        content.add(this.createTitleTextView(getString(R.string.max_speed), R.color.colorSecondAccent));
        content.add(this.createTextView(getString(R.string.formatted_speed, 0, getString(R.string.current_measurement))));

        GridView gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(new ViewAdapter(content));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.action_stop_tracking);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.speed_tracking_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        View view = this.findViewById(R.id.layout_speed_tracking_activity);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            this.changeView(SettingsActivity.class);
        } else if (id == R.id.action_stop_tracking) {
            Snackbar.make(view, "Stop tracking", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void changeView(Class<?> newActivity) {
        Intent intent = new Intent(this, newActivity);
        intent.putExtra(EXTRA_MESSAGE, "Some intent message");
        startActivity(intent);
    }
}
