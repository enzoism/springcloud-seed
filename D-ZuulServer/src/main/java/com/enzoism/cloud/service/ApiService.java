package com.enzoism.cloud.service;

import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: enzoism
 * Date: 2020/7/14- 1:40
 */
public interface ApiService {
    Boolean hasKeyInSession(String name);
    String checkUsernameAndPassword(String username, String password);
}
