package com.example.cidseuser.shiftintosleep;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

public class LanguageActivity extends AppCompatActivity {
    private RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        /* Initialize Radio Group and attach click handler */
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.clearCheck();

        /* Attach CheckedChangeListener to radio group */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if(null!=rb && checkedId > -1){
                    String langCOde = "en";
                   // Toast.makeText(LanguageActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                    if ( rb.getText().equals("English")) {
                        langCOde = "en";
                    } else if (rb.getText().equals("Spanish")) {
                        langCOde = "es";
                    }
                    Resources res = getResources();
                 // Change locale settings in the app.
                    DisplayMetrics dm = res.getDisplayMetrics();
                    android.content.res.Configuration conf = res.getConfiguration();
                    conf.locale = new Locale(langCOde);
                    res.updateConfiguration(conf, dm);
                    Log.i("LANG", "Language changed");
                    finish();

                    Intent intent = new Intent(LanguageActivity.this, MainActivity.class);
                    startActivity(intent);

                }

            }
        });
    }





//        spinspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String languageToLoad = "en";
//                if (i == 0) {
//                    Log.i("Spinner", "English");
//                    languageToLoad = "en";
//                } else if (i==1) {
//                    Log.i("Spinner", "French");
//                    languageToLoad = "fr";
//                } else if (i == 2){
//                    Log.i("Spinner", "Spanish");
//                    languageToLoad =  "es";
//                }
//
//
//
//                Resources res = getResources();
//                // Change locale settings in the app.
//                DisplayMetrics dm = res.getDisplayMetrics();
//                android.content.res.Configuration conf = res.getConfiguration();
//                conf.locale = new Locale(languageToLoad);
//                res.updateConfiguration(conf, dm);
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


}
