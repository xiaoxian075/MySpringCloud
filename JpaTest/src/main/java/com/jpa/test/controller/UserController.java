package com.jpa.test.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jpa.test.entity.User;
import com.jpa.test.param.Params;
import com.jpa.test.service.UserService;

import java.util.List;

/**
 * Created by 孙义朗 on 2017/11/16 0016.
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/getUser")
    public List<User> getUser() {
        List<User> uList = userService.findAll();
        return uList;
    }

    //查询User，单表，多条件
    @PostMapping(value = "/getUser/{pageNum}/{pageSize}")
    public List<User> getUser(@PathVariable("pageNum") Integer pageNum,
                               @PathVariable("pageSize") Integer pageSize,
                               @RequestBody User user) {
        List<User> uList = userService.findAll(pageNum, pageSize, user);
        return uList;
    }

    //查询User，多表，多条件
    @PostMapping(value = "/getUser2/{pageNum}/{pageSize}")
    public List<User> getUser2(@PathVariable("pageNum") Integer pageNum,
                               @PathVariable("pageSize") Integer pageSize,
                               @RequestBody Params params) {
        List<User> uList = userService.findAll(pageNum, pageSize, params);
        return uList;
    }
    
    //查询User，单表，多条件
    @PostMapping(value = "/getUser3/{pageNum}/{pageSize}")
    public String getUser(@PathVariable("pageNum") Integer pageNum,
                               @PathVariable("pageSize") Integer pageSize) {
//        List<User> uList = userService.findAll(pageNum, pageSize, user);
//        return uList;
    	return "hello";
    }
}
