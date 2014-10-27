package com.qh.half.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import butterknife.InjectView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.qh.half.HalfApplication;
import com.qh.half.R;
import com.qh.half.adapter.HomeHotAdapter;
import com.qh.half.api.home.HomeHotApi;
import com.qh.half.http.JsonHttpListener;
import com.qh.half.http.ZhidianHttpClient;
import com.qh.half.model.LeftPhoto;
import com.qh.half.util.LOGUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2014/10/22.
 */
public class HomeHotFragment extends BaseFragment {
    @InjectView(R.id.listView)
    ListView mListView;
    private List<LeftPhoto> leftPhotoList;

    @Override
    protected int getViewId() {
        return R.layout.fragment_home_hot_layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getHomeHot();
        PauseOnScrollListener scrollListener = new PauseOnScrollListener(ImageLoader.getInstance(), true, true);
        mListView.setOnScrollListener(scrollListener);
    }

    private void getHomeHot() {
        HomeHotApi homeHotApi = new HomeHotApi();
        homeHotApi.userid = HalfApplication.loginUser.userid;
        homeHotApi.page = 1;
        ZhidianHttpClient.request(homeHotApi, new JsonHttpListener(getActivity()) {
            @Override
            public void onRequestSuccess(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    if ("1".equals(jsonObject.optString("result"))) {
                        leftPhotoList = jsonToList(LeftPhoto.class, jsonString, "left_photo");
                        LOGUtil.e(TAG, leftPhotoList.size());
                        HomeHotAdapter homeHotAdapter = new HomeHotAdapter(getActivity(), leftPhotoList, getChildFragmentManager());
                        mListView.setAdapter(homeHotAdapter);
                    } else {
                        showServerErorr();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
