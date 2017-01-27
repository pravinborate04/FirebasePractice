package com.firebasepractice.pravin103082.firebasepractice;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by pravin103082 on 17-11-2016.
 */

public class FragmentViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments;
    List<String> tabs;

    public FragmentViewPagerAdapter(FragmentManager fm,List<Fragment> fragments,List<String> tabs) {
        super(fm);
        this.fragments=fragments;
        this.tabs=tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position);
    }
}
