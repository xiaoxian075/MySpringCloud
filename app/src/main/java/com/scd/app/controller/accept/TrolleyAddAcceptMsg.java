package com.scd.app.controller.accept;

import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TrolleyAddAcceptMsg extends BaseAcceptMsg {

	private String odd;
	private long commodityId;	// 商品ID
	private long attrId;	// 商品属性ID
	private int num;	// 商品数量
	
	@Override
	public boolean check() {
		if (	odd == null || odd.length() == 0 || odd.length() >32 ||
				commodityId <= 0 ||
				attrId <= 0 ||
				num <= 0) {
			return false;
		}
		
		return true;
	}

}
