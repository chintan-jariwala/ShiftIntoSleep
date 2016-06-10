package com.example.cidseuser.shiftintosleep;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by mlall on 6/9/2016.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("TESTing", "Position " + position);
        if (position == 0) {
            return new TabFragment();
        } else if (position == 1) {
            return new SetupFragment();
        } else if (position == 2) {
            return new AlarmsFragment();
        } else if (position == 3) {
            return new StatsFragment();
        } else if (position == 4) {
            return new TipsFragment();
        }
        return new TabFragment();

    }

    @Override
    public int getCount(){
        return 5;
    }
}
