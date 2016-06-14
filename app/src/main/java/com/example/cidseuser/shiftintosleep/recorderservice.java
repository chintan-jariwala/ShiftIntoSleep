package com.example.cidseuser.shiftintosleep;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cidseuser on 6/13/2016.
 */
public class recorderservice extends Service {

    private MediaRecorder mRecorder = null;
    private Timer myTimer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "service is created", Toast.LENGTH_LONG ).show();
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile("/dev/null");

            try {

                mRecorder.prepare();

            } catch (IOException exception) {
                //Recorder could not be started

            }


            mRecorder.start();


            myTimer = new Timer();
            myTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                   double amp = getAmplitude();
//                    Toast.makeText(recorderservice.this,"Amplitude - " + amp, Toast.LENGTH_SHORT).show();
                    Log.i("TAG", "Amplitude - " + amp);
                }

            }, 0, 1000);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service is created", Toast.LENGTH_LONG ).show();
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;

        }
    }


    public double getAmplitude() {
        if (mRecorder != null)
            return mRecorder.getMaxAmplitude();
        else
            return 0;

    }
}
