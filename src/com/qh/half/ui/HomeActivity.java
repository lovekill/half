package com.qh.half.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.qh.half.R;
import com.qh.half.util.LOGUtil;

/**
 * Created by Administrator on 2014/10/21.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.tablayout)
    LinearLayout mTablayout;
    //    @InjectView(android.R.id.tabcontent)
//    FrameLayout mTabcontent;
    @InjectView(android.R.id.tabs)
    TabWidget mTabs;
    @InjectView(R.id.tabHost)
    TabHost mTabHost;
    @InjectView(R.id.homeTab)
    LinearLayout mHomeTab;
    @InjectView(R.id.squart)
    LinearLayout mSquart;
    @InjectView(R.id.message)
    LinearLayout mMessage;
    @InjectView(R.id.mine)
    LinearLayout mMine;
    @InjectView(R.id.mainCamara)
    LinearLayout mMainCamara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        ButterKnife.inject(this);
        ButterKnife.inject(this);
        ButterKnife.inject(this);
        mTabHost.setup();
        LOGUtil.e(TAG, R.id.homeTab);
        mTabHost.addTab(mTabHost.newTabSpec("home").setIndicator("分类").setContent(R.id.homefragment));
        mTabHost.addTab(mTabHost.newTabSpec("feed").setIndicator("广场").setContent(R.id.squartfragment));
        mTabHost.addTab(mTabHost.newTabSpec("explore").setIndicator("消息").setContent(R.id.messagefragment));
        mTabHost.addTab(mTabHost.newTabSpec("setting").setIndicator("我的").setContent(R.id.mineFragment));
        mHomeTab.setOnClickListener(this);
        mSquart.setOnClickListener(this);
        mMessage.setOnClickListener(this);
        mMine.setOnClickListener(this);
        mMainCamara.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homeTab:
                mTabHost.setCurrentTab(0);
                break;
            case R.id.squart:
                mTabHost.setCurrentTab(1);
                break;
            case R.id.message:
                mTabHost.setCurrentTab(2);
                break;
            case R.id.mine:
                mTabHost.setCurrentTab(3);
                break;
            case R.id.mainCamara:
                Intent intent = new Intent(this,PublistHalfActivity.class);
                startActivity(intent);
                break;
        }
    }
}
