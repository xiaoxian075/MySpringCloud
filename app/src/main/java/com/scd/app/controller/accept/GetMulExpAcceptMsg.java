package com.scd.app.controller.accept;

import java.util.List;

import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetMulExpAcceptMsg extends BaseAcceptMsg {
	
	private List<List<Long>> commodityIdList;
	private long addressId;

	@Override
	public boolean check() {
		if (commodityIdList == null || commodityIdList.size() == 0 || addressId <= 0) {
			return false;
		}

		return true;
	}

}
