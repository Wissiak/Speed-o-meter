package com.speedOMeter.speedOMeter;

import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ActivityGraph extends ActivityDrawer {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.createDrawer(R.layout.content_graph_layout);

        GraphView graph = (GraphView) findViewById(R.id.graph_view);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setVerticalAxisTitle("km/h");
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3)
        });
        graph.addSeries(series);
    }
}
