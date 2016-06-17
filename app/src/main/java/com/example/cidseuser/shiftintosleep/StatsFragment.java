package com.example.cidseuser.shiftintosleep;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;

/**
 * Created by cidseuser on 6/10/2016.
 */
public class StatsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_personal_data, container, false);

        Button btn = (Button)view.findViewById(R.id.editButtonSchedule);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), CalenderActivity.class);
                        startActivity(intent);
            }
        });

        Button graphBtn = (Button)view.findViewById(R.id.Graphbtn);
        graphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Graph.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void editButtonClicked(View view) {

//        Intent intent = new Intent(this, CalenderActivity.class);
//        startActivity(intent);

        Intent intent = new Intent(getActivity(), Graph.class);
        startActivity(intent);
    }
}
