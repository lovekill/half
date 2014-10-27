package com.qh.half.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.InjectView;
import com.qh.half.R;

/**
 * Created by Administrator on 2014/10/22.
 */
public class SquartFragment extends BaseFragment {
    @InjectView(R.id.leftHiden)
    ImageView mLeftHiden;
    @InjectView(R.id.squartSquart)
    TextView mSquartSquart;
    @InjectView(R.id.squartFriend)
    TextView mSquartFriend;
    @InjectView(R.id.squartViewPager)
    ViewPager mSquartViewPager;


    @Override
    protected int getViewId() {
        return R.layout.fragment_squart_layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SquartPageAdapter squartPageAdapter = new SquartPageAdapter() ;
        mSquartViewPager.setAdapter(squartPageAdapter);
    }

    class SquartPageAdapter extends BaseFragmentAdaper{

        public SquartPageAdapter() {
            super(getChildFragmentManager());
        }

        @Override
        public Fragment getFragment(int i) {
            if(i==0) return  new SquartContentFragment();
            return new SquartFriendFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
