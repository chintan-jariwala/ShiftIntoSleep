package com.example.cidseuser.shiftintosleep;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

// Array of options --} ArrayAdapter --} ListView
// List view: {views: da_items.xml}

public class MusicListActivity extends Activity {

    private MediaPlayer MediaPlayer;
    private int musicIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the user interface layout for the music page
        setContentView(R.layout.music_activity);
        // List View: {views: da_items.xml}

        registerClickCallback();
        populateListView();
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String frame="";
                if (i == 0){
                    frame = "relaxing_lullaby";
                } else if (i==1) {
                    frame = "waterfall_sounds";
                } else if (i==2) {
                    frame = "rainforest_stream";
                } else if (i==3) {
                    frame = "ocean_waves";
                } else if (i==4) {
                    frame = "piano_music";
                } else if (i==5) {
                    frame = "violin";
                }

                if (musicIndex == i) {
                    if (MediaPlayer.isPlaying()) { Toast.makeText(MusicListActivity.this, "Playing " + i, Toast.LENGTH_LONG).show();
                        MediaPlayer.pause();
                    } else { Toast.makeText(MusicListActivity.this, "Paused " + i, Toast.LENGTH_LONG).show();
                        MediaPlayer.start();
                    }

                } else {
                    int resID=getResources().getIdentifier(frame, "raw", getPackageName());
                    if(MediaPlayer != null && MediaPlayer.isPlaying()) {
                        MediaPlayer.stop();
                    }

                    MediaPlayer = MediaPlayer.create(MusicListActivity.this,resID);
                    MediaPlayer.start();
                }

                musicIndex = i;
            }
        });}

    public void MediaPlayer(String path, String fileName){
        //set up MediaPlayer
        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(path + File.separator + fileName);
            mp.prepare();
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void populateListView() {
        // Create list of items
        String[] myItems = {"Lullaby", "Waterfall", "Rain Forest", "Oceans", "Piano", "Violin"};
        // Build Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,               // Context For The Activity
                R.layout.da_items,  // layout to use(create)
                myItems);           // Items to be displayed
        // Configure the list view.
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);

    }
}
