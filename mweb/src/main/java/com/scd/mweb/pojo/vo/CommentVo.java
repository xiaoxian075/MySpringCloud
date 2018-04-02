package com.scd.mweb.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018-03-05.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {
    private long id;
    private int isAnonymous;
    private long accountId;
    private String accountHeadUrl;
    private String nickName;
    private String content;
    private long commentTime;
    private ArrayList<String> pictureUrl;
}
