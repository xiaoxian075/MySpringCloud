package com.scd.app.pojo.vo;

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
public class OrderInfoVo {
	private long id;
	private String odd;	// 订单号
	private String title;
	private String showPic;
	private BigDecimal price;
	private long state;	// 订单状态 1：待付款 2：待发货 3：待收货 4：待评价 5：已完成 6：取消订单 7:失效 8：异常单
	private BigDecimal goodsPrice;	// 商品价格
	private BigDecimal expPrice;	// 运费
	private List<OrderCommodityBo> commudityList;	// 商品信息
	private AddressBo address;	// 收货地址
	private LogisticsBo logistics;	// 物流信息
	private long surplusTime;	// 剩余时间
	private long payTime;
	private long createTime;
}
