package com.scd.app.controller.accept;

import java.util.List;

import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.bo.OrderGoodsBo;
import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SubmitOrderAcceptMsg extends BaseAcceptMsg {

	private String odd;
	private int type;
	private long addressId;
	private List<OrderGoodsBo> orderList;
	
	@Override
	public boolean check() {
		if (	odd == null || odd.length() == 0 || odd.length() > 32 ||
				!Type.checkOrderSubmitType(type) ||
				addressId <= 0 ||
				orderList == null || orderList.size() == 0) {
			return false;
		}
		
		return true;
	}

}

