package com.example.cidseuser.shiftintosleep;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the user interface layout for the front page
        setContentView(R.layout.activity_personal_data);
    }

    public void editButtonClicked(View view) {

        Intent intent = new Intent(this, CalenderActivity.class);
        startActivity(intent);

    }

}
