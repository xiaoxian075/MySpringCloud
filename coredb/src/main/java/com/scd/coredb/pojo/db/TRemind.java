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
@Table(name = "t_remind")
public class TRemind {

	public final static String ID = "id";
	public final static String ACCOUNT = "account";
	public final static String ODD = "odd";		// 订单号
	public final static String ORDER_STATE = "orderState";	//订单状态 1：待付款 2：待发货 3：待收货 4：待评价 5：已完成 6：取消订单 7：异常单
	public final static String GOODS_PRICE = "goodsPrice";	//商品价格
	public final static String EXP_PRICE = "expPrice";	//运费
	public final static String ADDR_INFO = "addrInfo";	//收货地址
	public final static String ORDER_TIME = "orderTime";
	public final static String STATE = "state";	//状态  1：提醒 2：已处理
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
	
	@Column(name = "order_state", nullable = false, length = 11)
	private int orderState;
	
	@Digits(integer=11, fraction=2)
	@Column(name = "goods_price")
	private BigDecimal goodsPrice;
	
	@Digits(integer=11, fraction=2)
	@Column(name = "exp_price")
	private BigDecimal expPrice;
	
	/*@Lob*/@Basic(fetch=FetchType.LAZY)
	@Column(name = "addr_info")
	private String addrInfo;
	
	@Column(name = "order_time", nullable = false, length = 20)
	private long orderTime;
	
	@Column(name = "state", nullable = false, length = 11)
	private int state;
	
	@Column(name = "create_time", nullable = false, length = 20)
	private long createTime;
	
	@Column(name = "update_time", nullable = false, length = 20)
	private long updateTime;
}
