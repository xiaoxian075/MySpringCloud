package com.jpa.test.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by 孙义朗 on 2017/10/31.
 */
@Data
@Entity
public class School {
    @Id
    private String schoolId;
    private String schoolName;


}