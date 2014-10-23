package com.qh.half.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import butterknife.InjectView;
import com.qh.half.R;
import com.qh.half.util.ImageLoadUtil;
import com.qh.half.util.LOGUtil;

/**
 * Created by Administrator on 2014/10/23.
 */
public class SigleImageFragment extends BaseFragment {
    @InjectView(R.id.imageView)
    ImageView mImageView;
public static String URL = "url";
    public SigleImageFragment(){}
    @Override
    protected int getViewId() {
        return R.layout.fragment_sigle_image;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LOGUtil.e(TAG,getArguments().getString(URL));
        ImageLoadUtil.displayImage(getArguments().getString(URL),mImageView);
    }
}
