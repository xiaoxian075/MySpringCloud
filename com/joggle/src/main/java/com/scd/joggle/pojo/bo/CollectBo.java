package com.scd.joggle.pojo.bo;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Administrator on 2018-03-13.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectBo {
    private long id;
    private long accountId;
    private String account;
    private long commodityId;
    private long createTime;
    private String commodityName;
    private String commodityImg;
    private BigDecimal price;
}
