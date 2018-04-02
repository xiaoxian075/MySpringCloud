package com.scd.joggle.pojo.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayRecordSelectParam {
//    private String account;
//    private String payNumber;
//    private String orderNumber;
//    private int payWay;
//    private int payAct;
  	private String tradeOdd;
	private String orderOdd;
	private String payAccount;
	private String receiveAccount;
	private int payWay;
	private int act;
    private int page;
    private int size;
}
