package com.example.cidseuser.shiftintosleep;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by mlall on 6/9/2016.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new TabFragment();
        } else if (position == 1) {
            return new SettingsFragment();
        } else if (position == 2) {
            return new AlarmsFragment();
        } else if (position == 3) {
            return new StatsFragment();
        } else if (position == 4) {
            return new MusicFragment();
        } else if (position == 5) {
            return new TipsFragment();
        }



        return null;
    }
    @Override
    public int getCount(){
        return 6;
    }

}
