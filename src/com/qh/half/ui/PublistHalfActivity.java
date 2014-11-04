package com.qh.half.ui;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.qh.half.R;
import com.qh.half.ui.view.CameraPreview;
import com.qh.half.util.ImageUtil;
import com.qh.half.util.LOGUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2014/10/28.
 */
public class PublistHalfActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.close)
    ImageView mClose;
    @InjectView(R.id.camaraLight)
    ImageView mCamaraLight;
    @InjectView(R.id.forground)
    ImageView mForground;
    @InjectView(R.id.change)
    ImageView mChange;
    @InjectView(R.id.takePic)
    ImageView mTakePic;
    @InjectView(R.id.camaraPreView)
    FrameLayout mCamaraPreViewLayout;
    @InjectView(R.id.orLayout)
    LinearLayout mOrLayout;
    @InjectView(R.id.veLayout)
    LinearLayout mVeLayout;
    @InjectView(R.id.previewLayout)
    RelativeLayout mPreviewLayout;
    @InjectView(R.id.functionLayout)
    LinearLayout mFunctionLayout;
    @InjectView(R.id.titleLayout)
    RelativeLayout mTitleLayout;
    private CameraPreview mCamaraPreview;
    private boolean light = true;
    private boolean showLeft = true;
    private Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_half);
        ButterKnife.inject(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(0, dm.widthPixels, 0, 0);
        mFunctionLayout.setLayoutParams(params);
        if (checkCameraHardware()) {
            mCamera = getCameraInstance();
            mCamaraPreview = new CameraPreview(this, mCamera);
            mCamaraPreViewLayout.addView(mCamaraPreview);
        }
        mTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCamera != null) {
                    mCamera.takePicture(null, null, null, mPictureCallback);
                }
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.release();
                finish();
            }
        });
        mCamaraLight.setOnClickListener(this);
        mChange.setOnClickListener(this);
    }

    /**
     * Check if this device has a camera
     */
    private boolean checkCameraHardware() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera != null) {
            mCamera.startPreview();
        }
    }

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
            c.setDisplayOrientation(90);
            c.getParameters().setRotation(90);
            c.getParameters().setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camaraLight:
                if (light) {
                    mCamera.getParameters().setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                } else {
                    mCamera.getParameters().setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                }
                light = !light;
                mCamera.startPreview();
                break;
            case R.id.change:
                if (showLeft) {
                    mOrLayout.setVisibility(View.GONE);
                    mVeLayout.setVisibility(View.VISIBLE);
                    showLeft = false;
                } else {
                    mOrLayout.setVisibility(View.VISIBLE);
                    mVeLayout.setVisibility(View.GONE);
                    showLeft = true;
                }
                break;
        }
    }

    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/half.jpg";
            LOGUtil.e(TAG,"data length="+data.length);
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Matrix m = new Matrix();
            m.setRotate(90, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
            LOGUtil.e(TAG,mTitleLayout.getMeasuredHeight());
            final Bitmap bm = Bitmap.createBitmap(bitmap, mTitleLayout.getMeasuredHeight(), 0, bitmap.getHeight(), bitmap.getHeight(), m, true);
            ImageUtil.savePic(path, bm);
            mCamera.stopPreview();
            ImageFilterActivity.startImageFilter(PublistHalfActivity.this,path, showLeft);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCamera != null) {
            mCamera.release();
        }
    }
}
