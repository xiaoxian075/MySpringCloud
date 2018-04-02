package com.scd.joggle.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderGoodsBo {
	private long communityId;	// 商品ID
	private long attrId;	// 商品属性ID
	private int num;	// 商品数量
}
