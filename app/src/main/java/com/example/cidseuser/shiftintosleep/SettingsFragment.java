package com.example.cidseuser.shiftintosleep;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;

/**
 * Created by cidseuser on 6/10/2016.
 */
public class SettingsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("SETTINGS","On create viwe");

        View view = inflater.inflate(R.layout.activity_settings, container, false);


        Button btn = (Button)view.findViewById(R.id.Langbutt);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), LanguageActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


    public void addLangButtonListener (View view){

    }
}