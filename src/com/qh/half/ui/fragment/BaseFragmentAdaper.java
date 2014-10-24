package com.qh.half.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.qh.half.util.LOGUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/10/23.
 */
public abstract class BaseFragmentAdaper extends FragmentPagerAdapter {
    Map<Integer,Fragment> fragmentMap = new HashMap<Integer, Fragment>();
    public BaseFragmentAdaper(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if(fragmentMap.get(i)!=null){
            return fragmentMap.get(i);
        }else {
           fragmentMap.put(i,getFragment(i));
            return fragmentMap.get(i);
        }
    }

     public  abstract Fragment getFragment(int i) ;
}
