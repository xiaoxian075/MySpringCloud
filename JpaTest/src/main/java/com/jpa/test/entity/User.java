package com.jpa.test.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by 孙义朗 on 2017/10/31.
 */
@Entity
@Data
public class User {
    @Id
    private String userId;
    private String userName;
    private String password;
    private String gender;//性别
    private String age;
    @ManyToOne
    private School school;
    @ManyToOne
    private Grade grade;
}
