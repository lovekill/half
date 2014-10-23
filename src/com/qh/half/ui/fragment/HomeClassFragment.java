package com.qh.half.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.InjectView;
import com.qh.half.R;
import com.qh.half.api.home.HomeClassApi;
import com.qh.half.http.JsonHttpListener;
import com.qh.half.http.ZhidianHttpClient;
import com.qh.half.model.HomeClass;
import com.qh.half.util.ImageLoadUtil;
import com.qh.half.util.LOGUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2014/10/22.
 */
public class HomeClassFragment extends BaseFragment {
    @InjectView(R.id.topViewPage)
    ViewPager mTopViewPage;
    @InjectView(R.id.indicatorLayout)
    LinearLayout mIndicatorLayout;
    @InjectView(R.id.eventImage1)
    ImageView mEventImage1;
    @InjectView(R.id.eventText1)
    TextView mEventText1;
    @InjectView(R.id.eventImage2)
    ImageView mEventImage2;
    @InjectView(R.id.eventText2)
    TextView mEventText2;
    @InjectView(R.id.eventImage3)
    ImageView mEventImage3;
    @InjectView(R.id.eventText3)
    TextView mEventText3;
    @InjectView(R.id.eventTitle)
    TextView mEventTitle;
    @InjectView(R.id.hotTitle)
    TextView mHotTitle;
    @InjectView(R.id.oneThree)
    LinearLayout mOneThree;
    @InjectView(R.id.twoThree)
    LinearLayout mTwoThree;
    private List<HomeClass> mTopScrollList;
    private List<HomeClass> mOneTwoCardList;
    private List<HomeClass> mThreeCard1;
    private List<HomeClass> mThreeCard2;

    @Override
    protected int getViewId() {
        return R.layout.fragment_home_class_layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getHomeClass();
    }

    public void getHomeClass() {
        HomeClassApi homeClassApi = new HomeClassApi();
        ZhidianHttpClient.request(homeClassApi, new JsonHttpListener(getActivity()) {
            @Override
            public void onRequestSuccess(String jsonString) {
                super.onRequestSuccess(jsonString);
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray jsonArray = jsonObject.optJSONArray("class");
                    String str = jsonArray.getJSONObject(0).toString();
                    mTopScrollList = jsonToList(HomeClass.class, str, "class_list");
                    mEventTitle.setText(jsonArray.getJSONObject(1).optString("title"));
                    mOneTwoCardList = jsonToList(HomeClass.class, jsonArray.getJSONObject(2).toString(), "class_list");
                    mHotTitle.setText(jsonArray.getJSONObject(3).optString("title"));
                    mThreeCard1 = jsonToList(HomeClass.class, jsonArray.getJSONObject(4).toString(), "class_list");
                    mThreeCard2 = jsonToList(HomeClass.class, jsonArray.getJSONObject(5).toString(), "class_list");
                    initEventView(mOneTwoCardList);
                    initOneCard(mThreeCard1, mOneThree);
                    initOneCard(mThreeCard2, mTwoThree);
//                    ImageLoadUtil.displayImage(mThreeCard1.get(0).class_photo,mTestImage);
                    if(mTopScrollList.size()>0) {
                        ImagePageAdapter adapter = new ImagePageAdapter();
                        mTopViewPage.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initEventView(List<HomeClass> eventList) {
        ImageLoadUtil.displayImage(eventList.get(0).class_photo, mEventImage1);
//        mEventText1.setText(eventList.get(0).class_name);
        ImageLoadUtil.displayImage(eventList.get(1).class_photo, mEventImage2);
//        mEventText2.setText(eventList.get(1).class_name);
        ImageLoadUtil.displayImage(eventList.get(2).class_photo, mEventImage3);
//        mEventText3.setText(eventList.get(2).class_name);
    }

    private void initOneCard(List<HomeClass> list, LinearLayout parentLayout) {
        for (HomeClass homeClass : list) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_hot_item, null,false);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            TextView textView = (TextView) view.findViewById(R.id.textView);
            ImageLoadUtil.displayImage(homeClass.class_photo, imageView);
            LOGUtil.e(TAG, homeClass.class_photo);
            textView.setText(homeClass.class_name);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            p.width = 1;
//            view.setLayoutParams(p);
            TextView t = new TextView(getActivity());
            t.setText("---");
            parentLayout.addView(view);
        }
    }
    class ImagePageAdapter extends BaseFragmentAdaper{

        public ImagePageAdapter() {
            super(getChildFragmentManager());
        }

        @Override
        public Fragment getFragment(int i) {
            if(i<mTopScrollList.size()) {
                SigleImageFragment sigleImageFragment = new SigleImageFragment();
                Bundle b = new Bundle();
                b.putString(SigleImageFragment.URL, mTopScrollList.get(i).class_photo);
                sigleImageFragment.setArguments(b);
                return sigleImageFragment;
            }else{
                return null ;
            }
        }

        @Override
        public int getCount() {
            return mTopScrollList.size();
        }
    }
}
