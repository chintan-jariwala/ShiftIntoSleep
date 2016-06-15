package com.example.cidseuser.shiftintosleep;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;


/**
 * Created by cidseuser on 6/13/2016.
 */
public class MusicFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.music, container, false);

        Button btnMusic = (Button) view.findViewById(R.id.btnMusic);
        btnMusic.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                  if (v.getId() == R.id.btnMusic) {
                  Intent i = new Intent();
                  startActivity(i);
            }
        }
    });

    return view;

        }
    }