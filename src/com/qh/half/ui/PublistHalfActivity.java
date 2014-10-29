package com.qh.half.ui;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.qh.half.R;
import com.qh.half.ui.view.CameraPreview;
import com.qh.half.util.LOGUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    private CameraPreview mCamaraPreview;
    private boolean light = true;
    private boolean showLeft = true;
    private Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_half);
        ButterKnife.inject(this);
        ButterKnife.inject(this);
        ButterKnife.inject(this);
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
                    showLeft=false;
                }else {
                    mOrLayout.setVisibility(View.VISIBLE);
                    mVeLayout.setVisibility(View.GONE);
                    showLeft=true;
                }
                break;
        }
    }
    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            String path = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
            File pictureFile = new File(path+"/half.jpg") ;
            if(pictureFile.exists()) pictureFile.delete();
            try {
                pictureFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (pictureFile == null){

                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
               LOGUtil.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                LOGUtil.d(TAG, "Error accessing file: " + e.getMessage());
            }
            mCamera.stopPreview();
            mCamera.release();
            ImageFilterActivity.startImageFilter(PublistHalfActivity.this,pictureFile.getAbsolutePath(),showLeft);
        }
    } ;
}
