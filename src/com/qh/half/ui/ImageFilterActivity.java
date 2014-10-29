package com.qh.half.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.Ragnarok.BitmapFilter;
import com.qh.half.R;
import com.qh.half.util.ImageUtil;

/**
 * Created by USER on 2014/10/29.
 */
public class ImageFilterActivity extends BaseActivity implements View.OnClickListener{
    @InjectView(R.id.close)
    ImageView mClose;
    @InjectView(R.id.camaraPreView)
    ImageView mCamaraPreView;
    @InjectView(R.id.orLayout)
    LinearLayout mOrLayout;
    @InjectView(R.id.veLayout)
    LinearLayout mVeLayout;
    @InjectView(R.id.he0)
    ImageView mHe0;
    private boolean isLeft = true;
    private static String PATH = "imagePath";
    private static String ISLEFT = "isleft";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_half);
        ButterKnife.inject(this);
        int size = getResources().getDimensionPixelSize(R.dimen.image_decode);
        mCamaraPreView.setImageBitmap(ImageUtil.decodeSampledBitmapFromFile(getIntent().getStringExtra(PATH),size,size));
        if(!getIntent().getBooleanExtra(ISLEFT,true)){
            mOrLayout.setVisibility(View.GONE);
            mVeLayout.setVisibility(View.VISIBLE);
        }
    }

    public static void startImageFilter(Context context, String imagepath, boolean isLeft) {
        Intent intent = new Intent(context, ImageFilterActivity.class);
        intent.putExtra(PATH, imagepath);
        intent.putExtra(ISLEFT, isLeft);
        context.startActivity(intent);
    }

    public void change(){
        Bitmap b =((BitmapDrawable) mCamaraPreView.getDrawable()).getBitmap();
       Bitmap fiBit =  BitmapFilter.changeStyle(b,BitmapFilter.SOFT_GLOW_STYLE,0.6);
        mCamaraPreView.setImageBitmap(fiBit);
    }
    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.he0:
               change();
               break;
       }
    }
}
