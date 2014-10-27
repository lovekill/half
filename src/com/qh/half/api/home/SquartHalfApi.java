package com.qh.half.api.home;

import com.qh.half.api.AbstractApi;

/**
 * Created by USER on 2014/10/27.
 */
public class SquartHalfApi extends AbstractApi {
    public String userid ;
    @Override
    protected String getPath() {
        return "half_half.php";
    }
}
