package com.jpa.test.service;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jpa.test.dao.UserDao;
import com.jpa.test.entity.Grade;
import com.jpa.test.entity.School;
import com.jpa.test.entity.User;
import com.jpa.test.param.Params;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 孙义朗 on 2017/11/16 0016.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    //查询User，单表，多条件
    @Override
    public List<User> findAll(int pageNum, int pageSize, User user) {
        Pageable pageable = new PageRequest(pageNum, pageSize);
        List<User> uList = userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (user.getUserId() != null && !user.getUserId().equals("")) {
                    predicates.add(cb.like(root.get("userId").as(String.class), "%" + user.getUserId() + "%"));
                }
                if (user.getUserName() != null && !user.getUserName().equals("")) {
                    predicates.add(cb.like(root.get("userName").as(String.class), "%" + user.getUserName() + "%"));
                }
                if (user.getGender() != null && !user.getGender().equals("")) {
                    predicates.add(cb.like(root.get("gender").as(String.class), "%" + user.getGender() + "%"));
                }
                if (user.getAge() != null && !user.getAge().equals("")) {
                    predicates.add(cb.like(root.get("age").as(String.class), "%" + user.getAge() + "%"));
                }
                Predicate[] pre = new Predicate[predicates.size()];
                criteriaQuery.where(predicates.toArray(pre));
                return cb.and(predicates.toArray(pre));
            }
        }, pageable);

        return uList;
    }

    //查询User，多表，多条件
    @Override
    public List<User> findAll(int pageNum, int pageSize, Params params) {
        Pageable pageable = new PageRequest(pageNum, pageSize);
        List<User> uList = userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                //根据userId 查询user
                if (StringUtils.isNotBlank(params.getUserId())) {
                    list.add(cb.equal(root.get("userId").as(String.class), params.getUserId()));
                }
                //根据userName 模糊查询user
                if (StringUtils.isNotBlank(params.getUserName())) {
                    list.add(cb.like(root.get("userName").as(String.class), "%" + params.getUserName() + "%"));
                }
                //根据gender 查询user
                if (StringUtils.isNotBlank(params.getGender())) {
                    list.add(cb.equal(root.get("gender").as(String.class), params.getGender()));
                }
                //根据age>? 查询user
                if (StringUtils.isNotBlank(params.getAge())) {
                    list.add(cb.gt(root.get("age").as(Integer.class), Integer.valueOf(params.getAge())));
                }
                //根据gradeName 查询user
                if (StringUtils.isNotBlank(params.getGradeName())) {
                    Join<Grade, User> join = root.join("grade", JoinType.LEFT);
                    list.add(cb.equal(join.get("gradeName"), params.getGradeName()));
                }
                //根据schoolName 查询user
                if (StringUtils.isNotBlank(params.getSchoolName())) {
                    Join<School, User> join = root.join("grade", JoinType.LEFT);
                    list.add(cb.equal(join.get("school").get("schoolName"), params.getSchoolName()));
                }
                Predicate[] pre = new Predicate[list.size()];
                criteriaQuery.where(list.toArray(pre));
                return cb.and(list.toArray(pre));
            }
        }, pageable);

        return uList;
    }

}
