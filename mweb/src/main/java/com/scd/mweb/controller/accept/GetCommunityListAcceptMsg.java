package com.scd.mweb.controller.accept;


import com.scd.joggle.constant.Type;
import com.scd.sdk.util.pojo.H5BasePageAcceptMsg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCommunityListAcceptMsg extends H5BasePageAcceptMsg {

	/**
	 * 类型 1：学习教育 2：美食分享 3：生活健康 4：健身健美
	 */
	private int type;

	@Override
	public boolean check() {
		if (!Type.checkCommodityType(type)) {
			return false;
		}
		page = getPage(page);
		size = getSize(size);
		
		return true;
	}
}



