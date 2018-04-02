package com.scd.joggle.pojo.po;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommodityAttrPo {

	private long id;
	private long commodityId;
	private long attrId;
	private String attrName;
	private BigDecimal price;
	private long stockNum;
	private long createTime;
	private long updateTime;
}
