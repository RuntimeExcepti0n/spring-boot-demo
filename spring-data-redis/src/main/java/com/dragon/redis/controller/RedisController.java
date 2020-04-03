package com.dragon.redis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dragon.redis.entity.User;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/")
    public void redisTest(){
        stringRedisTemplate.opsForValue().set("name","dragon");
        stringRedisTemplate.opsForValue().set("age","12",12, TimeUnit.SECONDS);
        List<String> segments = Lists.newArrayList("122","wewe","453");
        redisTemplate.opsForValue().set("sgement",segments);
    }

    @PostMapping("set")
    public void redisSet(@RequestBody JSONObject jsonObject){
        String key = jsonObject.getString("key");
        Object value = jsonObject.get("value");
        redisTemplate.opsForValue().set(key,value);
    }

    @PostMapping("setUser")
    public void setUser(@RequestBody JSONObject jsonObject){
        String key = jsonObject.getString("key");
        JSONObject value = jsonObject.getJSONObject("value");
        JSONArray hobby = value.getJSONArray("hobby");
        List<String> hobbys = JSONObject.parseArray(JSON.toJSONString(hobby), String.class);
        User user = new User();
        user.setAddress(value.getString("address"));
        user.setAge((Integer) value.get("age"));
        user.setName(value.getString("name"));
        user.setHobby(hobbys);
        redisTemplate.opsForValue().set(key,user);
    }

    @GetMapping("/get")
    public Object redisGet(@RequestParam String key){
        Object obj = redisTemplate.opsForValue().get(key);
        String s = stringRedisTemplate.opsForValue().get(key);
        System.out.println(s);
        return obj;
    }
}
