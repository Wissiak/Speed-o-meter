package com.speedOMeter.speedOMeter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SpeedTrackingActivity extends DrawerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.speed_tracking_activity);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

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

        this.createDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("hioooooo");
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
}
