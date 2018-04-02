package com.scd.admin.controller.accept;

import com.scd.sdk.util.pojo.BasePageAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendGoodsAcceptMsg extends BasePageAcceptMsg {

	private String orderOdd;
	private String code;
	private String expOdd;
	
	
	
	@Override
	public boolean check() {
		if (
				orderOdd == null || orderOdd.length() == 0 || orderOdd.length() > 64 ||
				code == null || code.length() == 0 || code.length() > 64 ||
				expOdd == null || expOdd.length() == 0 || expOdd.length() > 64 ) {
			return false;
		}
		page = getPage(page);
		size = getSize(size);
		return true;
	}

}

