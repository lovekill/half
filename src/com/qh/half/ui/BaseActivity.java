package com.qh.half.ui;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import com.qh.half.ui.fragment.BaseFragment;

/**
 * Created by engine on 2014/10/19.
 */
public class BaseActivity extends FragmentActivity{
    public String TAG = getClass().getSimpleName();
    public void addFragment(BaseFragment fragment){
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
    }
}
