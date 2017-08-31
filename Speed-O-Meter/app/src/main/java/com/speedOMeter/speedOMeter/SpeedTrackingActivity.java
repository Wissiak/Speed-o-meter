package com.speedOMeter.speedOMeter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class SpeedTrackingActivity extends AppCompatActivity {@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int view = R.layout.speed_tracking_activity;
        setContentView(view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<View> contentCur = new ArrayList<>();
        contentCur.add(this.createTitleTextView(getString(R.string.current_speed), R.color.colorThirdAccent));
        contentCur.add(this.createTextView(getString(R.string.formatted_speed, 0, getString(R.string.current_measurement))));

        ArrayList<View> contentAvg = new ArrayList<>();
        contentAvg.add(this.createTitleTextView(getString(R.string.average_speed), R.color.colorFourthAccent));
        contentAvg.add(this.createTextView(getString(R.string.formatted_speed, 0, getString(R.string.current_measurement))));

        ArrayList<View> contentMax = new ArrayList<>();
        contentMax.add(this.createTitleTextView(getString(R.string.max_speed), R.color.colorSecondAccent));
        contentMax.add(this.createTextView(getString(R.string.formatted_speed, 0, getString(R.string.current_measurement))));

        LinearLayout activityContent = (LinearLayout) findViewById(R.id.speed_tracking_content);
        activityContent.addView(createGridView(contentCur));
        activityContent.addView(createGridView(contentAvg));
        activityContent.addView(createGridView(contentMax));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.action_stop_tracking);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    private TextView createTitleTextView(String text, int colorId) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(34);
        textView.setTextColor(ResourcesCompat.getColor(getResources(), colorId, null));
        textView.setGravity(Gravity.CENTER_VERTICAL);
        return textView;
    }

    private GridView createGridView(List<View> content) {
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (240 * scale + 0.5f);
        GridView gridView = new GridView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        gridView.setLayoutParams(params);
        gridView.setGravity(Gravity.CENTER);
        gridView.setNumColumns(GridView.AUTO_FIT);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setAdapter(new ViewAdapter(content));
        gridView.setColumnWidth(pixels);
        return gridView;
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
