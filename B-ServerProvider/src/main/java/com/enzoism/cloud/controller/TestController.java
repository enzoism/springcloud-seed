package com.enzoism.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: enzoism
 * Date: 2020/7/7- 1:02
 */
@RestController
public class TestController {

    @Value("${server.port}")
    String port;

    @GetMapping("/get")
    public String getMessage(@RequestParam("name")String name){
        return "Hi " + name + " ,I am from port:" + port;
    }

}