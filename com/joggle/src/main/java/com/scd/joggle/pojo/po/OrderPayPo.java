package com.scd.joggle.pojo.po;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018-03-07.
 */
@Getter
@Setter
public class OrderPayPo {
    private long orderId;
    private String orderNumber;
    private String payNumber;
    private long accountId;
    private long shopId;
    private long payTime;
    private int payWay;
    private BigDecimal payAmount;
    private int payType;
    private int payAct;
    private String account;
    private int yunpayNoticeState;
    private long noticeTime;

    public OrderPayPo() {
    }

    public OrderPayPo(long orderId, String orderNumber, String payNumber, long accountId, long shopId, long payTime, int payWay, BigDecimal payAmount, int payType, int payAct, String account) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.payNumber = payNumber;
        this.accountId = accountId;
        this.shopId = shopId;
        this.payTime = payTime;
        this.payWay = payWay;
        this.payAmount = payAmount;
        this.payType = payType;
        this.payAct = payAct;
        this.account = account;
    }
}
