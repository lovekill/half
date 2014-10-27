package com.qh.half.ui;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.google.gson.Gson;
import com.qh.half.DaoManager;
import com.qh.half.HalfApplication;
import com.qh.half.MainActivity;
import com.qh.half.R;
import com.qh.half.api.login.SnsLoginApi;
import com.qh.half.greendao.DaoMaster;
import com.qh.half.greendao.DaoSession;
import com.qh.half.greendao.LoginUser;
import com.qh.half.http.JsonHttpListener;
import com.qh.half.http.ZhidianHttpClient;
import com.qh.half.model.User;
import com.qh.half.util.LOGUtil;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SnsAccount;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.SocializeUser;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.UMQQSsoHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2014/10/21.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.login");
    @InjectView(R.id.school)
    ImageView mSchool;
    @InjectView(R.id.weibo)
    ImageView mWeibo;
    @InjectView(R.id.douban)
    ImageView mDouban;
    @InjectView(R.id.qq)
    ImageView mQq;

    private String at = "" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        ButterKnife.inject(this);
        mSchool.setOnClickListener(this);
        mWeibo.setOnClickListener(this);
        mDouban.setOnClickListener(this);
        mQq.setOnClickListener(this);
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "1101736944",
                "s7HFgqRAJ8NmDhk3");
        qqSsoHandler.addToSocialSDK();
        if(hasLogin()){
            showMainHome();
        }
    }

    private boolean hasLogin(){
        List<LoginUser> loginUsers = DaoManager.getDaoSession().getLoginUserDao().loadAll();
        if(loginUsers.size()>0){
            User user = new User() ;
            HalfApplication.loginUser = user ;
            HalfApplication.loginUser.avatar_large = loginUsers.get(0).getUserPhoto();
            HalfApplication.loginUser.userid= loginUsers.get(0).getUserid();
            HalfApplication.loginUser.username = loginUsers.get(0).getUserName();
            return true;
        }else {
            return  false ;
        }
    }
    private  void showMainHome(){
        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.school:
                at = "rr" ;
                mController.doOauthVerify(this, SHARE_MEDIA.RENREN, authListener);
                break;
            case R.id.qq:
                at = "qq" ;
                mController.doOauthVerify(this, SHARE_MEDIA.QQ, authListener);
                break;
            case R.id.weibo:
                at = "wb" ;
                mController.doOauthVerify(this, SHARE_MEDIA.SINA, authListener);
                break;
            case R.id.douban:
                at = "db" ;
                mController.doOauthVerify(this, SHARE_MEDIA.DOUBAN, authListener);
                break;
        }
    }

    private SocializeListeners.UMAuthListener authListener = new SocializeListeners.UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {
            LOGUtil.e(TAG, "auth success:" + share_media);
            mController.getPlatformInfo(LoginActivity.this, share_media, mUMDataListener);
        }

        @Override
        public void onError(SocializeException e, SHARE_MEDIA share_media) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    };


    private SocializeListeners.UMDataListener mUMDataListener = new SocializeListeners.UMDataListener() {
        @Override
        public void onStart() {
            Toast.makeText(LoginActivity.this, "获取平台数据开始...", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(int status, Map<String, Object> info) {
            if (status == 200 && info != null) {
                StringBuilder sb = new StringBuilder();
                Set<String> keys = info.keySet();
                for (String key : keys) {
                    sb.append(key + "=" + info.get(key).toString() + "\r\n");
                }
                LOGUtil.e("TestData", sb.toString());
                snsLogin(info);
            } else {
                LOGUtil.e("TestData", "发生错误：" + status);
            }
        }
    };
private void snsLogin(Map<String ,Object> info){
    SnsLoginApi snsLoginApi = new SnsLoginApi() ;
    snsLoginApi.at = at ;
    snsLoginApi.head = info.get("profile_image_url").toString() ;
    snsLoginApi.name = info.get("screen_name").toString() ;
    snsLoginApi.snsID = info.get("uid").toString() ;
    snsLoginApi.token = info.get("access_token").toString() ;
    ZhidianHttpClient.request(snsLoginApi,new JsonHttpListener(this){
        @Override
        public void onRequestSuccess(String jsonString) {
            super.onRequestSuccess(jsonString);
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                if("1".equals(jsonObject.optString("result"))){
                    HalfApplication.loginUser =new Gson().fromJson(jsonString,User.class);
                    LoginUser loginUser = new LoginUser() ;
                    loginUser.setUserid(HalfApplication.loginUser.userid);
                    loginUser.setUserName(HalfApplication.loginUser.username);
                    loginUser.setUserPhoto(HalfApplication.loginUser.avatar_large);
                    DaoManager.getDaoSession().getLoginUserDao().insert(loginUser);
                    showMainHome();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onResultFail(String jsonString) {
            super.onResultFail(jsonString);
        }
    });
}
}
