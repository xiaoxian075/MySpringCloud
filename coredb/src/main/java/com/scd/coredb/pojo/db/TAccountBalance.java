package com.scd.coredb.pojo.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_account_balance")
public class TAccountBalance {	  
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 20)
	private long id;
	
	/**
	 * 账号
	 */
	@Column(name = "account", nullable = false, length = 64)
	private String account;
	
//	/**
//	 * 等级 1：普通会员 2：VIP会员
//	 */
//	@Column(name = "level", nullable = false, length = 4)
//	private int level;
	
	/**
	 * 余额 *********.******
	 */
	@Digits(integer=11, fraction=2)
	@Column(name = "balance"/*, nullable = false, length = 16*/)
	private BigDecimal balance;
	
	/**
	 * 云币
	 */
	@Column(name = "yun_balance", nullable = false, length = 20)
	private long yunBalance;
	
	/**
	 * 创建时间
	 */
	@Column(name = "create_time", nullable = false, length = 20)
	private long createTime;
	
	/**
	 * 更新时间
	 */
	@Column(name = "update_time", nullable = false, length = 20)
	private long updateTime;

	/**
	 * 支付状态
	 */
	@Column(name = "pay_state", nullable = false, length = 20)
	private int payState;
	
	/**
	 * 累计收益
	 */
	@Digits(integer=11, fraction=2)
	@Column(name = "total_earnings")
	private BigDecimal totalEarnings;
	
	/**
	 * 累计消费
	 */
	@Digits(integer=11, fraction=2)
	@Column(name = "total_consume")
	private BigDecimal totalConsume;
	
	/**
	 * 累计充值
	 */
	@Digits(integer=11, fraction=2)
	@Column(name = "total_recharge")
	private BigDecimal totalRecharge;

}
