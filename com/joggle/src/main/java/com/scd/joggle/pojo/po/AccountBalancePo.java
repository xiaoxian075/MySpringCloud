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
public class AccountBalancePo {	  

	private long id;
	
	/**
	 * 账号
	 */
	private String account;
	
	/**
	 * 等级 1：普通会员 2：VIP会员
	 */
	private int level;
	
	/**
	 * 余额 *********.******
	 */
	private BigDecimal balance;
	
	/**
	 * 云币
	 */
	private long yunBalance;
	
	/**
	 * 创建时间
	 */
	private long createTime;
	
	/**
	 * 更新时间
	 */
	private long updateTime;
}
