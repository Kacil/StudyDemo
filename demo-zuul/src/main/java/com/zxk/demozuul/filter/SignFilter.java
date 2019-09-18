package com.zxk.demozuul.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zxk.demozuul.util.AESUtils;
import com.zxk.demozuul.util.RsaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description 验签
 **/
@Slf4j
@Component
public class SignFilter extends ZuulFilter {
    //@Value("${zuul.ignored.routes}")
    private String[] ignoreRoutes = {};
    //@Value("${sign.RSAPrivateKey}")
    private String RSAPrivateKey;

    //@Value("${sign.enabled}")
    private String signEnabled;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        if (org.apache.commons.lang3.StringUtils.equals(signEnabled, "true")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * run：过滤器的具体逻辑。
     * 要把请求参数进行验签（解密）之后传给后续的微服务，首先获取到request，但是在request中只有getParameter()而没有setParameter()方法
     * 所以直接修改url参数不可行，另外在reqeust中虽然可以使用setAttribute(),但是可能由于作用域（request）的不同，一台服务器中才能getAttribute
     * 在这里设置的attribute在后续的微服务中是获取不到的，因此必须考虑另外的方式：即获取请求的输入流，并重写，即重写json参数，
     * ctx.setRequest(new HttpServletRequestWrapper(request) {})，这种方式可重新构造上下文中的request
     *
     * @return
     */
    @Override
    public Object run() throws ZuulException {
        log.info("---------------into signFilter-----------");
        RequestContext context = RequestContext.getCurrentContext();
        try {
            HttpServletRequest request = context.getRequest();
            boolean isIgnore = false;
            for (String str : ignoreRoutes) {
                if (request.getRequestURI().contains(str)) {
                    isIgnore = true;
                    break;
                }
            }
            if (isIgnore) {
                return null;
            }

            //请求的方法
            String method = request.getMethod();
            log.info("{}>>>{}", method, request.getRequestURL());

            InputStream inputStream = request.getInputStream();
            String body = StreamUtils.copyToString(inputStream, Charset.forName("UTF-8"));
            if (StringUtils.isEmpty(body)) {
                body = "{}";
            }

            /*if ("GET".equals(method)) {
                List<String> param;
                Map<String, Object> paramMap = Maps.newTreeMap(
                        new Comparator<String>() {
                            @Override
                            public int compare(String o1, String o2) {
                                //升序排序
                                return o1.compareTo(o2);
                            }
                        }
                );

                // 关键步骤，一定要get一下,下面才能取到值requestQueryParams
                request.getParameterMap();
                Map<String, List<String>> requestQueryParams = context.getRequestQueryParams();
                Enumeration enu = request.getParameterNames();
                while (enu.hasMoreElements()) {
                    param = Lists.newArrayList();
                    String paraName = (String) enu.nextElement();
                    System.out.println(paraName + ": " + request.getParameter(paraName));
                    param.add(request.getParameter(paraName));
                    requestQueryParams.put(paraName, param);
                    paramMap.put(paraName, request.getParameter(paraName));
                }

                if (!paramMap.containsKey("sign") || !paramMap.containsKey("timestamp") || !paramMap.containsKey("key")) {
                    //buildResponse(context, ResultWrap.init(CommonConstants.FALIED, "参数异常"));
                    return null;
                }
                //TODO  后续进行时间戳的时效性的判断
                //拿出排完序的参数，拼装起来，进行验签
                StringBuffer stb = new StringBuffer();
                paramMap.forEach((k, v) -> {
                    if (!"sign".equals(k) && !"timestamp".equals(k) && !"key".equals(k)) {
                        stb.append(k + "=" + v + "&");
                    }

                });
                stb.deleteCharAt(stb.length() - 1);

                //拼装完成，先用RSA秘钥解密出AES的key，再用AES的key对排序后的参数加密，然后对比
                String AESKey = RsaUtils.decrypt(RSAPrivateKey, new String(AESUtils.base642Byte(paramMap.get("key").toString()), Charset.forName("UTF-8")));

                String encrypt = AESUtils.byte2Base64(AESUtils.encrypt(stb.toString(), AESKey));
                encrypt = replaceBlank(encrypt);
                String base64Sign = replaceBlank(paramMap.get("sign").toString());
                if (!org.apache.commons.lang3.StringUtils.equals(encrypt, base64Sign)) {
                    //buildResponse(context, ResultWrap.init(CommonConstants.FALIED, "签名异常"));
                    return null;
                }

                context.setRequestQueryParams(requestQueryParams);

            } else if ("POST".equals(method) || "PUT".equals(method)) {
                // post和put需重写HttpServletRequestWrapper
                JSONObject requestBody = JSONObject.parseObject(body);
                System.out.println("requestBody.getString(\"key\") = " + requestBody.getString("key"));

                if (StringUtils.isEmpty(requestBody.getString("sign")) || StringUtils.isEmpty(requestBody.getString("timestamp")) || StringUtils.isEmpty(requestBody.getString("key"))) {
                   // buildResponse(context, ResultWrap.init(CommonConstants.FALIED, "参数异常"));
                    return null;
                }

                if (!checkSign(requestBody)) {
                    //buildResponse(context, ResultWrap.init(CommonConstants.FALIED, "签名异常"));
                    return null;
                }

                if (requestBody.containsKey("sign")) {
                    requestBody.remove("sign");
                }
                if (requestBody.containsKey("timestamp")) {
                    requestBody.remove("timestamp");
                }
                if (requestBody.containsKey("key")) {
                    requestBody.remove("key");
                }

                String reqBody = requestBody.toString();

                log.info(reqBody);

                context.setRequest(new JsonHttpServletRequsetWrapper(request, JsonMapUtil.toHashMap(reqBody)));

            }*/
        } catch (IOException e) {
            //buildResponse(context, ResultWrap.init(CommonConstants.SC_FORBIDDEN, "解析签名异常~"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        context.setResponseStatusCode(HttpStatus.OK.value());
        context.setSendZuulResponse(true);
        return null;
    }

    private boolean checkSign(JSONObject requestBody) throws Exception {
        Map<String, String> dataParam = new HashMap<>();
        for (Map.Entry<String, Object> item : requestBody.entrySet()) {
            String key = item.getKey();
            Object value = item.getValue();
            dataParam.put(key, String.valueOf(value));
        }

        List<Map.Entry<String, String>> list = new ArrayList<>(dataParam.entrySet());
        Collections.sort(list,
                new Comparator<Map.Entry<String, String>>() {
                    //升序排序
                    @Override
                    public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                        return o1.getKey().compareTo(o2.getKey());
                    }
                });
        String AESKey = null;
        String timestamp = null;
        StringBuffer temp = new StringBuffer();
        for (Map.Entry<String, String> mapping : list) {
            if ("sign".equals(mapping.getKey())) {
                continue;
            }
            if ("key".equals(mapping.getKey())) {
                AESKey = mapping.getValue();
            } else if ("timestamp".equals(mapping.getKey())) {
                timestamp = mapping.getValue();
            } else {
                temp.append(mapping.getKey() + "=" + String.valueOf(mapping.getValue()));
                temp.append("&");
            }

        }
        temp.deleteCharAt(temp.length() - 1);
        //TODO 后续可进行判断时间戳的有效性，控制在10秒以内
        //拿到key 进行RSA解密，然后在调用解密后的key对排序后的参数进行加密，和sign进行比较
        AESKey = new String(AESUtils.base642Byte(AESKey), Charset.forName("UTF-8"));
        String decryptKey = RsaUtils.decrypt(RSAPrivateKey, AESKey);
        String tempSign = AESUtils.byte2Base64(AESUtils.encrypt(temp.toString(), decryptKey));
        tempSign = replaceBlank(tempSign);
        log.info("decryptKey={},AESKey={},sign={}", decryptKey, AESKey, tempSign);
        String base64Sign = dataParam.get("sign");
        base64Sign = replaceBlank(base64Sign);
        if (!org.apache.commons.lang3.StringUtils.equals(base64Sign, tempSign)) {
            return false;
        }
        return true;
    }

    private String replaceBlank(String str) {
        String ret = "";

        if (str != null) {

            Pattern pattern = Pattern.compile("\\s*|\t|\r|\n");

            Matcher m = pattern.matcher(str);

            ret = m.replaceAll("");

        }
        return ret;
    }

    private void buildResponse(RequestContext context, Map<String, Object> resMsg) {
        context.setResponseBody(JSON.toJSONString(resMsg));
        context.getResponse().setContentType("application/json;charset=UTF-8");
        context.setSendZuulResponse(false);
    }
}
