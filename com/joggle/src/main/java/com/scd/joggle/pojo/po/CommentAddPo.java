package com.scd.joggle.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Administrator on 2018-03-06.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentAddPo {
    private long accountId;

    private String orderId;

    private long goodsId;

    private String goodsDescribe;

    private int describeStar;

    private String content;

    private int isAnonymous;

    private String logistics;

    private int logisticsStar;

    private String service;

    private int serviceStar;

    private String pictureUrl;

    private String account;

    private String nickName;

    private String headPicUrl;

}
