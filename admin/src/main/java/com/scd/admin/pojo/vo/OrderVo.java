package com.scd.admin.pojo.vo;

import java.math.BigDecimal;
import java.util.List;

import com.scd.joggle.pojo.bo.AddressBo;
import com.scd.joggle.pojo.bo.LogisticsBo;
import com.scd.joggle.pojo.bo.OrderCommodityBo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderVo {
	private long id;
	private String account;	// 账号
	private String odd;	// 订单号
	private int state;	// 订单状态 1：待付款 2：待发货 3：待收货 4：待评价 5：已完成 6：取消订单 7:失效 8：异常单
	private BigDecimal goodsPrice;	// 商品价格
	private BigDecimal expPrice;	// 运费
	private long lastPay;	// 截止付款时间
	private List<OrderCommodityBo> goodsInfo;	// 商品信息
	private AddressBo address;	// 收货地址
	private LogisticsBo logistics;	// 物流信息
	private long createTime;
	private long updateTime;
	private long payTime;
}
