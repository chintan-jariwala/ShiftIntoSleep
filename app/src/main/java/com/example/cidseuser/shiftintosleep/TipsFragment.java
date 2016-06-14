package com.example.cidseuser.shiftintosleep;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;

/**
 * Created by cidseuser on 6/10/2016.
 */
public class TipsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tips, container, false);

        Button btnSleepwalking = (Button) view.findViewById(R.id.btnSleepwalking);
        btnSleepwalking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.btnSleepwalking) {
                    Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("http://sleepeducation.org/sleep-disorders-by-category/parasomnias/sleepwalking"));
                    startActivity(i);
                }
            }
        });

        Button btnInsominia = (Button) view.findViewById(R.id.btnInsomnia);
        btnInsominia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.btnInsomnia) {
                    Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("http://sleepeducation.org/essentials-in-sleep/insomnia"));
                    startActivity(i);
                }
            }
        });

        Button btnNightTerrors = (Button) view.findViewById(R.id.btnNightTerrors);
        btnNightTerrors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.btnNightTerrors) {
                    Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("http://sleepeducation.org/sleep-disorders-by-category/parasomnias/sleep-terrors/overview-facts"));
                    startActivity(i);
                }
            }
        });

        Button btnRestlessLegsSyndrome = (Button) view.findViewById(R.id.btnRestlessLegsSyndrome);
        btnRestlessLegsSyndrome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.btnRestlessLegsSyndrome) {
                    Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("http://sleepeducation.org/essentials-in-sleep/restless-legs-syndrome"));
                    startActivity(i);
                }
            }
        });

        Button btnSleepApnea = (Button) view.findViewById(R.id.btnSleepApnea);
        btnSleepApnea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.btnSleepApnea) {
                    Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("http://sleepeducation.org/essentials-in-sleep/sleep-apnea"));
                    startActivity(i);
                }
            }
        });

        Button btnJetLag = (Button) view.findViewById(R.id.btnJetLag);
        btnJetLag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.btnJetLag) {
                    Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("http://sleepeducation.org/essentials-in-sleep/jet-lag"));
                    startActivity(i);
                }
            }
        });

        Button btnNarcolepsy = (Button) view.findViewById(R.id.btnNarcolepsy);
        btnNarcolepsy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.btnNarcolepsy) {
                    Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("http://sleepeducation.org/essentials-in-sleep/narcolepsy"));
                    startActivity(i);
                }
            }
        });

        return view;
    }


   }