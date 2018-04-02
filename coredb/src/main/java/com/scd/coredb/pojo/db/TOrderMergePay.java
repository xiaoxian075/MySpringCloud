package com.scd.coredb.pojo.db;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_order_merge_pay")
public class TOrderMergePay {

	public final static String ID = "id";
	public final static String ACCOUNT = "account";
	public final static String ODD = "odd";		// 订单号
	public final static String CHILD_ODD = "childOdd";	//子订单列表
	public final static String AMOUNT = "amount";	//订单金额
	public final static String STATE = "state";	//订单状态 1：合单支付请求 2：已付款 3:已失效
	public final static String CREATE_TIME = "createTime";
	public final static String UPDATE_TIME = "updateTime";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 20)
	private long id;
	
	@Column(name = "account", nullable = false, length = 64)
	private String account;
	
	@Column(name = "odd", nullable = false, length = 32)
	private String odd;
	
	/*@Lob*/@Basic(fetch=FetchType.LAZY)
	@Column(name = "child_odd")
	private String childOdd;
	
	@Digits(integer=11, fraction=2)
	@Column(name = "amount")
	private BigDecimal amount;
	
	@Column(name = "state", nullable = false, length = 11)
	private int state;
	
	@Column(name = "create_time", nullable = false, length = 20)
	private long createTime;
	
	@Column(name = "update_time", nullable = false, length = 20)
	private long updateTime;
}
