package com.qh.half.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import butterknife.InjectView;
import com.qh.half.HalfApplication;
import com.qh.half.R;
import com.qh.half.adapter.HomeHotAdapter;
import com.qh.half.api.home.SquartHalfApi;
import com.qh.half.http.JsonHttpListener;
import com.qh.half.http.ZhidianHttpClient;
import com.qh.half.model.LeftPhoto;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by USER on 2014/10/27.
 */
public class SquartContentFragment extends BaseFragment {
    @InjectView(R.id.squartListView)
    ListView mSquartListView;
    List<LeftPhoto> leftPhotoList  ;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getHalf();
    }

    private void getHalf(){
        SquartHalfApi squartHalfApi = new SquartHalfApi();
        squartHalfApi.userid= HalfApplication.loginUser.userid;
        ZhidianHttpClient.request(squartHalfApi,new JsonHttpListener(getActivity()){
            @Override
            public void onRequestSuccess(String jsonString) {
                super.onRequestSuccess(jsonString);
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    if("1".equals(jsonObject.optString("result"))){
                        leftPhotoList = jsonToList(LeftPhoto.class,jsonString,"left_photo") ;
                        initView();
                    }else {
                        showServerErorr();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initView(){
        if(leftPhotoList.size()>0){
            HomeHotAdapter adapter = new HomeHotAdapter(getActivity(),leftPhotoList);
            mSquartListView.setAdapter(adapter);
        }
    }

    @Override
    protected int getViewId() {
        return R.layout.fragment_squart_content;
    }
}
