package com.scd.app.pojo.vo;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommodityAttrVo {
	private long id;
	private long commodityId;	//商品ID
	private long attrId;		//属性ID
	private String attrName;	//属性名称
	private BigDecimal price;	//价格
	private long stockNum;		//库存数量
}
