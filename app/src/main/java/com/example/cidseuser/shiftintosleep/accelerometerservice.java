package com.example.cidseuser.shiftintosleep;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by cidseuser on 6/14/2016.
 */
 public class accelerometerservice extends Service implements SensorEventListener{

    Sensor sensor;
    SensorManager sm;
    MediaPlayer mPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Service is created.", Toast.LENGTH_LONG).show();
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service is started.", Toast.LENGTH_LONG).show();
       return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service is ended.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //displayReading.setText("X"+event.values[0]"Y"+event"Z")
        Log.i("Accelorometer sensor","X"+event.values[0]+"\nY"+event.values[1]+"\nZ"+event.values[2]);

        Date date = new Date();
        DatabaseOperations db = new DatabaseOperations(this);
        db.putUserAccelerometer("userName", event.values[0], event.values[1], event.values[2], date.toString());
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int argl) {

    }
}
