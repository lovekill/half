package com.qh.half.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.Ragnarok.BitmapFilter;
import com.qh.half.R;
import com.qh.half.util.LOGUtil;

import java.io.IOException;

/**
 * Created by USER on 2014/10/29.
 */
public class ImageFilterActivity extends BaseActivity implements View.OnClickListener {
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
    @InjectView(R.id.imagelayout)
    RelativeLayout mImagelayout;
    private boolean isLeft = true;
    private static String PATH = "imagePath";
    private static String ISLEFT = "isleft";
    private Bitmap changeBitmap;
    private Bitmap originBitmap;
    private String path = "";
    int degree = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_half);
        ButterKnife.inject(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dm.widthPixels);
        mImagelayout.setLayoutParams(params);
        path = getIntent().getStringExtra(PATH);
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int size = getResources().getDimensionPixelSize(R.dimen.image_decode);
//        mCamaraPreView.setImageBitmap(ImageUtil.decodeSampledBitmapFromFile(path,size,size));
//        Bitmap bitmap = ImageUtil.decodeSampledBitmapFromFile(path, size, size);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        LOGUtil.e(TAG, "w=" + bitmap.getWidth() + ",h=" + bitmap.getHeight());
        degree = 0;
        if (degree > 0) {
            Matrix m = new Matrix();
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            m.setRotate(degree); // 旋转angle度
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                    m, true);// 从新生成图片
        }
        mCamaraPreView.setImageBitmap(bitmap);
        if (!getIntent().getBooleanExtra(ISLEFT, true)) {
            mOrLayout.setVisibility(View.GONE);
            mVeLayout.setVisibility(View.VISIBLE);
        }
        mHe0.setOnClickListener(this);
    }

    public static void startImageFilter(Context context, String imagepath, boolean isLeft) {
        Intent intent = new Intent(context, ImageFilterActivity.class);
        intent.putExtra(PATH, imagepath);
        intent.putExtra(ISLEFT, isLeft);
        context.startActivity(intent);
    }

    public void change() {
        originBitmap = ((BitmapDrawable) mCamaraPreView.getDrawable()).getBitmap();
//        Bitmap fiBit = BitmapFilter.changeStyle(b, BitmapFilter.SOFT_GLOW_STYLE, 0.6);
//        mCamaraPreView.setImageBitmap(fiBit);
        if (changeBitmap != null) {
            changeBitmap.recycle();
            changeBitmap = null;
        }

        applyStyle(16);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.he0:
                change();
                break;
        }
    }

    private void applyStyle(int styleNo) {
        switch (styleNo) {
            case BitmapFilter.AVERAGE_BLUR_STYLE:
                changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.AVERAGE_BLUR_STYLE, 5); // maskSize, must odd
                break;
            case BitmapFilter.GAUSSIAN_BLUR_STYLE:
                changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.GAUSSIAN_BLUR_STYLE, 1.2); // sigma
                break;
            case BitmapFilter.SOFT_GLOW_STYLE:
                changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.SOFT_GLOW_STYLE, 0.6);
                break;
            case BitmapFilter.LIGHT_STYLE:
                int width = originBitmap.getWidth();
                int height = originBitmap.getHeight();
                changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.LIGHT_STYLE, width / 3, height / 2, width / 2);
                break;
            case BitmapFilter.LOMO_STYLE:
                changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.LOMO_STYLE,
                        (originBitmap.getWidth() / 2.0) * 95 / 100.0);
                break;
            case BitmapFilter.NEON_STYLE:
                changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.NEON_STYLE, 200, 100, 50);
                break;
            case BitmapFilter.PIXELATE_STYLE:
                changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.PIXELATE_STYLE, 10);
                break;
            case BitmapFilter.MOTION_BLUR_STYLE:
                changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.MOTION_BLUR_STYLE, 10, 1);
                break;
            case BitmapFilter.OIL_STYLE:
                changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.OIL_STYLE, 5);
                break;
            default:
                changeBitmap = BitmapFilter.changeStyle(originBitmap, styleNo);
                break;
        }
        mCamaraPreView.setImageBitmap(changeBitmap);
    }

}
