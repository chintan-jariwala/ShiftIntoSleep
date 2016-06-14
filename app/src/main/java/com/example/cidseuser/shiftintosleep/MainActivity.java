package com.example.cidseuser.shiftintosleep;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.ToggleButton;


public class MainActivity extends AppCompatActivity {

    private ToggleButton vibrationToggle;
    private Spinner spinspin;
    private int brightness;
    private ContentResolver cResolver;
    private Window window;
    private SeekBar brightnessSeekbar;
    private SeekBar volumeSeekbar;
    private AudioManager audio;

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
        setContentView(R.layout.activity_main);

        vibrationToggle = (ToggleButton)findViewById(R.id.vib_toggle_button);
        addToggleButtonListener();

        volumeSeekbar = (SeekBar)findViewById(R.id.seekBarVolume);
        volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        brightnessSeekbar = (SeekBar)findViewById(R.id.seekBarBrightness);
        brightnessSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i("Testing", "Value - " + i);

                cResolver =   MainActivity.this.getApplicationContext().getContentResolver(); //getContentResolver();
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
                Intent intent = new Intent(MainActivity.this, DummyActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        spinspin = (Spinner)findViewById(R.id.spin_ner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.language_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinspin.setAdapter(adapter);




    }

    private void addToggleButtonListener() {

        vibrationToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {

                SharedPreferences settings = getSharedPreferences("settings", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("vibrateon", isChecked);

                editor.commit();
                Vibrator v = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                v.vibrate(500);

            }

        });

    }

    public void addButtonListener(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);


    }


}


