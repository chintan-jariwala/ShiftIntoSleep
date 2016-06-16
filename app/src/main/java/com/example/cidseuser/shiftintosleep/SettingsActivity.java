package com.example.cidseuser.shiftintosleep;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by cidseuser on 6/16/2016.
 */
public class SettingsActivity extends AppCompatActivity {

    private ToggleButton vibrationToggle;
    private int brightness;
    private ContentResolver cResolver;
    private Window window;
    private SeekBar brightnessSeekbar;
    private SeekBar volumeSeekbar;
    private SeekBar musicVolume;
    private AudioManager audioManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this)) {
                // Do stuff here
            }
            else {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }

        // Set the user interface layout for the front page
        setContentView(R.layout.activity_settings);





        vibrationToggle = (ToggleButton)findViewById(R.id.vib_toggle_button);
        addToggleButtonListener();



        brightnessSeekbar = (SeekBar)findViewById(R.id.seekBarBrightness);
        brightnessSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i("Testing", "Value - " + i);

                cResolver =   SettingsActivity.this.getApplicationContext().getContentResolver(); //getContentResolver();
                window = getWindow();



                Settings.System.putInt(cResolver,
                        Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
                WindowManager.LayoutParams layoutpars = window.getAttributes();
                layoutpars.screenBrightness = i / (float)255;
                window.setAttributes(layoutpars);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Intent intent = new Intent(SettingsActivity.this, DummyActivity.class);
                SettingsActivity.this.startActivity(intent);
            }
        });

        this.setVolumeControlStream(AudioManager.STREAM_ALARM);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);


        initControls();}
    private void initControls(){
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        volumeSeekbar = (SeekBar)findViewById(R.id.seekBarVolume);
        volumeSeekbar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM));
        musicVolume = (SeekBar)findViewById(R.id.seekBarMusic);
        musicVolume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        try {
            volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onStopTrackingTouch(SeekBar arg0) {

                }

                public void onStartTrackingTouch(SeekBar arg0) {

                }

                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_ALARM, progress, 0);

                }
            });
            musicVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
                public void onStopTrackingTouch(SeekBar arg0) {

                }

                public void onStartTrackingTouch(SeekBar arg0) {

                }

                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }}







    private void addToggleButtonListener() {

        vibrationToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {

                SharedPreferences settings = getSharedPreferences("settings", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("vibrateon", isChecked);

                editor.apply();
                Vibrator v = (Vibrator) SettingsActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                v.vibrate(500);

            }

        });

    }








    public void addButtonListener(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);


    }

    public void addLangButtonListener (View view){
        finish();
        Intent intent = new Intent(this, LanguageActivity.class);
        startActivity(intent);
    }




}