package com.scd.admin.controller.accept;

import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RichAcceptMsg extends BaseAcceptMsg {

	private long type;
	private long id;
	private long linkId;
	private String data;
	//private String link;
	
	
	
	@Override
	public boolean check() {
		if (
				type <= 0 ||
				id <= 0 ||
				linkId < 0 ||
				data == null) {
			return false;
		}
		return true;
	}

}
