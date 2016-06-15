package com.example.cidseuser.shiftintosleep;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the user interface layout for the front page
        setContentView(R.layout.activity_personal_data);

//        Intent intent = new Intent(this, accelerometerservice.class);
//        startService(intent);
//        DatabaseOperations db = new DatabaseOperations(this);
//        db.putUserInformation("xyxz", "123");

        Intent intent = new Intent(this, Graph.class);
        startActivity(intent);

//        buildFitnessClient();
    }

    public void editButtonClicked(View view) {

//        Intent intent = new Intent(this, CalenderActivity.class);
//        startActivity(intent);

        Intent intent = new Intent(this, Graph.class);
        startActivity(intent);
    }






}
