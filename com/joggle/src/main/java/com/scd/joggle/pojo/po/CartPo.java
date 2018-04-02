package com.scd.joggle.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018-03-14.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartPo {
    private int id;
    private int num;
    private long accountId;
    private long commodityId;
    private String commodityName;
    private String commodityImg;
    private String goodsInfo;
    private BigDecimal price;
    private long createTime;
    private String account;
}
