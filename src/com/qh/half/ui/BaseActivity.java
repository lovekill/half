package com.qh.half.ui;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qh.half.ui.fragment.BaseFragment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by engine on 2014/10/19.
 */
public class BaseActivity extends FragmentActivity{
    public String TAG = getClass().getSimpleName();
    public void addFragment(BaseFragment fragment){
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
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
                T t = new Gson().fromJson(array.getJSONObject(i).toString(), clazz);
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

        map = new Gson().fromJson(array, new TypeToken<Map<String, Integer>>() {
        }.getType());

        return map;

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
        T t = new Gson().fromJson(jsonObject.toString(), clazz);
        return t;
    }
}
