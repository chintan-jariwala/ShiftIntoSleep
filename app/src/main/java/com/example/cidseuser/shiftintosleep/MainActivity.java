package com.example.cidseuser.shiftintosleep;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.ToggleButton;


public class MainActivity extends AppCompatActivity {

    private ToggleButton vibrationToggle;
    private Spinner spinspin;
    private int brightness;
    private ContentResolver cResolver;
    private Window window;

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

        spinspin = (Spinner)findViewById(R.id.spin_ner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.language_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinspin.setAdapter(adapter);


        cResolver = getContentResolver();
        window = getWindow();

        try
        {
            Settings.System.putInt(cResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            brightness = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS);
        }
        catch (Settings.SettingNotFoundException e)
        {
            Log.e("Error", "Cannot access system brightness");
            e.printStackTrace();
        }
        Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
        WindowManager.LayoutParams layoutpars = window.getAttributes();
        layoutpars.screenBrightness = brightness / (float)255;
        window.setAttributes(layoutpars);

    }

    private void addToggleButtonListener() {

        vibrationToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {

                SharedPreferences settings = getSharedPreferences("settings", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("vibrateon", isChecked);

                editor.commit();
            }

        });

    }
    public void addButtonListener(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);


    }


}


