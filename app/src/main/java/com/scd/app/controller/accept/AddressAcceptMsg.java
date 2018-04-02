package com.scd.app.controller.accept;

import com.scd.joggle.constant.Type;
import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddressAcceptMsg extends BaseAcceptMsg {

	private long id;
	private String name;
	private String phone;
	private long addrId;
	private String addrDetail;
	private int isDefault;
	
	@Override
	public boolean check() {
		if (	id < 0 ||
				name == null || name.length() == 0 || name.length() >32 ||
				phone == null || phone.length() == 0 || phone.length() >16 ||
				addrId <= 0 ||
				addrDetail == null || addrDetail.length() == 0 || addrDetail.length() > 127 ||
				!Type.checkDefaultAddr(isDefault)) {
			return false;
		}
		
		return true;
	}

}
