package com.scd.joggle.pojo.po;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Administrator on 2018-03-14.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectPo {
    private long id;
    private long accountId;
    private String account;
    private long commodityId;
    private String commodityName;
    private long createTime;
    private String commodityImg;
    private BigDecimal price;
}
