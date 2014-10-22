package com.qh.half.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.qh.half.R;
import com.qh.half.util.LOGUtil;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 14-4-22.
 */
public class JsonHttpListener extends JsonHttpResponseHandler implements DialogInterface.OnCancelListener {
    private final String TAG = JsonHttpListener.class.getSimpleName();
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private AsyncHttpClient asyncHttpClient;
    private IUIWhithNetListener listener;
    private Fragment mFragment;

    public JsonHttpListener(Context context) {
        this.mContext = context;
        if (mContext != null) {
//            mProgressDialog = new ProgressDialog(mContext, "正在加载更多数据");
//            mProgressDialog.setOnCancelListener(this);
        }
    }

    public JsonHttpListener(Context context, String message) {
        this.mContext = context;
        if (mContext != null) {
//            mProgressDialog = new LoadDataDialog(mContext, message + "......");
//            mProgressDialog.setOnCancelListener(this);
        }
    }

    public JsonHttpListener(boolean isNextPage) {
    }

    public JsonHttpListener(IUIWhithNetListener listener) {
        this.listener = listener;
        if (listener instanceof Fragment) {
            mFragment = (Fragment) listener;
        }

    }

    public void setAsyncHttpClient(AsyncHttpClient asyncHttpClient) {
        this.asyncHttpClient = asyncHttpClient;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        dismissProgress();
        if (mFragment != null) {
            if (mFragment.getActivity() == null) return;
        }
        LOGUtil.e(TAG, response.toString());
        if (listener != null) {
            listener.onSuccess(response.toString());
        }
        onRequestSuccess(response.toString());
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        if (listener != null) {
            listener.onFailure(throwable);
        }
        dismissProgress();
        if (mContext != null) {
            Toast.makeText(mContext, R.string.net_error, Toast.LENGTH_SHORT).show();
        }
        throwable.printStackTrace();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (listener != null) {
            listener.onLoadStart();
        }
        if (mProgressDialog != null && mContext != null) {
            LOGUtil.e("jsonListener", "show");
            mProgressDialog.show();
        }
    }

    public void dismissProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            LOGUtil.e("jsonListener", "dismiss");
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (asyncHttpClient != null) {
            asyncHttpClient.cancelRequests(mContext, true);
        }
    }

    public void onRequestSuccess(String jsonString) {

    }

    public void onResultFail(String jsonString) {
        LOGUtil.e("onResultFail", jsonString);
        if (mContext != null) {
        }
    }


    private boolean dispatchJson(String json) {
        if (json == null) {
            return false;
        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            if ("1".equals(jsonObject.optString("result"))) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
