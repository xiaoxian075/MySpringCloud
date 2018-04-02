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
public class OrderDetailBo {

	private long commudityId;	// 商品ID
	private long attrId;	// 属性ID
	private String attrName;
	private BigDecimal price;
	private int num;		// 数量
}
