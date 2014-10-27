package com.qh.half;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.qh.half.ui.BaseActivity;
import com.qh.half.ui.fragment.HomeClassFragment;
import com.qh.half.ui.fragment.HomeFragment;
import com.qh.half.ui.view.CameraPreview;

public class MainActivity extends BaseActivity{
    @InjectView(R.id.camaraPreViewLayout)
    FrameLayout mCamaraPreViewLayout;
    @InjectView(R.id.button)
    Button mButton;
    CameraPreview mCamaraPreview ;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        ButterKnife.inject(this);
//        if(checkCameraHardware()) {
//            mCamaraPreview = new CameraPreview(this, getCameraInstance());
//            mCamaraPreViewLayout.addView(mCamaraPreview);
//        }
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getCameraInstance().stopPreview();
//                getCameraInstance().release();
//            }
//        });
        addFragment(new HomeFragment(),true);
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
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
}
