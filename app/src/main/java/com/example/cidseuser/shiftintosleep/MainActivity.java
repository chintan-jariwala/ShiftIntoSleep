package com.example.cidseuser.shiftintosleep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the user interface layout for the front page
        setContentView(R.layout.activity_main);


        Intent intent = new Intent(this, recorderservice.class);
        startService(intent);

    }
    public void buttonClicked (View view) {
        Intent intent = new Intent(this, MusicListActivity.class);
        startActivity(intent);
    }
}
