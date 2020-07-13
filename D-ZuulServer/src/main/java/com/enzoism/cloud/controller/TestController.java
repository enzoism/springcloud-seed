package com.enzoism.cloud.controller;

import com.enzoism.cloud.service.ApiService;
import com.enzoism.cloud.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: enzoism
 * Date: 2020/7/7- 1:12
 */
@RestController
public class TestController {

    @Autowired
    ApiService apiService;

    /**
     * 跳转登录页面
     */
    @RequestMapping("/sso/loginPage")
    private ModelAndView loginPage(String url) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("url", url);
        return modelAndView;
    }

    /**
     * 页面登录
     */
    @RequestMapping("/sso/login")
    private String login(HttpServletResponse response, String username, String password, String url) {
        String check = apiService.checkUsernameAndPassword(username, password);
        if (!StringUtils.isEmpty(check)) {
            try {
                Cookie cookie = CookieUtil.createCookie(check);
                response.addCookie(cookie);
                //重定向到原先访问的页面
                response.sendRedirect(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        // TODO 优化成“和前端约定的json格式”
        return "登录失败";
    }

}
