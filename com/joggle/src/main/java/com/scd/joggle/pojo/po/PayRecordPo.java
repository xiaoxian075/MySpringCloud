package com.scd.joggle.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayRecordPo {
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
