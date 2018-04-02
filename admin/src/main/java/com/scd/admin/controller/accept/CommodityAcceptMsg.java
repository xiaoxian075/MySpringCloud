package com.scd.admin.controller.accept;

import java.util.List;

import com.scd.joggle.constant.Type;
import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommodityAcceptMsg extends BaseAcceptMsg {
	
	private long id;
	private String shortTitle;
	private String fullTitle;
	private long shopId;
	private int shopProductType;
	private String showPic;
	private List<String> listPic;
	private long addrId;
	private String serviceItem;
	private int isFreeExp;

	@Override
	public boolean check() {
		if (	id < 0 ||
				shortTitle == null || shortTitle.trim().length() == 0 || shortTitle.trim().length() > 127 ||
				fullTitle == null || fullTitle.trim().length() == 0 || fullTitle.trim().length() > 255 ||
				shopId < 0 ||
				showPic == null || showPic.trim().length() == 0 || showPic.trim().length() > 255 ||
				listPic == null || listPic.size() == 0 ||
				addrId < 0 ||
				!Type.checkCommodityType(shopProductType) ||
				!Type.checkCommodityFree(isFreeExp)) {
			return false;
		}
		
		if (serviceItem.trim().length() == 0) {
			serviceItem = null;
		} else if (serviceItem.trim().length() > 255) {
			return false;
		}
		return true;
	}

}
