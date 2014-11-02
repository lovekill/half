package com.qh.half.ui.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.qh.half.util.LOGUtil;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2014/10/21.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static String TAG = "CameraPreview";
    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            LOGUtil.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        List<Camera.Size> sizeList = mCamera.getParameters().getSupportedPictureSizes();
        LOGUtil.e(TAG,"w="+w+",h="+h);
        for (Camera.Size s : sizeList) {
            LOGUtil.e(TAG, "size:w=" + s.width + ",h=" + s.height);
        }
        Camera.Size size = sizeList.get(sizeList.size()-1);
        mCamera.setDisplayOrientation(90);
        mCamera.getParameters().setPreviewSize(size.width,size.height);
        mCamera.getParameters().setPictureFormat(PixelFormat.JPEG);// 设置照片的输出格式
        mCamera.getParameters().set("jpeg-quality", 85);// 照片质量
        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e) {
            LOGUtil.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
}
