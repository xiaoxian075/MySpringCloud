package com.scd.joggle.pojo.po;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayResultPo {
    private String tradeOdd;
    private String orderOdd;
    private int act;
    private int payWay;
//    private int orderType;
//    private String payAccount;
//    private String receiveAccount;
	private BigDecimal amount;
    private String tradeTime;
}
