package com.zxk.demozuul.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 瑾瑜琳琅
 * Date: 2019/6/5
 * Time: 10:44
 * Description: No Description
 */
public class JsonMapUtil {
    public JsonMapUtil() {
    }
    public static Map toHashMap(String requestParams) {
        JSONObject jsonObject = JSONObject.parseObject(requestParams);
        return _toHashMap(jsonObject);
    }

    private static Map _toHashMap(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        } else {
            Map<Object, Object> datas = new HashMap<>(jsonObject.size());
            Map.Entry entry = null;
            Object v;
            for (Iterator i = jsonObject.entrySet().iterator(); i.hasNext(); datas.put(entry.getKey(), v)) {
                entry = (Map.Entry) i.next();
                v = entry.getValue();
                v = getObject(v);

            }
            return datas;
        }
    }

    private static Object _toObjectArray(JSONArray jsonArray) {
        Object arr = Array.newInstance(Object.class, jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            Object v = jsonArray.get(i);
            v = getObject(v);

            Array.set(arr, i, v);

        }

        return arr;
    }

    private static Object getObject(Object v) {
        if (v != null && v instanceof JSONAware) {
            if (v instanceof JSONObject) {
                v = _toHashMap((JSONObject) v);
            } else if (v instanceof JSONArray) {
                v = _toObjectArray((JSONArray) v);
            }
        }
        return v;
    }
}
