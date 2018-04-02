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
public class RemindPo {
	private long id;
	private String account;
	private String odd;
	private int orderState;
	private BigDecimal goodsPrice;
	private BigDecimal expPrice;
	private String addrInfo;
	private long orderTime;
	private int state;
	private long createTime;
	private long updateTime;
}
