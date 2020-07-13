package com.enzoism.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * User: enzoism
 * Date: 2020/7/7- 1:12
 */
@RestController
public class TestController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/show")
    public String showMessage(@RequestParam String name){
        return restTemplate.getForObject("http://producer/get?name="+name, String.class);
    }

    @GetMapping("/login")
    public ModelAndView loginPage(@RequestParam String url){
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("url", url);
        return modelAndView;
    }
}
