package com.example.cidseuser.shiftintosleep;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

// Array of options --} ArrayAdapter --} ListView
// List view: {views: da_items.xml}

public class MusicListActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
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
                String fname="";
                if (i == 0){
                    fname = "relaxing_lullaby";
                } else if (i==1) {
                    fname = "waterfall_sounds";
                } else if (i==2) {
                    fname = "rainforest_stream";
                } else if (i==3) {
                    fname = "ocean_waves";
                } else if (i==4) {
                    fname = "piano_music";
                } else if (i==5) {
                    fname = "violin";
                }

                if (musicIndex == i) {
                    if (mediaPlayer.isPlaying()) { Toast.makeText(MusicListActivity.this, "Playing " + i, Toast.LENGTH_LONG).show();
                        mediaPlayer.pause();
                    } else { Toast.makeText(MusicListActivity.this, "Paused " + i, Toast.LENGTH_LONG).show();
                        mediaPlayer.start();
                    }

                } else {
                    int resID=getResources().getIdentifier(fname, "raw", getPackageName());
                    if(mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }

                    mediaPlayer = MediaPlayer.create(MusicListActivity.this,resID);
                    mediaPlayer.start();
                }

                musicIndex = i;
            }
        });}

    public void audioPlayer(String path, String fileName){
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
