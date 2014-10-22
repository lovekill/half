package com.qh.half.api.login;

import com.qh.half.api.AbstractApi;

/**
 * Created by Administrator on 2014/10/22.
 */
public class SnsLoginApi extends AbstractApi {
    public String pass_key;
    public String snsID;
    public String at;
    public String name;
    public String head;
    public String token;

    @Override
    protected String getPath() {
        return "half_login_sns.php";
    }
}
