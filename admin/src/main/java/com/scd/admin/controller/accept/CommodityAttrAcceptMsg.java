package com.scd.admin.controller.accept;

import java.math.BigDecimal;

import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommodityAttrAcceptMsg extends BaseAcceptMsg {
	
	private long id;
	private long commodityId;
	private int attrId;
	private String attrName;
	private BigDecimal price;
	private int stockNum;

	@Override
	public boolean check() {
		if (	id < 0 ||
				commodityId <=0 ||
				attrId <= 0 ||
				attrName == null || attrName.trim().length() == 0 || attrName.trim().length() > 255 ||
				price == null || price.compareTo(BigDecimal.ZERO) < 0 ||
				stockNum < 0 ) {
			return false;
		}
		return true;
	}

}
