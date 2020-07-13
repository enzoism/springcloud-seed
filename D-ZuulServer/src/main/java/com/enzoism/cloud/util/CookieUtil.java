package com.enzoism.cloud.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: enzoism
 * Date: 2020/7/14- 2:25
 */
public class CookieUtil {

    // 获取cookie
    public static String getCookie(HttpServletRequest request){
        String accessToken = request.getParameter("accessToken");
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    accessToken = cookie.getValue();
                }
            }
        }
        return accessToken;
    }
    // 保存cookie
    public static Cookie createCookie(String accessTokenValue) {
        Cookie cookie = new Cookie("accessToken", accessTokenValue);
        cookie.setMaxAge(60 * 3);
        // 设置域
        // cookie.setDomain("huanzi.cn");
        // 设置访问路径
        cookie.setPath("/");
        return cookie;
    }


}
