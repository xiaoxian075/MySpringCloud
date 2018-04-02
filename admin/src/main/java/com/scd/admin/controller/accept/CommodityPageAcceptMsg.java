package com.scd.admin.controller.accept;

import com.scd.joggle.constant.Type;
import com.scd.sdk.util.pojo.BasePageAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommodityPageAcceptMsg extends BasePageAcceptMsg {

	private long shopId;
	private int shopProductType;
	private int state;
	
	@Override
	public boolean check() {
		if (
				shopId < 0 ||
				(shopProductType > 0 && !Type.checkCommodityType(shopProductType)) ||
				(state >= 0 && !Type.checkCommodityState(state)) ) {
			return false;
		}
		page = getPage(page);
		size = getSize(size);
		return true;
	}

}
