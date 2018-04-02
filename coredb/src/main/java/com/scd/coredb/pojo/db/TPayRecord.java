package com.scd.coredb.pojo.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_pay_record")
public class TPayRecord {

    public final static String ID = "id";
    public final static String TRADE_ODD = "tradeOdd";	// 交易流水号
    public final static String ORDER_ODD = "orderOdd";	// 订单编号
    public final static String ACT = "act";				// 动作 6：购物 3：充值 2：升级
    public final static String PAY_WAY = "payWay";				// 支付方式  1:余额 2：SDK
    public final static String ORDER_TYPE = "orderType";		// 订单类型  1:单笔 2：多笔
    public final static String PAY_ACCOUNT = "payAccount";	// 付款账号
    public final static String RECEIVE_ACCOUNT = "receiveAccount";	// 收款账号
    public final static String AMOUNT = "amount";		// 交易金额
    public final static String TRADE_TIME = "tradeTime";	//交易时间
    public final static String CREATE_TIME = "createTime";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, length = 20)
    private long id;

    @Column(name = "trade_odd", nullable = false, length = 32)
    private String tradeOdd;
    
    @Column(name = "order_odd", nullable = false, length = 32)
    private String orderOdd;
    
    @Column(name = "act", nullable = false, length = 11)
    private int act;
    
    @Column(name = "pay_way", nullable = false, length = 11)
    private int payWay;
    
    @Column(name = "order_type", nullable = false, length = 11)
    private int orderType;

    @Column(name = "pay_account", nullable = false, length = 64)
    private String payAccount;

    @Column(name = "receive_account", nullable = false, length = 64)
    private String receiveAccount;

    @Digits(integer=11, fraction=2)
	@Column(name = "amount")
	private BigDecimal amount;

    @Column(name = "trade_time", nullable = false, length = 32)
    private String tradeTime;

    @Column(name = "create_time", nullable = false, length = 20)
    private long createTime;
}

