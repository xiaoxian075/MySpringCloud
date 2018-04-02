package com.scd.admin.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayRecordVo {
//    private long id;
//    private String account;
//    private long accountId;
//    private long noticeTime;
//    private long orderId;
//    private String orderNumber;
//    private int payAct;
//    private BigDecimal payAmount;
//    private String payNumber;
//    private long payTime;
//    private int payType;
//    private int payWay;
//    private long shopId;
//    private String tradeNo;
//    private int yunpayNoticeState;
    private long id;
    private String tradeOdd;
    private String orderOdd;
    private int act;
    private int payWay;
    private int orderType;
    private String payAccount;
    private String receiveAccount;
	private BigDecimal amount;
    private String tradeTime;
    private long createTime;
}
