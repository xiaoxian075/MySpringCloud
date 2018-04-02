package com.scd.app.pojo.vo;

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
public class CartVo {
    private int id;
    private int num;
    private long accountId;
    private long commodityId;
    private String commodityName;
    private String commodityImg;
    private Object goodsInfo;
    private BigDecimal price;
    private long createTime;
    private String account;
}
