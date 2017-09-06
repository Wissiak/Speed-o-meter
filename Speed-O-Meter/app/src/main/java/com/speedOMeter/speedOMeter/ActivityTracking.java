package com.speedOMeter.speedOMeter;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ActivityTracking extends ActivityDrawer {
    private SharedPreferenceHandler preferenceHandler = null;
    private boolean isTracking;
    private GPSTracker tracker;
    private Location lastLocation;
    private Consumer consumer = new Consumer() {
        @Override
        void consume(Object o) {
            if (o instanceof Location) {
                if (lastLocation != null) {
                    Measurement measurement = new Measurement(lastLocation, ((Location) o), (((Location) o).getTime() - lastLocation.getTime()) / 1000);
                    preferenceHandler.setSpeed(measurement.getSpeed(preferenceHandler.getMeasurement()));
                }
                lastLocation = (Location) o;
            }
        }
    };

    public ActivityTracking() {
        this.isTracking = true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(isTracking) {
            //Create SharedPreferenceHandler if onStart() is called the first time
            preferenceHandler = new SharedPreferenceHandler(getApplicationContext());
        }
        isTracking = true;

        final Handler handler = new Handler();
        new Thread(new Runnable() {
            public void run() {
                while (isTracking) {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable(){
                        public void run() {
                            //Update speed every 1.5s
                            updateContent(preferenceHandler.getCurrentSpeed(), preferenceHandler.getAverageSpeed(), preferenceHandler.getMaxSpeed());
                            //preferenceHandler.setSpeed(new Random().nextFloat() * 200); //TODO: remove this
                        }
                    });
                }
            }
        }).start();
        tracker = new GPSTracker(getApplicationContext(), ActivityTracking.this, consumer);
    }

    private void updateContent(float curSpeed, float averageSpeed, float maxSpeed) {
        String measurement = preferenceHandler.getMeasurement().getAbbreviation();
        TextView curText = (TextView) findViewById(R.id.cur_text);
        TextView avgText = (TextView) findViewById(R.id.avg_text);
        TextView maxText = (TextView) findViewById(R.id.max_text);
        curText.setText(curSpeed + "\n" + measurement);
        avgText.setText(averageSpeed + "\n" + measurement);
        maxText.setText(maxSpeed + "\n" + measurement);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.createDrawer(R.layout.content_tracking_layout);
        this.setContent();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.action_stop_tracking);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.action_stop_tracking);
                Drawable drawable;
                if(isTracking) {
                    isTracking = false;
                    drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_start);
                } else {
                    onStart();
                    drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_stop);
                }
                fab.setImageDrawable(drawable);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.isTracking = true;
    }

    @Override
    protected void onStop() {
        super.onStop();

        this.isTracking = false;
    }

    @Override
    public void onPause() {
        super.onPause();

        this.isTracking = false;
    }

    private void setContent() {
        ArrayList<View> contentCur = new ArrayList<>();
        contentCur.add(this.createTextView(getString(R.string.current_speed), true, 0));
        contentCur.add(this.createTextView("0.0", false, R.id.cur_text));

        ArrayList<View> contentAvg = new ArrayList<>();
        contentAvg.add(this.createTextView(getString(R.string.average_speed), true, 0));
        contentAvg.add(this.createTextView("0.0", false, R.id.avg_text));

        ArrayList<View> contentMax = new ArrayList<>();
        contentMax.add(this.createTextView(getString(R.string.max_speed), true, 0));
        contentMax.add(this.createTextView("0.0", false, R.id.max_text));

        LinearLayout activityContent = (LinearLayout) findViewById(R.id.speed_tracking_content);
        //Create a grid view to support landscape mode
        activityContent.addView(createGridView(contentCur, R.color.colorFourthAccent));
        activityContent.addView(createGridView(contentAvg, R.color.colorThirdAccent));
        activityContent.addView(createGridView(contentMax, R.color.colorSecondAccent));
    }

    private TextView createTextView(String text, boolean isTitle, int id) {
        TextView textView = new TextView(this);
        if (!isTitle) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 100, 0, 0);
            textView.setLayoutParams(params);
            textView.setTextSize(45);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setId(id);
        } else {
            textView.setTextSize(20);
        }
        textView.setText(text);
        textView.setGravity(isTitle ? Gravity.CENTER : Gravity.CENTER);
        return textView;
    }

    private int calculatePxFromDp(int dp) {
        final float dpScale = getResources().getDisplayMetrics().density;
        return (int) (dp * dpScale + 0.5f);
    }

    private GridView createGridView(List<View> content, int colorId) {
        int margin = calculatePxFromDp(16);
        GridView gridView = new GridView(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        gridView.setPadding(margin, margin, margin, margin);
        gridView.setLayoutParams(params);
        gridView.setNumColumns(GridView.AUTO_FIT);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setAdapter(new ViewAdapter(content));
        gridView.setColumnWidth(this.calculatePxFromDp(240));
        gridView.setBackgroundColor(ResourcesCompat.getColor(getResources(), colorId, null));
        gridView.setGravity(Gravity.CENTER);
        return gridView;
    }
}
