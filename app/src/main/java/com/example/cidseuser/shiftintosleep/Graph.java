package com.example.cidseuser.shiftintosleep;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.IBinder;
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

    accelerometerservice  mService;
    private XYSeries series;
    int index = 0;
    GraphicalView chartView;
    XYMultipleSeriesRenderer mRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        showGraphView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, accelerometerservice.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConnection);

    }


    private void showGraphView() {

        DatabaseOperations db = new DatabaseOperations(this);
        ArrayList<Double> list  = db.getAllAccelerometer();
        series = new XYSeries("Noise Levels (amp)");
        int i = 1;
//        for (Double xvalue : list) {
//            Log.i("Acc", "X - " + xvalue);
//            series.add(i++,xvalue);
//        }



        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series);

        // Now we create the renderer
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.RED);

// we add point markers
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setPointStrokeWidth(3);
         mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);
        // We want to avoid black border
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
// Disable Pan on two axis
        mRenderer.setPanEnabled(true, false);
        mRenderer.setYAxisMax(10);
        mRenderer.setYAxisMin(-10);

        int xmax = 200; /* Collections.max(arrayList); */
        int  xmin = xmax - 168;
        mRenderer.setXAxisMin(0);
        mRenderer.setXAxisMax(100);
        mRenderer.setShowGrid(true); // we show the grid
        mRenderer.setXTitle("Time");
        mRenderer.setAxisTitleTextSize(40);
        mRenderer.setYTitle("Noise Levels");
        mRenderer.setMargins(new int[] {10, 50, 50, 5});
        mRenderer.setInScroll(false);
        mRenderer.setZoomEnabled(true, true);
        chartView = ChartFactory.getLineChartView(this, dataset, mRenderer);
        ScrollView linearLayout = (ScrollView)findViewById(R.id.chart);
        linearLayout.addView(chartView,0);


    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            accelerometerservice.LocalBinder binder = (accelerometerservice.LocalBinder) service;
            mService = binder.getService();
            mService.setActivity(Graph.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {

        }
    };

    public void onAccelerometerValueChanged(double x) {
        Log.i("Accelorometer sensor","X"+x);
        series.add(index++, x);


        double maxX = series.getMaxX();
        double minX = maxX - 10; // deltaX is your required x-range
        double maxY = series.getMinY();
        double minY = series.getMaxY();

        mRenderer.setRange(new double[] { minX, maxX, -10, 10 });

        chartView.repaint();


    }
}
