package com.speedOMeter.speedOMeter;

import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;

public class ActivityGraph extends ActivityDrawer {

    private SharedPreferenceHandler preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.createDrawer(R.layout.content_graph_layout);

        preference =  new SharedPreferenceHandler(getApplicationContext());

        createGraph();
    }

    @Override
    public void onStart(){
        super.onStart();
       createGraph();
    }

    public void createGraph(){
        GraphView graph = (GraphView) findViewById(R.id.graph_view);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setVerticalAxisTitle(preference.getMeasurement().getAbbreviation());
        List<Float> floatList = preference.getMeasurements();
        DataPoint[] data = new DataPoint[floatList.size()];
        for (int i = 0; i < floatList.size(); i++) {
            data[i] = new DataPoint(i, floatList.get(i));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(data);
        graph.removeAllSeries();
        graph.addSeries(series);
    }
}
