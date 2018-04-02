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
@Table(name = "t_order")
public class TOrder {

	public final static String ID = "id";
	public final static String ACCOUNT = "account";
	public final static String ODD = "odd";		// 订单号
	public final static String STATE = "state";	//订单状态 1：待付款 2：待发货 3：待收货 4：待评价 5：已完成 6：取消订单 7：异常单
	public final static String GOODS_PRICE = "goodsPrice";	//商品价格
	public final static String EXP_PRICE = "expPrice";	//运费
	public final static String LAST_PAY = "lastPay";	//截止付款时间
	public final static String GOODS_INFO = "goodsInfo";	//商品信息
	public final static String ADDR_INFO = "addrInfo";	//收货地址
	public final static String LOGISTICS_INFO = "logisticsInfo";	//物流信息
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
	
	@Column(name = "state", nullable = false, length = 11)
	private int state;
	
	@Digits(integer=11, fraction=2)
	@Column(name = "goods_price")
	private BigDecimal goodsPrice;
	
	@Digits(integer=11, fraction=2)
	@Column(name = "exp_price")
	private BigDecimal expPrice;
	
	@Column(name = "last_pay", nullable = false, length = 20)
	private long lastPay;
	
	/*@Lob*/@Basic(fetch=FetchType.LAZY)
	@Column(name = "goods_info")
	private String goodsInfo;
	
	/*@Lob*/@Basic(fetch=FetchType.LAZY)
	@Column(name = "addr_info")
	private String addrInfo;
	
	@Column(name = "logistics_info", nullable = false, length = 255)
	private String logisticsInfo;
	
	@Column(name = "create_time", nullable = false, length = 20)
	private long createTime;
	
	@Column(name = "update_time", nullable = false, length = 20)
	private long updateTime;

	@Column(name = "pay_time", length = 20)
	private long payTime;

	@Column(name = "integral", length = 20)
	private long integral;

}


