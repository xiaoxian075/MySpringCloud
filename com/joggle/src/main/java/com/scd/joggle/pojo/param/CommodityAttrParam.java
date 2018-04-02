package com.scd.joggle.pojo.param;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommodityAttrParam {
	private long id;
	private long commodityId;
	private int attrId;
	private String attrName;
	private BigDecimal price;
	private int stockNum;
}
