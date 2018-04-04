package com.jpa.test.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by 孙义朗 on 2017/11/16.
 */
@Entity
@Data
public class Grade {
    @Id
    private String gradeId;
    private String gradeName;
    @ManyToOne
    private School school;


}
