package com.zxk.demozuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zxk.demozuul.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 屏蔽非法访问请求，对于只限于服务间调用的api，请遵守命名规范：uri中含有-anon/internal
 */
@Component
public class InternalURIAccessFilter extends ZuulFilter implements InitializingBean, DisposableBean {

    private static Logger log = LoggerFactory.getLogger(InternalURIAccessFilter.class);

    private static final String[][] preAuthenticationIgnoreUris = {
            {"/v1.0/facade/hi", "*"},
            {"/v1.0/facade/user/", "*"}
    };

    private static String[] deplay = null;

    //@Value("#{'${ipaddress}'.split(',')}")
    public void setDeplay(String[] ipaddress) {
        this.deplay = ipaddress;
    }

    //@Value(value = "${sso.logoutPath}")
    private String logoutPath;

    @Override
    public void destroy() throws Exception {
        //JedisUtil.close();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //JedisUtil.init(ssoRedisAddress);
    }

    /**
     * 返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
     * pre：路由之前
     * routing：路由之时
     * post： 路由之后
     * error：发送错误调用
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * filterOrder：过滤的顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 这里可以写逻辑判断，是否要过滤
     * @return
     */
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        //获取客户端请求路劲
        String uri = request.getRequestURI().toString().toLowerCase();
        //获取客户端请求方法
        String method = request.getMethod();
        //获取客户端请求ip
        String requestIp = null;

        try {
            requestIp = HttpUtils.getIpAddress(request);
            log.info(String.format("====AuthenticationHeaderFilter.shouldFilter - %s", requestIp));
        } catch (IOException e) {
            log.info("Error=======================获取客户端ip异常!");
            e.printStackTrace();
        }

        /**URI带有 -anon 表示为可匿名访问 则不过滤  例如通道的回调以及支付状态的页面接口*/
        /*if(PatternMatchUtils.simpleMatch("*-anon*", uri)){

            return false;
        }*/

        /**若项目在发版升级，则拒绝所有请求*/


        /*for (int j = 0; j < deplay.length; j++) {
            if (true || deplay[j].equals(requestIp)) {
                log.info("requst============ip" + requestIp);
                log.info(String.format("====AuthenticationHeaderFilter.shouldFilter - http method: (%s)", method));
                log.info(String.format("====AuthenticationHeaderFilter.shouldFilter - %s", uri));
                for (int i = 0; i < preAuthenticationIgnoreUris.length; i++) {
                    if (uri.startsWith(preAuthenticationIgnoreUris[i][0].toLowerCase()) &&
                            (preAuthenticationIgnoreUris[i][1].equals("*") || method.equalsIgnoreCase(preAuthenticationIgnoreUris[i][1]))) {
                        log.info("this will be not use filter uri={}", uri);
                        return false;
                    }
                }
            }
        }*/

        return true;

    }

    /**
     * 过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问。
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();
        String uri = request.getRequestURI().toString().toLowerCase();
        String method = request.getMethod();
        //make url
        String servletPath = request.getServletPath();
        log.info(String.format("====AuthenticationHeaderFilter.run - %s request to %s", request.getMethod(), uri));

        //sso begin
        //退出
        // logout filter
        /*if (logoutPath != null
                && logoutPath.trim().length() > 0
                && logoutPath.equals(servletPath)) {

            // logout
            //ssoTokenLoginHelper.logout(request);

            // response
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpServletResponse.SC_OK);
            requestContext.setResponseBody("{\"code\":" + "000000" + ", \"msg\":\"\"}");
            requestContext.set("isSuccess", false);
            return null;
        }*/

        //登录判断
        // login filter
        log.info("===============================login filter 开始检查token==========================================");

        // 塞入查询到的用户 set sso user
        //request.setAttribute(Conf.SSO_USER, xxlUser);

        requestContext.setResponseStatusCode(HttpStatus.OK.value());
        //requestContext.setResponseBody("Forbidden");
        requestContext.setSendZuulResponse(true);
        return null;
    }


    private void stopZuulRoutingWithError(RequestContext ctx, HttpStatus status, String responseText) {
        ctx.removeRouteHost();
        ctx.setResponseStatusCode(status.value());
        ctx.setResponseBody(responseText);
        ctx.setSendZuulResponse(false);
    }
}
