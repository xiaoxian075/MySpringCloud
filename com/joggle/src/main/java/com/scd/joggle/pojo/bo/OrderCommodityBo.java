package com.scd.joggle.pojo.bo;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCommodityBo {
	private long commudityId;	// 商品ID
	private String title;
	private String showPic;
	private long attrId;	// 属性ID
	private String attrName;
	private long shopId;
	private String shopName;
	private BigDecimal price;
	private long num;		// 数量
}
