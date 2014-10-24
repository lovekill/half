package com.qh.half.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import butterknife.InjectView;
import com.qh.half.HalfApplication;
import com.qh.half.R;
import com.qh.half.api.home.HomeHotApi;
import com.qh.half.http.JsonHttpListener;
import com.qh.half.http.ZhidianHttpClient;

/**
 * Created by Administrator on 2014/10/22.
 */
public class HomeHotFragment extends BaseFragment {
    @InjectView(R.id.listView)
    ListView mListView;

    @Override
    protected int getViewId() {
        return R.layout.fragment_home_hot_layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getHomeHot();
    }

    private void getHomeHot(){
        HomeHotApi homeHotApi = new HomeHotApi() ;
        homeHotApi.userid= HalfApplication.loginUser.userid;
        homeHotApi.page=1 ;
        ZhidianHttpClient.request(homeHotApi,new JsonHttpListener(getActivity()){

        });
    }
}
