package com.qh.half.util;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
/**
 * Created by Administrator on 14-4-24.
 */
public class ImageLoadUtil {
    private static String TAG = "ImageLoadUtil";

    public static void displayImage(String url, ImageView imageView) {
        ImageLoader.getInstance().displayImage(url, imageView, getOptions());
    }

    public static void displayAvatarImage(String url, ImageView imageView) {
        ImageLoader.getInstance().displayImage(url, imageView, getAvatarOptions());
    }




    public static DisplayImageOptions getOptions() {
        return getBaseBuild()
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .displayer(new RoundedBitmapDisplayer(0)).build();
    }

    public static DisplayImageOptions getBigOptions() {
        return getBaseBuild()
                .build();
    }

    public static DisplayImageOptions getAvatarOptions() {
        return getBaseBuild()
                .build();
    }

    public static DisplayImageOptions.Builder getBaseBuild() {

        DisplayImageOptions.Builder build = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true);
        return build;
    }
}
