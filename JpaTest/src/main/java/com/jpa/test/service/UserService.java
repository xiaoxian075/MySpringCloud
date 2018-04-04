package com.jpa.test.service;

import com.jpa.test.entity.User;
import com.jpa.test.param.Params;

import java.util.List;

/**
 * Created by 孙义朗 on 2017/11/16 0016.
 */
public interface UserService {
    List<User> findAll();

    List<User> findAll(int pageNum, int pageSize,User user);

    List<User> findAll(int pageNum, int pageSize, Params params);

}
