package com.jpa.test.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jpa.test.entity.User;

import java.util.List;

/**
 * Created by 孙义朗 on 2017/11/16 0016.
 */
public interface UserDao extends JpaRepository<User,String> {
    List<User> findAll();

    List<User> findAll(Specification<User> spc, Pageable pageable);


}