package com.scd.app.pojo.vo;

import lombok.*;

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
