package com.qh.half.api.home;

import com.qh.half.api.AbstractApi;

/**
 * Created by Administrator on 2014/10/22.
 */
public class HomeHotApi extends AbstractApi {
    public String userid ;
    public int page;

    @Override
    protected String getPath() {
        return "half_home.php";
    }
}
