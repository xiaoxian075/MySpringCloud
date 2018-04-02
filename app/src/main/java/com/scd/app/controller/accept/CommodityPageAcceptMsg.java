package com.scd.app.controller.accept;

import com.scd.joggle.constant.Type;
import com.scd.sdk.util.pojo.BasePageAcceptMsg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommodityPageAcceptMsg extends BasePageAcceptMsg {

	/**
	 * 店铺ID, 0表示不选择店铺
	 */
	private long shopId;
	/**
	 * 类型 0表示不选择 1：学习教育 2：美食分享 3：生活健康 4：健身健美
	 */
	private int type;

	@Override
	public boolean check() {
		if (shopId < 0) {
			return false;
		}
		if (type > 0 && !Type.checkCommodityType(type)) {
			return false;
		}
		page = getPage(page);
		size = getSize(size);
		
		return true;
	}
}
