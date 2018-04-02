package com.scd.app.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018-03-07.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayVo {
    private long id;
    private long orderId;
    private String payNumber;
    private long accountId;
    private long shopId;
    private long payTime;
    private int payWay;
    private BigDecimal payMoney;
    private int payType;
    private int payAct;
    private String account;
    private String orderNumber;
}
