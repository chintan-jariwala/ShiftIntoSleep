package com.example.cidseuser.shiftintosleep;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;

public class Graph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        showGraphView();
    }

    private void showGraphView() {

        DatabaseOperations db = new DatabaseOperations(this);
        ArrayList<Double> list  = db.getAllAccelerometer();
        XYSeries series = new XYSeries("Noise Levels (amp)");
        for (Double xvalue : list) {
            Log.i("Acc", "X - " + xvalue);
            series.add(12,xvalue);
        }



        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series);

        // Now we create the renderer
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.RED);

// we add point markers
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setPointStrokeWidth(3);
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);
        // We want to avoid black border
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
// Disable Pan on two axis
        mRenderer.setPanEnabled(true, false);
        mRenderer.setYAxisMax(100);
        mRenderer.setYAxisMin(0);

        int xmax = 200; /* Collections.max(arrayList); */
        int  xmin = xmax - 168;
        mRenderer.setXAxisMin(0);
        mRenderer.setXAxisMax(15);
        mRenderer.setShowGrid(true); // we show the grid
        mRenderer.setXTitle("Time");
        mRenderer.setAxisTitleTextSize(40);
        mRenderer.setYTitle("Noise Levels");
        mRenderer.setMargins(new int[] {10, 50, 50, 5});
        mRenderer.setInScroll(false);
    mRenderer.setZoomEnabled(true, true);
        GraphicalView chartView = ChartFactory.getLineChartView(this, dataset, mRenderer);
        ScrollView linearLayout = (ScrollView)findViewById(R.id.chart);
        linearLayout.addView(chartView,0);


    }

}
