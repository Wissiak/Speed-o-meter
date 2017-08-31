package com.speedOMeter.speedOMeter;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphActivity extends DrawerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.graph_activity);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        GraphView graph = (GraphView) findViewById(R.id.graph_view);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setVerticalAxisTitle("km/h");
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3)
        });
        graph.addSeries(series);

        this.createDrawer();
    }
}
