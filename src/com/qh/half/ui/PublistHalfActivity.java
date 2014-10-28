package com.qh.half.ui;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.qh.half.R;
import com.qh.half.ui.view.CameraPreview;

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
    private CameraPreview mCamaraPreview ;
    private boolean light=true ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_half);
        ButterKnife.inject(this);
        ButterKnife.inject(this);
                if(checkCameraHardware()) {
            mCamaraPreview = new CameraPreview(this, getCameraInstance());
            mCamaraPreViewLayout.addView(mCamaraPreview);
        }
        mTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCameraInstance().stopPreview();
                getCameraInstance().release();
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCameraInstance().stopPreview();
                getCameraInstance().release();
                finish();
            }
        });
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
            c.getParameters().setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camaraLight:
                if(light){
                    getCameraInstance().getParameters().setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                }else {
                    getCameraInstance().getParameters().setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                }
                light = !light;
                getCameraInstance().stopPreview();
                break;
        }
    }
}
