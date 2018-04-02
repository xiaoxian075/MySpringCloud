package com.scd.admin.controller.accept;

import com.scd.joggle.constant.Type;
import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityAcceptMsg extends BaseAcceptMsg {
	private long id;
	
	/**
	 * 标题
	 */
	private String title;

	/**
	 * 类型 1：学习教育 2：美食分享 3：生活健康 4：健身健美
	 */
	private int type;
	
	/**
	 * url
	 */
	private String url;

	@Override
	public boolean check() {
		if (
				id < 0 ||
				title == null || title.length() == 0 || title.length() > 32 ||
				!Type.checkCommodityType(type) ||
				url == null || url.length() == 0 ) {
			return false;
		}
		return true;
	}
}
