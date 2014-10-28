package com.qh.half.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import butterknife.InjectView;
import com.qh.half.R;

/**
 * Created by Administrator on 2014/10/22.
 */
public class HomeFragment extends BaseFragment {
    @InjectView(R.id.focus)
    TextView mFocus;
    @InjectView(R.id.hot)
    TextView mHot;
    @InjectView(R.id.classTab)
    TextView mClassTab;
    @InjectView(R.id.homeViewPage)
    ViewPager mHomeViewPage;
    private String[] titles = {"关注", "热门", "精选"};

    @Override
    protected int getViewId() {
        return R.layout.fragment_home_layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHomeViewPage.setAdapter(new HomePageAdapter());
    }

    class HomePageAdapter extends BaseFragmentAdaper {

        public HomePageAdapter() {
            super(getChildFragmentManager());
        }


        @Override
        public Fragment getFragment(int i) {
            if (i == 1) return new HomeHotFragment();
            if (i == 2) return new HomeClassFragment();
            return new HomeFocusFragment();
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
