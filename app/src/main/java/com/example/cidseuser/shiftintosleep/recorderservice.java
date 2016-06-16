package com.example.cidseuser.shiftintosleep;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Binder;
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
    private  Graph mActivity = null;
    private final IBinder mBinder = new NoiseLocalBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class NoiseLocalBinder extends Binder {
        recorderservice getService() {
            // Return this instance of LocalService so clients can call public methods
            return recorderservice.this;
        }
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
                    if (mActivity != null) {
                        DatabaseOperations db = new DatabaseOperations(recorderservice.this);
                        db.putUserNoise("userName", amp, System.currentTimeMillis());
                        mActivity.onAmpChange(amp,System.currentTimeMillis());
                    }
                }

            }, 0, 100);
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
            myTimer.cancel();
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

    public void setActivity(Graph activity) {
        mActivity  = activity;
    }
}
