package com.qh.half.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qh.half.util.LOGUtil;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 14-4-22.
 */
public abstract class BaseFragment extends Fragment {
    protected Gson gson = new Gson();
    protected String TAG = BaseFragment.class.getSimpleName();
    private View view;

    public BaseFragment() {
    }

    protected View headView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        LOGUtil.e(TAG, "onViewStateRestored");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        headView = inflater.inflate(R.layout.default_head_layout, null);
        if (view == null) {
            view = inflater.inflate(getViewId(), null, false);
            ButterKnife.inject(this, view);
        } else if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getModelName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getModelName());
    }

    public void setTitleView(int id) {
        if (id > 0) {
            View view = LayoutInflater.from(getActivity()).inflate(id, null);
            setTitleView(view);
        }
    }

    public void setTitleView(View view) {
        if (view != null) {
            ((BaseActivity) getActivity()).setmTitleView(view);
        }
    }

    public void setDefaultTitle(String title) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.default_title_layout, null);
        TextView titleText = (TextView) v.findViewById(R.id.title);
        titleText.setText(title);
        v.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    goBack();
                }
            }
        });
        setTitleView(v);
    }

    public void goBack() {
        ((BaseActivity) getActivity()).onBackPressed();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LOGUtil.e(TAG, "onViewCreated");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onLoadStart() {
        if (!(getActivity() instanceof HomeActivity)) {
            ((BaseActivity) getActivity()).loadStart();
        }
    }

    @Override
    public void onSuccess(String content) {
        if (getActivity() == null) return;
        if (!(getActivity() instanceof HomeActivity)) {
            ((BaseActivity) getActivity()).loadSuccess();
        }
    }

    @Override
    public void onFailure(Throwable error) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).loadFail();
        }
    }

    protected void cacheJson(long modelId, String json, AbstractApi abstractApi) {
        if (getActivity() == null) return;
        TARequest request = new TARequest();
        request.setResouce(modelId);
        request.setData(json);
        request.setTag(abstractApi);
        ((TAActivity) getActivity()).doCommand(R.string.saveCommond, request,
                this, false, false);
    }

    protected void loadCach(long model) {
        WhereCondition recource = JsonEntryDao.Properties.Resouce.eq(model);
        QueryBuilder<JsonEntry> queryBuilder = DaoManager.getDaoSession()
                .getJsonEntryDao().queryBuilder().where(recource);
        TARequest request = new TARequest();
        request.setData(queryBuilder);
        ((TAActivity) getActivity()).doCommand(R.string.getCommond, request,
                this, false, false);

    }

    @Override
    public void onSuccess(TAResponse response) {

    }

    @Override
    public void onRuning(TAResponse response) {

    }

    @Override
    public void onFailure(TAResponse response) {

    }

    @Override
    public void onFinish() {

    }

    public void back() {
        if (getFragmentManager().getFragments().size() > 1) {
            getFragmentManager().popBackStack();
        } else {
            getActivity().finish();
        }
    }

    /**
     * @param clazz
     * @param json
     * @param key   jsonObject 里面的key如果是多层可以这样写 如:data.a.b.c
     * @return
     * @throws org.json.JSONException
     */
    protected <T> T jsonToBean(Class<T> clazz, String json, String key)
            throws JSONException {
        if (json == null)
            return null;
        if (clazz == null)
            return null;
        JSONObject jsonObject = new JSONObject(json);
        if (key != null) {
            String[] keys = key.split("\\.");
            for (String k : keys) {
                jsonObject = jsonObject.optJSONObject(k);
                if (jsonObject == null)
                    return null;
            }
        }
        T t = gson.fromJson(jsonObject.toString(), clazz);
        return t;
    }

    protected <T> T jsonToBean(Class<T> clazz, String json)
            throws JSONException {
        return jsonToBean(clazz, json, "data");
    }

    synchronized protected <T> List<T> jsonToList(Class<T> clazz, String json, String key)
            throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray array = jsonObject.optJSONArray(key);
        List<T> list = new ArrayList<T>();
        if (array == null || array.length() == 0)
            return list;
        for (int i = 0; i < array.length(); i++) {
            try {
                T t = gson.fromJson(array.getJSONObject(i).toString(), clazz);
                list.add(t);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return list;
    }

    protected <T> Map<String, Integer> jsonToMap(String json, String key)
            throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String array = jsonObject.optString(key);
        Map<String, Integer> map = new HashMap<String, Integer>();
        if (array == null || array.length() == 0) {
            return map;
        }

        map = gson.fromJson(array, new TypeToken<Map<String, Integer>>() {
        }.getType());

        return map;

    }

    protected abstract int getViewId();

    protected abstract String getModelName();

}
