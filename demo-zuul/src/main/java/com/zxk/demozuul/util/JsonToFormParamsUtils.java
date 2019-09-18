package com.zxk.demozuul.util;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: 瑾瑜琳琅
 * Date: 2019/6/5
 * Time: 10:46
 * Description: No Description
 */
public class JsonToFormParamsUtils {
    private Map<String, String[]> paramList = new HashMap();

    public JsonToFormParamsUtils() {
    }

    public Map<String, String[]> toHttpParams(Map jsonMap) {
        return this._toHttpParams(jsonMap);
    }

    private Map<String, String[]> _toHttpParams(Map jsonMap) {
        this.promp(jsonMap, null);
        return this.paramList;

    }

    private void promp(Map jsonMap, String prefix) {
        Iterator iterator = jsonMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Object, Object> entry = (Map.Entry) iterator.next();
            String key = entry.getKey().toString();
            Object value = entry.getValue();
            if (value != null) {
                if (value instanceof Collection) {
                    this.pcollection((Collection) value, (prefix == null ? "" : prefix + ".") + key);
                } else if (value.getClass().isArray()) {
                    Object[] arr = (Object[]) value;
                    this.pcollection(Arrays.asList(arr), (prefix == null ? "" : prefix + ".") + key);
                } else if (value instanceof Map) {
                    this.promp((Map) value, (prefix == null ? "" : prefix + ".") + key);
                } else {
                    this.paramList.put((prefix == null ? "" : prefix + ".") + key, new String[]{value.toString()});
                }
            }


        }


    }

    private void pcollection(Collection<?> datas, String prefix) {
        int i = 0;
        List<String> valueList = null;
        Iterator<?> iterator = datas.iterator();
        while (iterator.hasNext()) {
            Object value = iterator.next();
            if (value != null) {
                if (value instanceof Collection) {
                    this.pcollection((Collection) value, prefix + "[" + i + "]");
                    ++i;
                } else if (value.getClass().isArray()) {
                    Object[] arr = (Object[]) value;
                    this.pcollection(Arrays.asList(arr), prefix + "[" + i + "]");

                } else if (value instanceof Map) {
                    this.promp((Map) value, prefix + "[" + i + "]");
                    ++i;
                } else {
                    if (valueList == null) {
                        valueList = new ArrayList<>();
                    }
                    valueList.add(value.toString());
                }
            }
        }
        if (valueList != null) {
            String[] valueArray = new String[valueList.size()];
            this.paramList.put(prefix, valueList.toArray(valueArray));
        }
    }
}
