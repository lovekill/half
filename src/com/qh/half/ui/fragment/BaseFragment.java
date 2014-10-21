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
import com.qh.half.ui.BaseActivity;
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
    }

    @Override
    public void onPause() {
        super.onPause();
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
