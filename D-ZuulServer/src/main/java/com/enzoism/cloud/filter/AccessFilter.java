package com.enzoism.cloud.filter;

import com.enzoism.cloud.service.ApiService;
import com.enzoism.cloud.util.CookieUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * Zuul过滤器，实现了路由检查
 */
public class AccessFilter extends ZuulFilter {

    @Value("${server.port}")
    private String port;

    @Autowired
    private ApiService apiService;

    /**
     * 通过int值来定义过滤器的执行顺序
     */
    @Override
    public int filterOrder() {
        // PreDecoration之前运行
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    /**
     * 过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型：
     * public static final String ERROR_TYPE = "error";
     * public static final String POST_TYPE = "post";
     * public static final String PRE_TYPE = "pre";
     * public static final String ROUTE_TYPE = "route";
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 过滤器的具体逻辑
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();

        //访问路径
        StringBuilder url = new StringBuilder(request.getRequestURL().toString());

        //从cookie里面取值（Zuul丢失Cookie的解决方案：https://blog.csdn.net/lindan1984/article/details/79308396）
        String accessToken = CookieUtil.getCookie(request);

        //过滤规则：
        //访问的是登录页面、登录请求则放行
        if (url.toString().contains("/sso/loginPage") ||
                url.toString().contains("/sso/login") ||
                //cookie有令牌且存在于Redis
                (!StringUtils.isEmpty(accessToken) && apiService.hasKeyInSession(accessToken))
        ) {
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
            return null;
        } else {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);

            //如果是get请求处理参数，其他请求统统跳转到首页
            String method = request.getMethod();
            if ("GET".equals(method)) {
                url.append("?");
                Map<String, String[]> parameterMap = request.getParameterMap();
                Object[] keys = parameterMap.keySet().toArray();
                for (int i = 0; i < keys.length; i++) {
                    String key = (String) keys[i];
                    String value = parameterMap.get(key)[0];
                    url.append(key).append("=").append(value).append("&");
                }
                //处理末尾的&符合
                url.delete(url.length() - 1, url.length());
            } else {
                //首页链接，或者其他固定页面
                url = new StringBuilder("XXX");
            }

            try {
                //重定向到登录页面
                response.sendRedirect("http://localhost:"+port+"/sso/loginPage?url=" + url);
                System.out.println("---------重定向到登录页面:http://localhost:"+port+"/consumerB/sso/loginPage?url=" + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 返回一个boolean类型来判断该过滤器是否要执行
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 从request中获得参数Map，并返回可读的Map
     *
     * @param request
     * @return
     */
    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map<String, String> returnMap = new HashMap<String, String>();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry<String, String> entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

}
