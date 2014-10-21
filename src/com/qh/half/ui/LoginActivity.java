package com.qh.half.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.qh.half.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        ButterKnife.inject(this);
        mSchool.setOnClickListener(this);
        mWeibo.setOnClickListener(this);
        mDouban.setOnClickListener(this);
        mQq.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.school:
               mController.login(this, SHARE_MEDIA.RENREN, authListener);
               break;
           case R.id.qq:
               mController.doOauthVerify(this, SHARE_MEDIA.QQ,authListener);
               break;
           case R.id.weibo:
               mController.doOauthVerify(this, SHARE_MEDIA.SINA,authListener);
               break;
           case R.id.douban:
               mController.doOauthVerify(this, SHARE_MEDIA.DOUBAN,authListener);
               break;
       }
    }
    private SocializeListeners.UMAuthListener authListener = new SocializeListeners.UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {

        }

        @Override
        public void onError(SocializeException e, SHARE_MEDIA share_media) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    };

    private SocializeListeners.SocializeClientListener mloginListener = new SocializeListeners.SocializeClientListener() {
        @Override
        public void onStart() {

        }

        @Override
        public void onComplete(int i, SocializeEntity socializeEntity) {

        }
    };
}
