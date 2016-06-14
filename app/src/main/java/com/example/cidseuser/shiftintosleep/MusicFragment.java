package com.example.cidseuser.shiftintosleep;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

/**
 * Created by cidseuser on 6/13/2016.
 */
public class MusicFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void music(View view) {
        Intent intent = new Intent(this, MusicListActivity.class);
        startActivity(intent);
    }
}