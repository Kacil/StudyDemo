package com.zxk.demozuul.filter.warpper;

import com.zxk.demozuul.util.JsonMapUtil;
import com.zxk.demozuul.util.JsonToFormParamsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
*
 */
public class JsonHttpServletRequsetWrapper extends HttpServletRequestWrapper {
    private Logger logger = LoggerFactory.getLogger(JsonHttpServletRequsetWrapper.class);
    private Map<String, String[]> paramList;
    public JsonHttpServletRequsetWrapper(HttpServletRequest request) {
        super(request);
    }

    public JsonHttpServletRequsetWrapper(HttpServletRequest request, Map<String, Object> jsonMap) {
        super(request);
        if (CollectionUtils.isEmpty(jsonMap)) {
            this.paramList = request.getParameterMap();
        } else {
            JsonToFormParamsUtils utils = new JsonToFormParamsUtils();
            this.paramList = utils.toHttpParams(jsonMap);
            this.paramList.putAll(this.getRequest().getParameterMap());

        }

    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.getParamsList();
    }

    @Override
    public String getParameter(String key) {
        String[] v = this.getParamsList().get(key);
        return v != null && v.length > 0 ? v[0] : null;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return new KeyEnumeration(this.getParamsList().keySet());
    }

    @Override
    public String[] getParameterValues(String key) {
        return this.getParamsList().get(key);
    }

    private Map<String, String[]> getParamsList() {
        if (this.paramList == null) {
            this.paramList = this.getRequest().getParameterMap();
            Map<String, String[]> tempList = this._read();
            if (tempList != null && tempList.size() > 0) {
                tempList.putAll(this.paramList);
                this.paramList = tempList;
            }
        }
        return this.paramList;
    }

    private Map<String, String[]> _read() {
        String requestParams = null;
        BufferedReader in;
        try {
            in = this.getReader();
            StringWriter out = new StringWriter(128);
            char[] buffer = new char[512];
            boolean var5 = true;
            while (true) {
                int bytesRead;
                if ((bytesRead = in.read(buffer)) == -1) {
                    requestParams = out.toString();
                    break;

                }
                out.write(buffer, 0, bytesRead);
            }


        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        if (StringUtils.hasText(requestParams)) {
            if (logger.isDebugEnabled()) {
                logger.debug("JsonParams=" + requestParams);
            }
            in = null;
            Map jsonMap = null;
            try {
                jsonMap = JsonMapUtil.toHashMap(requestParams);
            } catch (Exception e) {
                logger.debug("JsonParams=" + requestParams);
            }
            JsonToFormParamsUtils utils = new JsonToFormParamsUtils();
            return utils.toHttpParams(jsonMap);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("jsonParams={}");
            }
            return null;
        }
    }

    class KeyEnumeration implements Enumeration<String> {
        private Iterator<String> iterator;
        private Set<String> keySet;

        public KeyEnumeration(Set<String> keySet) {
            this.keySet = keySet;
        }

        @Override
        public boolean hasMoreElements() {
            return this.getIterator().hasNext();
        }

        @Override
        public String nextElement() {
            return this.getIterator().next();
        }

        private Iterator<String> getIterator() {
            if (this.iterator == null) {
                this.iterator = this.keySet.iterator();
            }
            return this.iterator;
        }

        public String toString() {
            return this.keySet.toString();
        }
    }
}
