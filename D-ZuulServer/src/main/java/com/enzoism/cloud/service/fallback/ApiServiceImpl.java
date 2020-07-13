package com.enzoism.cloud.service.fallback;

import com.enzoism.cloud.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: enzoism
 * Date: 2020/7/14- 1:44
 */
@Service
public class ApiServiceImpl implements ApiService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 判断key是否存在
     */
    @Override
    public Boolean hasKeyInSession(String key) {
        return redisTemplate.hasKey(key);
    }
    /**
     * 如果用户名和密码正确就存放在redis中
     * 没有就返回null，redis不存放数据
     */
    @Override
    public String checkUsernameAndPassword(String username, String password) {
        //通行令牌
        String flag = null;
        if ("huanzi".equals(username) && "123456".equals(password)) {
            //用户名+时间戳（这里只是demo，正常项目的令牌应该要更为复杂）
            flag = username + System.currentTimeMillis();
            //令牌作为key，存用户id作为value（或者直接存储可暴露的部分用户信息也行）设置过期时间（我这里设置3分钟）
            redisTemplate.opsForValue().set(flag, "1", (long) (3 * 60), TimeUnit.SECONDS);
        }
        return flag;
    }




}
