package com.qh.half.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/10/23.
 */
public abstract class BaseFragmentAdaper extends FragmentPagerAdapter {
    private List<Fragment>  fragmentList = new ArrayList<Fragment>();
    public BaseFragmentAdaper(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if(fragmentList.size()<=i){
            fragmentList.add(getFragment(i));
        }
        return fragmentList.get(i);
    }

     public  abstract Fragment getFragment(int i) ;
}
