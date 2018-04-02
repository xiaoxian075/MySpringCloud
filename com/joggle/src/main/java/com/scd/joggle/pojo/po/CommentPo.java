package com.scd.joggle.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2018-03-07.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentPo {

    private long id;
    private int isAnonymous;
    private long accountId;
    private String accountHeadUrl;
    private String nickName;
    private String content;
    private long commentTime;
    private String pictureUrl;

}
