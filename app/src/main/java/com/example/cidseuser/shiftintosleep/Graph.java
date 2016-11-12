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
import android.view.View;
import android.widget.ScrollView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;

public class Graph extends AppCompatActivity {

    accelerometerservice  mService;
    recorderservice mNoiseDetector;
    private XYSeries series;
    private XYSeries ampSeries;

    int index = 0;
    int ampIndex = 0;
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
    }

    public void start_recording (View view) {

        series.clear();
        ampSeries.clear();
        Intent intent = new Intent(this, accelerometerservice.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        Intent ampIntent = new Intent(this, recorderservice.class);
        bindService(ampIntent, mAmpConnection, Context.BIND_AUTO_CREATE);
    }

    public void stop_recording (View view) {

        unbindService(mConnection);
        unbindService(mAmpConnection);
    }

    public void showHistory (View view) {
        series.clear();
        ampSeries.clear();

        DatabaseOperations db = new DatabaseOperations(this);
        ArrayList<Accelerometer> list  = db.getAllAccelerometer();

        for (Accelerometer acc : list) {
            series.add(acc.timestamp, acc.x);
        }


        ArrayList<Noise> noiseArrayList  = db.getAllNoise();
        for (Noise noise : noiseArrayList) {
            ampSeries.add(noise.timestamp, noise.amp/5000);
        } double sum =0;
                for (Noise noise : noiseArrayList) {
                    sum = sum + noise.amp/5000;
                }
        double avg = sum/noiseArrayList.size();

        int size = noiseArrayList.size(); System.out.println("size of array list after creating: " + size);
        System.out.println("length: " + size);
        int THRESHOLD = 120;

        for (Noise noise : noiseArrayList) {
            double diff = noise.amp/5000 - avg;
            if (diff > THRESHOLD) {

            }
        }







        double maxX = series.getMaxX();
        double minX = maxX - 10000; // deltaX is your required x-range
        mRenderer.setRange(new double[] { minX, maxX, -10, 10 });
        chartView.repaint();
    }

    private void showGraphView() {


        series = new XYSeries("Acce Levels (amp)");
        ampSeries = new XYSeries("Noise Levels (amp)");


        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series);
        dataset.addSeries(ampSeries);

        // Now we create the renderer
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.RED);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setPointStrokeWidth(3);

        XYSeriesRenderer ampRender = new XYSeriesRenderer();
        ampRender.setLineWidth(2);
        ampRender.setColor(Color.BLUE);
        ampRender.setPointStyle(PointStyle.CIRCLE);
        ampRender.setPointStrokeWidth(3);

// we add point markers

         mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);
        mRenderer.addSeriesRenderer(ampRender);
        // We want to avoid black border
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins

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


    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mAmpConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            recorderservice.NoiseLocalBinder binder = (recorderservice.NoiseLocalBinder) service;
            mNoiseDetector = binder.getService();
            mNoiseDetector.setActivity(Graph.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {

        }
    };


    public void onAccelerometerValueChanged(double x, long timestamp) {

        series.add(timestamp, x);


        double maxX = series.getMaxX();
        double minX = maxX - 10000; // deltaX is your required x-range
        mRenderer.setRange(new double[] { minX, maxX, -10, 10 });
        chartView.repaint();
    }

    public void onAmpChange(double x, long timestamp) {
        ampSeries.add(timestamp, x/5000);
        Log.i("AMp sensor","X "+timestamp);

        chartView.repaint();
    }
}
