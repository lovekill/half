package com.qh.half.util;

import android.util.Log;
import com.qh.half.BuildConfig;

/**
 * Created by Administrator on 14-4-22.
 */
public class LOGUtil {
    public static void v(String tag,Object o){
        if(BuildConfig.DEBUG){
            Log.v("half:"+tag,o+"")  ;
        }
    }
    public static void d(String tag,Object o){
        if(BuildConfig.DEBUG){
            Log.d("half:"+tag,o+"")  ;
        }
    }
    public static void i(String tag,Object o){
        if(BuildConfig.DEBUG){
            Log.i("half:"+tag,o+"")  ;
        }
    }
    public static void w(String tag,Object o){
        if(BuildConfig.DEBUG){
            Log.w("half:"+tag,o+"")  ;
        }
    }
    public static void e(String tag,Object o){
        if(BuildConfig.DEBUG){
            Log.e("half:"+tag,o+"")  ;
        }
    }
}
